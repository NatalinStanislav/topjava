package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.storage.MealMapStorage;
import ru.javawebinar.topjava.storage.Storage;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);

    private Storage<Meal> storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = new MealMapStorage();
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String index = request.getParameter("id");
        int id = Integer.parseInt(index);
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));
        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("dateTime"));
        Meal meal = new Meal(dateTime, description, calories);
        if (id == -1) {
            storage.save(meal);
        } else {
            meal.setId(id);
            storage.update(meal);
        }
        response.sendRedirect("meals");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meals");

        String action = request.getParameter("action");

        if (action == null) {
            request.setAttribute("mealsList", MealsUtil.filteredByStreams(storage.getAll(), LocalTime.MIN, LocalTime.MAX, 2000));
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
            return;
        }

        switch (action) {
            case "delete":
                storage.delete(Integer.parseInt(request.getParameter("id")));
                response.sendRedirect("meals");
                return;
            case "edit":
                request.setAttribute("meal", storage.get(Integer.parseInt(request.getParameter("id"))));
                request.getRequestDispatcher("/edit.jsp").forward(request, response);
            case "create":
                request.setAttribute("meal", new Meal());
                request.getRequestDispatcher("/edit.jsp").forward(request, response);
        }
    }
}

