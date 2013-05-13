var Hosprice = Hosprice || {};


$(document).ready(function(){
	console.log("executed...");
	$('#contact-form').on("submit", function(event){
		console.log("clicked form...");
		event.preventDefault();
		
		
		var contactInformation = {};
		contactInformation.name = $('#contact-form input[name=name]').val();
		contactInformation.email = $('#contact-form input[name=contactEmail]').val();
		contactInformation.companyName = $('#contact-form input[name=companyName]').val(); 
		contactInformation.website = $('#contact-form input[name=website]').val();
		contactInformation.reason = $('#contact-form textArea[name=reason]').val();
		console.log(contactInformation);
		contactInformation = JSON.stringify(contactInformation);

		var url = "/hosprice/rest/user/contact";
		$.ajax(url, {
			crossDomain: true,
			contentType: "application/json",
			data: contactInformation,
			type: "POST",
			success: function(data) {
				console.log("data was saved correctly");
				$('#launchThanksModal').click();
				//var myData = $.parseJSON(data);
			},
			error: function(xhr, textStatus, error){
							  console.log(xhr.statusText);
							  console.log(textStatus);
							  console.log(error);
			}
		});
		
		return false;
	});
	
	
});