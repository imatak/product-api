package com.example.productapi.service;

import com.example.productapi.dto.HnbRateResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

/**
 * Service for retrieving the EUR→USD exchange rate from the HNB v3 API.
 * Results are cached to avoid unnecessary external API calls.
 */
@Slf4j
@Service
public class HnbService {

    private final RestTemplate restTemplate;
    private final String apiUrl;

    public HnbService(@Value("${hnb.api-url}") String apiUrl) {
        this.apiUrl = apiUrl;
        this.restTemplate = new RestTemplate();
    }

    /**
     * Retrieves the current EUR→USD exchange rate from HNB.
     * Falls back to a default value (1.08) if parsing or network requests fail.
     *
     * @return EUR→USD rate as BigDecimal
     */
    @Cacheable("hnbRate")
    public BigDecimal getEurToUsdRate() {
        try {
            String url = apiUrl + "?valuta=USD";
            HnbRateResponse[] response = restTemplate.getForObject(url, HnbRateResponse[].class);

            if (response != null && response.length > 0) {
                BigDecimal rate = new BigDecimal(response[0].srednji_tecaj().replace(",", "."));
                log.info("Fetched EUR→USD rate from HNB: {} (date: {})", rate, response[0].datum_primjene());
                return rate;
            } else {
                log.warn("HNB API returned empty array for USD rate.");
            }
        } catch (Exception ex) {
            log.warn("Failed to fetch EUR→USD rate from HNB, falling back to default", ex);
        }

        BigDecimal fallbackRate = BigDecimal.valueOf(1.08);
        log.info("Using fallback EUR→USD rate: {}", fallbackRate);
        return fallbackRate;
    }

}
