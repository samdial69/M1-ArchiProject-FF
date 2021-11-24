package fr.univlorrainem1archi.friendsfiestas_v1.address.repo;

import fr.univlorrainem1archi.friendsfiestas_v1.address.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepo extends JpaRepository<Address,Long> {
}
