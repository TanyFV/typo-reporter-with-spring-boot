package io.hexlet.typoreporter.domain.typo;

import io.hexlet.typoreporter.domain.Identifiable;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@Entity
public class Typo implements Identifiable<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "typo_id_seq")
    @SequenceGenerator(name = "typo_id_seq", allocationSize = 15)
    private Long id;

    private String pageUrl;
    private String reporterName;
    private String reporterComment;

    private String textBeforeTypo;
    private String textTypo;
    private String textAfterTypo;

    public enum TypoStatus {

        REPORTED,
        IN_PROGRESS,
        RESOLVED,
        CANCELED
    }

    @Enumerated(EnumType.STRING)
    private TypoStatus typoStatus = TypoStatus.REPORTED;

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj || id != null && obj instanceof Typo other && id.equals(other.id);
    }
}
