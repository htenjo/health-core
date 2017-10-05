package co.zero.health.service;

import co.zero.health.model.AbstractEntity;
import org.apache.commons.lang.NotImplementedException;

import java.util.Optional;

public interface GenericCrud<T extends AbstractEntity> {
    default T save(T entity) {
        throw new NotImplementedException();
    }

    default Iterable<T> save(Iterable<T> entities){
        throw new NotImplementedException();
    }

    Optional<T> find(Long entityId);

    default T update(T entity){
        return save(entity);
    }

    void delete(Long entityId);
}
