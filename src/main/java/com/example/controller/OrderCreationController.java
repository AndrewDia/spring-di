package com.example.controller;

import com.example.model.Order;
import com.example.model.OrderProduct;
import com.example.service.OrderService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class OrderCreationController extends HttpServlet {
    private final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
            "applicationContext.xml");
    private final OrderService orderService = context.getBean("orderService", OrderService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/createOrder.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        List<OrderProduct> products = (List<OrderProduct>) session.getAttribute("productsList");
        int productId = Integer.parseInt(req.getParameter("productId"));
        int quantity = Integer.parseInt(req.getParameter("quantity"));

        int productsSize = 0;
        if (products != null)
            productsSize = products.size();

        products = orderService.addOneMoreProduct(productId, quantity, products);

        if (products == null)
            req.setAttribute("error", "productId");
        else if (productsSize == products.size())
            req.setAttribute("error", "alreadyInList");
        else {
            if (req.getParameter("action").equals("Create")) {
                orderService.createOrder(products);
                session.invalidate();
            } else if (req.getParameter("action").equals("Add one more product")) {
                session.setAttribute("productsList", products);
            }
        }

        RequestDispatcher dispatcher = req.getRequestDispatcher("/createOrder.jsp");
        dispatcher.forward(req, resp);
    }
}
