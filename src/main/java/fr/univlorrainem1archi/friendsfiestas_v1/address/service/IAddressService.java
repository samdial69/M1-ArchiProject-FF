package fr.univlorrainem1archi.friendsfiestas_v1.address.service;

import fr.univlorrainem1archi.friendsfiestas_v1.address.model.Address;

import java.util.List;

public interface IAddressService {
    List<Address> getAddresses();
    Address getAddress(Long id);
    Address create(Address address);
    Address update(Long id,Address address);
    boolean delete(Long id);
}
