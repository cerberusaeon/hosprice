

$(document).ready(function(){
	console.log("executed...");

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
			  zipcode: '',
			  state: '',
			  city: '',
			  diagnosis: '',
			  hospital: '',
			  url : function(){
					  var params = $.param({zipcode:this.zipcode, state: this.state, city:this.city, diagnosis:this.diagnosis, hospital:this.hospital});  //probably an easier way to do this...
					  var executeUrl ="/hosprice/rest/provider/hospital?"+params;
					  return executeUrl;
				},
				initialize: function(){
					console.log("initializing the collection...");
					console.log(this.zipcode);
				},
				parse: function(response){
					console.log(response);
					$(response).each(function(){ 
					    console.log("main", this);
					    var tempView = new ProviderView({model: this});
					 });
					return response;
				},
				display:function(){
					console.log("might as well try this too");
				}
		});


		var ProviderView = Backbone.View.extend({
		    tagName: 'div',
		    id: 'results-container',
		    className: 'recommendationContainer',
		    template: $("#template-recommendation").html(),
		    initialize: function() {
				    //this.model.bind("change", this.render, this);
				    //this.render();
				  },

			render: function() {
				  console.log(this.model.attributes);
				  var templ = _.template(this.template);
				  this.$el.html(templ(this.model.toJSON()));
				  return this;
				  }
		});
		
		var ProviderListView = Backbone.View.extend({
			
			el: $("#results-container"),
			
			initialize: function(){
				this.collection = new ProviderCollection(myProviders);
				this.render();
			},
			render:function(){
				var that = this;
				_.each(this.collection.models, function(item){
					that.renderProvider(item);
				}, this);
			},
			renderProvider: function(item){
				var providerView = new ProviderView({
					model: item
				});
				this.$el.append(providerView.render().el);
			},
			addProvider: function(){
				var formData = {};
				
				$("#provider-form input").children("input").each(function(i, el){
					formData[el.id] = $(el).val();
					
					//providerCollection.push(formData);  Could call this later but this isn't what we really want to do
					this.collection.add(new Provider(formData));
				});
			}
		});
	
		
	
	//////////////////////////////////////////////
		
		
		
		
	//////////////////// Temp Model code for example ////////////////////////////
	
		
//	var myProvider = new Provider({
//		providerId: 34423,
//		legacyId: '4324',
//		name: 'Calloway Provider',
//		address1: '198 Halpine Rd',
//		address2: '#1246',
//		city: 'Rockville',
//		state: 'MD',
//		zipcode: '20852',
//		regionName: 'Montgomery',
//		regionState: 'MD'
//	});	
//	
//	myProviderView = new ProviderView({
//		model: myProvider
//	});
//		
//	$("#results-container").html(myProviderView.render().el);
		
		var myProviderListView = new ProviderListView();
	
	
		
	////////////////////End Temp Model code /////////////////////////////////////	
	
	
	console.log("done executing app setup");
	
	$('#provider-form').on("submit", function(event){
		console.log("clicked form...");
		event.preventDefault();
		var formZipcode = $('#provider-form input[name=zipcode]').val();
		var formState = $('#provider-form input[name=state]').val();
		var formCity = $('#provider-form input[name=city]').val();
		var formHospital = $('#provider-form input[name=hospital]').val();
		var formDiagnosis = $('#provider-form input[name=diagnosis]').val();
		
		var providerCollection = new ProviderCollection({zipcode:formZipcode, state:formState, city:formCity, hospital:formHospital, diagnosis:formDiagnosis});
		console.log("===== provider =====");
		console.log(providerCollection);
		providerCollection.zipcode = formZipcode;
		console.log(providerCollection.zipcode);
		console.log("===== provider =====");
		providerCollection.fetch({
	        success: function (result, options) {
	        	console.log("success fetching provider via backbone...");
	        	var myProvider = providerCollection.at(0);
	        	//providerCollection.
	        	console.log(providerCollection);
	        	
	        },
	        error: function(xhr, textStatus, error){
	        	console.log("did we get an error");
				  console.log(xhr.statusText);
				  console.log(textStatus);
				  console.log(error);
	        }
	    });
		providerCollection.render();
		return false;
	});
	
	//needed for safe paths, hitting back button etc
	 new HospriceRouter();
	 Backbone.history.start({pushState: true});
	
});