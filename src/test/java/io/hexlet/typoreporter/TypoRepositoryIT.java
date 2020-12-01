package io.hexlet.typoreporter;

import io.hexlet.typoreporter.config.AuditConfiguration;
import io.hexlet.typoreporter.domain.typo.Typo;
import io.hexlet.typoreporter.repository.TypoRepository;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(AuditConfiguration.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("testcontainers")
class TypoRepositoryIT {
    @Autowired
    private TypoRepository repository;

    @ParameterizedTest
    @MethodSource("io.hexlet.typoreporter.test.utils.EntitiesFactory#getTypos")
    void saveTypoEntity(final Typo typo) {
        final var id = repository.save(typo.setId(null)).getId();
        assertThat(repository.existsById(id)).isTrue();
    }
}
