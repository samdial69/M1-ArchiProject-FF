package fr.univlorrainem1archi.friendsfiestas_v1.salon.resource;

import fr.univlorrainem1archi.friendsfiestas_v1.address.model.Address;
import fr.univlorrainem1archi.friendsfiestas_v1.common.Response;
import fr.univlorrainem1archi.friendsfiestas_v1.common.ResponseBuilder;
import fr.univlorrainem1archi.friendsfiestas_v1.salon.models.Salon;
import fr.univlorrainem1archi.friendsfiestas_v1.salon.services.SalonService;
import fr.univlorrainem1archi.friendsfiestas_v1.task.models.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<Response> create(@RequestBody Salon salon){
        return new ResponseBuilder(CREATED,"Created successfully","salon",salonService.create(salon)).buildResponse();
    }

    @PutMapping("/modifier/{id}")
    public ResponseEntity<Response> update(@PathVariable("id") Long id, @RequestBody Salon salon){
        return new ResponseBuilder(OK,"Updated successfully","salon",salonService.update(id,salon)).buildResponse();
    }

    @DeleteMapping("/supprimer/{id}")
    public ResponseEntity<Response> delete(@PathVariable("id") Long id){
        return new ResponseBuilder(OK,"Deleted successfully!","isDeleted",salonService.delete(id)).buildResponse();
    }

    @PutMapping("/{idSalon}/modifier-adresse/{idAddress}")
    public ResponseEntity<Response> updateAddress(@PathVariable("idSalon") Long idSalon,
                                                  @PathVariable("idAddress") Long idAddress,
                                                  @RequestBody Address address)
    {
        return new ResponseBuilder(OK,"Updated event address successfully!",
                "salon",
                salonService.saveOrUpdateAddressInSalon(idSalon,idAddress,address))
                .buildResponse();
    }

    @PostMapping("/{idSalon}/ajouter-adresse")
    public ResponseEntity<Response> addAddress(@PathVariable("idSalon") Long idSalon,@RequestBody Address address){
        return new ResponseBuilder(OK,"Adding successfully an address to the salon",
                "salon",
                salonService.saveOrUpdateAddressInSalon(idSalon,null,address)).buildResponse();
    }

    @PostMapping(value = "/{idSalon}/ajouter-tache")
    public ResponseEntity<Response> addTask(@PathVariable("idSalon") Long idSalon, @RequestBody Task task){
        return new ResponseBuilder(OK,"Adding a task to a salon successfully!","salon",
                salonService.addTask(idSalon,task)).buildResponse();
    }

    @GetMapping("/{idSalon}/ajouter-membre/{idUser}")
    public ResponseEntity<Response> addMembreToSalon(@PathVariable("idSalon") Long idSalon,@PathVariable("idUser") Long idUser){
        return new ResponseBuilder(OK,"Adding user to a salon : become member","salon",
                salonService.addMemberToSalon(idSalon,idUser)).buildResponse();
    }

    @PutMapping("/{idSalon}/member/{idMember}valider-tache/{idTask}")
    public ResponseEntity<Response> validateTask(@PathVariable("idSalon") Long idSalon, @PathVariable("idMember") Long idMember, @PathVariable("idTask") Long idTask){
        return new ResponseBuilder(OK, "Tâche validée avec succès", "salon",
                salonService.validateTask(idSalon, idMember, idTask)).buildResponse();
    }

//    @PostMapping("/{idSalon}/ajouter-message")
//    public ResponseEntity<Response> addMessage(@PathVariable("idSalon") Long idSalon, @RequestBody Message message)
//    {
//        return new ResponseBuilder(OK,"Adding a new message to a salon successfully!","salon",
//                salonService.addMessage(idSalon,message)).buildResponse();
//    }

}
