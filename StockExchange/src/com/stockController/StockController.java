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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.stockBeans.Company;
import com.stockBeans.Customer;
import com.stockBeans.Shares;
import com.stockDAO.StackJDBCTemplate;

@Controller
@Scope(value = "session")
public class StockController {

	@Autowired
	private StackJDBCTemplate jdbcTemplate;

	private HttpSession session;

	@RequestMapping("/login")
	public ModelAndView helloWorld(HttpServletRequest request, HttpServletResponse res) {
		if (session != null) {
			Customer customer = (Customer) session.getAttribute("loggedUser");
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.setViewName("firstPage");
			modelAndView.addObject("message", "Hello " + customer.getCustomerName());
			modelAndView.addObject("customer", customer);

			return modelAndView;

		}
		
		String userName = request.getParameter("name");
		String password = request.getParameter("password");

		Customer customer = jdbcTemplate.checkLogin(userName, password);
		if (customer != null) {
			String message = "HELLO " + userName;

			ModelAndView modelAndView = new ModelAndView();
			modelAndView.setViewName("firstPage");
			modelAndView.addObject("message", message);
			modelAndView.addObject("customer", customer);
			session = request.getSession();
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

		if (session == null) {
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
			return new ModelAndView("viewStock", "message", "No shares information is present");
		}

	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(ModelMap model) {
		if (session != null) {
			session.invalidate();
			session = null;
		}

		return "redirect:index";
	}

	@RequestMapping(value = "/viewCompanies", method = RequestMethod.GET)
	public ModelAndView viewCompanies(ModelMap model) {
		List<Company> companies = jdbcTemplate.listCompanies();
		if (companies.size() != 0) {
			return new ModelAndView("viewCompany", "companyList", companies);
		} else {
			return new ModelAndView("viewCompany", "message", "No shares information is present");
		}
	}

	@RequestMapping(value = "/buySellStock", method = RequestMethod.GET)
	public ModelAndView buySellStock(ModelMap model) {
		Customer customer = (Customer) session.getAttribute("loggedUser");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("trading");
		System.out.println(customer.toString());
		
		modelAndView.addObject("customer", customer);
		modelAndView.addObject("companyList", jdbcTemplate.listCompanies());

		return modelAndView;
	}

	// getNumberOfShares

	@RequestMapping(value = "/getNumberOfShares", method = RequestMethod.GET)
	public @ResponseBody String getNumberOfShares(@RequestParam String custid, @RequestParam String cmpid) {
		String cust_id = custid;
		String cmp_id = cmpid;

		int num = jdbcTemplate.getShareNumbers(cust_id, cmp_id);
		System.out.println("Number of shares: "+num);
		return new String(""+num);
	}

	
	@RequestMapping("/buyStocksForCompany")
	public ModelAndView buyStocksForCompany(HttpServletRequest request, HttpServletResponse res) {
		if (session == null) {
			return new ModelAndView("index", "message", "You must login to view this page");
		}

		Customer customer = (Customer) session.getAttribute("loggedUser");

		if (customer == null) {
			return new ModelAndView("index", "message", "You must login to view this page");
		}
		
		
		String companyString = request.getParameter("companyName");
		int cmpID=new Integer(companyString.split(",")[0]);
		
		String numOfSharestoBuy = request.getParameter("numOfSharestoBuy");
		String shareValue = request.getParameter("shareValue");
		return null;

	}
}
