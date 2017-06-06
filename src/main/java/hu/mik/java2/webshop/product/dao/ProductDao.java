package hu.mik.java2.webshop.product.dao;

import java.util.List;
import hu.mik.java2.webshop.product.bean.Product;

public interface ProductDao {
	
	public List<Product> findAll();
	
	public List<Product> findByCategoryLike(String categoryId);

}
