package fr.univlorrainem1archi.friendsfiestas_v1.address.resource;

import fr.univlorrainem1archi.friendsfiestas_v1.address.model.RequestBodyAddress;
import fr.univlorrainem1archi.friendsfiestas_v1.address.service.AddressService;
import fr.univlorrainem1archi.friendsfiestas_v1.common.Response;
import fr.univlorrainem1archi.friendsfiestas_v1.common.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("api/v1/friends-fiestas/adresses")
public class AddressController {

    private final AddressService addressService;

    @Autowired
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping("/")
    public ResponseEntity<Response> getAddresses(){
        return new ResponseBuilder(OK,"Retrieving all addresses","addresses",addressService.getAddresses()).buildResponse();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getAddress(@PathVariable("id") Long id){
        return new ResponseBuilder(OK,"Retrieve an address","address",addressService.getAddress(id)).buildResponse();
    }

    @PostMapping("/ajouter")
    public ResponseEntity<Response> create(@Valid @RequestBody RequestBodyAddress address){
        return new ResponseBuilder(CREATED,"Address created successfully!","address",addressService.create(address)).buildResponse();
    }

    @PutMapping("/modifier/{id}")
    public ResponseEntity<Response> update(@PathVariable("id") Long id,@Valid @RequestBody  RequestBodyAddress address){
        if(!addressService.existById(id)){
          return (ResponseEntity<Response>) ResponseEntity.notFound();
        }
        return new ResponseBuilder(OK,"Address Updated successfully!","address",addressService.update(id,address)).buildResponse();
    }

    @DeleteMapping("/supprimer/{id}")
    public ResponseEntity<Response> delete(@PathVariable("id") Long id){
        if(!addressService.existById(id)){
            return (ResponseEntity<Response>) ResponseEntity.notFound();
        }
        return new ResponseBuilder(OK,"Address deleted Successfully","IsDeleted",addressService.delete(id)).buildResponse();
    }
}
