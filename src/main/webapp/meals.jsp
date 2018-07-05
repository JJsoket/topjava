<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<h2>Meals</h2>
<div>
    <table>
        <thead>
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
            <th>Delete</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="meal" items="${meals}">
            <tr bgcolor="${meal.exceed == true ? "#ff4500" : "#228b22"}">
                <td><a href="meals?u=${meal.id}">${meal.dateTime}</a></td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td><a href="meals?d=${meal.id}">&#x2715;</a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<button><a href="meal_form.jsp">Add new</a></button>
</body>
</html>
