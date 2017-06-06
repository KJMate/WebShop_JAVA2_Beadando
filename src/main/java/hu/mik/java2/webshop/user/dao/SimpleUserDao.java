package hu.mik.java2.webshop.user.dao;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import hu.mik.java2.webshop.user.bean.User;

public interface SimpleUserDao extends JpaRepository<User, Integer>{
	
	//public User createUser(User user);
	
	public List<User> findByusernameLike(String username);

}
