package fr.univlorrainem1archi.friendsfiestas_v1.salon.resource;

import fr.univlorrainem1archi.friendsfiestas_v1.address.model.RequestBodyAddress;
import fr.univlorrainem1archi.friendsfiestas_v1.common.Response;
import fr.univlorrainem1archi.friendsfiestas_v1.common.ResponseBuilder;
import fr.univlorrainem1archi.friendsfiestas_v1.constantes.Search;
import fr.univlorrainem1archi.friendsfiestas_v1.member.model.Member;
import fr.univlorrainem1archi.friendsfiestas_v1.message.models.Message;
import fr.univlorrainem1archi.friendsfiestas_v1.salon.models.RequestBodySalon;
import fr.univlorrainem1archi.friendsfiestas_v1.salon.services.SalonService;
import fr.univlorrainem1archi.friendsfiestas_v1.task.models.RequestBodyTask;
import fr.univlorrainem1archi.friendsfiestas_v1.task.models.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("api/v1/friends-fiestas/salons")
public class SalonController {
    private final SalonService salonService;

    @Autowired
    public SalonController(SalonService salonService) {
        this.salonService = salonService;
    }

    @GetMapping("/")
    public ResponseEntity<Response> getSalons(){
        return new ResponseBuilder(OK,"Fetching all salons successfully!","salons",salonService.getSalons()).buildResponse();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getSalon(@PathVariable("id") Long id){
        return new ResponseBuilder(OK,"Fetching a salon by id:"+id,"salon",salonService.getSalon(id)).buildResponse();
    }

    @PostMapping("/ajouter")
    public ResponseEntity<Response> create(@RequestBody RequestBodySalon salon){
        return new ResponseBuilder(CREATED,"Created successfully","salon",salonService.create(salon)).buildResponse();
    }

    @PutMapping("/modifier/{id}")
    public ResponseEntity<Response> update(@PathVariable("id") Long id, @RequestBody RequestBodySalon salon){
        return new ResponseBuilder(OK,"Updated successfully","salon",salonService.update(id,salon)).buildResponse();
    }

    @DeleteMapping("/supprimer/{id}")
    public ResponseEntity<Response> delete(@PathVariable("id") Long id){
        return new ResponseBuilder(OK,"Deleted successfully!","isDeleted",salonService.delete(id)).buildResponse();
    }
    @GetMapping("/mes-salons/{idHost}")
    public ResponseEntity<Response> getSalonsByHost(@PathVariable("idHost") Long idHost){
        return new ResponseBuilder(OK,"Retrieving salons by host id: "+idHost,"salons",salonService.getSalonByHost(idHost)).buildResponse();
    }

    @PostMapping("/{idSalon}/ajouter-adresse")
    public ResponseEntity<Response> addAddress(@PathVariable("idSalon") Long idSalon,@RequestBody RequestBodyAddress address){
        return new ResponseBuilder(OK,"Adding successfully an address to the salon",
                "salon",
                salonService.saveOrUpdateAddressInSalon(idSalon,null,address)).buildResponse();
    }

    @PutMapping("/{idSalon}/modifier-adresse/{idAddress}")
    public ResponseEntity<Response> updateAddress(@PathVariable("idSalon") Long idSalon,
                                                  @PathVariable("idAddress") Long idAddress,
                                                  @RequestBody RequestBodyAddress address)
    {
        return new ResponseBuilder(OK,"Updated event address successfully!",
                "salon",
                salonService.saveOrUpdateAddressInSalon(idSalon,idAddress,address))
                .buildResponse();
    }

    @PostMapping(value = "/{idSalon}/ajouter-tache")
    public ResponseEntity<Response> addTask(@PathVariable("idSalon") Long idSalon,
                                            @RequestBody RequestBodyTask task){
        return new ResponseBuilder(OK,"Adding a task to a salon successfully!","salon",
                salonService.addTask(idSalon,task)).buildResponse();
    }

    @PutMapping("/{idSalon}/modifier-tache/{idTask}")
    public ResponseEntity<Response> updateTask(@PathVariable("idSalon") Long idSalon,
                                               @PathVariable("idTask") Long idTask,
                                               @RequestBody Task task){
        return new ResponseBuilder(OK,"Task modified successfully!","salon",
                salonService.updateTask(idSalon, idTask, task)).buildResponse();
    }

    @GetMapping("/{idSalon}/voir-taches")
    public ResponseEntity<Response> getTasksBySalon(@PathVariable("idSalon") Long idSalon){
        return new ResponseBuilder(OK, "Retrieving all tasks from salon", "task",
                salonService.getTasksBySalon(idSalon)).buildResponse();
    }

    @DeleteMapping("/{idSalon}/supprimer-tache/{idTask}")
    public ResponseEntity<Response> deleteTask(@PathVariable("idSalon") Long idSalon,
                                               @PathVariable("idTask") Long idTask){
        return new ResponseBuilder(OK,"Task deleted successfully!","salon",
                salonService.deleteTask(idSalon, idTask)).buildResponse();
    }

    @GetMapping("/{idSalon}/ajouter-membre/{idUser}")
    public ResponseEntity<Response> addMembreToSalon(@PathVariable("idSalon") Long idSalon,
                                                     @PathVariable("idUser") Long idUser){
        return new ResponseBuilder(OK,"Adding user to a salon : become member","salon",
                salonService.addMemberToSalon(idSalon,idUser)).buildResponse();
    }

    @GetMapping("/{idSalon}/membres")
    public ResponseEntity<Response> getMembersBySalon(@PathVariable("salonId") Long salonId){
        return new ResponseBuilder(OK,"Retrieve members of salon id"+salonId,"salon",salonService.getMembers(salonId)).buildResponse();
    }

    @PutMapping("{idSalon}/presence-membre/{idMember}")
    public ResponseEntity<Response> setPresenceMember(@PathVariable("idSalon") Long idSalon,
                                                      @PathVariable("idMember") Long idMember,
                                                      @RequestBody Member member){
        return new ResponseBuilder(OK, "Member presence is update", "salon", salonService.setPresenceMember(idSalon, idMember, member)).buildResponse();
    }

    @DeleteMapping("/{idSalon}/supprimer-membre/{idMember}")
    public ResponseEntity<Response> deleteMember(@PathVariable("idSalon") Long idSalon,
                                                 @PathVariable("idMember") Long idMember){
        return new ResponseBuilder(OK, "Member deleted successfully", "salon",
                salonService.deleteMember(idSalon, idMember)).buildResponse();
    }

    @GetMapping("/{idSalon}/taches/{idTask}/membre/{idMember}")
    public ResponseEntity<Response> affectMemberToTask(@PathVariable("idSalon") Long salonId,
                                                       @PathVariable("idTask") Long idTask,
                                                       @PathVariable("idMember") Long idMember){
        return new ResponseBuilder(OK,"Task affected successfully to a member!",
                "salon",
                salonService.affectMemberToTask(salonId,idTask,idMember)).buildResponse();
    }

    @PutMapping("/{idSalon}/membre/{idMember}/valider-tache/{idTask}")
    public ResponseEntity<Response> validateTask(@PathVariable("idSalon") Long idSalon,
                                                 @PathVariable("idMember") Long idMember,
                                                 @PathVariable("idTask") Long idTask){
        return new ResponseBuilder(OK, "Task validated successfully", "salon",
                salonService.validateTask(idSalon, idMember, idTask)).buildResponse();
    }

    @PostMapping("/{idSalon}/membre/{idMember}/ajouter-message")
    public ResponseEntity<Response> addMessage(@PathVariable("idSalon") Long idSalon,
                                               @PathVariable("idMember") Long idMember,
                                               @RequestBody Message message)
    {
        return new ResponseBuilder(OK,"Adding a new message to a salon successfully!","salon",
                salonService.addMessage(idSalon,idMember,message)).buildResponse();
    }

    @GetMapping("/{userId}/tous-les-salons")
    public ResponseEntity<Response> getSalonsByMembers(@PathVariable("userId") Long userId){
        return new ResponseBuilder(OK,"Retrieving all salons where am member by id "+userId,"salons",
                salonService.getSalonsByMembers(userId)).buildResponse();
    }

    @GetMapping("/{idSalon}/voir-messages")
    public ResponseEntity<Response> getmessages(@PathVariable("idSalon") Long idSalon){
        return new ResponseBuilder(OK,"Retrieving all messages","messages",salonService.getMessages(idSalon)).buildResponse();
    }

    @PostMapping("/recherche-utilisateurs")
    public ResponseEntity<Response> getUserPseudoContains(@RequestBody Search pseudo){
        return new ResponseBuilder(OK,"Searching user by pseudo contains: "+pseudo,"users",
                salonService.getUserPseudoLike(pseudo.getPseudo())).buildResponse();
    }

    @GetMapping("/{idSalon}/nb-messages")
    public ResponseEntity<Response> getMessagesBySalon(@PathVariable("idSalon") Long idSalon){
        return new ResponseBuilder(OK, "Retrieving all messages by salon id"+idSalon, "number_message",
                salonService.getMessages(idSalon).size()).buildResponse();
    }

}
