package fr.univlorrainem1archi.friendsfiestas_v1.message.services;

import fr.univlorrainem1archi.friendsfiestas_v1.message.models.Message;
import fr.univlorrainem1archi.friendsfiestas_v1.salon.models.Salon;

import java.util.List;

public interface IMessage {
   List<Message> getMessages(Salon salon);
//    Message create(Message message);
//    boolean delete(Long id);

}
