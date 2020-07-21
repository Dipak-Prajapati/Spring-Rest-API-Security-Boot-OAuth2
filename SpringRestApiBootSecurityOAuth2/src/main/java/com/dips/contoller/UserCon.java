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
import org.springframework.web.bind.annotation.RequestParam;
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
		System.out.println("Email and password : " + userModel.getEmail() + " : " + userModel.getPwd());
		if (userModel.getEmail().equals("admin@gmail.com") && userModel.getPwd().equals("aaaaaaaa")) {
			// Admin
			System.out.println("Admin Method");
			List<UserInfo> list = (List<UserInfo>) userController.getAllUser(requestHeader);
			mav = new ModelAndView("adminProfile");
			mav.addObject("userModel", list);
			session.setAttribute("loginUser", "adminuser");
			session.setAttribute("login", list);
			return mav;
		} else {
			userModel = userController.getUserById(userModel.getUserName(),userModel.getPwd());
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

	
}
