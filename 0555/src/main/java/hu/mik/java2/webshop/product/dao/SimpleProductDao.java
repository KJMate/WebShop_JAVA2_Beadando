package hu.mik.java2.webshop.product.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import hu.mik.java2.webshop.product.bean.Product;

public interface SimpleProductDao extends JpaRepository<Product, Integer>{
	
	public List<Product> findByCategoryIdLike(String categoryId);
	
	public Product findByproductNameLike(String productName);
	
	public List<Product> findBydiscountGreaterThanEqual(Integer discountVal);
}
