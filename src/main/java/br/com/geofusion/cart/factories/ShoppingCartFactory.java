package br.com.geofusion.cart.factories;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import br.com.geofusion.cart.models.ShoppingCart;
import br.com.geofusion.cart.repositories.ShoppingCartRepository;

/**
 * Classe responsável pela criação e recuperação dos carrinhos de compras.
 */
@Service
public class ShoppingCartFactory {

	private ShoppingCartRepository shoppingCartRepository;
	
	/**
     * Cria e retorna um novo carrinho de compras para o cliente passado como parâmetro.
     *
     * Caso já exista um carrinho de compras para o cliente passado como parâmetro, este carrinho deverá ser retornado.
     *
     * @param clientId
     * @return ShoppingCart
     */
    public ShoppingCart create(String clientId) {
    	
    	ShoppingCart shoppingCart = this.shoppingCartRepository.findByClientId(clientId);
    	
    	if (shoppingCart == null) {
    		ShoppingCart newCart = new ShoppingCart(clientId, new ArrayList<>());
    		return this.shoppingCartRepository.save(newCart);
    	}
    	
    	return shoppingCart;
    }

    /**
     * Retorna o valor do ticket médio no momento da chamada ao método.
     * O valor do ticket médio é a soma do valor total de todos os carrinhos de compra dividido
     * pela quantidade de carrinhos de compra.
     * O valor retornado deverá ser arredondado com duas casas decimais, seguindo a regra:
     * 0-4 deve ser arredondado para baixo e 5-9 deve ser arredondado para cima.
     *
     * @return BigDecimal
     */
    public BigDecimal getAverageTicketAmount() {
    	 List<ShoppingCart> allCarts = shoppingCartRepository.findAll();
         if (allCarts.isEmpty()) {
             return new BigDecimal(0);
         }
         BigDecimal result = allCarts
                 .stream()
                 .reduce(new BigDecimal(0), (sum, ob) -> sum.add(ob.getAmount()), BigDecimal::add);

         return result.divide(new BigDecimal(allCarts.size()), RoundingMode.HALF_UP);
    }

    /**
     * Invalida um carrinho de compras quando o cliente faz um checkout ou sua sessão expirar.
     * Deve ser efetuada a remoção do carrinho do cliente passado como parâmetro da listagem de carrinhos de compras.
     *
     * @param clientId
     * @return Retorna um boolean, tendo o valor true caso o cliente passado como parämetro tenha um carrinho de compras e
     * e false caso o cliente não possua um carrinho.
     */
    public boolean invalidate(String clientId) {
        ShoppingCart clientCart = this.shoppingCartRepository.findByClientId(clientId);
        
        if( clientCart == null ) {
        	return false;
        }
        
        this.shoppingCartRepository.deleteById(clientCart.getCartId());
        return true;
    }

    public List<ShoppingCart> listAll(){
    	return this.shoppingCartRepository.findAll();
    }
    
	public ShoppingCartRepository getShoppingCartRepository() {
		return shoppingCartRepository;
	}

	public void setShoppingCartRepository(ShoppingCartRepository shoppingCartRepository) {
		this.shoppingCartRepository = shoppingCartRepository;
	}
    
}
