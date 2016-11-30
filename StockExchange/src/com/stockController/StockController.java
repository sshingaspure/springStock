package com.stockController;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.stockBeans.Customer;
import com.stockBeans.Shares;
import com.stockDAO.StackJDBCTemplate;

@Controller
public class StockController {
	
	@Autowired
	private StackJDBCTemplate jdbcTemplate;

    @RequestMapping("/login")  
    public ModelAndView helloWorld(HttpServletRequest request,HttpServletResponse res) {  
        String userName=request.getParameter("name");  
        String password=request.getParameter("password");  
          
        Customer customer=jdbcTemplate.checkLogin(userName, password);
        if(customer!=null){  
        String message = "HELLO "+userName;  
        return new ModelAndView("firstPage", "message", message);  
        }  
        else{  
            return new ModelAndView("index", "message","Sorry, username or password error");  
        }  
    }
    
    @RequestMapping(value="/index",method = RequestMethod.GET)
    public String printHello(ModelMap model) {
       return "index";
    }
    
    @RequestMapping(value="/viewStock",method = RequestMethod.GET)
    public ModelAndView viewStock(ModelMap model) {
       
    	List<Shares> list=jdbcTemplate.listShares(1);
    	
    	if (list.size()!=0) {
    		
    		System.out.println("size of list is :"+list.size());
    		return new ModelAndView("viewStock","listShares",list);
		}else{
			System.out.println("size of list is :"+list.size());
			return new ModelAndView("viewStock","message","To shares information is present");
		}
    	
    	
    }

	
}
