<%@ page import="ru.javawebinar.topjava.util.MealsUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Meals</title>
</head>
<body>
<section>
    <table border="1" cellpadding="8" cellspacing="0">
        <tr>
            <th>Дата/Время</th>
            <th>Описание</th>
            <th>Калории</th>
            <th>Изменить/Удалить</th>
        </tr>
        <c:forEach items="${mealsList}" var="meal">
            <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.MealTo"/>
            <tr style="color: <%=meal.isExcess()?"red":"green"%>">
                <td>${meal.dateTime.format(MealsUtil.formatter)}</td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td><a href="meals?id=${meal.id}&action=edit">Изменить</a> <a href="meals?id=${meal.id}&action=delete">Удалить</a>
                </td>
            </tr>
        </c:forEach>
    </table>
    <br/>
    <button onclick="location.href='meals?action=create'">Добавить кушонье</button>
</section>
</body>
</html>
