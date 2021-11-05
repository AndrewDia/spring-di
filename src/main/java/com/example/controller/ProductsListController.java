package com.example.controller;

import com.example.service.ProductService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProductsListController extends HttpServlet {
    private final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
            "applicationContext.xml");
    private final ProductService productService = context.getBean("productService", ProductService.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String filter = request.getParameter("filter");
        if (filter != null && filter.equals("ordered"))
            request.setAttribute("orderedProductsList", productService.findOrderedProducts());
        else
            request.setAttribute("productsList", productService.findAll());
        RequestDispatcher dispatcher = request.getRequestDispatcher("/productsList.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("action").equals("delete_product")) {
            int productId = Integer.parseInt(request.getParameter("productId"));
            productService.delete(productId);
        } else if (request.getParameter("action").equals("delete_all")) {
            productService.deleteAll(request.getParameter("password"));
        }
        response.sendRedirect("/list/products");
    }
}
