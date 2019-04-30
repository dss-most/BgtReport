var productResult = Array();

var orgResult = Array();
for(var i=0; i<data.length; i++) {
	if(typeof(orgResult[data[i].owner]) == 'undefined') {
		orgResult[data[i].owner] = data[i].totalAmount;
	} else {
		orgResult[data[i].owner] += data[i].totalAmount;
	}
}

function generateData(dict) {
	var jsonData = [];
	
	for(key in dict) {
		jsonData.push({
			name: key,
			data1: dict[key]
		});
	}
	
	return jsonData;
};

var graphStore;

var gridStoreProduct;
var gridStoreOwner;
var gridColumnModel_BudgetUsed;
var gridColumnModel_BudgetAlloc;
var currentGridStore;
var currentGridColumnModel;


var tm;
var curData;
var curNode;
var curParent;


function storeMode(i) {
	if(i == 1) {
		Ext.getCmp('grid').reconfigure(gridStoreProduct, currentGridColumnModel);
		
		currentGridStore = gridStoreProduct;
		
	} else if(i == 2) {
		Ext.getCmp('grid').reconfigure(gridStoreOwner, currentGridColumnModel);
		
		currentGridStore = gridStoreOwner;
	}
};

function columnMode(i) {
	if(i == 1) {
		Ext.getCmp('grid').reconfigure(currentGridStore, gridColumnModel_BudgetAlloc);
		currentGridColumnModel = gridColumnModel_BudgetAlloc;
		
	} else if(i == 2) {
		Ext.getCmp('grid').reconfigure(currentGridStore, gridColumnModel_BudgetUsed);
		currentGridColumnModel = gridColumnModel_BudgetUsed;
	}
}

function collapseAll() {
	
	
}

function expandAll() {
	
}

function showInfo(node) {
	$('#info').empty();
	$('#info').append(node.name + "</br>");
	
	$('#info').append($('#info').append("งบที่ได้รับ = " + Ext.util.Format.number(node.data.totalAllocate, '0,000') + " บาท</br>"));
	$('#info').append($('#info').append("งบที่ใช้ไป(รวมผูกพัน) = " + Ext.util.Format.number(node.data.totalAllocate-node.data.totalLeft, '0,000') + " บาท</br>"));
	$('#info').append($('#info').append("งบที่เหลือ = " + Ext.util.Format.number(node.data.totalLeft, '0,000') + " บาท</br>"));
	
}

Ext.require('Ext.chart.*');
Ext.require('Ext.layout.container.Fit');

