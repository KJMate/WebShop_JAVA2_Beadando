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
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.renderers.NumberRenderer;

import hu.mik.java2.webshop.product.bean.Product;
import hu.mik.java2.webshop.product.service.ProductService;
import hu.mik.java2.webshop.shoppingcart.bean.ShoppingCart;
import hu.mik.java2.webshop.shoppingcart.service.ShoppingCartService;
import hu.mik.java2.webshop.user.bean.User;
import hu.mik.java2.webshop.user.service.UserService;

@Theme("valo")
@SuppressWarnings("serial")
@SpringView(name = PaymentView.VIEW_NAME)
public class PaymentView extends VerticalLayout implements View {
	public static final String VIEW_NAME = "payment";

	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	
	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;
	
	@Autowired
	@Qualifier("shoppingCartServiceImpl")
	private ShoppingCartService shoppingCartService;

	private User actualUser = SignInView.getUser();
	private Integer osszeg = 0;
	
	private List<Product> termekLista = new ArrayList<>();
	private List<ShoppingCart> bevKocsiLista;
	private List<User> lista;
	
	@PostConstruct
	void init() {
		Page.getCurrent().setTitle("Web Shop - Fizetés");
		HorizontalLayout horiLay = new HorizontalLayout();
		bevKocsiLista = shoppingCartService.findAll();
		lista = userService.listUsers();
		horiLay.addComponent(createProductsGridView());
		horiLay.addComponent(paymentDetails());

		this.addComponent(horiLay);
	}

	private Component paymentDetails() {
		VerticalLayout vertLayout = new VerticalLayout();
		
		Panel cardDetailsPanel = new Panel("Kártya adatok");
		TextField cardNumber = new TextField("Kártya száma");
		TextField cardName = new TextField("Kártyára írt név");
		TextField cardDate = new TextField("Érvényességi idő");
		Label osszegField = new Label("Végösszeg: " + osszeg + " Ft");
		
		Button btnPayment = new Button("Fizet");
		
		cardDetailsPanel.setWidth("35%");
		cardDetailsPanel.setHeight("400");
		cardDetailsPanel.setSizeUndefined();
		vertLayout.addComponent(cardDetailsPanel);
		vertLayout.setComponentAlignment(cardDetailsPanel, Alignment.TOP_CENTER);
		FormLayout _content_l = new FormLayout();
		
		_content_l.addComponent(osszegField);
		_content_l.addComponent(cardNumber);
		_content_l.addComponent(cardName);
		_content_l.addComponent(cardDate);
		_content_l.addComponent(btnPayment);
		
		btnPayment.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				updateUser();
				getUI().getNavigator().navigateTo(ProfilView.VIEW_NAME);
			}

			
		});
		
		cardDetailsPanel.setContent(_content_l);
		return vertLayout;
	}

	private VerticalLayout createProductsGridView() {
		VerticalLayout vertLayout = new VerticalLayout();
	
		Grid<Product> grid = new Grid<>();
		for (User user : lista) {
			if (user.getId() == actualUser.getId()) {
				for (ShoppingCart kocsi : bevKocsiLista) {
					if (kocsi.getUser_id() == actualUser.getId() && kocsi.getIsPaid() == 0) {
						termekLista.add(kocsi.getProduct());
					}
				}

			}
		}

		grid.setItems(termekLista);
		grid.addColumn(Product::getProductName).setCaption("Név");
		grid.addColumn(Product::getDescription).setCaption("Leírás");
		grid.addColumn(Product::getPrice, new NumberRenderer("%d Ft")).setCaption("Ár");
        grid.setWidth("50%");
		vertLayout.addComponent(grid);
		
		
		for(Product product1 : termekLista){
			osszeg += product1.getPrice();
		}
				
		
		return vertLayout;
	}
	
	private void updateUser() {
		for (User user : lista) {
			if (user.getId() == actualUser.getId()) {
				for (ShoppingCart kocsi : bevKocsiLista) {
					if (kocsi.getUser_id() == actualUser.getId() && kocsi.getIsPaid() == 0) {
						kocsi.setIsPaid(1);
						shoppingCartService.save(kocsi);
					}
				}

			}
		}
		
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		// nothing to do here!!!
	}
}
