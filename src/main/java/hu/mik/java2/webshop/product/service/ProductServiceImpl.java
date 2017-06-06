package hu.mik.java2.webshop.product.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.mik.java2.webshop.product.bean.Product;
import hu.mik.java2.webshop.product.dao.SimpleProductDao;

@Service("productServiceImpl")
public class ProductServiceImpl implements ProductService {

	@Autowired
	private SimpleProductDao productDao;

	@Override
	public List<Product> listProducts() {
		return this.productDao.findAll();
	}

	@Override
	public List<Product> listProductsByCategoryId(String categoryId) {
		return this.productDao.findByCategoryIdLike(categoryId);
	}
	
}
