package hu.mik.java2.webshop.product.bean;

import javax.persistence.*;

@Entity
@Table(name = "t_products")
public class Product {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GEN")
	@SequenceGenerator(name = "SEQ_GEN", sequenceName = "productSeq", allocationSize = 1, initialValue = 50)
	private Integer id;
	
	@Column(name = "category_id")
	private String categoryId;
	@Column(name = "product_name")
	private String productName;
	@Column(name = "price")
	private Integer price;
	@Column(name = "description")
	private String description;
	@Column(name = "discount")
	private Integer discount;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getDiscount() {
		return discount;
	}
	public void setDiscount(Integer discount) {
		this.discount = discount;
	}
	
}
