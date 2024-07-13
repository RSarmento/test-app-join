package br.com.join.testAppJoin.controller;

import br.com.join.testAppJoin.builder.ProductBuilder;
import br.com.join.testAppJoin.builder.ProductBuilder;
import br.com.join.testAppJoin.entity.Product;
import br.com.join.testAppJoin.repository.ProductRepository;
import br.com.join.testAppJoin.repository.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:test.properties")
@Disabled
class ProductControllerTest {

    private final String TEST_PRODUCT_1_NAME = "name 1";
    private final String TEST_PRODUCT_1_DESC = "description 1";
    private final String TEST_PRODUCT_2_NAME = "name 1";
    private final String TEST_PRODUCT_2_DESC = "description 1";
    private final String TEST_PRODUCT_3_NAME = "name 1";
    private final String TEST_PRODUCT_3_DESC = "description 1";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ProductRepository repository;

    @Autowired
    private ProductController controller;

    private Product testProduct1;
    private Product testProduct2;
    private Product testProduct3;

    @BeforeEach
    void setUp() {
        ProductBuilder builder = new ProductBuilder();
        testProduct1 = builder
                .withName(TEST_PRODUCT_1_NAME)
                .withDescription(TEST_PRODUCT_1_DESC)
                .build();
        testProduct2 = builder
                .withName(TEST_PRODUCT_2_NAME)
                .withDescription(TEST_PRODUCT_2_DESC)
                .build();

        testProduct3 = builder
                .withName(TEST_PRODUCT_3_NAME)
                .withDescription(TEST_PRODUCT_3_DESC)
                .build();
        repository.saveAll(Arrays.asList(testProduct1, testProduct2, testProduct3));
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @WithMockUser(roles="USER")
    void index_ThreeResults_Success() throws Exception {

        this.mvc.perform(get("/product"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content[0].id", is(1)))
                .andExpect(jsonPath("$.content[0].name", is(TEST_PRODUCT_1_NAME)))
                .andExpect(jsonPath("$.content[0].description", is(TEST_PRODUCT_1_DESC)))
                .andExpect(jsonPath("$.content[1].id", is(2)))
                .andExpect(jsonPath("$.content[1].name", is(TEST_PRODUCT_2_NAME)))
                .andExpect(jsonPath("$.content[1].description", is(TEST_PRODUCT_2_DESC)))
                .andExpect(jsonPath("$.content[2].id", is(3)))
                .andExpect(jsonPath("$.content[2].name", is(TEST_PRODUCT_3_NAME)))
                .andExpect(jsonPath("$.content[2].description", is(TEST_PRODUCT_3_DESC)))
                .andExpect(jsonPath("$.totalPages", is(1)))
                .andExpect(jsonPath("$.totalElements", is(3)))
                .andExpect(jsonPath("$.pageable.pageNumber", is(0)));
    }

    @Test
    void testGet() throws Exception {

        this.mvc.perform(get("/product/" + testProduct1.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content[0].id", is(testProduct1.getId())))
                .andExpect(jsonPath("$.content[0].name", is(testProduct1.getName())))
                .andExpect(jsonPath("$.content[0].description", is(testProduct1.getDescription())))
                .andExpect(jsonPath("$.content[0].price", is(testProduct1.getPrice())))
        ;
    }

    @Test
    void testSave() throws Exception {

        String testName = "test category 4";
        String testDesc = "test description 4";
//        String json = Json.createObjectBuilder()
//                .add("name", testName)
//                .add("description", testDesc)
//                .build()
//                .toString();

        this.mvc.perform(post("/areas")
                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(json)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.content[0].id", is(4)))
                .andExpect(jsonPath("$.content[0].name", is(testName)))
                .andExpect(jsonPath("$.content[0].description", is(testDesc)));
    }

    @Test
    void testUpdate() throws Exception {

//        String json = Json.createObjectBuilder()
//                .add("name", "Área 2")
//                .add("responsible", "admin")
//                .add("parentArea", Json.createObjectBuilder().add("id", 1L))
//                .add("company", Json.createObjectBuilder().add("id", 1L))
//                .build()
//                .toString();

        this.mvc.perform(put("/areas/2")
                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(json)
                )
                .andExpect(status().isOk());
    }

    @Test
    void testDelete() throws Exception {

        this.mvc.perform(delete("/areas/2")
                )
                .andExpect(status().isOk());
    }
}