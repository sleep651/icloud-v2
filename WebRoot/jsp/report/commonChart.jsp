<%@ page language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
  String  theight = request.getParameter("theight");
  String  twidth  = request.getParameter("twidth");
  System.out.print("aaaaaaaaaheight"+theight+"bbbbbwidth"+twidth);

 %>

<!-- HTML5 -->  
<!DOCTYPE html>  
<html>  
    <head>  
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <!--<meta name="viewport" content="width=device-width,height=device-height, initial-scale=0.8, minimum-scale=0.8, maximum-scale=2"/>-->
    <title>chart</title>
		<style>
			svg>text[text-anchor="end"]>tspan{display:none;}
		</style>    
	  <link rel="stylesheet" href="${ctx}/components/chart/jquery.mobile-1.4.5.min.css"/>
    <script language="javascript" type="text/javascript" src="<c:url value="/components/chart/jquery-1.7.min.js"/>"></script>
	  <script language="javascript" type="text/javascript" src="<c:url value="/components/chart/highcharts.js"/>"></script>
	  <script language="javascript" type="text/javascript" src="<c:url value="/components/chart/chart.js"/>"></script>
	  <script language="javascript" type="text/javascript" src="${ctx}/components/chart/jquery.mobile-1.4.5.js"></script>
	  <script id="source" language="javascript" type="text/javascript">  
	        	var t_height='<%=theight%>',t_width='<%=twidth%>';
	        	var tlength;
		        var position=0;
	        	var final_var;
						$(document).ready(function(){
								   	 	$('#container').chart({
											  	color:${form.color},
													title:${form.title},
													type:${form.type},
													label:${form.label},
													json:${form.json}
											});
								 tlength = $(".highcharts-legend g.highcharts-legend-item").length;
								 resizeDiv();
								 
								});

			  //modified by wangchyg@sina.cn
	      function  call_load(H_var){ //H_var  -1 1
	        var H_var =  parseInt(H_var);
	      	if(tlength==1){
	      		
	      		}else{
					      	final_var=position+H_var;
					      	if(final_var<0){
					      			//-------------------------------------------wangcy---------------------------------
					      			$(".highcharts-legend g.highcharts-legend-item").not(":eq("+position+")").trigger('click');  
					      		   position=tlength;
					      		}else if(final_var>tlength){
					      			 $(".highcharts-legend g.highcharts-legend-item").not(":first").trigger('click');
					      		   position=0;
					      		}else if(final_var==tlength){
					      			 $(".highcharts-legend g.highcharts-legend-item").not(":eq("+position+")").trigger('click');
					      		   position=tlength;
					      		}else{
					      			  if(position==tlength){
					      			     $(".highcharts-legend g.highcharts-legend-item").not(":eq("+final_var+")").trigger('click') 
					      			  }else{
					      			  	//-------------------------------------------wangcy---------------------------------
					      			  	  if(Highcharts.charts[0].legend.allItems[position].visible){
					      			  	  	  //Highcharts.charts[0].legend.allItems[position].hide();
					      			  	  	   Highcharts.charts[0].legend.allItems[position].setVisible(0);
					      			  	  	}else{
					      			  	  	  // Highcharts.charts[0].legend.allItems[position].show();  
					      			  	  	  Highcharts.charts[0].legend.allItems[position].setVisible(1);
					      			  	  	}
					      			  	   if(Highcharts.charts[0].legend.allItems[final_var].visible){
					      			  	  	  Highcharts.charts[0].legend.allItems[final_var].hide();
					      			  	  	}else{
					      			  	  	   Highcharts.charts[0].legend.allItems[final_var].show();  
					      			  	  	}
					      			  	//-------------------------------------------wangcy---------------------------------	  
					      				 	 //$(".highcharts-legend g.highcharts-legend-item:eq("+position+")").trigger('click');  
					    	           //$(".highcharts-legend g.highcharts-legend-item:eq("+final_var+")").trigger('click'); 
					      				}
					   
					      				position=final_var;
					      		}
					      		resizeDiv();
	      			}
	    	}
	      function resizeDiv(){
	      	  		var t_chart =Highcharts.charts[0];
					      if(!t_width){
					      		 t_chart.setSize($("#container").width(),$("#container").height());
					       }else{
					      	  t_chart.setSize(t_width,t_height);
					      } 	
	      	}	
	    	$(function(){
	    		  	 $(".highcharts-legend g.highcharts-legend-item").not(":first").trigger('click');
	    		});
	        		
	    </script>	
    </head>  
    <body> 
    	 <div data-role="page">
        <div id="container" style="min-width: 300px;min-height:200px;margin: 0 auto">  </div>    
       </div>
        <script type="text/javascript">  
            //全局变量，触摸开始位置  
            var startX = 0, startY = 0;  
              
            //touchstart事件  
            function touchSatrtFunc(evt) {  
                try  
                {  
                    evt.preventDefault(); //阻止触摸时浏览器的缩放、滚动条滚动等  
                    var touch = evt.touches[0]; //获取第一个触点  
                    var x = Number(touch.pageX); //页面触点X坐标  
                    var y = Number(touch.pageY); //页面触点Y坐标  
                    //记录触点初始位置  
                    startX = x;  
                    startY = y;  
  
                    var text = 'TouchStart:（' + x + ', ' + y + '）';  
                   // document.getElementById("result").innerHTML = text;  
                }  
                catch (e) {  
                    alert('touchSatrtFunc：' + e.message);  
                }  
            }  
  
            //touchmove事件，这个事件无法获取坐标  
            function touchMoveFunc(evt) {  
                try  
                {  
                    evt.preventDefault(); //阻止触摸时浏览器的缩放、滚动条滚动等  
                    var touch = evt.touches[0]; //获取第一个触点  
                    var x = Number(touch.pageX); //页面触点X坐标  
                    var y = Number(touch.pageY); //页面触点Y坐标  
  
                     //  var text = 'TouchMove:(___x____' + x + '---, y---' + y + '--startX-'+startX+')';  
                    //判断滑动方向 
                    if(x<startX){   //left
                    	call_load(-1);
                    }else if(x>startX){   //right
                    	call_load(1);
                    }
                  // alert(text);
                }  
                catch (e) {  
                    alert('touchMoveFunc：' + e.message);  
                }  
            }  
  
            //touchend事件  
            function touchEndFunc(evt) {  
                try {  
                	  //evt.preventDefault(); //阻止触摸时浏览器的缩放、滚动条滚动等  
                    var text = 'TouchEnd';  
                }  
                catch (e) {  
                    alert('touchEndFunc：' + e.message);  
                }  
            }  
  
            //绑定事件  
            function bindEvent() {  
                document.addEventListener('touchstart', touchSatrtFunc, false);  
                document.addEventListener('touchmove', touchMoveFunc, false);  
                document.addEventListener('touchend', touchEndFunc, false);  
            }  
  
            //判断是否支持触摸事件  
            function isTouchDevice() {  
                try {  
                    document.createEvent("TouchEvent");  
                    bindEvent(); //绑定事件  
                }  
                catch (e) {  
                   // alert("不支持TouchEvent事件！" + e.message);  
                }  
            }  
            window.onload = isTouchDevice;  
    </script>  
    </body>  
</html>  