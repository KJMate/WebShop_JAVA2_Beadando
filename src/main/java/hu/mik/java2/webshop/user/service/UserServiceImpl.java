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
	public List<User>listUsersByUserName(String username) {
		return this.userDao.findByusernameLike(username);
	}

//	@Override
//	public User createNewUser(User user) {
//		return this.userDao.createUser(user);
//	}



}
