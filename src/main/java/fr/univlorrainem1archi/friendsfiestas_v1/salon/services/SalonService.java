package fr.univlorrainem1archi.friendsfiestas_v1.salon.services;

import fr.univlorrainem1archi.friendsfiestas_v1.address.model.Address;
import fr.univlorrainem1archi.friendsfiestas_v1.address.repository.AddressRepo;
import fr.univlorrainem1archi.friendsfiestas_v1.address.service.AddressService;
import fr.univlorrainem1archi.friendsfiestas_v1.salon.models.Salon;
import fr.univlorrainem1archi.friendsfiestas_v1.salon.repository.SalonRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@Slf4j
public class SalonService implements ISalonService{
    private final SalonRepo salonRepo;
    private final AddressService addressService;

    @Autowired
    public SalonService(SalonRepo salonRepo,AddressService addressService) {
        this.salonRepo = salonRepo;
        this.addressService = addressService;
    }

    @Override
    public List<Salon> getSalons() {
        log.info("Retrieving all salons");
        return salonRepo.findAll();
    }

    @Override
    public Salon getSalon(Long id) {
        if (!existById(id)){
            throw new IllegalArgumentException("Not salon found by id: "+id);
        }
        log.info("Retrieving a salon by id: {} ",id);
        return salonRepo.findById(id).orElseThrow();
    }

    @Override
    public Salon create(Salon salon) {
        log.info("Creating a salon : "+salon.getName());
        return salonRepo.save(salon);
    }

    @Override
    public Salon update(Long id, Salon salon) {
        if (!existById(id)){
            throw new IllegalArgumentException("Not salon found by id: "+id);
        }
        log.info("Updating a salon by id: {}",id);
        salon.setId(id);
        return salonRepo.save(salon) ;
    }

    @Override
    public boolean delete(Long id) {
        if (!existById(id)){
            throw new IllegalArgumentException("Not salon found by id: "+id);
        }
        log.info("Deleting a salon by id: {}",id);
        salonRepo.deleteById(id);
        return true;
    }

    @Override
    public Salon updateAddressInSalon(Long salonId,Long addressId, Address address) {
        if (!existById(salonId)){
            throw new IllegalArgumentException("Not salon found by id: "+salonId);
        }
        addressService.update(addressId,address);
        Salon salon = salonRepo.findById(salonId).orElseThrow();
        salon.setAddressEvent(address);
        return salonRepo.save(salon);
    }

    @Override
    public Salon saveAddressInSalon(Long salonId, Address address) {
        if (!existById(salonId)){
            throw new IllegalArgumentException("Not salon found by id: "+salonId);
        }
        addressService.create(address);
        Salon salon = salonRepo.findById(salonId).orElseThrow();
        salon.setAddressEvent(address);
        return salonRepo.save(salon);
    }

    @Override
    public Salon saveOrUpdateAddressInSalon(Long salonId,Long addressId,Address address){
        if(!existById(salonId)){
            throw new IllegalArgumentException("Not salon found by id: "+salonId);
        }
        if (addressId == null){
            addressService.create(address);
        }else {
            addressService.update(addressId,address);
        }
        Salon salon = salonRepo.findById(salonId).orElseThrow();
        salon.setAddressEvent(address);
        return salonRepo.save(salon);
    }

    public boolean existById(Long id){
        return salonRepo.existsById(id);
    }
}
