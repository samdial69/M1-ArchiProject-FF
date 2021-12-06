package fr.univlorrainem1archi.friendsfiestas_v1.salon.repository;

import fr.univlorrainem1archi.friendsfiestas_v1.salon.models.Salon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SalonRepo extends JpaRepository<Salon,Long> {
    List<Salon> findSalonByHost_Id(Long hostId);
}
