package com.dips.contoller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.dips.model.AddressListDto;
import com.dips.model.UserInfo;
import com.dips.service.UserInfoService;

//@CrossOrigin(origins = "http://localhost:8081")
@RestController
//@Controller
public class UserController {
	@Autowired
	private UserInfoService userService;
	
	/*@RequestMapping(value = { "/", "/index" })
	public ModelAndView displayIndexPage() {
		ModelAndView mav = new ModelAndView("index");
		return mav;
	}
	@RequestMapping("/registration")
	public ModelAndView displayRegistrationPage() {
		ModelAndView mav = new ModelAndView("registration");
		return mav;
	}
	@RequestMapping("/login")
	public ModelAndView displayLoginPage() {
		ModelAndView mav = new ModelAndView("login");
		return mav;
	}*/
	

	//@GetMapping("/user")
	public Object getAllUser(@RequestHeader HttpHeaders requestHeader) {
		System.out.println("getAllUser()");
		List<UserInfo> userInfos = userService.getAllActiveUserInfo();
		if (userInfos == null || userInfos.isEmpty()) {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
		return userInfos;
	}

	//, consumes = {"multipart/form-data"}
	@RequestMapping(value="/insertUser",method = RequestMethod.POST)
	public UserInfo addUser(UserInfo userRecord,AddressListDto addressListDto,
			@RequestParam("image") MultipartFile image,@RequestHeader HttpHeaders requestHeader) {
		System.out.println("IN Adduser method:::::::::::::::::");
		System.out.println("userRecord :"+userRecord + ":" + "Address :" + addressListDto);
		try {
			if (image != null && image.getSize() > 0) {
				// u.setProfilePicture(image.getBytes());
				userRecord.setPic(image.getBytes());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		userRecord.setAddressModel(addressListDto.getAddressmodel());
		userRecord = userService.addUser(userRecord);
		return userRecord;
	}
	
	@PostMapping("/logincheck")
	public ResponseEntity<UserInfo> showUser(Model m, UserInfo userModel, HttpSession session,@RequestHeader HttpHeaders requestHeader) {
		//ModelAndView mav = null;
		System.out.println("Email and password : " + userModel.getUserName() + " : " + userModel.getPassword());
		if (userModel.getUserName().equals("admin") && userModel.getPassword().equals("aaaaaaaa")) {
			// Admin
			System.out.println("Admin Method");
			List<UserInfo> list =(List<UserInfo>) getAllUser(requestHeader);
			//mav = new ModelAndView("adminProfile");
			//mav.addObject("userModel", list);
			session.setAttribute("loginUser", "adminuser");
			session.setAttribute("login", list);
			//return new ResponseEntity<>(list, HttpStatus.OK);
			return null;
		} else {
			System.out.println("USER");
			userModel = getUserById(userModel.getUserName(),userModel.getPassword());
			if (null != userModel) {
				//mav = new ModelAndView("profile");
				//mav.addObject("userModel", userModel);
				session.setAttribute("loginUser", "user");
				session.setAttribute("login", userModel);
				return new ResponseEntity<>(userModel, HttpStatus.OK);
			} else {
				
				//mav = new ModelAndView("login");
				//mav.addObject("message", "Invalid Details ! try with another");
			}
			return new ResponseEntity<>(userModel, HttpStatus.OK);
		}
	}
	
	@RequestMapping("/logoutuser")
	//@ResponseBody
	public String logoutUser(Model m, HttpSession session,@RequestHeader HttpHeaders requestHeader) {
		//ModelAndView mav = null;
		session.removeAttribute("loginUser");
		session.invalidate();
		//mav = new ModelAndView("login");
		m.addAttribute("logoutmessage", "Logout SuccessFully");
		return "login";
	}
	
	//@PostMapping(value="/user")
	/*public UserInfo addUser(UserInfo userRecord) {
		System.out.println("IN Adduser method:::::::::::::::::");
		System.out.println("userRecord :"+userRecord );
		return userService.addUser(userRecord);
	}*/

	@PostMapping("/userUpdate")
	public UserInfo updateUser(UserInfo userRecord,@RequestParam("image") MultipartFile image) {
		System.out.println("id:"+userRecord.getId()+"userRecord :"+userRecord+"::::"+userRecord.getEmail());
		try {
			if (image != null && image.getSize() > 0) {
				// u.setProfilePicture(image.getBytes());
				userRecord.setPic(image.getBytes());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("userRecord :"+userRecord);
		return userService.updateUser(userRecord);
	}
	
	@PostMapping("/user/changePassword")
	public UserInfo updateUserPassword(UserInfo userRecord) {
		System.out.println("id:"+userRecord.getId()+"password:"+userRecord.getPassword());
		return userService.updatePassword(userRecord.getId(),userRecord);
	}
	
/*@PutMapping("/user/changeRole/{id}")
	public UserInfo updateUserRole(@RequestBody UserInfo userRecord, @PathVariable Integer id) {
		return userService.updateRole(id,userRecord);
	}*/

	@DeleteMapping("/user/{id}")
	public void deleteUser(@PathVariable Integer id) {
		userService.deleteUser(id);
	}

	//@GetMapping("/user/{id}")
	/*public ResponseEntity<UserInfo> getUserById(@PathVariable Integer id) {
		UserInfo userInfo = userService.getUserInfoById(id);
		if (userInfo == null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(userInfo, HttpStatus.OK);
	}*/
	
	public UserInfo getUserById(String userName,String password) {
		System.out.println("userName :"+userName + ":::" + "password :" + password);
		UserInfo userInfo = userService.getUserInfoById(userName,password);
		if (userInfo == null) {
			return null;
		}
		return userInfo;
	}
	
	@PostMapping("/useredit")
	public UserInfo getUserByIdd(Model m,@RequestParam("id")Integer id,@RequestHeader HttpHeaders requestHeader) {
		System.out.println("userName :"+id);
		UserInfo userInfo = userService.getUserInfoByIdd(id);
		if (userInfo == null) {
			return null;
		}
		ModelAndView mav = new ModelAndView();
		mav.addObject("userModel", userInfo);
		return userInfo;
	}
	
	@RequestMapping("/cancle")
	public String displayProfilePage(Model m, HttpSession session, UserInfo userModel,@RequestHeader HttpHeaders requestHeader) {
		//ModelAndView mav = null;
		if (session.getAttribute("loginUser").equals("user")) {
			//mav = new ModelAndView("profile");
			//mav.addObject("userModel", session.getAttribute("login"));
			//m.addAttribute("userModel", session.getAttribute("login"));
			return "profile";
		} else {
			//mav = new ModelAndView("adminProfile");
			//mav.addObject("userModel", session.getAttribute("login"));
			//m.addAttribute("userModel", session.getAttribute("login"));
			return "adminProfile";
		}
	}
}
