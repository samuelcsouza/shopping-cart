package br.com.geofusion.cart.resources;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.geofusion.cart.models.ShoppingCart;
import br.com.geofusion.cart.repositories.ShoppingCartRepository;

@RestController
@RequestMapping(path = "/cart")
public class ShoppingCartResource {

	private ShoppingCartRepository shoppingCartRepository;

	public ShoppingCartResource(ShoppingCartRepository shoppingCartRepository) {
		this.shoppingCartRepository = shoppingCartRepository;
	}

	@PostMapping
	public ResponseEntity<ShoppingCart> save(@RequestBody ShoppingCart cart) {
		this.shoppingCartRepository.save(cart);
		return new ResponseEntity<>(cart, HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<List<ShoppingCart>> listAll() {
		List<ShoppingCart> carts;

		carts = this.shoppingCartRepository.findAll();
		return new ResponseEntity<>(carts, HttpStatus.OK);
	}

	@GetMapping(path = "/{id}")
	public ResponseEntity<Optional<ShoppingCart>> getById(@PathVariable Long id) {
		Optional<ShoppingCart> shoppingCart;

		try {
			shoppingCart = this.shoppingCartRepository.findById(id);
		} catch (Exception e) {
			return new ResponseEntity<Optional<ShoppingCart>>(HttpStatus.NOT_FOUND);
		}

		if (shoppingCart.isEmpty())
			return new ResponseEntity<Optional<ShoppingCart>>(HttpStatus.NOT_FOUND);

		return new ResponseEntity<Optional<ShoppingCart>>(shoppingCart, HttpStatus.OK);
	}

}
