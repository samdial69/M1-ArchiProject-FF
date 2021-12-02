package fr.univlorrainem1archi.friendsfiestas_v1.salon.services;

import fr.univlorrainem1archi.friendsfiestas_v1.address.model.Address;
import fr.univlorrainem1archi.friendsfiestas_v1.address.service.AddressService;
import fr.univlorrainem1archi.friendsfiestas_v1.member.model.Member;
import fr.univlorrainem1archi.friendsfiestas_v1.member.services.MemberService;
import fr.univlorrainem1archi.friendsfiestas_v1.message.services.MessageService;
import fr.univlorrainem1archi.friendsfiestas_v1.salon.models.Salon;
import fr.univlorrainem1archi.friendsfiestas_v1.salon.repository.SalonRepo;
import fr.univlorrainem1archi.friendsfiestas_v1.task.models.Task;
import fr.univlorrainem1archi.friendsfiestas_v1.task.services.TaskService;
import fr.univlorrainem1archi.friendsfiestas_v1.user.models.User;
import fr.univlorrainem1archi.friendsfiestas_v1.user.services.UserService;
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
    private final TaskService taskService;
    private final UserService userService;
    private final MemberService memberService;
    private final MessageService messageService;

    @Autowired
    public SalonService(SalonRepo salonRepo, AddressService addressService, TaskService taskService,
                        MessageService messageService,
                        UserService userService,
                        MemberService memberService) {
        this.salonRepo = salonRepo;
        this.addressService = addressService;
        this.taskService = taskService;
        this.messageService = messageService;
        this.userService = userService;
        this.memberService = memberService;
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
        //TODO set le host à partir de là quand on implementera Spring-security
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

//    @Override
//    public Salon addMessage(Long salonId, Message message) {
//        if(!existById(salonId)){
//            throw new IllegalArgumentException("Not salon found by id: "+salonId);
//        }
//        Salon salon = getSalon(salonId);
//        message.setSalon(salon);
//        messageService.create(message);
//        return salonRepo.save(salon);
//    }

    @Override
    public Salon addTask(Long salonId, Task task) {
        if(!existById(salonId)){
            throw new IllegalArgumentException("Not salon found by id: "+salonId);
        }
        Salon salon = getSalon(salonId);
        task.setSalon(salon);
        taskService.create(task);
        return salon;
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
        Salon salon = this.getSalon(salonId);
        salon.setAddressEvent(address);
        salonRepo.save(salon);
        return salon;
    }

    public boolean existById(Long id){
        return salonRepo.existsById(id);
    }

    public Salon addMemberToSalon(Long idSalon, Long idUser) {
        if (!existById(idSalon)){
            throw new IllegalArgumentException("Not salon found by id: "+idSalon);
        }
        if (!userService.existById(idUser)){
            throw new IllegalArgumentException("Not user found by id: "+idSalon);
        }
        Salon salon = this.getSalon(idSalon);
        User user = userService.getUser(idUser);

        Member member = new Member();
        member.setSalon(salon);
        member.setUser(user);

        memberService.create(member);
        return salon;
    }
}
