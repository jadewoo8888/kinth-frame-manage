<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ include file="../shared/taglib.jsp"%>


<html>

<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en" class="no-js">
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<meta charset="utf-8" />
<title>kinth | 登录</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1.0" name="viewport" />
<meta content="" name="description" />
<meta content="" name="author" />
<meta name="MobileOptimized" content="320">


<%@ include file="../shared/importCss.jsp"%>
<%@ include file="../shared/importJs.jsp"%>


</head>

<body>

	<div class="jumbotron">
		<div class="container">
			<p style="text-align: center;">选择角色</p>
			<p></p>
			
			<c:forEach items="${userAuth.roles}" var="item" varStatus="status">
				<p>
					<c:choose>
						<c:when test="${status.index % 2 == 0}">
						<a href="<c:url value='/user/selRoleLogin/${ item.id }/${userAuth.id}/${userAuth.loginName }/${userAuth.realName }/'/>" class="btn btn-primary btn-lg btn-block" style="font-size: 50px" role="button">
							<span class="glyphicon glyphicon-user"></span> ${ item.name }
						</a>
						</c:when>
						<c:otherwise>
						<a href="<c:url value='/user/selRoleLogin/${ item.id }/${userAuth.id}/${userAuth.loginName }/${userAuth.realName }/'/>" class="btn btn-default btn-lg btn-block" style="font-size: 50px" role="button">
							<span class="glyphicon glyphicon-user"></span> ${ item.name }
						</a>
						</c:otherwise>
					</c:choose>
				</p>

			</c:forEach>
			
		</div>
	</div>

</body>

</html>