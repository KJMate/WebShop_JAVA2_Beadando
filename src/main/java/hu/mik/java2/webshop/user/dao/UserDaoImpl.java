package hu.mik.java2.webshop.user.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import hu.mik.java2.webshop.user.bean.User;

@Repository
@Transactional(propagation = Propagation.REQUIRED)
public class UserDaoImpl implements UserDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public User createNewUser(User user) {
		if (user.getUsername() == null) {
			this.entityManager.persist(user);
			return user;
		} else {
			return this.entityManager.merge(user);
		}
	}

	@Override
	public List<User> findUserByName(String username) {

		return this.entityManager.createQuery("SELECT u FROM User u WHERE u.username LIKE :username", User.class)
				.setParameter("username", username).getResultList();

	}

}
