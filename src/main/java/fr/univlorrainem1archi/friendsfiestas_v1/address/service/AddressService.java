package fr.univlorrainem1archi.friendsfiestas_v1.address.service;

import fr.univlorrainem1archi.friendsfiestas_v1.address.model.Address;
import fr.univlorrainem1archi.friendsfiestas_v1.address.model.AddressDTO;
import fr.univlorrainem1archi.friendsfiestas_v1.address.model.AddressMapper;
import fr.univlorrainem1archi.friendsfiestas_v1.address.model.RequestBodyAddress;
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
    private final AddressMapper addressMapper;

    @Autowired
    public AddressService(AddressRepo addressRepo, AddressMapper addressMapper) {
        this.addressRepo = addressRepo;
        this.addressMapper = addressMapper;
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
    public Address create(RequestBodyAddress address) {
        log.info("Saving an address {}",address.getCompleteAddress());
        AddressDTO addressDTO = addressMapper.to(address);
        return addressRepo.save(this.addressMapper.to(addressDTO));
    }

    public Address convert(RequestBodyAddress address){
        AddressDTO addressDTO = addressMapper.to(address);
        return this.addressMapper.to(addressDTO);
    }

    @Override
    public Address update(Long id, RequestBodyAddress address) {
        log.info("Update an address {}",address.getCompleteAddress());
        AddressDTO addressDTO = addressMapper.to(address);
        addressDTO.setId(id);
        return addressRepo.save(this.addressMapper.to(addressDTO));
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
