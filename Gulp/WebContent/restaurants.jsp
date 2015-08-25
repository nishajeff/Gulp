<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Gulp -- Restaurants</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
  <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
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
      <li class="active"><a href="#">List of Restaurants</a></li>
       <li><a href="Profile.jsp">Profile Search</a></li>
      </ul>
    </div>
  </div>
</nav>
<form action="GetRestaurants" method="post">
<label>&nbsp;</label>
<input type="submit" value="Get Restaurants" id="submit">
</form>
</body>
</html>