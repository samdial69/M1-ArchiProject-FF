package fr.univlorrainem1archi.friendsfiestas_v1.address.service;

import fr.univlorrainem1archi.friendsfiestas_v1.address.model.Address;
import fr.univlorrainem1archi.friendsfiestas_v1.address.repository.AddressRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@Slf4j
public class AddressService implements IAddressService{
    private final AddressRepo addressRepo;

    @Autowired
    public AddressService(AddressRepo addressRepo) {
        this.addressRepo = addressRepo;
    }

    @Override
    public List<Address> getAddresses() {
        log.info("Retrieve all addresses");
        return this.addressRepo.findAll();
    }

    @Override
    public Address getAddress(Long id) {
        log.info("Retrieve an address by id {}",id);
        return addressRepo.findById(id).orElseThrow();
    }

    @Override
    public Address create(Address address) {
        log.info("Saving an address {}",address.getCompleteAddress());
        return addressRepo.save(address);
    }

    @Override
    public Address update(Long id, Address address) {
        log.info("Update an address {}",address.getCompleteAddress());
        address.setId(id);
        return addressRepo.save(address);
    }

    @Override
    public boolean delete(Long id) {
        log.info("Deleting address by id {}:", id);
        if (!existById(id)){
            throw  new IllegalArgumentException("No address found by id:"+ id);
        }
        addressRepo.deleteById(id);
        return true;
    }

    public boolean existById(Long id){
        return addressRepo.existsById(id);
    }
}
