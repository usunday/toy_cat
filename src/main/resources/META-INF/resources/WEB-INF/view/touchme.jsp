<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>Dynamic Vector Circle</title>
	<script src="/webjars/jquery/3.0.0/dist/jquery.js"></script>
    <script type="text/javascript">
    	var serverip;
		$(document).ready(function() {
			//createCircle($(window).width()/2, $(window).height()/2);
			//run pin init
			serverip = "${serverip}"; 
			console.log("serverIP:"+serverip);
			createCircles($(window).width()/2, $(window).height()/2, ${circles}, ${distance});
			$(window).resize(function(){
				moveCircles($(window).width()/2, $(window).height()/2, ${circles}, ${distance});
			});
		});
        var svgNS = "http://www.w3.org/2000/svg";
		var myCircles = new Array();
		
		function init(pincnt){
			var url = "http://"+serverip+"/init/"+pincnt;
			$.post(url);  
		}
		function sendSignal(number, direction){
			number = number.substring(8,number.length);
			var url = "http://"+serverip+"/pinCnt/"+number+"/"+direction;
			$.post(url);  
		}
		
		function getColor(color){
			if(color == 0)
				return "YELLOW"
			else if(color == 1)
				return "RED";
			else if(color == 2)
				return "GREEN";
			else if(color == 3)
				return "BLUE";
			else
				return "BLACK";
		}
		
		function onMouseDown(evt){
			console.log(evt.target.id);
			var selectedCircle = document.getElementById(evt.target.id);
			selectedCircle.setAttributeNS(null, "fill", "white");
			sendSignal(evt.target.id,"UP");
		}
		function onMouseUp(evt){
			console.log(evt.target.id);
			var selectedCircle = document.getElementById(evt.target.id);
			selectedCircle.setAttributeNS(null, "fill", "red");
			sendSignal(evt.target.id,"DOWN");
		}
        function createCircle(x,y)
        {
			console.log("create:"+x+"|"+y);
            var myCircle = document.createElementNS(svgNS,"circle"); //to create a circle, for rectangle use rectangle
            myCircle.setAttributeNS(null,"id","mycircle");
            myCircle.setAttributeNS(null,"cx",x);
            myCircle.setAttributeNS(null,"cy",y);
            myCircle.setAttributeNS(null,"r",50);
            myCircle.setAttributeNS(null,"fill","black");
            myCircle.setAttributeNS(null,"stroke","none");
            document.getElementById("mySVG").appendChild(myCircle);
        }
		
		function createCircles(x,y, count, distance){
			console.log("createCircles:"+x+"|"+y+"|"+count+"|"+distance);
			var i=0, pos;
			var angle = Math.PI*2/count;
			for(i=0; i<count; i++){
				console.log("loop:"+x+"|"+y+"|"+i);
				pos = angle*(i+1);
				myCircles[i] = document.createElementNS(svgNS,"circle"); //to create a circle, for rectangle use rectangle
				myCircles[i].setAttributeNS(null,"id","myCircle"+i);
				myCircles[i].setAttributeNS(null,"cx",
											x + (distance * Math.cos(pos)));
				myCircles[i].setAttributeNS(null,"cy",
											y + (distance * Math.sin(pos)));
				myCircles[i].setAttributeNS(null,"r",
											distance * Math.cos((Math.PI-angle)/2));
				myCircles[i].setAttributeNS(null,"fill","red");
				myCircles[i].setAttributeNS(null,"stroke","none");
// 				myCircles[i].addEventListener("mousedown", onMouseDown);
// 				myCircles[i].addEventListener("mouseup", onMouseUp);
				myCircles[i].addEventListener("touchstart", onMouseDown);
				myCircles[i].addEventListener("touchend", onMouseUp);
				document.getElementById("mySVG").appendChild(myCircles[i]);
			}
        }

		function moveCircles(x,y,count,distance){
			console.log("resize:"+x+"|"+y+"|"+count);
			var i=0, pos;
			var angle = Math.PI*2/count;
			for(i=0; i<count; i++){
				console.log("loop:"+x+"|"+y+"|"+i);
				pos = angle*(i+1);
				myCircles[i].setAttributeNS(null,"cx",
											x + (distance * Math.cos(pos)));
				myCircles[i].setAttributeNS(null,"cy",
											y + (distance * Math.sin(pos)));
				myCircles[i].setAttributeNS(null,"r",
											distance * Math.cos((Math.PI-angle)/2));
				myCircles[i].setAttributeNS(null,"fill","green");
			}
		}

    </script>
</head>
<h2>circles: ${circles}</h2>
<h2>distance: ${distance}</h2>
<body>
	<input type="button" id="init_btn" onclick="init(${circles});" value="init" style=width:60;height:45;/>
    <svg id="mySVG" width='100%' height='100%' xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" style="background-color: blue;">
    </svg>
</body>
</html>