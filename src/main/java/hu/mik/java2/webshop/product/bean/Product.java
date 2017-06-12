package hu.mik.java2.webshop.product.bean;

import java.io.Serializable;
import javax.persistence.*;
import hu.mik.java2.webshop.category.bean.Category;

@Entity
@Table(name = "t_products")
public class Product implements Serializable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GEN")
	@SequenceGenerator(name = "SEQ_GEN", sequenceName = "T_PROD_SEQ", allocationSize = 1, initialValue = 50)
	private Integer id;
	
	
	@Column(name = "product_name")
	private String productName;
	
	@Column(name = "price")
	private Integer price;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "discount")
	private Integer discount;
	
	@JoinColumn(name = "category_id", referencedColumnName = "category_id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
	private Category category;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	
	@Override
	public String toString() {
		return category.getCategory_name();
	}
	
}
