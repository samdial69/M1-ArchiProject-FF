package fr.univlorrainem1archi.friendsfiestas_v1.salon.services;

import fr.univlorrainem1archi.friendsfiestas_v1.address.model.RequestBodyAddress;
import fr.univlorrainem1archi.friendsfiestas_v1.address.service.AddressService;
import fr.univlorrainem1archi.friendsfiestas_v1.member.model.Member;
import fr.univlorrainem1archi.friendsfiestas_v1.member.services.MemberService;
import fr.univlorrainem1archi.friendsfiestas_v1.message.models.Message;
import fr.univlorrainem1archi.friendsfiestas_v1.message.services.MessageService;
import fr.univlorrainem1archi.friendsfiestas_v1.salon.models.RequestBodySalon;
import fr.univlorrainem1archi.friendsfiestas_v1.salon.models.Salon;
import fr.univlorrainem1archi.friendsfiestas_v1.salon.models.SalonDTO;
import fr.univlorrainem1archi.friendsfiestas_v1.salon.models.SalonMapper;
import fr.univlorrainem1archi.friendsfiestas_v1.salon.repository.SalonRepo;
import fr.univlorrainem1archi.friendsfiestas_v1.task.models.RequestBodyTask;
import fr.univlorrainem1archi.friendsfiestas_v1.task.models.Task;
import fr.univlorrainem1archi.friendsfiestas_v1.task.services.TaskService;
import fr.univlorrainem1archi.friendsfiestas_v1.user.models.User;
import fr.univlorrainem1archi.friendsfiestas_v1.user.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@Slf4j
public class SalonService implements ISalonService{
    private final SalonRepo salonRepo;
    private final SalonMapper salonMapper;

    private final AddressService addressService;
    private final TaskService taskService;
    private final UserService userService;
    private final MemberService memberService;
    private final MessageService messageService;

    @Autowired
    public SalonService(SalonRepo salonRepo, SalonMapper salonMapper, AddressService addressService, TaskService taskService,
                        MessageService messageService,
                        UserService userService,
                        MemberService memberService) {
        this.salonRepo = salonRepo;
        this.salonMapper = salonMapper;
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
    public Salon create(RequestBodySalon salonBody) {
        log.info("Creating a salon : "+salonBody.getName());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User loggedUser = userService.findUserByPseudo(authentication.getName());

        Salon salon = convert(salonBody);
        salon.setHost(loggedUser);
        Member member = new Member();
        member.setSalon(salon);
        member.setUser(loggedUser);
        memberService.create(member);
        salonRepo.save(salon);
        return getSalon(salon.getId());
    }

    @Override
    public Salon update(Long id, RequestBodySalon salon) {
        if (!existById(id)){
            throw new IllegalArgumentException("Not salon found by id: "+id);
        }
        log.info("Updating a salon by id: {}",id);
        SalonDTO salonDTO = salonMapper.to(salon);
        salonDTO.setId(id);
        return salonRepo.save(this.salonMapper.to(salonDTO)) ;
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
    public Salon addTask(Long salonId, RequestBodyTask task) {
        if(!existById(salonId)){
            throw new IllegalArgumentException("Not salon found by id: "+salonId);
        }
        Salon salon = getSalon(salonId);

        Task taskConvert = this.taskService.convert(task);
        taskConvert.setSalon(salon);
        this.taskService.create(taskService.convertTo(taskConvert));
        return salon;
    }

    @Override
    public Salon saveOrUpdateAddressInSalon(Long salonId, Long addressId, RequestBodyAddress address){
        if(!existById(salonId)){
            throw new IllegalArgumentException("Not salon found by id: "+salonId);
        }
        if (addressId == null){
            addressService.create(address);
        }else {
            addressService.update(addressId,address);
        }
        Salon salon = this.getSalon(salonId);

        salon.setAddressEvent(this.addressService.convert(address));
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

    public Salon validateTask(Long idSalon, Long idMember, Long idTask) {
        if(!existById(idSalon)){
            throw new IllegalArgumentException("Not salon found by id: "+idSalon);
        }
        if(!memberService.existById(idMember)){
            throw new IllegalArgumentException("Not member found by id: "+idMember);
        }
        if(!taskService.existById(idTask)){
            throw new IllegalArgumentException("Not task found by id: "+idTask);
        }
        Task task = taskService.getTask(idTask);
        if(task.getSalon().getId().equals(idSalon)) {
            if(task.getAffectedMember().getId().equals(idMember))
            {
                task.setDone(true);
                taskService.update(idTask, taskService.convertTo(task));
            }
            else {
                throw new IllegalArgumentException("Member by id "+ idMember + " is not affected to this task");
            }
        }
        else {
            throw new IllegalArgumentException("Task by id " + idTask + " is not in this salon");
        }
        return this.getSalon(idSalon);
    }

    @Override
    public Salon affectMemberToTask(Long salonId, Long idTask, Long idMember) {
        if (!existById(salonId)){
            throw new IllegalArgumentException("Not salon found by id: "+salonId);
        }
        if (!taskService.existById(idTask)){
            throw new IllegalArgumentException("Not task found by id: "+idTask);
        }
        if (!memberService.existById(idMember)){
            throw new IllegalArgumentException("Not member found by id: "+idMember);
        }
        Member member = memberService.getMember(idMember);
        if (member.getSalon().getId().equals(salonId)){
            Task task = taskService.getTask(idTask);
            task.setAffectedMember(member);
            taskService.update(idTask,taskService.convertTo(task));
        }else {
            throw new IllegalArgumentException("Member by id "+idMember+" is not allowed to get a task in this salon");
        }
        return this.getSalon(salonId);
    }

    @Override
    public Salon addMessage(Long idSalon, Long idMember, Message message) {
        if (!existById(idSalon)){
            throw new IllegalArgumentException("Not salon found by id: "+idSalon);
        }
        if (!memberService.existById(idMember)){
            throw new IllegalArgumentException("Not member found by id: "+idMember);
        }
        Member member = memberService.getMember(idMember);
        if (member.getSalon().getId().equals(idSalon)){
            message.setMember(memberService.getMember(idMember));
            messageService.create(message);
            //memberService.update(idMember,member);
        }else {
            throw new IllegalArgumentException("Tu devrais arriver ici!");
        }
        return this.getSalon(idSalon);
    }

    private Salon convert(RequestBodySalon salon){
        SalonDTO salonDTO = this.salonMapper.to(salon);
        return this.salonMapper.to(salonDTO);
    }
}
