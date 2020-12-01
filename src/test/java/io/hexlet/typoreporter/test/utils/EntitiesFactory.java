package io.hexlet.typoreporter.test.utils;

import io.hexlet.typoreporter.domain.Identifiable;
import io.hexlet.typoreporter.domain.typo.Typo;

import java.util.stream.Stream;

public class EntitiesFactory {
    public static Stream<Typo> getTypos() {
        final var typo1 = new Typo()
                .setId(1L)
                .setPageUrl("http://site.com")
                .setReporterName("ReporterName")
                .setReporterComment("ReporterComment")
                .setTextBeforeTypo("TextBeforeTypo")
                .setTextTypo("TextTypo")
                .setTextAfterTypo("TextAfterTypo");

        final var typo2 = new Typo()
                .setId(2L)
                .setPageUrl("http://site.com")
                .setTextTypo("TextTypo")
                .setReporterName("ReporterName");
        return Stream.of(typo1, typo2);
    }

    public static Stream<? extends Identifiable<?>> getEntities() {
        return getTypos();
    }
}
