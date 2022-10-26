package br.com.geofusion.cart.models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

/**
 * Classe que representa o carrinho de compras de um cliente.
 */
@Entity
public class ShoppingCart {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long cartId;

	@Column(name = "clientId")
	private String clientId;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "Item_Cart", joinColumns = @JoinColumn(name = "cartId", insertable = false, updatable = false), inverseJoinColumns = @JoinColumn(name = "id", insertable = false, updatable = false))
	private List<Item> items;
	
	@ManyToMany(cascade = CascadeType.ALL)
	private List<Item> allItems;

	/**
	 * Construtor.
	 * 
	 * @param clientId
	 * @param items
	 */
	public ShoppingCart(String clientId, List<Item> items) {
		this.clientId = clientId;
		this.items = items;
	}

	public ShoppingCart() {
	}

	/**
	 * Permite a adição de um novo item no carrinho de compras.
	 *
	 * Caso o item já exista no carrinho para este mesmo produto, as seguintes
	 * regras deverão ser seguidas: - A quantidade do item deverá ser a soma da
	 * quantidade atual com a quantidade passada como parâmetro. - Se o valor
	 * unitário informado for diferente do valor unitário atual do item, o novo
	 * valor unitário do item deverá ser o passado como parâmetro.
	 *
	 * Devem ser lançadas subclasses de RuntimeException caso não seja possível
	 * adicionar o item ao carrinho de compras.
	 *
	 * @param product
	 * @param unitPrice
	 * @param quantity
	 */
	public void addItem(Product product, BigDecimal unitPrice, int quantity) {
		Item existsOnAllItems = this.getItemFromAllItems(product);

		if (existsOnAllItems == null) {
			throw new RuntimeException("Item not found!");
		} else {
			
			Item existsOnCart = this.getItemByProduct(product);
			
			if (existsOnCart == null) {
				this.items.add(existsOnAllItems);
			} else {
				existsOnCart.setQuantity(existsOnCart.getQuantity() + quantity);
				existsOnCart.setUnitPrice(unitPrice);
				
				int itemIndex = items.indexOf(existsOnCart);
				this.items.set(itemIndex, existsOnAllItems);
			}
		}
	}

	/**
	 * Permite a remoção do item que representa este produto do carrinho de compras.
	 *
	 * @param product
	 * @return Retorna um boolean, tendo o valor true caso o produto exista no
	 *         carrinho de compras e false caso o produto não exista no carrinho.
	 */
	public boolean removeItem(Product product) {
		Item itemToRemoved = this.getItemByProduct(product);
		
		if( itemToRemoved == null ) {
			return false;
		} else {
			this.items.remove(itemToRemoved);
			return true;
		}
	}

	/**
	 * Permite a remoção do item de acordo com a posição. Essa posição deve ser
	 * determinada pela ordem de inclusão do produto na coleção, em que zero
	 * representa o primeiro item.
	 *
	 * @param itemIndex
	 * @return Retorna um boolean, tendo o valor true caso o produto exista no
	 *         carrinho de compras e false caso o produto não exista no carrinho.
	 */
	public boolean removeItem(int itemIndex) {
		try {
			this.items.remove(itemIndex);
		} catch (Exception e) {
			return false;
		}

		return true;
	}

	/**
	 * Retorna o valor total do carrinho de compras, que deve ser a soma dos valores
	 * totais de todos os itens que compõem o carrinho.
	 *
	 * @return BigDecimal
	 */
	public BigDecimal getAmount() {
		BigDecimal totalAmount = new BigDecimal(0);

		for (Item item : this.items) {
			BigDecimal itemAmount = item.getAmount();

			totalAmount = totalAmount.add(itemAmount);
		}

		return totalAmount;
	}

	/**
	 * Retorna a lista de itens do carrinho de compras.
	 *
	 * @return items
	 */
	public Collection<Item> getItems() {
		return this.items;
	}

	private Item getItemByProduct(Product product) {
		for (Item item : this.items) {
			if(product.getCode() == item.getProduct().getCode()) {
				return item;
			}
		}
		return null;
	}
	
	private Item getItemFromAllItems(Product product) {
		for (Item item : this.allItems) {
			if(product.getCode() == item.getProduct().getCode()) {
				return item;
			}
		}
		return null;
	}

	/* Getters e Setters */

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientId() {
		return clientId;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

//	public Long getCartId() {
//		return cartId;
//	}

	public void setAllItems(List<Item> allItems) {
		this.allItems = allItems;
	}

}
