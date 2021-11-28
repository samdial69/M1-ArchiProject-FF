package fr.univlorrainem1archi.friendsfiestas_v1.message.repository;

import fr.univlorrainem1archi.friendsfiestas_v1.message.models.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepo extends JpaRepository<Message,Long> {
    List<Message> findMessagesBySalonId(Long  salonId);
}
