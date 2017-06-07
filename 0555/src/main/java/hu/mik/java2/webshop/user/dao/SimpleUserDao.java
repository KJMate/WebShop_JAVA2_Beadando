package hu.mik.java2.webshop.user.dao;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hu.mik.java2.webshop.user.bean.User;

@Repository
public interface SimpleUserDao extends JpaRepository<User, Integer>{
		
	@SuppressWarnings("unchecked")
	public User save(User user);
	
	public User findByusername(String username);
	
	public List<User> findAll();

}
