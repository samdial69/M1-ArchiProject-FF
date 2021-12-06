package fr.univlorrainem1archi.friendsfiestas_v1.salon.services;

import fr.univlorrainem1archi.friendsfiestas_v1.address.model.RequestBodyAddress;
import fr.univlorrainem1archi.friendsfiestas_v1.member.model.Member;
import fr.univlorrainem1archi.friendsfiestas_v1.message.models.Message;
import fr.univlorrainem1archi.friendsfiestas_v1.salon.models.RequestBodySalon;
import fr.univlorrainem1archi.friendsfiestas_v1.salon.models.Salon;
import fr.univlorrainem1archi.friendsfiestas_v1.task.models.RequestBodyTask;
import fr.univlorrainem1archi.friendsfiestas_v1.task.models.Task;

import java.util.List;

public interface ISalonService {
    List<Salon> getSalons();
    Salon getSalon(Long id);
    Salon create(RequestBodySalon salon);
    Salon update(Long id,RequestBodySalon salon);
    boolean delete(Long id);
    List<Salon> getSalonsByHostId(Long id);

    Salon saveOrUpdateAddressInSalon(Long salonId,Long addressId, RequestBodyAddress address);

    Salon addMemberToSalon(Long idSalon,Long idUser);
    Salon affectMemberToTask(Long salonId, Long idTask, Long idMember);
    List<Member> getMembers(Long salonId);
    Salon deleteMember(Long idSalon, Long idMember);

    Salon addTask(Long salonId, RequestBodyTask task);
    Salon validateTask(Long idSalon, Long idMember, Long idTask);
    Salon updateTask(Long idSalon, Long idTask, Task task);
    Salon deleteTask(Long idSalon, Long idTask);

    Salon addMessage(Long idSalon, Long idMember, Message message);
}
