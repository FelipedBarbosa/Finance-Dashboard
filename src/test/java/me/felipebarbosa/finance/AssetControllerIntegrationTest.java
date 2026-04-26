package me.felipebarbosa.finance;

import me.felipebarbosa.finance.dto.AssetRequestDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class AssetControllerIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void shouldCreateAssetViaPostAssets() {
        var request = new AssetRequestDTO("bitcoin", "Bitcoin", null);

        webTestClient.post()
                .uri("/assets")
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.symbol").isEqualTo("bitcoin")
                .jsonPath("$.name").isEqualTo("Bitcoin");
    }
}
