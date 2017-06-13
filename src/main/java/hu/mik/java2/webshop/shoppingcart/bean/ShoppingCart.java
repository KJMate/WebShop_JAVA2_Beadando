package hu.mik.java2.webshop.shoppingcart.bean;
import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import hu.mik.java2.webshop.product.bean.Product;
import hu.mik.java2.webshop.user.bean.User;

@Entity
@Table(name = "t_shopping_cart")
public class ShoppingCart implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GEN")
	@SequenceGenerator(name = "SEQ_GEN", sequenceName = "s_shopping_cart", allocationSize = 1, initialValue = 50)
	private Integer id;
	
	@Column(name="user_id")
	private Integer user_id;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "id", fetch = FetchType.EAGER)
	private List<User> users;
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="product_id")
	private Product product;
	
	
	@Column(name = "ISPAID")
	private Integer isPaid;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Integer getIsPaid() {
		return isPaid;
	}

	public void setIsPaid(Integer isPaid) {
		this.isPaid = isPaid;
	}

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

}
