package fr.univlorrainem1archi.friendsfiestas_v1.user.repository;

import fr.univlorrainem1archi.friendsfiestas_v1.user.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findUserByEmail(String email);
    User findUserByPseudo(String pseudo);
    List<User> findUsersByPseudoContains(String pseudo);
}
