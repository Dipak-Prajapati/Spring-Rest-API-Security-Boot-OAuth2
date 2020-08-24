package com.dip.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;



@Controller
public class UserController {
	
	@RequestMapping(value = { "/", "/index" })
	public String displayIndexPage() {
		return "index";
	}

	@RequestMapping("/registration")
	public String displayRegistrationPage() {
		return "registration";
	}

	@RequestMapping("/login")
	public String displayLoginPage() {
		return "login";
	}
	
	@PostMapping("/logincheck")
	public String LoginCheck()
	{
		//HttpRequestWithBody response = Unirest.post("http://localhost:8080/logincheck").header("accept","application/json");
		//HttpResponse<JsonNode> response = (HttpResponse<JsonNode>) Unirest.post("http://localhost:8080/logincheck").header("accept", "application/json");
		//System.out.println("Response : "  + response);
		return "profile";
	}
	
	@RequestMapping("/profile")
	public String displayProfilePage() {
		return "profile";
	}

}
