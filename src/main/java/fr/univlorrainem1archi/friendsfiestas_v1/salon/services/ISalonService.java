package fr.univlorrainem1archi.friendsfiestas_v1.salon.services;

import fr.univlorrainem1archi.friendsfiestas_v1.address.model.Address;
import fr.univlorrainem1archi.friendsfiestas_v1.salon.models.Salon;

import java.util.List;

public interface ISalonService {
    List<Salon> getSalons();
    Salon getSalon(Long id);
    Salon create(Salon salon);
    Salon update(Long id,Salon salon);
    boolean delete(Long id);

    Salon updateAddressInSalon(Long salonId,Long addressId, Address address);
    Salon saveAddressInSalon(Long salonId, Address address);
    Salon saveOrUpdateAddressInSalon(Long salonId,Long addressId, Address address);

}
