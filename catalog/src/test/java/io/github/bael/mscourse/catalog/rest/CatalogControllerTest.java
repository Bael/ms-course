package io.github.bael.mscourse.catalog.rest;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@AutoConfigureMockMvc
class CatalogControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnDefaultMessage() throws Exception {
        this.mockMvc.perform(get("/catalog/brand")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Torseo")));
    }

    @Test
    public void shouldReturnCategory() throws Exception {
        this.mockMvc.perform(get("/catalog/category")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("гребные")));
    }

}