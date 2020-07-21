$(document).ready(function() {

	/*
	 * <#-- //The token end point from where we can retrieve the access token
	 * var tokenEndPoint =
	 * "http://yoursitefinitysite/Sitefinity/Authenticate/OpenID/connect/token";
	 * var apiUrl="http://yoursitefinitysite/api/default/newsitems";
	 * 
	 * var client_id = "testApp"; var client_secret = "secret";
	 * 
	 * var accessToken; var refreshToken;
	 * 
	 * $("#getTokenBtn").on("click", getToken);
	 * $("#getTokenWithRefreshBtn").on("click", getAccessTokenFromRefreshToken);
	 * $("#apiCallBtn").on("click", callApi); -->
	 */
	$(document).on("click", "#login", function() {

		/*
		 * var username = $('#username').val(); var password =
		 * $('#password').val();
		 */
		// Call that gets the access and refresh token -->
		$.ajax({
			url : 'http://localhost:8080/oauth/token',
			type : 'POST',
			dataType : 'json',
			contentType : 'application/x-www-form-urlencoded; charset=UTF-8',
			data : {
				username : "dips",
				password : "dddddddd",
				grant_type : 'password',
				client_id : "dipak",
				client_secret : "secret"
			},

			success : function(data) {
				alert("hi");
				console.log(data.access_token);
				console.log(data.refresh_token);
				/*
				 * $('#token').text(data.access_token);
				 * $('#refreshToken').text(data.refresh_token);
				 * accessToken=data.access_token;
				 * refreshToken=data.refresh_token;
				 */
			},
			error : function(err) {
				alert(err.responseText);
			}
		});
	});
});
