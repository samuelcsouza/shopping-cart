package br.com.geofusion.cart.resources;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.geofusion.cart.factories.ShoppingCartFactory;
import br.com.geofusion.cart.models.Item;
import br.com.geofusion.cart.models.Product;
import br.com.geofusion.cart.models.ShoppingCart;
import br.com.geofusion.cart.repositories.ItemRepository;
import br.com.geofusion.cart.repositories.ProductRepository;
import br.com.geofusion.cart.repositories.ShoppingCartRepository;

@RestController
@RequestMapping(path = "/cart")
public class ShoppingCartResource {

	private ShoppingCartFactory factory = new ShoppingCartFactory();
	private ProductRepository productRepository;
	private ItemRepository itemRepository;

	public ShoppingCartResource(
			ShoppingCartRepository shoppingCartRepository, 
			ProductRepository productRepository,
			ItemRepository itemRepository) 
	{
		this.factory.setShoppingCartRepository(shoppingCartRepository);
		this.productRepository = productRepository;
		this.itemRepository = itemRepository;
	}

	@PostMapping
	public ResponseEntity<ShoppingCart> save(@RequestBody ShoppingCart cart) {
		ShoppingCart newCart = this.factory.create(cart.getClientId());
		return new ResponseEntity<>(newCart, HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<List<ShoppingCart>> listAll() {
		List<ShoppingCart> allCarts = this.factory.listAll();
		return new ResponseEntity<>(allCarts, HttpStatus.OK);
	}
	
	@PostMapping(path = "/{clientId}")
	public ResponseEntity<Object> addItem(@PathVariable String clientId, @RequestBody Product product){
		ShoppingCart clientCart = this.factory.create(clientId);
		clientCart.setAllItems(this.itemRepository.findAll());
		
		Product productExists = this.productRepository.findById(product.getCode()).orElse(null);
		if( productExists == null ) {
			return new ResponseEntity<>(new String("Product not found!"), HttpStatus.NOT_FOUND);
		}
		
		Item itemExists = null;
		for (Item item : this.itemRepository.findAll()) {
			if( item.getProduct().getCode() == productExists.getCode() ) {
				itemExists = item;
				break;
			}
		}
		
		if( itemExists == null ) {
			return new ResponseEntity<>(new String("Item not found!"), HttpStatus.NOT_FOUND);
		}
		
		clientCart.addItem(productExists, itemExists.getUnitPrice(), itemExists.getQuantity());
		
		this.factory.updateCart(clientCart);
		
		return new ResponseEntity<>(clientCart, HttpStatus.OK);
	}
	
	@GetMapping(path = "/{clientId}/average-ticket-amount")
	public ResponseEntity<Object> getAverageTicketAmount(@PathVariable String clientId){
		return new ResponseEntity<>(this.factory.getAverageTicketAmount(), HttpStatus.OK);
	}
	
	@DeleteMapping(path = "/{clientId}")
	public ResponseEntity<Object> invalidateCart(@PathVariable String clientId){
		boolean isDeleted = this.factory.invalidate(clientId);
		
		if( isDeleted )
			return new ResponseEntity<>(new String("Cart deleted!"), HttpStatus.OK);
		
		return new ResponseEntity<>(new String("Cart not exists"), HttpStatus.NOT_FOUND);
	}
}
