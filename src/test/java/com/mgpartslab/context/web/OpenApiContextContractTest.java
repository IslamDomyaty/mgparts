package com.mgpartslab.context.web;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.yaml.snakeyaml.Yaml;

class OpenApiContextContractTest {

    @Test
    void contractDocumentsEveryF01ApiOperationAndStableCodeSet() throws IOException {
        try (var stream = getClass().getResourceAsStream("/openapi/mg-parts-api.yaml")) {
            assertThat(stream).isNotNull();
            String contract = new String(stream.readAllBytes(), StandardCharsets.UTF_8);
            Map<?, ?> document = new Yaml().load(contract);
            @SuppressWarnings("unchecked")
            Map<String, Object> paths = (Map<String, Object>) document.get("paths");

            assertThat(document.get("openapi")).isEqualTo("3.1.0");
            assertThat(paths)
                    .containsKeys(
                            "/api/v1/context",
                            "/api/v1/context/quote");

            assertThat(contract)
                    .contains("operationId: getContext")
                    .contains("operationId: updateContext")
                    .contains("operationId: getDemonstrationQuote")
                    .contains("enum: [EG, GB]")
                    .contains("enum: [ar, en]")
                    .contains("enum: [EGP, GBP, USD, EUR]")
                    .contains("HALF_UP_AT_DISPLAY_MINOR_UNIT")
                    .contains("X-Correlation-ID");
        }
    }
}
