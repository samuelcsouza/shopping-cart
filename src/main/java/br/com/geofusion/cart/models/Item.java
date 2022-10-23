package br.com.geofusion.cart.models;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.com.geofusion.cart.factories.ShoppingCartFactory;

/**
 * Classe que representa um item no carrinho de compras.
 */
@Entity
public class Item {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne()
	@JoinColumn(name = "code")
	private Product product;
	
	@Column(name = "unitPrice")
	private BigDecimal unitPrice;
	
	@Column(name = "quantity")
	private int quantity;
	
	@ManyToOne
	@JoinColumn(name="clientId", nullable = true)
	private ShoppingCart shoppingCart;

	/**
     * Construtor da classe Item.
     *
     * @param product
     * @param unitPrice
     * @param quantity
     */
    public Item(Product product, BigDecimal unitPrice, int quantity) {
    	this.product = product;
		this.unitPrice = unitPrice;
		this.quantity = quantity;
    }
    
    public Item() {
    }

    /**
     * Retorna o produto.
     *
     * @return Produto
     */
    public Product getProduct() {
        return this.product;
    }

    /**
     * Retorna o valor unitário do item.
     *
     * @return BigDecimal
     */
    public BigDecimal getUnitPrice() {
        return this.unitPrice;
    }

    /**
     * Retorna a quantidade dos item.
     *
     * @return int
     */
    public int getQuantity() {
        return this.quantity;
    }

    /**
     * Retorna o valor total do item.
     *
     * @return BigDecimal
     */
    public BigDecimal getAmount() {
    	BigDecimal quantity = new BigDecimal(this.quantity);
    	
        return quantity.multiply(this.unitPrice);
    }

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
}
