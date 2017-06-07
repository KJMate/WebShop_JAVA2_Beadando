package hu.mik.java2.webshop.user.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import hu.mik.java2.webshop.user.bean.User;
import hu.mik.java2.webshop.user.dao.SimpleUserDao;

@Service("userServiceImpl")
public class UserServiceImpl implements UserService {
	
	@Autowired
	private SimpleUserDao userDao;

	@Override
	public User listUsersByUserName(String username) {
		return this.userDao.findByusername(username);
	}

	@Override
	public User save(User user) {
		return this.userDao.save(user);
	}

	@Override
	public List<User> listUsers() {
		return this.userDao.findAll();
	}



}
