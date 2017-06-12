package hu.mik.java2.webshop.category.dao;

import java.util.List;
import hu.mik.java2.webshop.category.bean.Category;


public interface CategoryDao {
	
	public List<Category> findAll();
	
	public Category save(Category category);
	
	public void delete(Category category);
}
