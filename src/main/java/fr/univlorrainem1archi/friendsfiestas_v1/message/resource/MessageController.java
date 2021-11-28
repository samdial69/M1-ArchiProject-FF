package fr.univlorrainem1archi.friendsfiestas_v1.message.resource;

import fr.univlorrainem1archi.friendsfiestas_v1.common.Response;
import fr.univlorrainem1archi.friendsfiestas_v1.common.ResponseBuilder;
import fr.univlorrainem1archi.friendsfiestas_v1.message.models.Message;
import fr.univlorrainem1archi.friendsfiestas_v1.message.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("api/v1/friends-fiestas/messages")
public class MessageController {
    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/{salonId}")
    public ResponseEntity<Response> getMessages(@PathVariable("salonId") Long salonId){
        return new ResponseBuilder(OK,"Retrieving all messages in a salon id: "+salonId,
                "messages",messageService.getMessages(salonId)).buildResponse();
    }

    @PostMapping("/ajouter")
    public ResponseEntity<Response> create(@RequestBody Message message)
    {
        return new ResponseBuilder(CREATED,"Create new message successfully!","message",
                messageService.create(message)).buildResponse();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> delete(@PathVariable("id") Long id){
        return new ResponseBuilder(OK,"Deleting message successfully!","isDeleted",
                messageService.delete(id)).buildResponse();
    }
}
