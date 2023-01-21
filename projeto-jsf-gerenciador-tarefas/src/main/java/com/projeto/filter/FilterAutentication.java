package com.projeto.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;

import com.projeto.model.ModelUsuario;

/**
 * Servlet Filter implementation class FilterAutentication
 */
@WebFilter("/FilterAutentication")
public class FilterAutentication extends HttpFilter implements Filter {
       
    /**
     * @see HttpFilter#HttpFilter()
     */
    public FilterAutentication() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
		
		System.out.println("Filtro !");
		HttpServletRequest req=(HttpServletRequest) request;
		ModelUsuario user = (ModelUsuario) req.getSession().getAttribute("userlogado");
		System.out.println(user);
		if (user==null) {
			request.getRequestDispatcher("/index.jsf").forward(request, response);
		}else {
			chain.doFilter(request, response);

		}
		// pass the request along the filter chain
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
