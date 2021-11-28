package fr.univlorrainem1archi.friendsfiestas_v1.user.repository;

import fr.univlorrainem1archi.friendsfiestas_v1.user.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findUserByEmail(String email);
    User findUserByPseudo(String pseudo);
}
