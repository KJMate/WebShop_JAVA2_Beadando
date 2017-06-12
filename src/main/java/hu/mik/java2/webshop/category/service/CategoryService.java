package hu.mik.java2.webshop.category.service;

import java.util.List;
import hu.mik.java2.webshop.category.bean.Category;


public interface CategoryService {
	
	public List<Category> listCategories();
	
	public Category save(Category category);
	
	public void delete(Category category);
}
