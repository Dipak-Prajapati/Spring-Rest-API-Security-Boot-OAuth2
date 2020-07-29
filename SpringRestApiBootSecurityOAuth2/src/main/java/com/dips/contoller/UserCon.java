package com.dips.contoller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.dips.model.AddressListDto;
import com.dips.model.UserInfo;

@Controller
public class UserCon {
	
	@Autowired
	private UserController userController;

	@RequestMapping(value = { "/", "/index" })
	public String displayIndexPage() {
		return "index";
	}
	
	@RequestMapping("/login")
	public String displayLoginPage() {
		return "login";
	}
	
	@RequestMapping("/registration")
	public String displayRegistrationPage() {
		System.out.println("Registration Page Display");
		return "registration";
	}
	
	@PostMapping(value="/user")
	public String registrationForm(UserInfo userModel,
			AddressListDto addressListDto,@RequestParam("image") MultipartFile image,
			Model m) {

		System.out.println("in registration form");
		userModel.setAddressModel(addressListDto.getAddressmodel());

		try {
			if (image != null && image.getSize() > 0) {
				// u.setProfilePicture(image.getBytes());
				userModel.setPic(image.getBytes());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		// userModel.setPic(image.getBytes());
		userController.addUser(userModel);
		System.out.println("in registration form1");
		
		return "index";
	}

	
	@PostMapping("/logincheck")
	public ModelAndView showUser(Model m, UserInfo userModel, HttpSession session,@RequestHeader HttpHeaders requestHeader) {
		ModelAndView mav = null;
		System.out.println("Email and password : " + userModel.getUserName() + " : " + userModel.getPassword());
		if (userModel.getUserName().equals("admin@gmail.com") && userModel.getPassword().equals("aaaaaaaa")) {
			// Admin
			System.out.println("Admin Method");
			List<UserInfo> list = (List<UserInfo>) userController.getAllUser(requestHeader);
			mav = new ModelAndView("adminProfile");
			mav.addObject("userModel", list);
			session.setAttribute("loginUser", "adminuser");
			session.setAttribute("login", list);
			return mav;
		} else {
			System.out.println("USER");
			userModel = userController.getUserById(userModel.getUserName(),userModel.getPassword());
			if (null != userModel) {
				mav = new ModelAndView("profile");
				mav.addObject("userModel", userModel);
				session.setAttribute("loginUser", "user");
				session.setAttribute("login", userModel);
			} else {
				
				mav = new ModelAndView("login");
				mav.addObject("message", "Invalid Details ! try with another");
			}
			return mav;
		}
	}
	
	@RequestMapping("/logoutuser")
	@ResponseBody
	public String logoutUser(Model m, HttpSession session) {
		session.removeAttribute("loginUser");
		session.invalidate();
		m.addAttribute("logoutmessage", "Logout SuccessFully");
		return "login";
	}
	
	/*@RequestMapping("/logoutuser")
	public  ModelAndView logoutUser(HttpSession session) {
	    ModelAndView mav = new ModelAndView();
	    session.removeAttribute("loginUser");
		session.invalidate();
		//m.addAttribute("logoutmessage", "Logout SuccessFully");
	    mav.setViewName("login");
	    mav.addObject("logoutmessage", "Logout SuccessFully");
	    return mav;     
	}*/

	//@PostMapping("/useredit")
	@RequestMapping(value = "/useredit", method = RequestMethod.POST)
	//@ResponseBody
	public String displayEditForm(Model m,@RequestParam("id") int id, HttpSession session) {
		System.out.println("displayEditForm" + id);
		session.setAttribute("editData", id);
		return "redirect:/editDetailsForm";
	}

	@RequestMapping("/editDetailsForm")
	@ResponseBody
	public ModelAndView editData(Model m, HttpSession session) {
		System.out.println("editData");
		ModelAndView mav = new ModelAndView("registration");
		UserInfo userModel = new UserInfo();
		userModel = userController.getUserByIdd((Integer) session.getAttribute("editData"));
		//m.addAttribute("userModel", userModel);
		mav.addObject("userModel", userModel);
		return mav;
	}

	
}
