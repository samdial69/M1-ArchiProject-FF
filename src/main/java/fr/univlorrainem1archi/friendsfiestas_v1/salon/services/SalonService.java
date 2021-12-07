package fr.univlorrainem1archi.friendsfiestas_v1.salon.services;

import fr.univlorrainem1archi.friendsfiestas_v1.address.model.RequestBodyAddress;
import fr.univlorrainem1archi.friendsfiestas_v1.address.service.AddressService;
import fr.univlorrainem1archi.friendsfiestas_v1.constantes.Presence;
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
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    public boolean existById(Long id){
        return salonRepo.existsById(id);
    }

    @Override
    public List<Salon> getSalons() {
        log.info("Retrieving all salons");
        return salonRepo.findAll();
    }

    @Override
    public List<Salon> getSalonsByHostId(Long id) {
        return salonRepo.findSalonByHost_Id(id);
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

    @Override
    public List<Member> getMembers(Long salonId) {
        if (!existById(salonId)){
            throw new IllegalArgumentException("Not salon found by id: "+salonId);
        }
        Salon salon = this.getSalon(salonId);
        return memberService.getMembersBySalonId(salon);
    }

    @Override
    public List<Salon> getSalonsByMembers(Long userId) {
        if (!userService.existById(userId)){
            throw new IllegalArgumentException("Not user found by id "+userId);
        }
        return memberService.getSalonsByMembers(userId);
    }

    public List<Salon> getSalonByHost(Long hostId){
        return salonRepo.findSalonByHost_Id(hostId);
    }

    @Override
    public List<Map<?, ?>> getMessages(Long salonId){
        if (!existById(salonId)){
            throw new IllegalArgumentException("Not salon found by id: "+salonId);
        }
        Salon salon = this.getSalon(salonId);
        List<Message> messages = messageService.getMessages(salon);
        return messages.stream().map(message -> {
            return Map.of("id",message.getMember().getUser().getId(),
                    "pseudo", message.getMember().getUser().getPseudo(),
                    "content",message.getContent(),
                    "sendAt",message.getSendAt());
        }).collect(Collectors.toList());
    }

    @Override
    public List<User> getUserPseudoContains(String pseudo) {
        if (!StringUtils.isNotEmpty(pseudo)){
            throw new IllegalArgumentException("This field can not be empty: "+pseudo);
        }
        return userService.getUsersPseudoContains(pseudo);
    }

    @Override
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

    @Override
    public Salon setPresenceMember(Long idSalon, Long idMember, Member member) {
        if (!existById(idSalon)){
            throw new IllegalArgumentException("Not salon found by id: "+idSalon);
        }
        if(!memberService.existById(idMember)){
            throw new IllegalArgumentException("Not member found by id: "+idMember);
        }
        Member currentMember = memberService.getMember(idMember);
        if(currentMember.getSalon().getId().equals(idSalon))
        {
            currentMember.setPresence(member.getPresence());
            memberService.update(idMember, currentMember);
            if(member.getPresence() == Presence.NON){
                memberService.delete(idMember);
            }
        }
        return this.getSalon(idSalon);
    }

    @Override
    public Salon deleteMember(Long idSalon, Long idMember) {
        if(!existById(idSalon)){
            throw new IllegalArgumentException("Not salon found by id: "+idSalon);
        }
        Salon salon = this.getSalon(idSalon);
        if(!memberService.existById(idMember)){
            throw new IllegalArgumentException("Not member found by id: "+idMember);
        }
        Member member = memberService.getMember(idMember);
        if(member.getSalon().getId().equals(idSalon)){
            memberService.delete(idMember);
            salonRepo.save(salon);
        }
        return salon;
    }

    @Override
    public Salon addTask(Long salonId, RequestBodyTask task) {
        if(!existById(salonId)){
            throw new IllegalArgumentException("Not salon found by id: "+salonId);
        }
        Salon salon = getSalon(salonId);

        Task taskConvert = this.taskService.convert(task);
        taskConvert.setSalon(salon);
        this.taskService.create(taskConvert);
        return salon;
    }

    @Override
    public Salon affectMemberToTask(Long salonId, Long idTask, Long idMember) {
        if (!existById(salonId)){
            throw new IllegalArgumentException("Not salon found by id: "+salonId);
        }
        Salon salon = this.getSalon(salonId);
        if (!taskService.existById(idTask)){
            throw new IllegalArgumentException("Not task found by id: "+idTask);
        }
        Task task = taskService.getTask(idTask);
        if (!memberService.existById(idMember)){
            throw new IllegalArgumentException("Not member found by id: "+idMember);
        }
        Member member = memberService.getMember(idMember);
        if (member.getSalon().getId().equals(salonId) && task.getSalon().getId().equals(salonId)){
            task.setAffectedMember(member);
            taskService.update(idTask, task);
        }
        else {
            throw new IllegalArgumentException("Member by id "+idMember+" is not allowed to get a task in this salon");
        }
        return salon;
    }

    @Override
    public Salon updateTask(Long idSalon, Long idTask, Task taskBody) {
        if(!existById(idSalon)){
            throw new IllegalArgumentException("Not salon found by id: "+idSalon);
        }
        Salon salon = this.getSalon(idSalon);
        if(!taskService.existById(idTask)){
            throw new IllegalArgumentException("Not task found by id: "+idTask);
        }
        Task task = taskService.getTask(idTask);
        if (task.getSalon().getId().equals(idSalon)){
            if(task.isDone()){
                throw new IllegalArgumentException("Task already done : "+idTask);
            }
            else {
                task.setDescription(taskBody.getDescription());
                taskService.update(idTask, task);
            }

        }
        return salon;
    }

    @Override
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
                taskService.update(idTask, task);
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
    public Salon deleteTask(Long idSalon, Long idTask) {
        if(!existById(idSalon)){
            throw new IllegalArgumentException("Not salon found by id: "+idSalon);
        }
        Salon salon = this.getSalon(idSalon);
        if(!taskService.existById(idTask)){
            throw new IllegalArgumentException("Not task found by id: "+idTask);
        }
        Task task = taskService.getTask(idTask);
        if (task.getSalon().getId().equals(idSalon)){
            taskService.delete(idTask);
            salonRepo.save(salon);
        }
        return salon;
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

    public List<Task> getTasksBySalon(Long idSalon) {
        if (!existById(idSalon)){
            throw new IllegalArgumentException("Not salon found by id: "+idSalon);
        }
            Salon salon = this.getSalon(idSalon);
            return taskService.getTasksBySalon(salon);
    }
}
