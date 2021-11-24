package fr.univlorrainem1archi.friendsfiestas_v1.address.service;

import fr.univlorrainem1archi.friendsfiestas_v1.address.model.Address;
import fr.univlorrainem1archi.friendsfiestas_v1.address.repo.AddressRepo;
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
    public Address getAddress(Address address) {
        log.info("Retrieve an address {}",address.getCompleteAddress());
        return addressRepo.findById(address.getId()).orElseThrow();
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
        if (existAddress(id)){
            throw  new IllegalArgumentException("No address found by id:"+ id);
        }
        addressRepo.deleteById(id);
        return true;
    }

    public boolean existAddress(Long id){
        return addressRepo.existsById(id);
    }
}
