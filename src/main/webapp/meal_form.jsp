<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Meal</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<h2>${empty meal ? "Create form" : "Edit form"}</h2>
<form action="/topjava/meals" method="post">
    <input type="number" name="id" value="${meal.id}" hidden>
    <label for="time">Time</label>
    <input type="datetime-local" id="time" name="time" value="${not empty meal ? meal.dateTime : ""}">
    <label for="description">Description</label>
    <input type="text" id="description" name="description" value="${not empty meal ? meal.description : ""}">
    <label for="calories">Calories</label>
    <input type="text" id="calories" name="calories" value="${not empty meal ? meal.calories : ""}">
    <input type="submit" value="Submit">
</form>
</body>
</html>
