package hu.mik.java2.webshop.shoppingcart.dao;


import java.util.List;

import hu.mik.java2.webshop.product.bean.Product;
import hu.mik.java2.webshop.shoppingcart.bean.ShoppingCart;

public interface ShoppingCartDao {
	
	public List<Product> findPruductById(Integer id);
	
	public ShoppingCart save(ShoppingCart shoppingCart);
	
	public List<ShoppingCart> findAll();
	
	public List<ShoppingCart> findByIdLike(Integer id);
	
	public void delete(ShoppingCart shoppingCart);
}
