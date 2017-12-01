package co.zero.health.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by hernan on 6/30/17.
 */
@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SurveyStatistics {
    @Id
    private Long surveyId;
    private Long companyId;
    private Long specialtyId;
    private Long templateId;
    private Long eventId;
    private Long patientId;
    @Column(columnDefinition = "text")
    private String surveyAnswersCsv;
    private Boolean finished;
}