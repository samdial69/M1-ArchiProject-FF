package fr.univlorrainem1archi.friendsfiestas_v1.salon.services;

import fr.univlorrainem1archi.friendsfiestas_v1.address.model.Address;
import fr.univlorrainem1archi.friendsfiestas_v1.salon.models.Salon;
import fr.univlorrainem1archi.friendsfiestas_v1.task.models.Task;

import java.util.List;

public interface ISalonService {
    List<Salon> getSalons();
    Salon getSalon(Long id);
    Salon create(Salon salon);
    Salon update(Long id,Salon salon);
    boolean delete(Long id);

    Salon saveOrUpdateAddressInSalon(Long salonId,Long addressId, Address address);
    Salon addTask(Long salonId, Task task);
    Salon addMemberToSalon(Long idSalon,Long idUser);
    Salon affectMemberToTask(Long salonId, Long idTask, Long idMember);
//    Salon addMessage(Long salonId, Message message);
}
