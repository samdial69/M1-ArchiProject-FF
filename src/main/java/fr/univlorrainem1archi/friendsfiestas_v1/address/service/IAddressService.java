package fr.univlorrainem1archi.friendsfiestas_v1.address.service;

import fr.univlorrainem1archi.friendsfiestas_v1.address.model.Address;
import fr.univlorrainem1archi.friendsfiestas_v1.address.model.RequestBodyAddress;

import java.util.List;

public interface IAddressService {
    List<Address> getAddresses();
    Address getAddress(Long id);
    Address create(RequestBodyAddress address);
    Address update(Long id,RequestBodyAddress address);
    boolean delete(Long id);
}
