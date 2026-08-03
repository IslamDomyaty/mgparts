package com.mgpartslab.context.web;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@AutoConfigureMockMvc
class ContextJourneyIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void initialContextUsesEgyptArabicAndEgpWithExplicitOptions() throws Exception {
        mockMvc.perform(get("/api/v1/context")
                        .header("X-Correlation-ID", "journey-default-001"))
                .andExpect(status().isOk())
                .andExpect(header().string("X-Correlation-ID", "journey-default-001"))
                .andExpect(jsonPath("$.active.market").value("EG"))
                .andExpect(jsonPath("$.active.locale").value("ar"))
                .andExpect(jsonPath("$.active.direction").value("rtl"))
                .andExpect(jsonPath("$.active.baseCurrency").value("EGP"))
                .andExpect(jsonPath("$.active.displayCurrency").value("EGP"))
                .andExpect(jsonPath("$.markets", hasSize(2)))
                .andExpect(jsonPath("$.locales", hasSize(2)))
                .andExpect(jsonPath("$.displayCurrencies", hasSize(4)))
                .andExpect(jsonPath("$.configuration.demonstrationData").value(true))
                .andExpect(jsonPath("$.demonstrationQuote.fxSnapshot.rate").value("1.000000"));
    }

    @Test
    void marketAndSubsequentChoicesPersistWithoutCrossChangingFields() throws Exception {
        MvcResult marketResult = mockMvc.perform(put("/api/v1/context")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"market\":\"GB\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.active.market").value("GB"))
                .andExpect(jsonPath("$.active.locale").value("en"))
                .andExpect(jsonPath("$.active.baseCurrency").value("GBP"))
                .andExpect(jsonPath("$.active.displayCurrency").value("GBP"))
                .andReturn();
        MockHttpSession session = (MockHttpSession) marketResult.getRequest().getSession(false);

        mockMvc.perform(put("/api/v1/context")
                        .session(session)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"locale\":\"ar\",\"currency\":\"EUR\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.active.market").value("GB"))
                .andExpect(jsonPath("$.active.locale").value("ar"))
                .andExpect(jsonPath("$.active.displayCurrency").value("EUR"))
                .andExpect(jsonPath("$.demonstrationQuote.baseAmount.currency").value("GBP"))
                .andExpect(jsonPath("$.demonstrationQuote.displayAmount.currency").value("EUR"));

        mockMvc.perform(get("/api/v1/context").session(session))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.active.market").value("GB"))
                .andExpect(jsonPath("$.active.locale").value("ar"))
                .andExpect(jsonPath("$.active.displayCurrency").value("EUR"));
    }

    @Test
    void htmlLanguageSwitchKeepsThePageAndUpdatesDocumentLanguageAndDirection() throws Exception {
        MvcResult initial = mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("lang=\"ar\"")))
                .andExpect(content().string(containsString("dir=\"rtl\"")))
                .andExpect(content().string(containsString("data-testid=\"demo-notice\"")))
                .andReturn();
        MockHttpSession session = (MockHttpSession) initial.getRequest().getSession(false);

        mockMvc.perform(post("/context/language")
                        .session(session)
                        .param("locale", "en")
                        .param("returnTo", "/"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        mockMvc.perform(get("/").session(session))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("lang=\"en\"")))
                .andExpect(content().string(containsString("dir=\"ltr\"")))
                .andExpect(content().string(containsString("A precise parts-learning workspace.")));
    }

    @Test
    void unsupportedCodeReturnsStableNonLocalizedErrorAndCorrelationId() throws Exception {
        mockMvc.perform(put("/api/v1/context")
                        .header("X-Correlation-ID", "negative-market-001")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"market\":\"UK\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("CONTEXT_MARKET_UNSUPPORTED"))
                .andExpect(jsonPath("$.correlationId").value("negative-market-001"))
                .andExpect(jsonPath("$.fieldErrors[0].field").value("market"));
    }

    @Test
    void emptyUpdateAndInvalidQuoteAreIntentionalNegativeStates() throws Exception {
        mockMvc.perform(put("/api/v1/context")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("CONTEXT_UPDATE_EMPTY"));

        mockMvc.perform(get("/api/v1/context/quote").param("baseMinor", "-1"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("QUOTE_BASE_MINOR_INVALID"));
    }

    @Test
    void pageContextPostRejectsExternalReturnTarget() throws Exception {
        mockMvc.perform(post("/context/currency")
                        .param("currency", "USD")
                        .param("returnTo", "https://example.test/steal"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    void invalidPageContextRendersLocalizedRecoveryInsteadOfAnUnhandledError() throws Exception {
        mockMvc.perform(post("/context/market").param("market", "UK"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("data-testid=\"context-error\"")))
                .andExpect(content().string(containsString("CONTEXT_MARKET_UNSUPPORTED")))
                .andExpect(content().string(containsString("خيار السياق هذا غير مدعوم")));
    }
}
