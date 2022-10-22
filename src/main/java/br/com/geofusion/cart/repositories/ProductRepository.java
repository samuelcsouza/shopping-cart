package br.com.geofusion.cart.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.geofusion.cart.models.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
