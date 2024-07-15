package br.com.join.testAppJoin.controller;

import br.com.join.testAppJoin.builder.CategoryBuilder;
import br.com.join.testAppJoin.entity.Category;
import br.com.join.testAppJoin.repository.CategoryRepository;
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
class CategoryControllerTest {

    private final String TEST_CATEGORY_1_NAME = "name 1";
    private final String TEST_CATEGORY_1_DESC = "description 1";
    private final String TEST_CATEGORY_2_NAME = "name 1";
    private final String TEST_CATEGORY_2_DESC = "description 1";
    private final String TEST_CATEGORY_3_NAME = "name 1";
    private final String TEST_CATEGORY_3_DESC = "description 1";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private CategoryRepository repository;

    @Autowired
    private CategoryController controller;

    private Long unusedCategoryId;
    private Category testCategory1;
    private Category testCategory2;
    private Category testCategory3;


    @BeforeEach
    void setUp() {
        CategoryBuilder builder = new CategoryBuilder();
        testCategory1 = builder
                .withName(TEST_CATEGORY_1_NAME)
                .withDescription(TEST_CATEGORY_1_DESC)
                .build();
        testCategory2 = builder
                .withName(TEST_CATEGORY_2_NAME)
                .withDescription(TEST_CATEGORY_2_DESC)
                .build();
        testCategory3 = builder
                .withName(TEST_CATEGORY_3_NAME)
                .withDescription(TEST_CATEGORY_3_DESC)
                .build();
        repository.saveAll(Arrays.asList(testCategory1, testCategory2, testCategory3));
        unusedCategoryId = 1000L;
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void index_ThreeResults_Success() throws Exception {

        this.mvc.perform(get("/category"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content[0].id", is(1)))
                .andExpect(jsonPath("$.content[0].name", is(TEST_CATEGORY_1_NAME)))
                .andExpect(jsonPath("$.content[0].description", is(TEST_CATEGORY_1_DESC)))
                .andExpect(jsonPath("$.content[1].id", is(2)))
                .andExpect(jsonPath("$.content[1].name", is(TEST_CATEGORY_2_NAME)))
                .andExpect(jsonPath("$.content[1].description", is(TEST_CATEGORY_2_DESC)))
                .andExpect(jsonPath("$.content[2].id", is(3)))
                .andExpect(jsonPath("$.content[2].name", is(TEST_CATEGORY_3_NAME)))
                .andExpect(jsonPath("$.content[2].description", is(TEST_CATEGORY_3_DESC)))
                .andExpect(jsonPath("$.totalPages", is(1)))
                .andExpect(jsonPath("$.totalElements", is(3)))
                .andExpect(jsonPath("$.pageable.pageNumber", is(0)));
    }

    @Test
    void get_ExistingCategory_Success() throws Exception {

        this.mvc.perform(get("/category/" + testCategory1.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content[0].id", is(testCategory1.getId())))
                .andExpect(jsonPath("$.content[0].name", is(testCategory1.getName())))
                .andExpect(jsonPath("$.content[0].description", is(testCategory1.getDescription())))
                ;
    }

    @Test
    void get_NonExistingCategory_Failure() throws Exception {

        this.mvc.perform(get("/category/" + unusedCategoryId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content[0].id", is(testCategory1.getId())))
                .andExpect(jsonPath("$.content[0].name", is(testCategory1.getName())))
                .andExpect(jsonPath("$.content[0].description", is(testCategory1.getDescription())))
                ;
    }

    @Test
    void save_NewValidCategory_Success() throws Exception {

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
    void save_NewInvalidCategory_Failure() throws Exception {

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
    void update_NewValidData_Success() throws Exception {

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
    void update_NewInvalidData_Success() throws Exception {

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
    void delete_ExistingCategory_Success() throws Exception {

        this.mvc.perform(delete("/areas/2")
                )
                .andExpect(status().isOk());
    }

    @Test
    void delete_NonExistingCategory_Failure() throws Exception {

        this.mvc.perform(delete("/areas/2")
                )
                .andExpect(status().isOk());
    }
}