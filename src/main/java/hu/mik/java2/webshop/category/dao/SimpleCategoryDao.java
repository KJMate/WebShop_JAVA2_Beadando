package hu.mik.java2.webshop.category.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import hu.mik.java2.webshop.category.bean.Category;

public interface SimpleCategoryDao extends JpaRepository<Category, Integer> {
	
	@SuppressWarnings("unchecked")
	public Category save(Category category);
	
	public void delete(Category category);

}
