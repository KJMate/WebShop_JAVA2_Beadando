package hu.mik.java2.webshop.vaadin;

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
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.renderers.NumberRenderer;
import com.vaadin.ui.themes.ValoTheme;

import hu.mik.java2.webshop.product.bean.Product;
import hu.mik.java2.webshop.product.service.ProductService;


@Theme("valo")
@SuppressWarnings("serial")
@SpringView(name = ProductsView.VIEW_NAME)
public class ProductsView extends VerticalLayout implements View {
	public static final String VIEW_NAME = "products";
	
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	
	private Panel products;
	
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
		
		products = new Panel();
		Grid<Product> productsGV = createProductsGridView();
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
		//btn.addStyleName(ValoTheme.BUTTON_SMALL);
		btn.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				Grid<Product> productsGV = createProductsGridView(caption);
				productsGV.setSizeFull();
				products.setContent(productsGV);
				products.setContent(productsGV);
			}
		});
		btn.addStyleName(ValoTheme.BUTTON_LINK);
		btn.setSizeFull();
		//btn.addStyleName(ValoTheme.BUTTON_PRIMARY);
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
		return grid;
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		// nothing to do here!!!
	}
}