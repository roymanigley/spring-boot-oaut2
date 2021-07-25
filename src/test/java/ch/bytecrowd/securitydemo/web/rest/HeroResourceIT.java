package ch.bytecrowd.securitydemo.web.rest;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FOUND;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.stream.IntStream;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import ch.bytecrowd.securitydemo.SecurityDemoApplication;
import ch.bytecrowd.securitydemo.domain.Hero;
import ch.bytecrowd.securitydemo.repository.HeroReporitory;

@SpringBootTest(classes = SecurityDemoApplication.class)
@AutoConfigureMockMvc
public class HeroResourceIT {
    
    @Autowired
    MockMvc mockMvc;

    @Autowired
    HeroReporitory heroReporitory;

    @Autowired
    ObjectMapper mapper;

    @Test
    @WithMockUser
    void testWithUser() throws Exception {
        mockMvc.perform(
            get("/api/hero")
            .contentType(MediaType.APPLICATION_JSON)    
        ).andExpect(status().is(OK.value()));
    }

    @Test
    @WithMockUser
    void testGetWithEmptyList() throws Exception {
        heroReporitory.deleteAll();
        mockMvc.perform(
            get("/api/hero")
            .contentType(MediaType.APPLICATION_JSON)    
        ).andExpect(
            status().is(OK.value())
        ).andExpect(
            jsonPath("$").isArray()
        ).andExpect(
            jsonPath("$").isEmpty()
        );
    }

    @Test
    @WithMockUser
    void testGetWithHeros() throws Exception {
        heroReporitory.deleteAll();
        IntStream.range(1, 100)
            .mapToObj(i -> i + "")
            .map(Hero::new)
            .forEach(heroReporitory::saveAndFlush);

        mockMvc.perform(
            get("/api/hero")
            .contentType(MediaType.APPLICATION_JSON)    
        ).andExpect(
            status().is(OK.value())
        ).andExpect(
            jsonPath("$").isArray()
        ).andExpect(
            jsonPath("$.size()").value(99)
        );
    }

    @Test
    @WithMockUser
    void testCreateWithValidHero() throws Exception {
        Hero hero = new Hero();
        hero.setName("AAAAA");
        mockMvc.perform(
            post("/api/hero")
            .content(toJson(hero))
            .contentType(MediaType.APPLICATION_JSON)    
        ).andExpect(
            status().is(OK.value())
        ).andExpect(
            jsonPath("$.id").isNotEmpty()
        ).andExpect(
            jsonPath("$.id").isNumber()
        );
    }

    @Test
    @Transactional
    @WithMockUser
    void testUpdateWithValidHero() throws Exception {
        Hero hero = new Hero();
        hero.setName("AAAAA");
        hero = heroReporitory.saveAndFlush(hero);

        hero.setName("NEW HERO NAME");
        mockMvc.perform(
            post("/api/hero")
            .content(toJson(hero))
            .contentType(MediaType.APPLICATION_JSON)    
        ).andExpect(
            status().is(OK.value())
            ).andExpect(
                jsonPath("$.id").value(CoreMatchers.is(hero.getId().intValue()))
            ).andExpect(
                jsonPath("$.name").value(CoreMatchers.is(hero.getName()))
            );
    }

    @Test
    @WithMockUser
    void testCreateWithInvalidHero() throws Exception {
        Hero hero = new Hero();
        mockMvc.perform(
            post("/api/hero")
            .content(toJson(hero))
            .contentType(MediaType.APPLICATION_JSON)    
        ).andExpect(
            status().is(BAD_REQUEST.value())
        );
    }

    @Test
    void testWithOutUser() throws Exception {
        mockMvc.perform(
            get("/api/hero")
            .contentType(MediaType.APPLICATION_JSON)    
        ).andExpect(status().is(FOUND.value()))
        .andExpect(header().string("Location", "http://localhost/login"));
    }

    String toJson(Object o) {
        try {
            return mapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
