package hu.mik.java2.webshop.product.service;

import java.util.List;

import hu.mik.java2.webshop.product.bean.Product;

public interface ProductService {

	public List<Product> listProducts();

	public List<Product> listProductsByCategoryId(String categoryId);

	public Product listProductByProductName(String productName);

	public List<Product> listProductByDiscount(Integer discountVal);

}
