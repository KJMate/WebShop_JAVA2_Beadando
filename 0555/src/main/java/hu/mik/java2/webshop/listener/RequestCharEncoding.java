package hu.mik.java2.webshop.listener;

import java.io.UnsupportedEncodingException;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class RequestCharEncoding implements ServletRequestListener {

	@Override
	public void requestDestroyed(ServletRequestEvent arg0) {}

	@Override
	public void requestInitialized(ServletRequestEvent servletRequestEvent) {
		try {
			servletRequestEvent.getServletRequest()
			.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
		
	}

}
