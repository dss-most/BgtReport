
var URL_ROOT = '/BgtReport/';

function myUrl(url) {
	return escape(URL_ROOT + url);
}

function escapeSlash(s) {
	
}

Handlebars.registerHelper("formatDecimal", function(number) {
	if(number == null || isNaN(number)) return "0.00";
	return $.formatNumber(number, {format:"#,###.00", locale:"us"});
});

Handlebars.registerHelper("formatDecimalSum", function(number1, number2) {
	if(number1 == null || isNaN(number1)) return "0.00";
	if(number2 == null || isNaN(number2)) return "0.00";
	
	return $.formatNumber(number1 + number2, {format:"#,###.00", locale:"us"});
});

Handlebars.registerHelper("formatShortDateBuddhist", function(date) {
	if(date == null || isNaN(date)) return null;
	return $.format.date(date,"d MMM yy");
});

/** Model **/ 
var Page = Backbone.Model.extend({
	defaults: {
		"totalPages" : 0,
		"totalElements": 0,
		"firstPage": false,
		"lastPage" : false,
		"currentPage" : 0
	},
	
	outputJSON: function() {
		var out = [];
		var i, firstSkipIndex, lastSkipIndex;
		var currentPage = this.get('currentPage');
		var totalPages = this.get('totalPages');
		var floorPage = Math.floor(currentPage/10) * 10 ;
		
		if(totalPages > 10 && currentPage >= 10) {
			//console.log("firstSkip");
			firstSkipIndex = currentPage-10+1;
		}
		
		//console.log(totalPages);
		//console.log(floorPage+10);
		var lastPage = totalPages < floorPage+10 ? totalPages : floorPage+10;   
		
		for(i = floorPage; i< lastPage; i++) {
			////console.log(i);
			var isCurrentPage = (currentPage == i);
			out.push({index: i+1, cssClass: isCurrentPage?"active":""});
		}
		
		if((totalPages  - currentPage) > 10) {
			//console.log("lastSkip");
			lastSkipIndex = currentPage+11;
		}
		
		var isOnePage = (totalPages <= 1);
		return {isOnePage: isOnePage, totalPages: totalPages,
			firstSkipIndex: firstSkipIndex, lastSkipIndex: lastSkipIndex, out: out,
			totalElements: this.get('totalElements')};
	}
});

var SubProjectBudget = Backbone.Model.extend({
	idAttribute: "id",
	urlRoot: myUrl('ProjectBudget')
});


var BudgetUsage = Backbone.Model.extend({
	idAttribute: "id",
	urlRoot : myUrl('BudgetUsage')
});

var PurchaseRequest = Backbone.Model.extend({
	idAttribute: "id",
	urlRoot : myUrl('PurchaseRequest')
});

var SubProject = Backbone.Model.extend({
	idAttribuge: "id",
	urlRooe : myUrl('SubProject')
});

/** Collection **/
var SubProjectBudgetCollection = Backbone.Collection.extend({
	model: SubProjectBudget
});

var BudgetUsageCollection = Backbone.Collection.extend({
	model: BudgetUsage,
	parse: function(response) {
		this.page = new Page({
			totalPages : response.totalPages,
			totalElements : response.totalElements,
			lastPage : response.lastPage,
			firstPage : response.firstPage,
			currentPage : response.number
		});
		return response.content;
	}
});

var PurchaseRequestCollection = Backbone.Collection.extend({
	model : PurchaseRequest,
	parse : function(response) {
		this.page = new Page({
			totalPages : response.totalPages,
			totalElements : response.totalElements,
			lastPage : response.lastPage,
			firstPage : response.firstPage,
			currentPage : response.number
		});
		return response.content;
	}
});

var SubProjectCollection = Backbone.Collection.extend({
	model : SubProject,
	parse : function(response) {
		return response;
	}
});

