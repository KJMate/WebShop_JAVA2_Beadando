package hu.mik.java2.webshop.product.dao;

import java.util.List;
import hu.mik.java2.webshop.product.bean.Product;

public interface ProductDao {
	
	public List<Product> findAll();
	
	public List<Product> findByCategoryLike(Integer categoryId);

	public Product findByproductNameLike(String productName);
	
	public List<Product> findBydiscountGreaterThanEqual(Integer discountVal);
	
	public Product save(Product product);
	
	public void delete(Product product);
	
	public Product findByIdLike(Integer id);
}
