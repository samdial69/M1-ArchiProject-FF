package fr.univlorrainem1archi.friendsfiestas_v1.message.repository;

import fr.univlorrainem1archi.friendsfiestas_v1.message.models.Message;
import fr.univlorrainem1archi.friendsfiestas_v1.salon.models.Salon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepo extends JpaRepository<Message,Long> {
    List<Message> findMessagesByMember_Salon(Salon salon);
//    List<Message> findMessagesByMember_Salon(Salon salon);
}
