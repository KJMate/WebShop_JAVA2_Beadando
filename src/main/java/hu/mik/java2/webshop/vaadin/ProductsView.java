package hu.mik.java2.webshop.vaadin;

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
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.renderers.NumberRenderer;
import com.vaadin.ui.themes.ValoTheme;
import hu.mik.java2.webshop.product.bean.Product;
import hu.mik.java2.webshop.product.service.ProductService;
import hu.mik.java2.webshop.user.bean.User;
import hu.mik.java2.webshop.user.service.UserService;

@Theme("valo")
@SuppressWarnings("serial")
@SpringView(name = ProductsView.VIEW_NAME)
public class ProductsView extends VerticalLayout implements View {
	public static final String VIEW_NAME = "products";

	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	
	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;

	public static User actualUser = null;
	String saved = "";
	private Panel products;
	
	private Grid<Product> productsGV;

	@PostConstruct
	void init() {
		Page.getCurrent().setTitle("Web Shop - Termékek");
		final VerticalLayout root = new VerticalLayout();
		root.setMargin(false);
		root.setSizeFull();

		final CssLayout page = new CssLayout();
		page.setSizeFull();

		final CssLayout filter = new CssLayout();
		filter.setWidth(20f, Unit.PERCENTAGE);
		filter.addStyleName(ValoTheme.LAYOUT_HORIZONTAL_WRAPPING);
		filter.addComponent(createFilterButton("Okos eszközök"));
		filter.addComponent(createFilterButton("TV és Szórakozás"));
		filter.addComponent(createFilterButton("Sport és szabadidő"));

		Button btnAddToCart = new Button("Kosárhoz adás");
		btnAddToCart.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				User user = SignInView.getUser();
				if(user == null) {
					getUI().getNavigator().navigateTo(SignInView.VIEW_NAME);
				} else {
					Collection<Product> selectedProducts = productsGV.getSelectedItems();
					
					for (Product product : selectedProducts) {
						saved += product.getProductName() + ";";
					}
					user.setSavedProducts(saved);
					userService.save(user);
					Notification.show("Sikeresen a kosárba rakta a terméket");
				}
			}
		});
		
		filter.addComponent(btnAddToCart);
		
		products = new Panel();
		productsGV = createProductsGridView();
		productsGV.setSizeFull();
		products.setContent(productsGV);
		products.setWidth(80f, Unit.PERCENTAGE);

		page.addComponent(products);
		page.addComponentAsFirst(filter);

		root.addComponent(page);

		this.addComponent(root);
	}

	private Button createFilterButton(String caption) {
		Button btn = new Button(caption);
		btn.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				productsGV = createProductsGridView(caption);
				productsGV.setSizeFull();
				products.setContent(productsGV);
			}
		});
		btn.addStyleName(ValoTheme.BUTTON_LINK);
		btn.setSizeFull();
		return btn;
	}

	private Grid<Product> createProductsGridView() {
		Grid<Product> grid = new Grid<>();
		List<Product> products = productService.listProducts();
		grid.setItems(products);
		grid.addColumn(Product::getId).setCaption("Azonosító").setHidden(true);
		grid.addColumn(Product::getCategoryId).setCaption("Kategória");
		grid.addColumn(Product::getProductName).setCaption("Név");
		grid.addColumn(Product::getDescription).setCaption("Leírás");
		grid.addColumn(Product::getPrice, new NumberRenderer("%d Ft")).setCaption("Ár");
		grid.addColumn(Product::getDiscount, new NumberRenderer("%d %%")).setCaption("Kedvezmény");
		grid.setSelectionMode(SelectionMode.MULTI);
		return grid;
	}
	
	private Grid<Product> createProductsGridView(String filter) {
		Grid<Product> grid = new Grid<>();
		List<Product> products = productService.listProductsByCategoryId(filter);
		grid.setItems(products);
		grid.addColumn(Product::getId).setCaption("Azonosító").setHidden(true);
		grid.addColumn(Product::getCategoryId).setCaption("Kategória");
		grid.addColumn(Product::getProductName).setCaption("Név");
		grid.addColumn(Product::getDescription).setCaption("Leírás");
		grid.addColumn(Product::getPrice, new NumberRenderer("%d Ft")).setCaption("Ár");
		grid.addColumn(Product::getDiscount, new NumberRenderer("%d %%")).setCaption("Kedvezmény");
		grid.setSelectionMode(SelectionMode.MULTI);
		return grid;
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// nothing to do here!!!
	}

	public static User getUser() {
		return actualUser;
	}
}