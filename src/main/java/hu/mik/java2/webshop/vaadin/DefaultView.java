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
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.renderers.NumberRenderer;
import com.vaadin.ui.themes.ValoTheme;
import hu.mik.java2.webshop.product.bean.Product;
import hu.mik.java2.webshop.product.service.ProductService;

@SuppressWarnings("serial")
@Theme("valo")
@UIScope
@SpringView(name = DefaultView.VIEW_NAME)
public class DefaultView extends VerticalLayout implements View {
	public static final String VIEW_NAME = "";

	@Autowired
	private HomeRobot robot;

	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	
	private Grid<Product> productsGV;
	
	@PostConstruct
	void init() {
		Page.getCurrent().setTitle(robot.getTitle());
		VerticalLayout page = new VerticalLayout();
		page.setMargin(false);
		
		Label lblWelcome = robot.createLabel(HomeRobot.WELCOME);
		Label lblDescription = robot.createLabel(HomeRobot.DESCRIPTION);
		Label lblWatermark = robot.createLabel(HomeRobot.WATERMARK);
		Label lblDiscount = new Label("Akciós termékek");
		productsGV = createProductsGridView();
		productsGV.setSizeFull();
		productsGV.setHeight("10em");
		
		lblWelcome.addStyleName(ValoTheme.LABEL_COLORED);
		lblWelcome.addStyleName(ValoTheme.LABEL_H1);
		
		lblDiscount.addStyleName(ValoTheme.LABEL_COLORED);
		lblDiscount.addStyleName(ValoTheme.LABEL_H2);
		
		lblDescription.addStyleName(ValoTheme.LABEL_NO_MARGIN);
		lblDescription.addStyleName(ValoTheme.LABEL_H2);
		lblWatermark.addStyleName(ValoTheme.LABEL_SMALL);
		lblWatermark.addStyleName(ValoTheme.LABEL_COLORED);
		
		page.addComponent(lblWelcome);
		page.addComponent(lblDiscount);
		page.addComponent(productsGV);
		page.addComponent(lblWatermark);
		page.addComponent(lblDescription);
		
		page.setComponentAlignment(lblWelcome, Alignment.TOP_CENTER);
		page.setComponentAlignment(lblWatermark, Alignment.MIDDLE_CENTER);
		page.setComponentAlignment(lblDescription, Alignment.BOTTOM_RIGHT);
		
		this.addComponent(page);
	}

	private Grid<Product> createProductsGridView() {
		Grid<Product> grid = new Grid<>();
		List<Product> products = productService.listProductByDiscount(20);
		grid.setItems(products);
		grid.addColumn(Product::getId).setCaption("Azonosító").setHidden(true);
		//grid.addColumn(Product::getCategoryId).setCaption("Kategória");
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