package fr.univlorrainem1archi.friendsfiestas_v1.user.repository;

import fr.univlorrainem1archi.friendsfiestas_v1.user.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role,Long> {
}
