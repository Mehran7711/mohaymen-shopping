package com.mohaymen.store.product;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import com.mohaymen.store.AbstractTest;
import com.mohaymen.store.web.response.ProductDto;
import io.restassured.http.ContentType;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

@Sql(scripts = "/clean.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class ProductControllerTest extends AbstractTest {

    @Test
    void shouldReturnProducts() {
        given().contentType(ContentType.JSON)
                .when()
                .get("/api/products")
                .then()
                .statusCode(200)
                .body("data", hasSize(10))
                .body("totalElements", is(20))
                .body("pageNumber", is(1))
                .body("totalPages", is(2))
                .body("isFirst", is(true))
                .body("isLast", is(false))
                .body("hasNext", is(true))
                .body("hasPrevious", is(false));
    }

    @Test
    void shouldGetProductByCode() {
        ProductDto product = given().contentType(ContentType.JSON)
                .when()
                .get("/api/products/{code}", "P300")
                .then()
                .statusCode(200)
                .assertThat()
                .extract()
                .body()
                .as(ProductDto.class);

        assertThat(product.code()).isEqualTo("P300");
        assertThat(product.name()).isEqualTo("تی‌شرت نخی مردانه");
        assertThat(product.description())
                .isEqualTo("تی‌شرتی راحت و سبک مناسب استفاده روزمره با پارچه باکیفیت و تنفس‌پذیر.");
        assertThat(product.price()).isEqualTo(new BigDecimal("19.9"));
    }
}
