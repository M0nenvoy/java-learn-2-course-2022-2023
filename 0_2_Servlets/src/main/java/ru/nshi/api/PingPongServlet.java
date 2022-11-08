package ru.nshi.api;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *  Задание 1
 *
 *  Создать обработчик: GET запрос на путь `/ping`
 *  и возвращающий `pong` c заголовком `Content-Type: text/plain`
 *
 */
@WebServlet("/ping")
public class PingPongServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setStatus(200);
        resp.setContentType("text/plain");

        String responseString = "pong";
        resp.getWriter().println(responseString);
    }
}
