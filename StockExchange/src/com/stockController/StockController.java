package com.stockController;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.stockBeans.Customer;
import com.stockBeans.Shares;
import com.stockDAO.StackJDBCTemplate;

@Controller
@Scope(value="session")
public class StockController {

	@Autowired
	private StackJDBCTemplate jdbcTemplate;
	
	private HttpSession session;
	
	@RequestMapping("/login")
	public ModelAndView helloWorld(HttpServletRequest request, HttpServletResponse res) {
		String userName = request.getParameter("name");
		String password = request.getParameter("password");

		Customer customer = jdbcTemplate.checkLogin(userName, password);
		if (customer != null) {
			String message = "HELLO " + userName;

			ModelAndView modelAndView = new ModelAndView();
			modelAndView.setViewName("firstPage");
			modelAndView.addObject("message", message);
			modelAndView.addObject("customer", customer);
			session=request.getSession();
			session.setAttribute("loggedUser", customer);
			return modelAndView;
		} else {
			return new ModelAndView("index", "message", "Sorry, username or password error");
		}
	}

	@RequestMapping(value = "/index")
	public String printHello(ModelMap model) {
		return "index";
	}

	@RequestMapping(value = "/viewStock", method = RequestMethod.GET)
	public ModelAndView viewStock(ModelMap model) {
		if (session==null) {
			return new ModelAndView("index", "message", "You must login to view this page");
		}
		Customer customer = (Customer) session.getAttribute("loggedUser");
		if (customer == null) {
			return new ModelAndView("index", "message", "You must login to view this page");
		}
		List<Shares> list = jdbcTemplate.listShares(customer.getCustomerId());

		if (list.size() != 0) {

			System.out.println("size of list is :" + list.size());
			return new ModelAndView("viewStock", "listShares", list);
		} else {
			System.out.println("size of list is :" + list.size());
			return new ModelAndView("viewStock", "message", "To shares information is present");
		}

	}

}
