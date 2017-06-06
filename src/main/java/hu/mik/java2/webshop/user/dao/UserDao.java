package hu.mik.java2.webshop.user.dao;

import java.util.List;

import hu.mik.java2.webshop.user.bean.User;

public interface UserDao {
	
	public List<User> findUserByName(String userName);

	public User createNewUser(User user);

}
