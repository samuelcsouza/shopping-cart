package br.com.geofusion.cart.models;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

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
	
	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "unitPrice")
	private BigDecimal unitPrice;
	
	@Column(name = "quantity")
	private int quantity;

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
}