Ext.onReady(function () {
	
	
	tm = new $jit.TM.Squarified({  
	    //where to inject the visualization  
	    injectInto: 'infovis',  
	    //show only one tree level  
	    levelsToShow: 3,  
	    //parent box title heights  
	    titleHeight: 15,  
	    //enable animations  
	    animate: animate,  
	    //use canvas text  
	    Label: {  
	      overridable: true,  
	      type: labelType,  
	      size: 9,  
	      family: 'Tahoma'  
	    },  
	    //box offsets  
	    offset: 1,  
	    //Attach left and right click events  
	    Events: {  
	      enable: true,  
	      onClick: function(node) {  

	    	curNode = node;
	    	
	    	if(node.id != 0) {
	    		$('#bCodeWeb').attr('disabled', 'disabled');
	    	}
	    	  
	    	if(node) tm.enter(node);  
	        
	        if(node.data.childNode != undefined) {
	        	$.getJSON('json/TreeMapSubProjectUsage?id='+node.id+'&bCodeWeb=2',function(data){
	    			$('#info').empty();
	    			$('#info').append("ค." + node.data.subProjectName + " (" + node.data.subProjectAbbr + ") <br/>");
	    			
	    			
	    			$.each(data.children, function(index, val){
	    				$('#info').append(val.name + " = ");
	    				$('#info').append(Ext.util.Format.number(val.data.$area, '0,000') + " บาท</br>");
	    			});
	    			
	    			if(data.data.budgetReservedList != undefined) {
	    				$('#info').append('<b>รายการที่ผูกพันงบประมาณแล้วและรอการเบิกจ่าย </b><br/>');
	    				$.each(data.data.budgetReservedList, function(index, val) {
	    					$('#info').append(val.name + " = ");
	    					$('#info').append(Ext.util.Format.number(val.data.amount, '0,000') + " บาท</br>");
	    					var line;
	    					if(val.data.description == null) {
	    						line = val.data.subject;
	    					} else {
	    						line = val.data.description;
	    					}
	    					$('#info').append(line + "<br/><br/>");
	    				});
	    			}
	    			
	    		});
	        } else {
	        	
	        }
	        
	        
	      },  
	      onRightClick: function() {  
	        tm.out();
	       
	        curNode = $jit.json.getParent(curData, curNode.id);
	        
	        showInfo(curNode);
	        
	        if(tm.root != 0) {
		    	$('#bCodeWeb').attr('disabled', 'disabled');
		    } else {
		    	$('#bCodeWeb').removeAttr('disabled');

		    }
	        
	      }
	    },  
	    duration: 1000,  
	    //Enable tips  
	    Tips: {  
	      enable: true,  
	      //add positioning offsets  
	      offsetX: 20,  
	      offsetY: 20,  
	      //implement the onShow method to  
	      //add content to the tooltip when a node  
	      //is hovered  
	      onShow: function(tip, node, isLeaf, domElement) {  
	        var html = "<div class=\"tip-title\">" + node.name   
	          + "</div><div class=\"tip-text\">";
	        
	        var data = node.data;
	        var totalLeftStr = Ext.util.Format.number(data.totalLeft, '0,000');
	        var totalAllocatetStr = Ext.util.Format.number(data.totalAllocate, '0,000');
	        
	        html += data.subProjectName + "ได้รับงบประมาณรวมทั้งสิ้น " + totalAllocatetStr + " บาท " +
	        		"<br/>ปัจจุบันมีเงินคงเหลือ " + totalLeftStr 
	        	+ " บาท (" +  data.percentLeft + "%)"; 
	        
	        tip.innerHTML =  html;   
	      }    
	    },
	    //Add the name of the node in the correponding label  
	    //This method is called once, on label creation.  
	    onCreateLabel: function(domElement, node){  
	        domElement.innerHTML = node.name;  
	        var style = domElement.style;  
	        style.display = '';  
	        style.border = '1px solid transparent';  
	        domElement.onmouseover = function() {  
	          style.border = '2px solid #9FD4FF';  
	        };  
	        domElement.onmouseout = function() {  
	          style.border = '1px solid transparent';  
	        };  
	    }  
	});
	
	
	graphStore = Ext.create('Ext.data.JsonStore', {
		fields:['name', 'data1'],
		data: generateData(productResult)
	});
	
	gridStoreProduct = Ext.create('Ext.data.JsonStore', {
		fields: ['subProjectAbbr', 'totalAllocate', 'totalCredited',
		         'totalDebited', 'totalAmount', 'product', 'owner', 'subProjectName',
		         'totalUsed', 'totalLeft', 'totalBudgetReserved', 'percentTotalLeft'],
		data: data,
		groupField: 'product'
		
	});
	
	gridStoreOwner = Ext.create('Ext.data.JsonStore', {
		fields: ['subProjectAbbr', 'totalAllocate', 'totalCredited',
		         'totalDebited', 'totalAmount', 'product', 'owner', 'subProjectName',
		         'totalUsed', 'totalLeft','totalBudgetReserved', 'percentTotalLeft'],
		data: data,
		groupField: 'owner'
		
	});
	
	gridColumnModel_BudgetAlloc = [{
		text: 'ผลผลิต',
		dataIndex: 'product',
		hidden: true 
	},{
		text: 'โครงการ', dataIndex:'subProjectAbbr', width: 65
	},{
		text: 'สำนัก/โครงการ', dataIndex:'owner', width: 70
	},{
		text: 'งปม.ได้รับ', dataIndex:'totalAmount', align: 'right', renderer: currencyRender,
		summaryType: 'sum', 
		summaryRenderer: function(value, summaryData, dataIndex) {
            return Ext.util.Format.number(value, '0,000');
        }
	},{
		text: 'งปม.รับโอน', dataIndex:'totalCredited', align: 'right', renderer: currencyRender,
		summaryType: 'sum', 
		summaryRenderer: function(value, summaryData, dataIndex) {
            return Ext.util.Format.number(value, '0,000');
        }
	},{
		text: 'งปม.โอนออก', dataIndex:'totalDebited', align: 'right', renderer: currencyRender,
		summaryType: 'sum', 
		summaryRenderer: function(value, summaryData, dataIndex) {
            return Ext.util.Format.number(value, '0,000');
        }
	},{
		text: 'งปม.สุทธิ', dataIndex:'totalAllocate', align: 'right', renderer: currencyRender,
		summaryType: 'sum', 
		summaryRenderer: function(value, summaryData, dataIndex) {
            return Ext.util.Format.number(value, '0,000');
        }
	}];
	
	gridColumnModel_BudgetUsed = [{
		text: 'ผลผลิต',
		dataIndex: 'product',
		hidden: true 
	},{
		text: 'โครงการ', dataIndex:'subProjectAbbr', width: 75
	},{
		text: 'สำนัก/โครงการ', dataIndex:'owner', width: 80
	},{
		text: 'งปม.สุทธิ', dataIndex:'totalAllocate', align: 'right', renderer: currencyRender,
		summaryType: 'sum',  width: 85,
		summaryRenderer: function(value, summaryData, dataIndex) {
            return Ext.util.Format.number(value, '0,000');
        }
	},{
		text: 'เบิกจ่ายแล้วสุทธิ', dataIndex:'totalUsed', align: 'right', renderer: currencyRender,
		summaryType: 'sum',   width: 85,
		summaryRenderer: function(value, summaryData, dataIndex) {
            return Ext.util.Format.number(value, '0,000');
        }
	},{
		text: 'ผูกพันงปม.แล้ว', dataIndex:'totalReserved', align: 'right', renderer: currencyRender,
		summaryType: 'sum',   width: 85,
		summaryRenderer: function(value, summaryData, dataIndex) {
            return Ext.util.Format.number(value, '0,000');
        }
	},{
		text: 'คงเหลือ', dataIndex:'totalLeft', align: 'right', renderer: currencyRender,
		summaryType: 'sum',   width: 85,
		summaryRenderer: function(value, summaryData, dataIndex) {
            return Ext.util.Format.number(value, '0,000');
        }
	}, {
		text: 'คงเหลือ (%)', dataIndex:'percentTotalLeft', align: 'right', 
		renderer: percentRender,  width: 75
	}];
	
	function currencyRender(val) {
		var formatedVal = Ext.util.Format.number(val, '0,000');
		if(val < 0) {
			return '<span style="color:red">('+formatedVal+')</span>';
		} else {
			return formatedVal;
		}
	};
	
	function percentRender(val) {
		var formatedVal = Ext.util.Format.number(val, '0,000.00');
		if(val < 0) {
			return '<span style="color:red">('+formatedVal+'%)</span>';
		} else {
			return formatedVal+'%';
		}
	}
	
	
	currentGridColumnModel = gridColumnModel_BudgetUsed;
	currentGridStore = gridStoreProduct;
	
	var grid = Ext.create('Ext.grid.Panel', {
		id: 'grid',
		renderTo: 'gridDiv',
		height: 600,
		store: currentGridStore,
		columns: currentGridColumnModel,
		features: [{
			ftype: 'groupingsummary',
			startCollapsed: true,
			groupHeaderTpl: '{name}  ({rows.length} โครงการ)'
		}]
	});
	
	grid.on('itemclick', function(view, record, item, index, e, opt) {
		$('#subProjectSummary').empty();
		$('#subProjectSummary').append('<b>ค.'+record.get('subProjectName')+' (' +
				record.get('subProjectAbbr') + ')</b>'  + index + '<br/>');
		//console.log(record);
		
		$.getJSON('json/subProjectBudgetAllocationSummary?id='+record.get('subProjectId'), function(data){
			
			$('#subProjectSummary').append('งบประมาณที่ได้รับ<br/>');
			
			
			$.each(data.budgetAllocationSet, function(index, ba) {
				if(ba.amount > 0) {
					$('#subProjectSummary').append(ba.budgetCode.name + '=' + ba.amount +'<br/>');
					
					if(ba.budgetAllocationItemSet != undefined) {
						
						$.each(ba.budgetAllocationItemSet, function(index, bai){
							//console.log('testing');
							$('#subProjectSummary').append('  >' + bai.description + '(' + bai.qty + ' ' + bai.uom + ')'  + bai.amount + '<br/>');
							
							if(bai.debitedBudgetSet != undefined) {
								$.each(bai.debitedBudgetSet, function(index, debit) {
									$('#subProjectSummary').append('  > - โอนออก ' + debit.amount + '</br>');
								});
							}
							
						});
					}
				}
			});
			
			
			$.each(data.budgetAllocationSet, function(index, ba) {
				$.each(ba.creditedBudgetSet, function(index, credit) {
					$('#subProjectSummary').append(' + ' + credit.id 
							+ ' = ' + credit.amount + '<br/>');					
					if(credit.budgetAllocationItem != undefined) {
							var bai = credit.budgetAllocationItem;
							$('#subProjectSummary').append('  +>' + bai.description + '(' + bai.qty + ' ' + bai.uom + ')'  + bai.amount + '<br/>');
							
					}
					
				});
				
				$.each(ba.debitedBudgetSet, function(index, debit) {
					$('#subProjectSummary').append(' - ' + debit.id 
							+ ' = ' + debit.amount + '<br/>');
				});
			});
		});
	});
	
    
	// initial treemap!
	$.get('json/TreeMapsBudgetUsed', {year: yearInput, bCodeWeb: bCodeWebInput }, function(data) {
		curData=data;
	    tm.loadJSON(data);  
	    tm.refresh();  
		
	    showInfo(data);
	});
	
	//add event to the back button
	var back = $jit.id('back');
	$jit.util.addEvent(back, 'click', function() {
	    tm.out();
	    
	    curNode = $jit.json.getParent(curData, curNode.id);
	    
	    showInfo(curNode);
	    if(tm.clickedNode.id != 0) {
	    	$('#bCodeWeb').attr('disabled', 'disabled');
	    } else {
	    	$('#bCodeWeb').removeAttr('disabled');
	    }
	});
	
});


function changeBudget(){
	var selected = $('#bCodeWeb').val();
	
	$.get('json/TreeMapsBudgetUsed', {year: yearInput, bCodeWeb: selected }, function(data) {
		curData = data;
		// if tm.clickNode is not in data
		var curNode = tm.clickedNode;
		
		if(curNode != null) {

			var subtree = $jit.json.getSubtree(data, curNode.id);
			
			if(subtree == null) {
				tm.out();
				tm.out();
			} 
		}
		
			
		tm.loadJSON(data);  
	    tm.refresh();  
	
		
	});
};