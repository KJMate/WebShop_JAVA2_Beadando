package hu.mik.java2.webshop.user.dao;


import java.util.List;

import hu.mik.java2.webshop.user.bean.User;


public interface UserDao {
	
	public User findUserByName(String userName);
	
	public User save(User user);
	
	public List<User> findAll();

}
