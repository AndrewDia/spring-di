package com.example.controller;

import com.example.service.ProductService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ProductCreationController extends HttpServlet {
    private final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
            "applicationContext.xml");
    private final ProductService productService = context.getBean("productService", ProductService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/createProduct.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("productName");
        int price = Integer.parseInt(req.getParameter("productPrice"));
        String status = req.getParameter("status");
        productService.save(name, price, status);

        HttpSession session = req.getSession();
        session.setAttribute("productSuccessful", "true");

        RequestDispatcher dispatcher = req.getRequestDispatcher("/createProduct.jsp");
        dispatcher.forward(req, resp);
    }
}
