$(document).ready(function() {

	
	$(document).on("click", "#login", function() {
		alert("clicked")
		$.ajax({
			url : 'http://localhost:8080/oauth/token',
			type : 'POST',
			dataType : 'json',
			contentType : 'application/x-www-form-urlencoded; charset=UTF-8',
			beforeSend: function (xhr) {
			    xhr.setRequestHeader ("Authorization", "Basic " + btoa("dipak" + ":" + "secret"));
			},
			data : {
				username : "dips",
				password : "dddddddd",
				grant_type : 'password',
				// client_id : "dipak",
				// client_secret : "secret"
			},

			success : function(data) {
				alert("hi");
				alert(data.access_token);
				sessionStorage.setItem("accessToken",data.access_token);
				console.log(data.access_token);
				console.log(data.refresh_token);
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
				alert(data);
				window.location = data;
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
				window.location = data;
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
		
		alert("accessToken :  "+accessToken);
		$.ajax({
			url : 'user',
			//enctype: 'multipart/form-data',
	        //processData: false,
			type : 'POST',
			beforeSend: function (xhr) {
			    xhr.setRequestHeader ("Authorization", "Bearer " + accessToken);
			},
			success : function(data) {
				alert("success reg btn AJAX CALL");
				alert(data);
				window.location = data;
				//previewDialog.html(data);
		        //previewDialog.dialog('open');
			},
			error : function(err) {
				alert(err.responseText);
			}
		});
	});

});