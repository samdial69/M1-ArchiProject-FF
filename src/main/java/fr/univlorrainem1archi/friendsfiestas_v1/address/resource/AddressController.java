package fr.univlorrainem1archi.friendsfiestas_v1.address.resource;

import fr.univlorrainem1archi.friendsfiestas_v1.address.model.Address;
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

    @GetMapping("/voir-addresse")
    public ResponseEntity<Response> getAddress(Address address){
        return new ResponseBuilder(OK,"Retrieve an address","address",addressService.getAddress(address)).buildResponse();
    }

    @PostMapping("/ajouter")
    public ResponseEntity<Response> create(@Valid @RequestBody Address address){
        return new ResponseBuilder(CREATED,"Address created successfully!","address",addressService.create(address)).buildResponse();
    }

    @PutMapping("/modifier/{id}")
    public ResponseEntity<Response> update(@PathVariable Long id,@Valid @RequestBody  Address address){
        if(!addressService.existAddress(id)){
          return (ResponseEntity<Response>) ResponseEntity.notFound();
        }
        return new ResponseBuilder(OK,"Address Updated successfully!","address",addressService.update(id,address)).buildResponse();
    }

    @DeleteMapping("/supprimer/{id}")
    public ResponseEntity<Response> delete(@PathVariable Long id){
        if(!addressService.existAddress(id)){
            return (ResponseEntity<Response>) ResponseEntity.notFound();
        }
        return new ResponseBuilder(OK,"Address deleted Successfully","IsDeleted",addressService.delete(id)).buildResponse();
    }
}
