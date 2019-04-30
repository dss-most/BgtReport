var productResult = Array();

var orgResult = Array();
for(var i=0; i<data.length; i++) {
	if(typeof(orgResult[data[i].owner]) == 'undefined') {
		orgResult[data[i].owner] = data[i].totalAmount;
	} else {
		orgResult[data[i].owner] += data[i].totalAmount;
	}
}

var graphStore;

var gridStoreProduct;
var gridStoreOwner;
var gridColumnModel_BudgetUsed;
var gridColumnModel_BudgetAlloc;
var currentGridStore;
var currentGridColumnModel;
var currbCodeWeb = 0;


var tm;
var curData;
var curNode;
var curParent;




function showInfo(node) {
	$('#info').empty();
	$('#info').append("<b>" + node.name + "</b></br>");
	
	$('#info').append("งบที่ได้รับ = " + Ext.util.Format.number(node.data.totalAllocate, '0,000') + " บาท</br>");
	$('#info').append("งบที่ใช้ไป(รวมผูกพัน) = " + Ext.util.Format.number(node.data.totalAllocate-node.data.totalLeft, '0,000') + " บาท</br>");
	$('#info').append("งบที่เหลือ = " + Ext.util.Format.number(node.data.totalLeft, '0,000') + " บาท</br>");
	
}

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
	    	  
	    	if(node) {
	    		tm.enter(node);  
	    	}
	        
	        if(node.data != undefined && node.data.childNode != undefined) {
	        	$.getJSON('json/TreeMapSubProjectUsage?id='+node.id+'&bCodeWeb='+currbCodeWeb,function(data){
	    			$('#info').empty();
	    			$('#info').append("<b>ค." + node.data.subProjectName + " (" + node.data.subProjectAbbr + ") </b><br/>");
	    			
	    			
	    			$.each(data.children, function(index, val){
	    				$('#info').append(val.name + " = ");
	    				$('#info').append(Ext.util.Format.number(val.data.$area, '0,000') + " บาท</br>");
	    			});
	    			
	    			if(data.data.budgetReservedList != undefined) {
	    				$('#info').append('<div style=\'margin-top:5px;\'><b>รายการที่ผูกพันงบประมาณแล้วและรอการเบิกจ่าย </b>');
	    				
	    				$.each(data.data.budgetReservedList, function(index, val) {
	    					$('#info').append('<div style=\'margin-bottom:5px;\'>');
	    					$('#info').append('<u>' + val.name + '</u> = ');
	    					$('#info').append(Ext.util.Format.number(val.data.amount, '0,000') + ' บาท</br>');
	    					var line;
	    					if(val.data.description == null) {
	    						line = val.data.subject;
	    					} else {
	    						line = val.data.description;
	    					}
	    					$('#info').append(line + '<br/>');
	    					$('#info').append('</div>');
	    				});
	    				$('#info').append('</div>');
	    			}
	    			
	    		});
	        } else {
	        	showInfo(curNode);
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
	
	
  
	// initial treemap!
	$.get('json/TreeMapsBudgetUsed', {year: yearInput, bCodeWeb: bCodeWebInput }, function(data) {
		curData=data;
		curNode=data;
	    tm.loadJSON(data);  
	    tm.refresh();  
		
	    showInfo(data);
	});
	
	//add event to the back button
	var back = $jit.id('back');
	$jit.util.addEvent(back, 'click', function() {
		tm.out();
		
		curNode = $jit.json.getParent(curData, curNode.id);
		
		if(curNode.data != undefined) {
			showInfo(curNode);
		}
	    
		
	    if(curNode != undefined && curNode.data != undefined && curNode.data.id != 0) {
	    	$('#bCodeWeb').attr('disabled', 'disabled');
	    } else {
	    	$('#bCodeWeb').removeAttr('disabled');
	    }
	});
	
});


function changeBudget(){
	var selected = $('#bCodeWeb').val();
	currbCodeWeb = selected;
	
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
	    
	    showInfo(data);
	});
};