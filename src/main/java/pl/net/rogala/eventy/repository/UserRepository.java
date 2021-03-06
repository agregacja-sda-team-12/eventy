package pl.net.rogala.eventy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.net.rogala.eventy.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Finding user in database by user's email.
     * @param email
     * @return Optional<User>
     */
    Optional<User> findByEmail(String email);

    /**
     * Finding user in database by user's nick.
     * @param nick
     * @return Optional<User>
     */
    Optional<User>findByNick(String nick);
    List<User> findAllByNick(String nick);
}
