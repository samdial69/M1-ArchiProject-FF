package fr.univlorrainem1archi.friendsfiestas_v1.message.services;

import fr.univlorrainem1archi.friendsfiestas_v1.message.models.Message;
import fr.univlorrainem1archi.friendsfiestas_v1.message.repository.MessageRepo;
import fr.univlorrainem1archi.friendsfiestas_v1.salon.models.Salon;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@Slf4j
public class MessageService implements IMessage{
    private final MessageRepo messageRepo;

    @Autowired
    public MessageService(MessageRepo messageRepo) {
        this.messageRepo = messageRepo;
    }

    public Message create(Message message) {
        log.info("Creating a message");
        return messageRepo.save(message);
    }


    public List<Message> getMessages(Salon salonId) {
        log.info("Fetching all tasks");
        return messageRepo.findMessagesByMember_Salon(salonId);
    }

//
//    @Override
//    public Message create(Message message) {
//        log.info("Creating a message");
//        return messageRepo.save(message);
//    }
//
//    @Override
//    public boolean delete(Long id) {
//        log.info("Deleting message by id {}:", id);
//        if (!existById(id)) {
//            throw new IllegalArgumentException("No message found by id:" + id);
//        }
//        messageRepo.deleteById(id);
//        return true;
//    }
//    public boolean existById(Long id){
//        return messageRepo.existsById(id);
//    }

}
