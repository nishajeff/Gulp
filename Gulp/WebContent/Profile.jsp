<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
  <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<title>UserSearch</title>
</head>
<body>
<nav class="navbar navbar-default">
  <div class="container-fluid">
    <div class="navbar-header">
      <a class="navbar-brand" href="Home.html">Gulp</a>
    </div>
    <div>
      <ul class="nav navbar-nav">
      <li><a href="review.jsp">Enter Review</a></li>
      <li><a href="restaurants.jsp">List of Restaurants</a></li>
       <li class="active"><a href="#">Profile Search</a></li>
       <li><a href="EditProfile.jsp">Edit Profile</a></li>
      <li><a href="EditRes.jsp">Edit Restaurant</a></li>
      <li><a href="Change.jsp">Change Review</a></li>
      </ul>
    </div>
  </div>
</nav>
<h3 align="center">Welcome To Profile Search.Enter your user ID:</h3>
<div align="center">
<form action="Search" method="post">
<label >User ID: </label>
<input  type="text" name="userID"  ><br>
<label>&nbsp;</label>
<input type="submit" value="Search" id="submit">
</form>
</div>
</body>
</html>