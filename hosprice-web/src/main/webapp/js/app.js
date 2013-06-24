

$(document).ready(function(){
	console.log("executed...");
	$("#back").click(function(){
		$("#search-result-container").fadeOut();
		
		$("#search-form").delay(500).fadeIn();
		alert("Yeah fucker");
		hosprice.providerListView.collection.reset();

	});
	//////////////////////////////////////////////
	var HospriceRouter = Backbone.Router.extend({

		  routes: {
			"dataMap":				"showDataMap",  
		    "help":                 "help",    // #help
		    "search/:query":        "search",  // #search/kiwis
		    "search/:query/p:page": "search",   // #search/kiwis/p7
		    "*other":				"defaultRoute"
		  },
		  
		  initialize: function(options){
			  console.log("Initializing router", options);
		  },

		  help: function() {
		    
		  },

		  search: function(query, page) {
		   
		  },
		  showDataMap: function(){
			  console.log("initialized router and executed showDataMap function");
		  }

		});



		
		//added for example
		var myProviders = [
		          	     {providerId: '100', name: 'Birshire Provider', zipcode: '33433'},
		          	     {providerId: '101', name: 'Baz Dallen Provider', zipcode: '55252'},
		          	     {providerId: '102', name: 'Mazata Provider', zipcode: '1651651'}
		          	     ];

		var Provider = Backbone.Model.extend({	
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
		        this.on("change:name", function(model){
		            var name = model.get("name"); 
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
			  zipcode: '',
			  state: '',
			  city: '',
			  diagnosis: '',
			  hospital: '',
			 
			  url : function(){
					  var params = $.param({zipcode:this.zipcode, state: this.state, city:this.city, diagnosis:this.diagnosis, hospital:this.hospital});  //probably an easier way to do this...
					  
					  var executeUrl ="/hosprice/rest/providers";
					  return executeUrl;
				},
				initialize: function(){
					console.log("initializing the collection...");
					console.log(this.zipcode);
				},
				parse: function(response){
					console.log(response);
					$(response).each(function(){ 
					    console.log("main >>>>", this);
					    var tempView = new ProviderView({model: this});
					    
					    //TODO: not sure if this is the best place to add points to map...
					   
					   
					   	var tempAddress = this.address1 + ", "+this.city+ ", "+this.state;

						geocoder = new google.maps.Geocoder();
						console.log("my address: ", tempAddress);
						geocoder.geocode( { 'address': tempAddress}, function(results, status) {
					      if (status == google.maps.GeocoderStatus.OK) {
					        map.setCenter(results[0].geometry.location);
					        map.setZoom(4);
					        var marker = new google.maps.Marker({
					            map: map,
					            position: results[0].geometry.location
					        });
					      } else {
					        alert("Geocode was not successful for the following reason: " + status);
					      }
					    });					    
					    
					 });
					return response;
				},
				display:function(){
					console.log("might as well try this too");
				},
				 codeAddress: function codeAddress(address) {
				 	console.log("executing codeAddres...");
				    //address = document.getElementById("address").value;
				    geocoder.geocode( { 'address': address}, function(results, status) {
				      if (status == google.maps.GeocoderStatus.OK) {
				        map.setCenter(results[0].geometry.location);
				        var marker = new google.maps.Marker({
				            map: map,
				            position: results[0].geometry.location
				        });
				      } else {
				        alert("Geocode was not successful for the following reason: " + status);
				      }
				    });
				  }
				
				
				
		});


		var ProviderView = Backbone.View.extend({
		    tagName: 'div',
		    id: 'search-result-container',
		    className: 'recommendationContainer well',
		    template: $("#template-recommendation").html(),
		    initialize: function() {
				    //this.model.bind("change", this.render, this);
				    //this.render();
			},

			render: function() {
						  console.log(this.model.attributes);
						  var templ = _.template(this.template);
						  this.$el.html(templ(this.model.toJSON()));
						  console.log("EL OF PROVIDER", this.$el);
						  var diagnosisId = "#diagnosis-label-groups-"+this.model.attributes.providerId;
						  console.log("DIAG ID", this.$el);
						  var delegationId = "diagnosis-related-groups-div-"+this.model.attributes.providerId;
						  

						  //needed to add listener to toggle info
						  this.$el.on('click', diagnosisId, function(){
						  	
						  	console.log($(this).find('[name="diagnosis_related_group_div"]'));
						  	$(this).next().toggle();  //don't ever move the element lol...so difficult
						  	
						  	
						  });
						  
						  return this;
			},
			deleteProvider: function(){
			
				this.model.destroy();  //delete model
				this.remove();	//delete view
			},
			events: {
				"click .delete": "deleteProvider"
			}
			
		});
		
		var ProviderListView = Backbone.View.extend({
			
			el: $("#search-result-container"),
			
			initialize: function(){
				this.collection = new ProviderCollection();
				//this.providerViewCollection = new Array();
				
				//display on add
				this.collection.on("add", this.renderProvider, this);
				this.collection.on("remove", this.removeProvider, this);
				this.collection.on("reset", this.render, this);
				this.collection.on("change", this.render, this);
			},
			render:function(){
				
				var that = this;
				var searchAgain = $("#search-again");
				//console.log("SEARCH AGAIN: ",searchAgain);
				var div = $("<div/>").append(searchAgain);
				
				this.$el.empty();
				this.$el.append(div);
				_.each(this.collection.models, function(item){
					that.renderProvider(item);
				}, this);
			},
			renderProvider: function(item){
				var providerView = new ProviderView({
					model: item
				});
				//this.providerViewCollection.add(providerView);
				this.$el.append(providerView.render().el);
				providerView.$el.css({position: 'relative', left: '800px', display: 'block', float: 'left', width: '100px', maxWidth: '175px'});
				providerView.$el.animate({left: 0},'slow').slideDown(2000);  //  .initially hides recommendation boxes
			},
			addProvider: function(e){//not sure if we really want to keep this, we won't be adding providers
				e.preventDefault();
				var formData = {};	//need to use the same name for ids as keys in the object to get this mapping to work
				
				$("#provider-form input").children("input").each(function(i, el){
					formData[el.id] = $(el).val();
					
					//providerCollection.push(formData);  Could call this later but this isn't what we really want to do
					this.collection.add(new Provider(formData));
				});
			},
			removeProvider: function(removedProvider){
				var removedProviderData = removedProvider.attributes;
				
				_.each(removedProvider, function(val, key){
					if(removedProviderData[key] === removedProvider.defaults[key]){
						delete removedProviderData[key];
					}
				});
				
				_.each(myProviders, function(provider){
					if(_.isEqual(provider, removedProviderData)){
						providers.splice(_.indexOf(providers, provider), 1);
					}
				});
			},
			events:{
				"click #add": "addProvider"
			}
		});
	
			
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
			this.providerListView = new ProviderListView();
			
			this.hospriceHello = function(){
				console.log("hosprice hello!");
			}
		}

		var hosprice = new Hosprice();
		hosprice.dataMap.sayHello();
		hosprice.hospriceHello();  

	
	console.log("done executing app setup");
	
	$('#provider-form').on("submit", function(event){
		console.log("clicked form...");
		event.preventDefault();

		$('#search-form').slideUp('slow');
		//$('#search-result-container').attr('display', 'block');
		

		var formZipcode = $('#provider-form input[name=zipcode]').val();
		var formState = $('#provider-form input[name=state]').val();
		var formCity = $('#provider-form input[name=city]').val();
		var formHospital = $('#provider-form input[name=hospital]').val();
		var formDiagnosis = $('#provider-form input[name=diagnosis]').val();
		
		
		//hosprice.providerListView.collection.reset();
		
		hosprice.providerListView.collection.zipcode = formZipcode;
		hosprice.providerListView.collection.fetch({
	        success: function (result, options) {
	        	console.log("success fetching provider via backbone...");
	        	
	        },
	        error: function(xhr, textStatus, error){
	        	console.log("did we get an error");
				  console.log(xhr.statusText);
				  console.log(textStatus);
				  console.log(error);
	        }
	    });
		//providerCollection.render();
		$('#search-result-container').slideDown('slow').delay(2000);
		return false;
	});
		
	//needed for safe paths, hitting back button etc
	 new HospriceRouter();
	 Backbone.history.start({pushState: true});
	
});