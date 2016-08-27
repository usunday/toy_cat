<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<script>
window.onload=function() {
    var cnvs = document.getElementById("cnvs");
    if (cnvs.getContext) {
        var ctx = cnvs.getContext('2d');
        
        ctx.beginPath();   
        // (동그라미) 50:50을 기준으로 반지름 40짜리 원을 그림
        ctx.arc(50,50,40,0,Math.PI*2,true);
        ctx.moveTo(150,150); // 좌표이동
        ctx.arc(150,150,40,0,Math.PI*2,true); 
        ctx.stroke();
    }
    else alert('canvas를 지원하지 않는 브라우저입니다.');
}
</script>
<title>Insert title here</title>
</head>
<body>
controlpage<br>
<h2>circles: ${circles}</h2>
<h2>distance: ${distance}</h2>
<canvas id="cnvs" width="1000" height="1000" style="background-color: ghostwhite; border: 1px solid black;"></canvas>
</body>
</html>