<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Error</title>
</head>
<body>
<h1>Thông báo</h1>
<div style="color: red;">${errorMessage}</div>
<p>Vui lòng liên hệ quản trị viên nếu bạn cần truy cập trang này.</p>
<a href="${pageContext.request.contextPath}/homePage">Quay lại trang chủ</a>
</body>
</html>
