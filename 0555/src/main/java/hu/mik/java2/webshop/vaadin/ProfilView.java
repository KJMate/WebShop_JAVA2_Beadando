package hu.mik.java2.webshop.vaadin;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import com.vaadin.annotations.Theme;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.renderers.NumberRenderer;
import com.vaadin.ui.themes.ValoTheme;
import hu.mik.java2.webshop.product.bean.Product;
import hu.mik.java2.webshop.product.service.ProductService;
import hu.mik.java2.webshop.user.bean.User;

@Theme("valo")
@SuppressWarnings("serial")
@SpringView(name = ProfilView.VIEW_NAME)
public class ProfilView extends VerticalLayout implements View {

	public static final String VIEW_NAME = "myprofile";
	
	public static User actualUser = null;
	
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	
	private Grid<Product> productsGV;
	String[] productNames;
	
	@PostConstruct
	void init() {
		actualUser = SignInView.getUser();
		
		Page.getCurrent().setTitle("Web Shop - " + actualUser.getLast_name() + " " + actualUser.getFirst_name());
		final VerticalLayout root = new VerticalLayout();
		root.setMargin(false);
		
		Label lblTitle = new Label(actualUser.getLast_name() + " " + actualUser.getFirst_name() + " (" + actualUser.getUsername() + ") profilja");
		lblTitle.setStyleName(ValoTheme.LABEL_H1);
		lblTitle.addStyleName(ValoTheme.LABEL_COLORED);
		
		String savedProducts = actualUser.getSavedProducts();
		
		if(savedProducts.equals("")){
			Notification.show("Még nem mentettél egy terméket sem!");
		}else{
			productNames = savedProducts.split(";");
		root.addComponent(lblTitle);
		if(productNames.length > 0) {
			productsGV = createSavedProductsGV(productNames);
			productsGV.setSizeFull();
			root.addComponent(productsGV);
		} 
		}
		
		root.setComponentAlignment(lblTitle, Alignment.TOP_CENTER);
		root.setComponentAlignment(productsGV, Alignment.MIDDLE_CENTER);
		
		this.addComponent(root);
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
		//nothing to do here.
	}

	public static User getUser() {
		return actualUser;
	}
	
}
