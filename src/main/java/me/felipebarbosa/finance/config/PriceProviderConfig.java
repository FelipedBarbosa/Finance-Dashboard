package me.felipebarbosa.finance.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Holds configuration properties for external price provider APIs.
 *
 * The properties are defined in {@code application.yml} under the prefix
 * {@code price.provider}. Example configuration:
 *
 * <pre>
 * price:
 *   provider:
 *     alphaVantage:
 *       apiKey: YOUR_ALPHA_VANTAGE_KEY
 *       baseUrl: https://www.alphavantage.co/query
 *     finnhub:
 *       apiKey: YOUR_FINNHUB_KEY
 *       baseUrl: https://finnhub.io/api/v1
 * </pre>
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "price.provider")
public class PriceProviderConfig {

    private Provider alphaVantage = new Provider();
    private Provider finnhub = new Provider();

    @Data
    public static class Provider {
        /** API key for the provider */
        private String apiKey;
        /** Base URL for the provider's endpoint */
        private String baseUrl;
    }
}
