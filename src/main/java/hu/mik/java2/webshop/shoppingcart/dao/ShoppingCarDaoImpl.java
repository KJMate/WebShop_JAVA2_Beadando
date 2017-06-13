package hu.mik.java2.webshop.shoppingcart.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import hu.mik.java2.webshop.product.bean.Product;
import hu.mik.java2.webshop.shoppingcart.bean.ShoppingCart;


@Repository
@Transactional(propagation = Propagation.REQUIRED)
public class ShoppingCarDaoImpl implements ShoppingCartDao {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<Product> findPruductById(Integer id) {
		return this.entityManager
				.createQuery("SELECT p FROM Product p WHERE p.category_id LIKE :id", Product.class)
				.setParameter("category_id", id)
				.getResultList();
	}

	@Override
	public ShoppingCart save(ShoppingCart shoppingCart) {
		return this.entityManager.merge(shoppingCart);
	}

	@Override
	public List<ShoppingCart> findAll() {
		return this.entityManager
				.createQuery("SELECT p FROM ShoppingCart p", ShoppingCart.class)
				.getResultList();
	}

	@Override
	public List<ShoppingCart> findByIdLike(Integer id) {
		return this.entityManager
				.createQuery("SELECT p FROM ShoppingCart p WHERE p.user_id LIKE :id", ShoppingCart.class)
				.setParameter("category_id", id)
				.getResultList();
	}

	@Override
	public void delete(ShoppingCart shoppingCart) {
		this.entityManager.remove(shoppingCart);
		
	}
	
	

}
