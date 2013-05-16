var Hosprice = Hosprice || {};


Provider = Backbone.Model.extend({
	url : function(){
		return "/hosprice/rest/provider/zipcode/"+this.get("zipcode");
	},
	
	defaults: {
		providerId: 0,
		legacyId: '',
		name: 'Temp Result',
		address1: '',
		address2: '',
		city: '',
		state: '',
		zipcode: '',
		regionName: '',
		regionState: '',
		diagnosisRelatedGroupModels: {}
	},
	  validate: function( attributes ){
		  console.log("here we go");
          if( attributes.name === 'blue' ){
        	  console.log("Get in my belly");
              return "You can't be blue";
          }
      },
    initialize: function(){
        alert("Welcome to this world");
        this.on("change:name", function(model){
            var name = model.get("name"); 
            alert("Changed my name to " + name );
        });
        
        this.bind("invalid", function(model, error){
            // We have received an error, log it, alert it or forget it :)
            alert( error );
        });
    },
    booberies: function(newName){
    	this.set({name: newName});
    	console.log("I thought it was a costume ball");
    }
   
});



/*var provider2 = new Provider;
provider2.booberies('red');
provider2.set({name: 'green'}, {'validate': 'true'});
provider2.set({name: 'blue'}, {'validate': 'true'});*/

$(document).ready(function(){
	console.log("executed...");
		
	
	$('#provider-form').on("submit", function(event){
		console.log("clicked form...");
		event.preventDefault();
		
		var zipcode = $('#provider-form input[name=zipcode]').val();
		var state = $('#provider-form input[name=state]').val();
		var city = $('#provider-form input[name=city]').val();
		var provider = new Provider({zipcode:zipcode, state:state, city:city});
		
		provider.fetch({
	        success: function (provider) {
	        	console.log("success fetching provider via backbone...");
	        	console.log(provider);
	            alert(provider.toJSON());
	        },
	        error: function(xhr, textStatus, error){
	        	console.log("did we get an error");
				  console.log(xhr.statusText);
				  console.log(textStatus);
				  console.log(error);
	        }
	    });
		

		
	/*	var providerModel = {};
		providerModel.zipcode = $('#provider-form input[name=zipcode]').val();
		providerModel.state = $('#provider-form input[name=state]').val();
		providerModel.city = $('#provider-form input[name=city]').val(); 
		
		console.log(providerModel);
		providerModel = JSON.stringify(providerModel);

		var url = "/hosprice/rest/provider/zipcode/"+providerModel.zipcode;
		$.ajax(url, {
			crossDomain: true,
			contentType: "application/json",
			data: providerModel,
			type: "POST",
			success: function(data) {
				console.log("data was saved correctly");
				//var i, recommendations = $("[id^=result-]");
				//for(i = 0; i < recommendations.length; i++){
				//	$(recommendations[i]).fadeOut(2000).delay(1000).fadeIn(2000);
					
				//}
				
				console.log(modelData);
				$("[id^=result-]").each(function(){
						$(this).fadeOut(2000).delay(1000);
						renderNew
						console.log("id is: "+$(this).attr("id"));
						$(this).delay(3000);
				});
				
			},
			error: function(xhr, textStatus, error){
							  console.log(xhr.statusText);
							  console.log(textStatus);
							  console.log(error);
			}
		});
	*/	
		return false;
	});
	
	
});