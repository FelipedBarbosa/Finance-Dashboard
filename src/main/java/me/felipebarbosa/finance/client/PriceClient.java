package me.felipebarbosa.finance.client;

import me.felipebarbosa.finance.config.PriceProviderConfig;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * Simple price client that can query either Alpha Vantage or Finnhub based on configuration.
 *
 * The client uses {@link WebClient} (non‑blocking) to call the external API and extracts the
 * current price as a {@link BigDecimal}. The response structure differs between providers;
 * each provider has its own parsing method.
 */
@Component
public class PriceClient {

    private final WebClient alphaVantageClient;
    private final WebClient finnhubClient;
    private final PriceProviderConfig config;

    public PriceClient(PriceProviderConfig config) {
        this.config = config;
        this.alphaVantageClient = WebClient.builder()
                .baseUrl(config.getAlphaVantage().getBaseUrl())
                .build();
        this.finnhubClient = WebClient.builder()
                .baseUrl(config.getFinnhub().getBaseUrl())
                .build();
    }

    /**
     * Retrieves the latest price for the given symbol using Alpha Vantage.
     * @param symbol ticker (e.g., "AAPL")
     * @return price wrapped in {@link Mono}
     */
    public Mono<BigDecimal> fetchPriceFromAlphaVantage(String symbol) {
        return alphaVantageClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("function", "GLOBAL_QUOTE")
                        .queryParam("symbol", symbol)
                        .queryParam("apikey", config.getAlphaVantage().getApiKey())
                        .build())
                .retrieve()
                .bodyToMono(Map.class)
                .map(this::extractAlphaVantagePrice);
    }

    /**
     * Retrieves the latest price for the given symbol using Finnhub.
     * @param symbol ticker (e.g., "AAPL")
     * @return price wrapped in {@link Mono}
     */
    public Mono<BigDecimal> fetchPriceFromFinnhub(String symbol) {
        return finnhubClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/quote")
                        .queryParam("symbol", symbol)
                        .queryParam("token", config.getFinnhub().getApiKey())
                        .build())
                .retrieve()
                .bodyToMono(Map.class)
                .map(this::extractFinnhubPrice);
    }

    private BigDecimal extractAlphaVantagePrice(Map<String, Object> response) {
        // Alpha Vantage returns { "Global Quote": { "05. price": "123.45", ... } }
        Object globalQuote = response.get("Global Quote");
        if (globalQuote instanceof Map) {
            Object priceStr = ((Map<?, ?>) globalQuote).get("05. price");
            if (priceStr instanceof String) {
                try {
                    return new BigDecimal(((String) priceStr).trim());
                } catch (NumberFormatException ignored) { }
            }
        }
        throw new IllegalStateException("Unable to parse price from Alpha Vantage response");
    }

    private BigDecimal extractFinnhubPrice(Map<String, Object> response) {
        // Finnhub returns { "c": 123.45, ... } where "c" is current price
        Object priceObj = response.get("c");
        if (priceObj instanceof Number) {
            return new BigDecimal(((Number) priceObj).toString());
        }
        if (priceObj instanceof String) {
            try {
                return new BigDecimal(((String) priceObj).trim());
            } catch (NumberFormatException ignored) { }
        }
        throw new IllegalStateException("Unable to parse price from Finnhub response");
    }
}
