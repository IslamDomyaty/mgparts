package com.mgpartslab.shared.observability;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class CorrelationIdsTest {

    @Test
    void acceptsSafeCallerValue() {
        assertThat(CorrelationIds.acceptOrCreate("journey-12345678"))
                .isEqualTo("journey-12345678");
    }

    @Test
    void replacesShortOrUnsafeCallerValues() {
        assertThat(CorrelationIds.acceptOrCreate("short")).isNotEqualTo("short");
        assertThat(CorrelationIds.acceptOrCreate("unsafe\nvalue")).doesNotContain("\n");
    }
}
