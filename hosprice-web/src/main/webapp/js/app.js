var DataMap = function(options){
	var searchInputHospital = options.searchInputHospital;
	var searchInputDiagnosis = options.searchInputDiagnosis;
	var searchInputZipcode = options.searchInputZipcode;
	var searchInputState = options.searchInputState;
	var searchInputCity = options.searchInputCity;
	
	this.getSearchInputCity = function(){
		return searchInputCity;
	}
	
	this.sayHello = function(){
		console.log("why hello?");
	}
}

var Hosprice = function(options){
	this.dataMap = new DataMap({searchInputCity:'fartCity'});
	
	this.hospriceHello = function(){
		console.log("hosprice hello!");
	}
}

var hosprice = new Hosprice();
hosprice.dataMap.sayHello();
hosprice.hospriceHello();  
console.log(hosprice.dataMap.getSearchInputCity());  //using dynamically created getter

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

var ProviderCollection = Backbone.Collection.extend({
	  model: Provider,
	  zipcode: '36302',
	  url : function(){
			return "/hosprice/rest/provider/zipcode/"+this.zipcode;
		},
		initialize: function(){
			console.log("initializing the collection...");
			console.log(this.zipcode);
		}
});


ProviderView = Backbone.View.extend({
    tagName: "div",
    id: "results-container",
    template: _.template($("#template-recommendation").html()),
    initialize: function() {
		    this.model.bind("change", this.render, this);
		    this.render();
		  },

		  render: function() {
			  console.log("DId you fucking render");
			  console.log(this.model.attributes);
			  //this.$el.html(this.template(this.model.attributes));
			  $("#results-container").append(this.$el.html(this.template(this.model.attributes)));
			  return this;
		  }
});



/*
 * var provider2 = new Provider; provider2.booberies('red');
 * provider2.set({name: 'green'}, {'validate': 'true'}); provider2.set({name:
 * 'blue'}, {'validate': 'true'});
 */

$(document).ready(function(){
	console.log("executed...");
	
	//hosprice.dataMap.test();
	
	$('#provider-form').on("submit", function(event){
		console.log("clicked form...");
		event.preventDefault();
		//hosprice.dataMap.test();
		var zipcode2 = $('#provider-form input[name=zipcode]').val();
		var state = $('#provider-form input[name=state]').val();
		var city = $('#provider-form input[name=city]').val();
		
		var providerCollection = new ProviderCollection({zipcode:zipcode2});
		console.log("===== provider =====");
		console.log(providerCollection);
		providerCollection.zipcode = zipcode2;
		console.log(providerCollection.zipcode);
		console.log("===== provider =====");
		//var provider = new Provider({zipcode:zipcode, state:state, city:city});
		//var providerView = new ProviderView({model: provider});
		providerCollection.fetch({
	        success: function (result, options) {
	        	console.log("success fetching provider via backbone...");
	        	var myProvider = providerCollection.at(0);
	        	console.log(providerCollection);
	        	
	        },
	        error: function(xhr, textStatus, error){
	        	console.log("did we get an error");
				  console.log(xhr.statusText);
				  console.log(textStatus);
				  console.log(error);
	        }
	    });
		
		
	/*
	 * var providerModel = {}; providerModel.zipcode = $('#provider-form
	 * input[name=zipcode]').val(); providerModel.state = $('#provider-form
	 * input[name=state]').val(); providerModel.city = $('#provider-form
	 * input[name=city]').val();
	 * 
	 * console.log(providerModel); providerModel =
	 * JSON.stringify(providerModel);
	 * 
	 * var url = "/hosprice/rest/provider/zipcode/"+providerModel.zipcode;
	 * $.ajax(url, { crossDomain: true, contentType: "application/json", data:
	 * providerModel, type: "POST", success: function(data) { console.log("data
	 * was saved correctly"); //var i, recommendations = $("[id^=result-]");
	 * //for(i = 0; i < recommendations.length; i++){ //
	 * $(recommendations[i]).fadeOut(2000).delay(1000).fadeIn(2000);
	 * 
	 * //}
	 * 
	 * console.log(modelData); $("[id^=result-]").each(function(){
	 * $(this).fadeOut(2000).delay(1000); renderNew console.log("id is:
	 * "+$(this).attr("id")); $(this).delay(3000); }); }, error: function(xhr,
	 * textStatus, error){ console.log(xhr.statusText); console.log(textStatus);
	 * console.log(error); } });
	 */	
		return false;
	});
	
	
});