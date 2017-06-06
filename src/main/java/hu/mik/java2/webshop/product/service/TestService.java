package hu.mik.java2.webshop.product.service;

import org.springframework.stereotype.Component;

@Component
public class TestService {
	public String sayHello(String name) {
		return "Hello " + name + "!";
	}
}
