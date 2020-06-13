<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal" scope="request"/>
    <title>Редактирование</title>
</head>
<body>
<section>
    <form method="post" action="meals" enctype="application/x-www-form-urlencoded">
        <h3>Ваша еда:</h3>
        <input type="hidden" name="id" value="${meal.id}">
        <label>Наименование:<input type="text" name="description" size=50 value="${meal.description}"></label><br/><br/>
        <label>Время:<input type="datetime-local" name="dateTime" value="${meal.dateTime}"/></label><br/><br/>
        <label>Калории:<input type="number" name="calories" value="${meal.calories}" min="0" max="5000"
                              step="1"></label><br/><br/>
        <hr>
        <button type="submit">Сохранить</button>
        <input
                action="action"
                onclick="window.history.go(-1); return false;"
                type="submit"
                value="Отменить"
        />
    </form>
</section>

</body>
</html>
