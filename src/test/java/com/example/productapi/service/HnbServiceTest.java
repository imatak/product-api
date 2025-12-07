package com.example.productapi.service;

import com.example.productapi.dto.HnbRateResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class HnbServiceTest {

    private RestTemplate restTemplate;
    private HnbService hnbService;

    @BeforeEach
    void setUp() {
        restTemplate = mock(RestTemplate.class);
        hnbService = new HnbService("http://dummy-url") {

            @Override
            public BigDecimal getEurToUsdRate() {
                return super.getEurToUsdRate();
            }
        };

        try {
            var field = HnbService.class.getDeclaredField("restTemplate");
            field.setAccessible(true);
            field.set(hnbService, restTemplate);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testRateParsing() {
        HnbRateResponse[] response = new HnbRateResponse[]{
                new HnbRateResponse("251", "2022-12-31", "SAD", "USA",
                        "7,042843", "7,085227", "840", "7,064035", "USD")
        };

        when(restTemplate.getForObject("http://dummy-url?valuta=USD", HnbRateResponse[].class))
                .thenReturn(response);

        BigDecimal rate = hnbService.getEurToUsdRate();
        assertEquals(new BigDecimal("7.064035"), rate);
    }

    @Test
    void testFallback() {
        when(restTemplate.getForObject("http://dummy-url?valuta=USD", HnbRateResponse[].class))
                .thenThrow(new RuntimeException("API failure"));

        BigDecimal rate = hnbService.getEurToUsdRate();
        assertEquals(BigDecimal.valueOf(1.08), rate);
    }
}