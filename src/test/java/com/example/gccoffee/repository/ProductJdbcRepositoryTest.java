package com.example.gccoffee.repository;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.example.gccoffee.model.Category;
import com.example.gccoffee.model.Product;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class ProductJdbcRepositoryTest {
    @Autowired
    ProductRepository productRepository;
    private final Product newProduct = new Product(UUID.randomUUID(), "new-product", Category.COFFEE_BEAN_PACKAGE, 1000L);

    @Test
    @DisplayName("상품을 추가할 수 있다.")
    void testInsert() {
        // given & when
        productRepository.insert(newProduct);
        // then
        var all = productRepository.findAll();
        assertThat(all.isEmpty(), is(false));
    }

    @Test
    @DisplayName("상품을 수정할 수 있다.")
    void testUpdate() {
        // given
        productRepository.insert(newProduct);
        // when
        newProduct.setProductName("updated-product");
        productRepository.update(newProduct);
        // then
        var product = productRepository.findById(newProduct.getProductId());
        assertThat(product.isEmpty(), is(false));
        assertThat(product.get(), samePropertyValuesAs(newProduct));
    }

    @Test
    @DisplayName("상품을 이름으로 조회할 수 있다.")
    void testFindByName() {
        // given
        productRepository.insert(newProduct);
        // when
        var product = productRepository.findByName(newProduct.getProductName());
        // then
        assertThat(product.isEmpty(), is(false));
    }

    @Test
    @DisplayName("상품을 Id로 조회할 수 있다.")
    void testFindById() {
        // given
        productRepository.insert(newProduct);
        // when
        var product = productRepository.findById(newProduct.getProductId());
        // then
        assertThat(product.isEmpty(), is(false));
    }

    @Test
    @DisplayName("상품을 카테고리로 조회할 수 있다.")
    void testFindByCategory() {
        // given
        productRepository.insert(newProduct);
        // when
        var product = productRepository.findByCategory(newProduct.getCategory());
        // then
        assertThat(product.isEmpty(), is(false));
    }

    @Test
    @DisplayName("상품을 전체 삭제한다.")
    void testDeleteAll() {
        // given & when
        productRepository.deleteAll();
        // then
        var all = productRepository.findAll();
        assertThat(all.isEmpty(), is(true));
    }
}

