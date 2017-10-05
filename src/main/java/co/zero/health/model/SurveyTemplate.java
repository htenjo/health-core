package co.zero.health.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

/**
 * Created by hernan on 6/30/17.
 */
@Getter
@Setter
@ToString
@Entity
public class SurveyTemplate extends AbstractEntity {
    private String name;
    @Column(columnDefinition = "text")
    private String jsSurvey;
    @Enumerated(EnumType.STRING)
    private SurveyType type;
    @ManyToOne
    private Specialty specialty;
}
