package hu.mik.java2.webshop.shoppingcart.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.mik.java2.webshop.product.bean.Product;
import hu.mik.java2.webshop.shoppingcart.bean.ShoppingCart;
import hu.mik.java2.webshop.shoppingcart.dao.SimpleShoppingCartDao;

@Service("shoppingCartServiceImpl")
public class ShoppingCartServiceImpl implements ShoppingCartService{

	@Autowired
	private SimpleShoppingCartDao shoppingDao;
	
	@Override
	public ShoppingCart save(ShoppingCart shoppingCart) {
		return this.shoppingDao.save(shoppingCart);
	}

	@Override
	public List<Product> findPruductById(Integer id) {
		return this.shoppingDao.findPruductById(id);
	}

	@Override
	public List<ShoppingCart> findAll() {
		return this.shoppingDao.findAll();
	}

	@Override
	public List<ShoppingCart> findByIdLike(Integer id) {
		return this.shoppingDao.findByIdLike(id);
	}

	@Override
	public void delete(ShoppingCart shoppingCart) {
		this.shoppingDao.delete(shoppingCart);
		
	}

}
