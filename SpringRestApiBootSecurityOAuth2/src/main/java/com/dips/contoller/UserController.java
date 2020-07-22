package com.dips.contoller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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

import com.dips.model.AddressListDto;
import com.dips.model.UserInfo;
import com.dips.service.UserInfoService;

@RestController
//@Controller
public class UserController {
	@Autowired
	private UserInfoService userService;

	//@GetMapping("/user")
	public Object getAllUser(@RequestHeader HttpHeaders requestHeader) {
		System.out.println("getAllUser()");
		List<UserInfo> userInfos = userService.getAllActiveUserInfo();
		if (userInfos == null || userInfos.isEmpty()) {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
		return userInfos;
	}

	/*@RequestMapping(value="/user",method = RequestMethod.POST, consumes = {"multipart/form-data"})
	public UserInfo addUser(UserInfo userRecord,AddressListDto addressListDto,
			@RequestParam("image") MultipartFile image) {
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
		return userService.addUser(userRecord);
	}*/
	
	//@PostMapping(value="/user")
	public UserInfo addUser(UserInfo userRecord) {
		System.out.println("IN Adduser method:::::::::::::::::");
		System.out.println("userRecord :"+userRecord );
		return userService.addUser(userRecord);
	}

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
		System.out.println("id:"+userRecord.getId()+"password:"+userRecord.getPwd());
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
}
