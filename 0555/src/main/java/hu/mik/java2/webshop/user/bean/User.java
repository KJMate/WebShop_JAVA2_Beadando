package hu.mik.java2.webshop.user.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@Entity
@Table(name="t_users")
public class User {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GEN")
	@SequenceGenerator(name = "SEQ_GEN", sequenceName = "s_user", allocationSize = 1, initialValue = 50)
	private Integer id;
	
	@Column(name = "username")
	private String username;
	
	@Column(name = "passwd")
	private String passwd;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "first_name")
	private String first_name;
	
	@Column(name = "last_name")
	private String last_name;
	
	@Column(name = "city")
	private String city;
	
	@Column(name = "street")
	private String street;
	
	@Column(name = "house_number")
	private Integer houseNumber;
	
	@Column(name = "postal_code")
	private Integer postalCode;
	
	@Column(name = "phone")
	private Integer phoneNumber;

	@Column(name = "saved_products")
	private String savedProducts;
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public Integer getHouseNumber() {
		return houseNumber;
	}

	public void setHouseNumber(Integer houseNumber) {
		this.houseNumber = houseNumber;
	}

	public Integer getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(Integer postalCode) {
		this.postalCode = postalCode;
	}

	public Integer getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(Integer phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getSavedProducts() {
		return savedProducts;
	}

	public void setSavedProducts(String savedProducts) {
		this.savedProducts = savedProducts;
	}

	@Override
	public String toString() {
		return "User [username=" + username + ", passwd=" + passwd + ", email=" + email + ", first_name=" + first_name
				+ ", last_name=" + last_name + ", city=" + city + ", street=" + street + ", houseNumber=" + houseNumber
				+ ", postalCode=" + postalCode + ", phoneNumber=" + phoneNumber + "]";
	}

}
