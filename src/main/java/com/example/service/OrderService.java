package com.example.service;

import com.example.dao.OrderDAO;
import com.example.dao.ProductDAO;
import com.example.dto.OrderDTO;
import com.example.model.Order;
import com.example.model.OrderProduct;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderService {
    private final OrderDAO orderDAO;
    private final ProductDAO productDAO;
    private final int userId = 1000 + (int) (Math.random() * 999);

    public OrderService(OrderDAO orderDAO, ProductDAO productDAO) {
        this.orderDAO = orderDAO;
        this.productDAO = productDAO;
    }

    public void createOrder(List<OrderProduct> products) {
        try {
            orderDAO.createOrderWithProducts(Order.createOrder(userId, "new"), products);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public List<OrderProduct> addOneMoreProduct(int productId, int quantity, List<OrderProduct> products) {
        if (productDAO.getProduct(productId) == null)
            return null;
        if (products == null)
            products = new ArrayList<>();
        else
            for (OrderProduct op : products)
                if (productId == op.getProductId())
                    return products;
        products.add(new OrderProduct(productId, quantity));
        return products;
    }

    public List<OrderDTO> findAllOrders() {
        return orderDAO.findAllOrders();
    }
}
