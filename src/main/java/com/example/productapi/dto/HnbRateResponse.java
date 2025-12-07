package com.example.productapi.dto;


/**
 * Response DTO representing a single HNB exchange rate entry.
 *
 * <p>Used to parse the HNB v3 API JSON response.</p>
 *
 * @param broj_tecajnice  Exchange rate list number
 * @param datum_primjene  Date the rate applies (ISO 8601 format: YYYY-MM-DD)
 * @param drzava          Country name
 * @param drzava_iso      ISO 3166-1 alpha-3 country code
 * @param kupovni_tecaj   Buying rate (string with comma as decimal separator)
 * @param prodajni_tecaj  Selling rate (string with comma as decimal separator)
 * @param sifra_valute    Currency numeric code (ISO 4217)
 * @param srednji_tecaj   Average exchange rate (string with comma as decimal separator)
 * @param valuta          Currency code (ISO 4217, e.g., "USD")
 */
public record HnbRateResponse(
        String broj_tecajnice,
        String datum_primjene,
        String drzava,
        String drzava_iso,
        String kupovni_tecaj,
        String prodajni_tecaj,
        String sifra_valute,
        String srednji_tecaj,
        String valuta
) {}