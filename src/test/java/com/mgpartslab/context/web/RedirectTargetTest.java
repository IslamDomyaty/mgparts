package com.mgpartslab.context.web;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class RedirectTargetTest {

    @Test
    void keepsAValidLocalPathAndQuery() {
        assertThat(RedirectTarget.safeLocalPath("/catalog?page=2")).isEqualTo("/catalog?page=2");
    }

    @Test
    void rejectsExternalProtocolRelativeAndHeaderInjectionTargets() {
        assertThat(RedirectTarget.safeLocalPath("https://example.test")).isEqualTo("/");
        assertThat(RedirectTarget.safeLocalPath("//example.test/path")).isEqualTo("/");
        assertThat(RedirectTarget.safeLocalPath("/catalog\r\nX-Test: value")).isEqualTo("/");
    }
}
