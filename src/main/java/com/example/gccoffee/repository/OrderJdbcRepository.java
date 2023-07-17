package com.example.gccoffee.repository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.gccoffee.model.Order;
import com.example.gccoffee.model.OrderItem;

@Repository
public class OrderJdbcRepository implements OrderRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public OrderJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Order insert(Order order) {
        var update = jdbcTemplate.update(
            "insert into `order`(order_id, email, address, postcode, order_status, created_at, updated_at)"
                + " values(:orderId, :email, :address, :postcode, :orderStatus, :createdAt, :updatedAt)",
            toOrderParamMap(order)
        );
        if (update != 1) {
            throw new RuntimeException("Nothing was inserted.");
        }
        order.getOrderItems().forEach(item -> jdbcTemplate.update(
            "insert into order_item(order_id, product_id, category, price, quantity, created_at, updated_at)"
                + " values(:orderId, :productId, :category, :price, :quantity, :createdAt, :updatedAt)",
            toOrderItemParamMap(order.getOrderId(), order.getCreatedAt(), order.getUpdatedAt(), item))
        );
        return order;
    }

    private static Map<String, Object> toOrderParamMap(Order order) {
        var paramMap = new HashMap<String, Object>();
        paramMap.put("orderId", order.getOrderId().toString());
        paramMap.put("email", order.getEmail().getAddress());
        paramMap.put("address", order.getAddress());
        paramMap.put("postcode", order.getPostcode());
        paramMap.put("orderStatus", order.getOrderStatus().toString());
        paramMap.put("createdAt", order.getCreatedAt());
        paramMap.put("updatedAt", order.getUpdatedAt());

        return paramMap;
    }

    private static Map<String, Object> toOrderItemParamMap(
        UUID orderId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        OrderItem item
    ) {
        var paramMap = new HashMap<String, Object>();
        paramMap.put("orderId", orderId.toString());
        paramMap.put("productId", item.productId().toString());
        paramMap.put("category", item.category().toString());
        paramMap.put("price", item.price());
        paramMap.put("quantity", item.quantity());
        paramMap.put("createdAt", createdAt);
        paramMap.put("updatedAt", updatedAt);

        return paramMap;
    }
}
