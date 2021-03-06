package kss.springframework.msscbeerservice.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kss.springframework.msscbeerservice.services.BeerService;
import kss.brewery.model.BeerDto;
import kss.brewery.model.BeerStyleEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BeerController.class)
class BeerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    BeerService beerService;

    @Test
    void getBeerById() throws Exception {
        mockMvc.perform(get("/api/v1/beer/" + UUID.randomUUID().toString()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void saveNewBeer() throws Exception {

        BeerDto build = getBeerDto();
        String beer_json = objectMapper.writeValueAsString(build);
        mockMvc.perform(post("/api/v1/beer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(beer_json)).andExpect(status().isCreated());
    }

    @Test
    void updateBeerById() throws Exception {
        BeerDto build = getBeerDto();
        String beer_json = objectMapper.writeValueAsString(build);
        mockMvc.perform(put("/api/v1/beer/" + UUID.randomUUID().toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(beer_json)).andExpect(status().isNoContent());
    }

    BeerDto getBeerDto(){
        return BeerDto.builder().beerName("Beer 1")
                .beerStyle(BeerStyleEnum.LAGER)
                .upc("678234L")
                .price(new BigDecimal("23.3"))
                .quantityOnHand(12)
                .build();
    }
}