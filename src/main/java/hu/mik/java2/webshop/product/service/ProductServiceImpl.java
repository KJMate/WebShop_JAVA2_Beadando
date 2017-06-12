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
	public List<Product> listProductsByCategoryId(Integer categoryId) {
		return this.productDao.findByCategoryIdLike(categoryId);
	}
	
	@Override
	public Product listProductByProductName(String productName) {
		return this.productDao.findByproductNameLike(productName);
	}

	@Override
	public List<Product> listProductByDiscount(Integer discountVal) {
		return this.productDao.findBydiscountGreaterThanEqual(discountVal);
	}

	@Override
	public Product save(Product product) {
		return this.productDao.save(product);
	}

	@Override
	public void deleteProduct(Product product) {
		this.productDao.delete(product);
	}
	
}
