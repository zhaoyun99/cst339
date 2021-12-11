package com.gcu;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gcu.business.SecurityServiceInterface;
import com.gcu.model.LoginModel;

@Controller
@RequestMapping("/login")
public class LoginController {

	@Autowired
	SecurityServiceInterface securityService;
	
	@GetMapping("/")
	public String display(Model model)
	{
		// Display Login Form View
		model.addAttribute("title", "Login Form");
		model.addAttribute("loginModel", new LoginModel());
		return "login";
	}
	
	@PostMapping("/doLogin")
	public String doLogin(@Valid LoginModel loginModel,BindingResult bindingResult, Model model)
	{
		// Check for validation errors
		if (bindingResult.hasErrors())
		{
			model.addAttribute("title", "Login Form");
			return "login";
		}
		
		
		// check for valid login name password
				if (securityService.isAuthenticated(loginModel))
				{
					// Login Success. Send Them to the success page to show what they entered
					model.addAttribute("model", loginModel);
					return "loginSuccess";
				}
				else {
					// login failed. Send them back to the login form.
					return "login";
				}
		
	}
	@PostMapping("/login_failure_handler")
	public String loginFailureHandler() {
	    System.out.println("Login failure handler....");
	     
	    return "login";
	}  
	@PostMapping("/login_success_handler")
    public String loginSuccessHandler() {
        System.out.println("Logging user login success...");
 
        return "index";
    }  

	
	
	
	
}


