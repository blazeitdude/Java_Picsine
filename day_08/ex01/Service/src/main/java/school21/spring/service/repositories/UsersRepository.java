package school21.spring.service.repositories;

import java.util.Optional;
import school21.spring.service.models.User;

public interface UsersRepository extends CrudRepository<User> {
    Optional<User> findByEmail(String email);
}
