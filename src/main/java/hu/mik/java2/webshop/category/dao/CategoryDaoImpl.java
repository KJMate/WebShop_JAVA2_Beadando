package hu.mik.java2.webshop.category.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import hu.mik.java2.webshop.category.bean.Category;


@Repository
@Transactional(propagation = Propagation.REQUIRED)
public class CategoryDaoImpl implements CategoryDao{

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<Category> findAll() {
		return this.entityManager
				.createQuery("SELECT c FROM Category c", Category.class)
				.getResultList();
	}

	@Override
	public Category save(Category category) {
		return this.entityManager.merge(category);
	}

	@Override
	public void delete(Category category) {
		if(!this.entityManager.contains(category)) {
			this.entityManager.merge(category);
		}
		this.entityManager.remove(category);		
	}

}
