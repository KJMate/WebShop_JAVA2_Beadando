package hu.mik.java2.webshop.product.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import hu.mik.java2.webshop.product.bean.Product;

@Repository
@Transactional(propagation = Propagation.REQUIRED)
public class ProductDaoImpl implements ProductDao {

	@PersistenceContext
	private EntityManager entityManager;
	
	
	@Override
	public List<Product> findAll() {
		return this.entityManager
				.createQuery("SELECT p FROM Product p", Product.class)
				.getResultList();
	}

	@Override
	public List<Product> findByCategoryLike(Integer categoryId) {
		return this.entityManager
				.createQuery("SELECT p FROM Product p WHERE p.category_id LIKE :categoryId", Product.class)
				.setParameter("category_id", categoryId)
				.getResultList();
	}
	
	@Override
	public Product findByproductNameLike(String product_name) {
		return this.entityManager
				.createQuery("SELECT DISTINCT p.product_name, p.price FROM Product p WHERE p.product_name LIKE :product_name", Product.class)
				.setParameter("product_name", product_name)
				.getSingleResult();
	}

	@Override
	public List<Product> findBydiscountGreaterThanEqual(Integer discountV) {
		return this.entityManager
				.createQuery("SELECT p FROM Product p WHERE p.discount >= :discountV", Product.class)
				.setParameter("discount", discountV)
				.getResultList();
	}

	@Override
	public Product save(Product product) {
			return this.entityManager.merge(product);
	}

	@Override
	public void delete(Product product) {
		if(!this.entityManager.contains(product)) {
			this.entityManager.merge(product);
		}
		this.entityManager.remove(product);
	}

}
