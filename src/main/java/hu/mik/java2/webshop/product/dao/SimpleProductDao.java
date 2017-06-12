package hu.mik.java2.webshop.product.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import hu.mik.java2.webshop.product.bean.Product;

public interface SimpleProductDao extends JpaRepository<Product, Integer>{
	
	@SuppressWarnings("unchecked")
	public Product save(Product product);
	
	public List<Product> findByCategoryIdLike(Integer categoryId);
	
	public Product findByproductNameLike(String productName);
	
	public List<Product> findBydiscountGreaterThanEqual(Integer discountVal);
}
