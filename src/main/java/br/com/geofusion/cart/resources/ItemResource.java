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

import br.com.geofusion.cart.models.Item;
import br.com.geofusion.cart.models.Product;
import br.com.geofusion.cart.repositories.ItemRepository;

@RestController
@RequestMapping(path = "/item")
public class ItemResource {
	
	private ItemRepository itemRepository;
	
	public ItemResource(ItemRepository itemRepository) {
		this.itemRepository = itemRepository;
	}
	
	@PostMapping
	public ResponseEntity<Item> save(@RequestBody Item item){
		
		this.itemRepository.save(item);
		return new ResponseEntity<>(item, HttpStatus.CREATED);
	}
	
	@GetMapping()
	public ResponseEntity<List<Item>> listAll(){
		List<Item> items = new ArrayList<Item>();
		
		items = this.itemRepository.findAll();
		return new ResponseEntity<>(items, HttpStatus.OK);
	}
	
	@GetMapping(path = "/{id}")
	public ResponseEntity<Optional<Item>> getById(@PathVariable Long id){
		Optional<Item> item;
		
		try {
			item = this.itemRepository.findById(id);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<Optional<Item>>(HttpStatus.NOT_FOUND);
		}
		
		if (item.isEmpty())
			return new ResponseEntity<Optional<Item>>(HttpStatus.NOT_FOUND);
		
		return new ResponseEntity<Optional<Item>>(item, HttpStatus.OK);
		
	}
	
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<Optional<Item>> deleteById(@PathVariable Long id){
		try {
			this.itemRepository.deleteById(id);
			return new ResponseEntity<Optional<Item>>(HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<Optional<Item>>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping(path = "/{id}")
	public ResponseEntity<Item> update(@PathVariable Long id, @RequestBody Item newItem){		
		return this.itemRepository.findById(id)
				.map(item -> {
					item = new Item(
							new Product(
									item.getProduct().getCode(),
									item.getProduct().getDescription()),
							newItem.getUnitPrice(),
							item.getQuantity());
					item.setId(id);
					Item itemUpdated = this.itemRepository.save(item);
					return ResponseEntity.ok().body(itemUpdated);
				}).orElse(ResponseEntity.notFound().build());
	}

}
