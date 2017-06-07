package hu.mik.java2.webshop.user.service;


import java.util.List;
import org.springframework.stereotype.Service;
import hu.mik.java2.webshop.user.bean.User;

@Service
public interface UserService {
	
	public User save(User user);

	public User listUsersByUserName(String userName);
	
	public List<User> listUsers();

}
