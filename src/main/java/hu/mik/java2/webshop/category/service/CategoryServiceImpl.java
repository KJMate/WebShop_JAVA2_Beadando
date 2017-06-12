package hu.mik.java2.webshop.category.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.mik.java2.webshop.category.bean.Category;
import hu.mik.java2.webshop.category.dao.SimpleCategoryDao;

@Service("categoryServiceImpl")
public class CategoryServiceImpl implements CategoryService{

	@Autowired
	private SimpleCategoryDao catDao;
	
	@Override
	public List<Category> listCategories() {
		return this.catDao.findAll();
	}

	@Override
	public Category save(Category category) {
		return this.catDao.save(category);
	}

	@Override
	public void delete(Category category) {
		this.catDao.delete(category);
		
	}

}
