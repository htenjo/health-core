package co.zero.health.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@Getter
@Setter
@ToString
@MappedSuperclass
public abstract class AbstractEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
}
