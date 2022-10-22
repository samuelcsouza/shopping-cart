package br.com.geofusion.cart.resources;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.geofusion.cart.models.Product;
import br.com.geofusion.cart.repositories.ProductRepository;

@RestController
@RequestMapping(path = "/product")
public class ProductResource {

	private ProductRepository productRepository;
	
	public ProductResource(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}
	
	@PostMapping
	public ResponseEntity<Product> save(@RequestBody Product product){
		
		this.productRepository.save(product);
		return new ResponseEntity<>(product, HttpStatus.CREATED);
	}
	
	@GetMapping()
	public ResponseEntity<List<Product>> listAll(){
		List<Product> products = new ArrayList<Product>();
		
		products = this.productRepository.findAll();
		return new ResponseEntity<>(products, HttpStatus.OK);
	}
	
	@GetMapping(path = "/{id}")
	public ResponseEntity<Optional<Product>> getById(@PathVariable Long id){
		Optional<Product> product;
		
		try {
			product = this.productRepository.findById(id);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<Optional<Product>>(HttpStatus.NOT_FOUND);
		}
		
		if (product.isEmpty())
			return new ResponseEntity<Optional<Product>>(HttpStatus.NOT_FOUND);
		
		return new ResponseEntity<Optional<Product>>(product, HttpStatus.OK);
		
	}
	
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<Optional<Product>> deleteById(@PathVariable Long id){
		try {
			this.productRepository.deleteById(id);
			return new ResponseEntity<Optional<Product>>(HttpStatus.OK);
		} catch (NoSuchElementException nsee) {
			return new ResponseEntity<Optional<Product>>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping(path = "/{id}")
	public ResponseEntity<Product> update(@PathVariable Long id, @RequestBody Product newProduct){
		return this.productRepository.findById(id)
				.map(product -> {
					product = new Product(id, newProduct.getDescription());
					Product productUpdated = this.productRepository.save(product);
					return ResponseEntity.ok().body(productUpdated);
				}).orElse(ResponseEntity.notFound().build());
	}
}
