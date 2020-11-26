package io.hexlet.typoreporter.domain;

import io.hexlet.typoreporter.domain.typo.Typo;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.assertj.core.api.Assertions.assertThat;


public class EntityTest {
    @Autowired
    private ObjectMapper objectMapper;
    @ParameterizedTest
    @ValueSource(classes = {Typo.class})
    void equalsHashCodeVerifier(Class<Identifiable<Long>> clazz) throws Exception {
        final var entityOne = clazz.getConstructor().newInstance();
        final var entityTwo = clazz.getConstructor().newInstance();

        assertThat(entityOne).isEqualTo(entityOne)
                .hasSameHashCodeAs(entityOne)
                .isNotEqualTo(new Object())
                .isNotEqualTo(null)
                .isNotEqualTo(entityTwo)
                .hasSameHashCodeAs(entityTwo);
    }
    @ParameterizedTest
    @ValueSource(classes = {Typo.class})
    void isSerializeNewEntityToJson(Class<?> clazz) {
        assertDoesNotThrow(() -> objectMapper.writeValueAsString(clazz.getConstructor().newInstance()));
    }

    @ParameterizedTest
    @MethodSource("io.hexlet.typoreporter.test.utils.EntitiesFactory#getEntities")
    void isSerializeEntityToJson(final Identifiable<Long> entity) {
        assertDoesNotThrow(() -> objectMapper.writeValueAsString(entity));
    }
    @ParameterizedTest
    @ValueSource(classes = {Typo.class})
    void isNotExceptionForToStringWithNewEntity(Class<?> clazz) throws Exception {
        assertDoesNotThrow(clazz.getConstructor().newInstance()::toString);
    }

    @ParameterizedTest
    @MethodSource("io.hexlet.typoreporter.test.utils.EntitiesFactory#getEntities")
    void isNotExceptionForToStringWithEntity(final Identifiable<Long> entity) {
        assertDoesNotThrow(entity::toString);
    }
    @ParameterizedTest
    @ValueSource(classes = {Typo.class})
    void isEqualsIfIdsEquals(Class<Identifiable<Long>> clazz) throws Exception {
        final var entityOne = clazz.getConstructor().newInstance().setId(1L);
        final var entityTwo = clazz.getConstructor().newInstance().setId(1L);
        assertThat(entityOne).isEqualTo(entityTwo);
        assertThat(entityOne).hasSameHashCodeAs(entityTwo);
    }

    @ParameterizedTest
    @ValueSource(classes = {Typo.class})
    void isNotEqualsIfIdsNotEquals(Class<Identifiable<Long>> clazz) throws Exception {
        final var entityOne = clazz.getConstructor().newInstance().setId(1L);
        final var entityTwo = clazz.getConstructor().newInstance().setId(2L);
        assertThat(entityOne).isNotEqualTo(entityTwo);
        assertThat(entityOne).hasSameHashCodeAs(entityTwo);
    }

    @ParameterizedTest
    @ValueSource(classes = {Typo.class})
    void isNotEqualsIfOneIdNull(Class<Identifiable<Long>> clazz) throws Exception {
        final var entityOne = clazz.getConstructor().newInstance().setId(1L);
        final var entityTwo = clazz.getConstructor().newInstance();
        assertThat(entityOne).isNotEqualTo(entityTwo);
        assertThat(entityOne).hasSameHashCodeAs(entityTwo);
    }
    //tests
}
