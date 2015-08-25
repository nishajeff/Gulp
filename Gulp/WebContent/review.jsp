<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Gulp!</title>
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
      <li class="active"><a href="#">Enter Review</a></li>
      <li><a href="restaurants.jsp">List of Restaurants</a></li>
       <li><a href="Profile.jsp">Profile Search</a></li>
      </ul>
    </div>
  </div>
</nav>

<form action="EnterReview" method="post">
<label>Restaurant ID: </label>
<input  type="text" name="resID"><br>
<label> Review: </label><br>
<textarea name= "review" rows="4" cols="50" placeholder="Type a review here">
</textarea> <br>
<label> Rating: </label>
<input  type="text" name="Rating"><br>
<label>Date: </label>
<input  type="text" name="Date"><br>
<label>&nbsp;</label>
<input type="submit" value="Enter" id="submit">
</form>

</body>
</html>