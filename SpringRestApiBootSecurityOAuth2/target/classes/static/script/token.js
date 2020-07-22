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
				userName : "dips",
				password : "dddddddd",
				grant_type : 'password',
				//client_id : "dipak",
				//client_secret : "secret"
			},

			success : function(data) {
				alert("hi");
				
				console.log(data.access_token);
				console.log(data.refresh_token);
			},
			error : function(err) {
				alert(err.responseText);
			}
		});
	});
});
