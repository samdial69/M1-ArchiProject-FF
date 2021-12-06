package fr.univlorrainem1archi.friendsfiestas_v1.salon.resource;

import fr.univlorrainem1archi.friendsfiestas_v1.address.model.RequestBodyAddress;
import fr.univlorrainem1archi.friendsfiestas_v1.common.Response;
import fr.univlorrainem1archi.friendsfiestas_v1.common.ResponseBuilder;
import fr.univlorrainem1archi.friendsfiestas_v1.message.models.Message;
import fr.univlorrainem1archi.friendsfiestas_v1.salon.models.RequestBodySalon;
import fr.univlorrainem1archi.friendsfiestas_v1.salon.services.SalonService;
import fr.univlorrainem1archi.friendsfiestas_v1.task.models.RequestBodyTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/{idSalon}/ajouter-adresse")
    public ResponseEntity<Response> addAddress(@PathVariable("idSalon") Long idSalon,@RequestBody RequestBodyAddress address){
        return new ResponseBuilder(OK,"Adding successfully an address to the salon",
                "salon",
                salonService.saveOrUpdateAddressInSalon(idSalon,null,address)).buildResponse();
    }

    @PostMapping(value = "/{idSalon}/ajouter-tache")
    public ResponseEntity<Response> addTask(@PathVariable("idSalon") Long idSalon,
                                            @RequestBody RequestBodyTask task){
        return new ResponseBuilder(OK,"Adding a task to a salon successfully!","salon",
                salonService.addTask(idSalon,task)).buildResponse();
    }

    @GetMapping("/{idSalon}/ajouter-membre/{idUser}")
    public ResponseEntity<Response> addMembreToSalon(@PathVariable("idSalon") Long idSalon,
                                                     @PathVariable("idUser") Long idUser){
        return new ResponseBuilder(OK,"Adding user to a salon : become member","salon",
                salonService.addMemberToSalon(idSalon,idUser)).buildResponse();
    }

    @PutMapping("/{idSalon}/membre/{idMember}/valider-tache/{idTask}")
    public ResponseEntity<Response> validateTask(@PathVariable("idSalon") Long idSalon,
                                                 @PathVariable("idMember") Long idMember,
                                                 @PathVariable("idTask") Long idTask){
        return new ResponseBuilder(OK, "Task validated successfully", "salon",
                salonService.validateTask(idSalon, idMember, idTask)).buildResponse();
    }

    @GetMapping("/{idSalon}/taches/{idTask}/membre/{idMember}")
    public ResponseEntity<Response> affectMemberToTask(@PathVariable("idSalon") Long salonId,
                                                       @PathVariable("idTask") Long idTask,
                                                       @PathVariable("idMember") Long idMember){
        return new ResponseBuilder(OK,"Task affected successfully to a member!",
                "salon",
                salonService.affectMemberToTask(salonId,idTask,idMember)).buildResponse();
    }

    @PostMapping("/{idSalon}/membre/{idMember}/ajouter-message")
    public ResponseEntity<Response> addMessage(@PathVariable("idSalon") Long idSalon,
                                               @PathVariable("idMember") Long idMember,
                                               @RequestBody Message message)
    {
        return new ResponseBuilder(OK,"Adding a new message to a salon successfully!","salon",
                salonService.addMessage(idSalon,idMember,message)).buildResponse();
    }

    @GetMapping("/{idSalon}/membres")
    public ResponseEntity<Response> getMembersBySalon(@PathVariable("salonId") Long salonId){
        return new ResponseBuilder(OK,"Retrieve members of salon id"+salonId,"salon",salonService.getMembers(salonId)).buildResponse();
    }

}
