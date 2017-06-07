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
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
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
		Label noProductLabel = new Label("Még nem mentettél egy terméket sem!");
		Page.getCurrent().setTitle("Web Shop - " + actualUser.getLast_name() + " " + actualUser.getFirst_name());
		final VerticalLayout root = new VerticalLayout();
		root.setMargin(false);

		Label lblTitle = new Label(actualUser.getLast_name() + " " + actualUser.getFirst_name() + " ("
				+ actualUser.getUsername() + ") profilja");
		lblTitle.setStyleName(ValoTheme.LABEL_H1);
		lblTitle.addStyleName(ValoTheme.LABEL_COLORED);
		root.addComponent(lblTitle);
		String savedProducts = actualUser.getSavedProducts();

		if (savedProducts == null) {

			root.addComponent(noProductLabel);
			root.setComponentAlignment(noProductLabel, Alignment.MIDDLE_CENTER);

		} else {

			productNames = savedProducts.split(";");

			// if(productNames.length > 0) {
			productsGV = createSavedProductsGV(productNames);
			productsGV.setSizeFull();
			root.addComponent(productsGV);
			root.setComponentAlignment(productsGV, Alignment.MIDDLE_CENTER);
			// }
		}

		root.setComponentAlignment(lblTitle, Alignment.TOP_CENTER);

		payment();
		signOut();

		this.addComponent(root);
	}

	private void signOut() {
		Button signOut = new Button("Kijelentkezés");

		signOut.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {		
				
				actualUser = null;
				SignInView.setActualUser(null);
				getUI().getNavigator().navigateTo(DefaultView.VIEW_NAME);
			}
		});
		this.addComponent(signOut);
		this.setComponentAlignment(signOut, Alignment.BOTTOM_RIGHT);
	}

	private void payment() {
		Button paymentBtn = new Button("Fizetés");

		paymentBtn.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				if (actualUser.getSavedProducts() != null) {
					getUI().getNavigator().navigateTo(PaymentView.VIEW_NAME);
				} else {
					Notification.show("Önnek nincsenek mentett termékei");
				}

			}
		});
		this.addComponent(paymentBtn);
		this.setComponentAlignment(paymentBtn, Alignment.BOTTOM_RIGHT);
		
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
		// nothing to do here.
	}

	public static User getUser() {
		return actualUser;
	}

}
