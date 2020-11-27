package io.hexlet.typoreporter.domain.typo;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@JsonTest
public class TypoTest {
    @Autowired
    private ObjectMapper objectMapper;

    private static Stream<String> getValidTyposJson() {
        final var jsonEmptyValues = """
                {
                  "id": "",
                  "pageUrl": "",
                  "reporterName": "",
                  "reporterComment": "",
                  "textBeforeTypo": "",
                  "textTypo": "",
                  "textAfterTypo": "",
                  "typoStatus": "REPORTED"
                }
                """;
        final var jsonNullValues = """
                {
                  "id": null,
                  "pageUrl": null,
                  "reporterName": null,
                  "reporterComment": null,
                  "textBeforeTypo": null,
                  "textTypo": null,
                  "textAfterTypo": null,
                  "typoStatus": null
                }
                """;
        final var typoJson = """
                {
                  "id": 1,
                  "pageUrl": "http://mysite.io/items",
                  "reporterName": "reporterName",
                  "reporterComment": "reporterComment",
                  "textBeforeTypo": "textBeforeTypo",
                  "textTypo": "textTypo",
                  "textAfterTypo": "textAfterTypo",
                  "typoStatus": "%s"
                }
                """;
        final var typoJsonStream = Arrays.stream(Typo.TypoStatus.values()).map(typoJson::formatted);
        return Stream.concat(typoJsonStream, Stream.of("{}", jsonEmptyValues, jsonNullValues));
    }

    private static Stream<String> getInvalidTyposJson() {
        final var jsonEmptyValues = """
                {
                  "id": "",
                  "pageUrl": "",
                  "reporterName": "",
                  "reporterComment": "",
                  "textBeforeTypo": "",
                  "textTypo": "",
                  "textAfterTypo": "",
                  "typoStatus": ""
                }
                """;
        return Stream.of(jsonEmptyValues, "notJson");
    }

    @ParameterizedTest
    @MethodSource("getValidTyposJson")
    void isDeserializeValidJsonToEntity(final String json) {
        assertDoesNotThrow(() -> objectMapper.readValue(json, Typo.class));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @MethodSource("getInvalidTyposJson")
    void isNotDeserializeInvalidJsonToEntity(final String json) {
        assertThrows(Exception.class, () -> objectMapper.readValue(json, Typo.class));
    }
}
