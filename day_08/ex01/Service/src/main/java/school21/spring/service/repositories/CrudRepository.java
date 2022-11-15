package school21.spring.service.repositories;

import school21.spring.service.models.User;

import java.util.List;

public interface CrudRepository<T> {
    User findById(Long id);
    List<T> findAll();
    void save(T entity);
    void update(T entity);
    void delete(Long id);
}
