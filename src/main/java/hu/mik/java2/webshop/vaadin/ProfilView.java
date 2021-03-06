package hu.mik.java2.webshop.vaadin;

import java.util.ArrayList;
import java.util.Collection;
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
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.renderers.NumberRenderer;
import com.vaadin.ui.themes.ValoTheme;
import hu.mik.java2.webshop.product.bean.Product;
import hu.mik.java2.webshop.product.service.ProductService;
import hu.mik.java2.webshop.shoppingcart.bean.ShoppingCart;
import hu.mik.java2.webshop.shoppingcart.service.ShoppingCartService;
import hu.mik.java2.webshop.user.bean.User;
import hu.mik.java2.webshop.user.service.UserService;

@Theme("valo")
@SuppressWarnings("serial")
@SpringView(name = ProfilView.VIEW_NAME)
public class ProfilView extends VerticalLayout implements View {

	public static final String VIEW_NAME = "myprofile";

	public static User actualUser = null;

	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;

	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;

	@Autowired
	@Qualifier("shoppingCartServiceImpl")
	private ShoppingCartService shoppingCartService;

	private Grid<Product> productsGV;

	List<Product> termekLista = new ArrayList<>();
	List<ShoppingCart> bevKocsiLista;

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

		List<User> lista = userService.listUsers();
		bevKocsiLista = shoppingCartService.findAll();

		for (User user : lista) {
			if (user.getId() == actualUser.getId()) {
				for (ShoppingCart kocsi : bevKocsiLista) {
					if (kocsi.getUser_id() == actualUser.getId() && kocsi.getIsPaid() == 0) {
						termekLista.add(kocsi.getProduct());
					} else {
						root.addComponent(noProductLabel);
						root.setComponentAlignment(noProductLabel, Alignment.MIDDLE_CENTER);
					}
				}

			}
		}

		productsGV = createSavedProductsGV(termekLista);
		productsGV.setSizeFull();
		root.addComponent(productsGV);

		root.setComponentAlignment(lblTitle, Alignment.TOP_CENTER);

		payment();
		signOut();
		deleteProducts();
		history();
		this.addComponent(root);
	}

	private void history() {
		Button history = new Button("Előzmények");

		history.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				List<User> lista = userService.listUsers();
				for (User user : lista) {
					if (user.getId() == actualUser.getId()) {
						for (ShoppingCart kocsi : bevKocsiLista) {
							if (kocsi.getUser_id() == actualUser.getId()) {
								termekLista.add(kocsi.getProduct());
							} 
						}

					}
				}
				productsGV = createSavedProductsGVHistory(termekLista);
				productsGV.setSizeFull();
			addComponent(productsGV);

			}

		
		});
		this.addComponent(history);
		this.setComponentAlignment(history, Alignment.BOTTOM_RIGHT);
		
	}

	private void deleteProducts() {
		Button delete = new Button("Törlés a kosárból");

		delete.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				Collection<Product> selectedProducts = productsGV.getSelectedItems();
				for (ShoppingCart kocsi : bevKocsiLista) {
					for (Product product : selectedProducts) {
						if (kocsi.getProduct().getId() == product.getId() && kocsi.getIsPaid() == 0) {

							shoppingCartService.delete(kocsi);
						}
					}

				}

			}
		});
		this.addComponent(delete);
		this.setComponentAlignment(delete, Alignment.BOTTOM_RIGHT);

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

				getUI().getNavigator().navigateTo(PaymentView.VIEW_NAME);

			}
		});
		this.addComponent(paymentBtn);
		this.setComponentAlignment(paymentBtn, Alignment.BOTTOM_RIGHT);
	}

	private Grid<Product> createSavedProductsGV(List<Product> lista) {
		Grid<Product> grid = new Grid<>();
		grid.setItems(lista);
		grid.addColumn(Product::getCategory).setCaption("Kategória");
		grid.addColumn(Product::getProductName).setCaption("Név");
		grid.addColumn(Product::getDescription).setCaption("Leírás");
		grid.addColumn(Product::getPrice, new NumberRenderer("%d Ft")).setCaption("Ár");
		grid.addColumn(Product::getDiscount, new NumberRenderer("%d %%")).setCaption("Kedvezmény");
		grid.getSelectionModel().setUserSelectionAllowed(true);
		grid.setSelectionMode(SelectionMode.MULTI);
		return grid;
	}
	
	private Grid<Product> createSavedProductsGVHistory(List<Product> termekLista) {
		Grid<Product> grid = new Grid<>();
		grid.setItems(termekLista);
		grid.addColumn(Product::getCategory).setCaption("Kategória");
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
