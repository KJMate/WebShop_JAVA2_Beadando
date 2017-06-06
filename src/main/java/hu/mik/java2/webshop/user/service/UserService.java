package hu.mik.java2.webshop.user.service;


import java.util.List;
import hu.mik.java2.webshop.user.bean.User;

public interface UserService {
	
	//public User createNewUser(User user);

	public List<User> listUsersByUserName(String userName);

}
