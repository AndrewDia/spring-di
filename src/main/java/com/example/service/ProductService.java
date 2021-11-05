package com.example.service;

import com.example.dao.ProductDAO;
import com.example.dto.OrderedProductDTO;
import com.example.model.Product;
import com.example.model.ProductsStatus;
import com.example.util.ConnectionProperties;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.SQLException;
import java.util.List;

public class ProductService {
    private final ProductDAO productDAO;

    public ProductService(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    public void save(String name, int price, String status) {
        productDAO.insertProduct(Product.createProduct(name, price, ProductsStatus.fromString(status)));
    }

    public List<OrderedProductDTO> findOrderedProducts() {
        return productDAO.findOrderedProducts();
    }

    public List<Product> findAll() {
        return productDAO.findAllProducts();
    }

    public void delete(int id) {
        productDAO.deleteProduct(id);
    }

    public void deleteAll(String password) {
        if (password.equals(ConnectionProperties.getPassword())) {
            try {
                productDAO.deleteAllProducts();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
