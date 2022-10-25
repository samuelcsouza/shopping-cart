package br.com.geofusion.cart.resources;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.geofusion.cart.factories.ShoppingCartFactory;
import br.com.geofusion.cart.models.Item;
import br.com.geofusion.cart.models.ShoppingCart;
import br.com.geofusion.cart.repositories.ShoppingCartRepository;

@RestController
@RequestMapping(path = "/cart")
public class ShoppingCartResource {

	private ShoppingCartFactory factory = new ShoppingCartFactory();

	public ShoppingCartResource(ShoppingCartRepository shoppingCartRepository) {
		this.factory.setShoppingCartRepository(shoppingCartRepository);
	}

	@PostMapping
	public ResponseEntity<ShoppingCart> save(@RequestBody ShoppingCart cart) {
//		List<ShoppingCart> carts = this.shoppingCartRepository.findAll();
//		String clientId = cart.getClientId();
//		
//		for (ShoppingCart shoppingCart : carts) {
//			if (shoppingCart.getClientId().equalsIgnoreCase(clientId)) {
//				return new ResponseEntity<>(shoppingCart, Http)
//			}
//		}
//		
//		this.shoppingCartRepository.save(cart);
		ShoppingCart newCart = this.factory.create(cart.getClientId());
		return new ResponseEntity<>(newCart, HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<List<ShoppingCart>> listAll() {
//		List<ShoppingCart> carts;
//		carts = this.shoppingCartRepository.findAll();
		List<ShoppingCart> allCarts = this.factory.listAll();
		return new ResponseEntity<>(allCarts, HttpStatus.OK);
	}
	
//	@PatchMapping(path = "/{clientId}")
//	public ResponseEntity<Object> addItems(@PathVariable String clientId, @RequestBody Item []itens){
//		ShoppingCart updatedCart = this.factory.updateCart(clientId, itens);
//		
//		if(updatedCart == null) {
//			return new ResponseEntity<>(new String("Cart not found!"), HttpStatus.NOT_FOUND);
//		}
//		
//		return new ResponseEntity<>(updatedCart, HttpStatus.OK);
//	}

//	@GetMapping(path = "/{id}")
//	public ResponseEntity<Optional<ShoppingCart>> getById(@PathVariable Long id) {
//		Optional<ShoppingCart> shoppingCart;
//
//		try {
//			shoppingCart = this.shoppingCartRepository.findById(id);
//		} catch (Exception e) {
//			return new ResponseEntity<Optional<ShoppingCart>>(HttpStatus.NOT_FOUND);
//		}
//
//		if (shoppingCart.isEmpty())
//			return new ResponseEntity<Optional<ShoppingCart>>(HttpStatus.NOT_FOUND);
//
//		return new ResponseEntity<Optional<ShoppingCart>>(shoppingCart, HttpStatus.OK);
//	}

}
