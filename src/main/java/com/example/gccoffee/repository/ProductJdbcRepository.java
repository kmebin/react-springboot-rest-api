package com.example.gccoffee.repository;

import static com.example.gccoffee.JdbcUtil.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.gccoffee.model.Category;
import com.example.gccoffee.model.Product;

@Repository
public class ProductJdbcRepository implements ProductRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public ProductJdbcRepository(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public List<Product> findAll() {
        return jdbcTemplate.query("select * from product", productRowMapper());
    }

    @Override
    public Product insert(Product product) {
        var update = jdbcTemplate.update(
            "insert into product(product_id, product_name, category, price, description, created_at, updated_at)"
                + " values(:productId, :productName, :category, :price, :description, :createdAt, :updatedAt)",
            toParamMap(product)
        );
        if (update != 1) {
            throw new RuntimeException("Nothing was inserted.");
        }
        return product;
    }

    @Override
    public Product update(Product product) {
        var update = jdbcTemplate.update(
            "update product set product_name = :productName, category = :category, price = :price, description = :description, created_at = :createdAt, updated_at = :updatedAt"
                + " where product_id = :productId",
            toParamMap(product)
        );
        if (update != 1) {
            throw new RuntimeException("Nothing was updated.");
        }
        return product;
    }

    @Override
    public Optional<Product> findById(UUID productId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(
                "select * from product where product_id = :productId",
                Collections.singletonMap("productId", productId),
                productRowMapper())
            );
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Product> findByName(String productName) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(
                "select * from product where product_name = :productName",
                Collections.singletonMap("productName", productName),
                productRowMapper())
            );
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Product> findByCategory(Category category) {
        return jdbcTemplate.query(
            "select * from product where category = :category",
            Collections.singletonMap("category", category.toString()),
            productRowMapper()
        );
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("delete from product", Collections.emptyMap());
    }

    private static RowMapper<Product> productRowMapper() {
        return (rs, rowNum) -> {
            var productId = UUID.fromString(rs.getString("product_id"));
            var productName = rs.getString("product_name");
            var category = Category.valueOf(rs.getString("category"));
            var price = rs.getLong("price");
            var description = rs.getString("description");
            var createdAt = toLocalDateTime(rs.getTimestamp("created_at"));
            var updatedAt = toLocalDateTime(rs.getTimestamp("updated_at"));

            return new Product(productId, productName, category, price, description, createdAt, updatedAt);
        };
    }

    private static Map<String, Object> toParamMap(Product product) {
        var paramMap = new HashMap<String, Object>();
        paramMap.put("productId", product.getProductId().toString());
        paramMap.put("productName", product.getProductName());
        paramMap.put("category", product.getCategory().toString());
        paramMap.put("price", product.getPrice());
        paramMap.put("description", product.getDescription());
        paramMap.put("createdAt", product.getCreatedAt());
        paramMap.put("updatedAt", product.getUpdatedAt());

        return paramMap;
    }
}
