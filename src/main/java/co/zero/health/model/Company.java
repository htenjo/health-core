package co.zero.health.model;

import co.zero.health.common.Constant;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import java.time.LocalDate;

/**
 * Created by hernan on 6/30/17.
 */
@Getter
@Setter
@ToString
@Entity
public class Company extends AbstractEntity {
    private String name;

    @JsonFormat(pattern = Constant.ENTITY_GENERIC_DATE_PATTERN)
    @JsonSerialize(using =  LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate createdDate;

    /**
     *
     */
    public Company(){}

    /**
     * Constructor useful for services that just have the companyId.
     * @param id Identifier of the company.
     */
    public Company(Long id) {
        this.setId(id);
    }
}
