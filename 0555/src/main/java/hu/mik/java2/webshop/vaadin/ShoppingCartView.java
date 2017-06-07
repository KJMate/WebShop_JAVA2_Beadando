package hu.mik.java2.webshop.vaadin;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.renderers.NumberRenderer;

import hu.mik.java2.webshop.product.bean.Product;
import hu.mik.java2.webshop.product.service.ProductService;
import hu.mik.java2.webshop.user.bean.User;
import hu.mik.java2.webshop.user.service.UserService;

@SuppressWarnings("serial")
@SpringView(name = ShoppingCartView.VIEW_NAME)
public class ShoppingCartView extends VerticalLayout implements View {
	public static final String VIEW_NAME = "cart";
	
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	
	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;
	
	private Grid<Product> productsGV;
	
	@PostConstruct
	void init() {
		Page.getCurrent().setTitle("Web Shop - Shopping Cart");
		addComponent(new Label("Shopping Cart"));
		
		User user = userService.listUsersByUserName("david");
		String savedProducts = user.getSavedProducts();
		String[] productNames = savedProducts.split(";");
		
		if(productNames.length > 0) {
			productsGV = createSavedProductsGV(productNames);
			productsGV.setSizeFull();
			this.addComponent(productsGV);
		} else {
			Notification.show("Még nem mentettél egy terméket sem!");
		}
	}

	private Grid<Product> createSavedProductsGV(String[] products) {
		List<Product> savedProducts = new ArrayList<>();
		Product productBuffer = null;
		Grid<Product> grid = new Grid<>();
		for (String name : products) {
			productBuffer = productService.listProductByProductName(name);
		    savedProducts.add(productBuffer);
		}
		grid.setItems(savedProducts);
		
		grid.addColumn(Product::getId).setCaption("Azonosító").setHidden(true);
		grid.addColumn(Product::getCategoryId).setCaption("Kategória");
		grid.addColumn(Product::getProductName).setCaption("Név");
		grid.addColumn(Product::getDescription).setCaption("Leírás");
		grid.addColumn(Product::getPrice, new NumberRenderer("%d Ft")).setCaption("Ár");
		grid.addColumn(Product::getDiscount, new NumberRenderer("%d %%")).setCaption("Kedvezmény");
		grid.getSelectionModel().setUserSelectionAllowed(false);
		return grid;
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		// nothing to do here!!!
	}
}