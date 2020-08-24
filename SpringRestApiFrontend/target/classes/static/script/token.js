$(document).ready(function() {

	
	//window.location = "http://localhost:8080/index";
	/*$( window ).load(function() {
		// Run code
		alert("The URL of this page is: " + window.location.href);
	});*/
	
	
	
	$(document).on("click", "#login", function() {
		alert("clicked")
		//var proxyurl = "https://cors-anywhere.herokuapp.com/";
		//var urll = "http://localhost:8080/oauth/token";
		$.ajax({
			url : 'http://localhost:8080/oauth/token',
			//url : proxyurl+urll,
			type : 'POST',
			dataType : 'json',
			contentType : 'application/x-www-form-urlencoded; charset=UTF-8',
			beforeSend: function (xhr) {
			    xhr.setRequestHeader ("Authorization", "Basic " + btoa("dipak" + ":" + "secret"));
			},
			data : {
				username : $('#userName').val(),
				password : $('#password').val(),
				grant_type : 'password',
				// client_id : "dipak",
				// client_secret : "secret"
			},

			success : function(data) {
				alert("hi");
				alert(data)
				alert(data.access_token);
				sessionStorage.setItem("accessToken",data.access_token);
				console.log(data.access_token);
				console.log(data.fname);
				windows.location="http://localhost:8081/profile";
			},
			error : function(err) {
				alert(err.responseText);
			}
		});
	});
	
	
	$(document).on("click", "#userEditBtn", function() {
		alert("userEditBtn clicked")
		var accessToken = sessionStorage.getItem("accessToken");
		
		alert("accessToken :  "+accessToken);
		$.ajax({
			url : 'useredit',
			type : 'POST',
			beforeSend: function (xhr) {
			    xhr.setRequestHeader ("Authorization", "Bearer " + accessToken);
			},
			data : {
				id : $('#idd').val()
			},
			success : function(data) {
				alert("success userEditBtn AJAX CALL");
				//alert(data);
				alert(data.fname);
				localStorage.setItem('userData', JSON.stringify(data));
				window.location = "/registration";

				//document.getElementById("Id").innerHTML = res.Id;
               /* document.getElementById('fname').value= data.fname;
                document.getElementById("lname").value= data.lname;
                document.getElementById("email").value= data.email;
                document.getElementById("userName").value= data.userName;
                /*document.getElementById("Email").innerHTML= res.Email;
                document.getElementById("Password").innerHTML= res.Password;
                document.getElementById("Category").innerHTML= res.Category;*/
				
			},
			error : function(err) {
				alert(err.responseText);
			}
		});
	});
	
	$(document).on("click", "#logoutbtn", function() {
		alert("logout btn clicked")
		var accessToken = sessionStorage.getItem("accessToken");
		
		alert("accessToken :  "+accessToken);
		$.ajax({
			url : 'logoutuser',
			type : 'POST',
			beforeSend: function (xhr) {
			    xhr.setRequestHeader ("Authorization", "Bearer " + accessToken);
			},
			success : function(data) {
				alert("success logout btn AJAX CALL");
				alert(data);
				window.location ="/login";
				//previewDialog.html(data);
		        //previewDialog.dialog('open');
			},
			error : function(err) {
				alert(err.responseText);
			}
		});
	});
	
	$(document).on("click", "#cancle", function() {
		alert("cancle btn clicked")
		var accessToken = sessionStorage.getItem("accessToken");
		
		alert("accessToken :  "+accessToken);
		$.ajax({
			url : 'cancle',
			//type : 'POST',
			beforeSend: function (xhr) {
			    xhr.setRequestHeader ("Authorization", "Bearer " + accessToken);
			},
			success : function(data) {
				alert("success cancle btn AJAX CALL");
				alert(data);
				window.location="/"+data;
				//previewDialog.html(data);
		        //previewDialog.dialog('open');
			},
			error : function(err) {
				alert(err.responseText);
			}
		});
	});

	
	$(document).on("click", "#register", function() {
		alert("register btn clicked")
		var accessToken = sessionStorage.getItem("accessToken");
		//let f = new FormData(this);
		alert("accessToken :  "+accessToken);
		$.ajax({
			url : 'insertUser',
			//enctype: 'multipart/form-data',
			//contentType : false,
	        //processData: false,
			//data : f,
			type : 'POST',
			beforeSend: function (xhr) {
			    xhr.setRequestHeader ("Authorization", "Bearer " + accessToken);
			},
			success : function(data) {
				alert("success reg btn AJAX CALL");
				alert(data);
				window.location="/index";
				//previewDialog.html(data);
		        //previewDialog.dialog('open');
			},
			error : function(err) {
				alert(err.responseText);
			}
		});
	});

});