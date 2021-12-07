package fr.univlorrainem1archi.friendsfiestas_v1.member.services;

import fr.univlorrainem1archi.friendsfiestas_v1.member.model.Member;
import fr.univlorrainem1archi.friendsfiestas_v1.member.repository.MemberRepo;
import fr.univlorrainem1archi.friendsfiestas_v1.salon.models.Salon;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class MemberService {
    private final MemberRepo memberRepo;

    public MemberService(MemberRepo memberRepo) {
        this.memberRepo = memberRepo;
    }

    public Member create(Member member){
        return memberRepo.save(member);
    }

    public boolean existById(Long idMember) {
        return memberRepo.existsById(idMember);
    }

    public Member getMember(Long idMember) {
        return memberRepo.findById(idMember).orElseThrow();
    }

    public List<Member> getMembersBySalonId(Salon salon){
        return memberRepo.findMembersBySalon(salon);
    }

    public List<Salon> getSalonsByMembers(Long userId){
        return memberRepo.findMembersByUserId(userId).
                stream().map(Member::getSalon)
                .collect(Collectors.toList());
    }

    public Member update(Long idMember, Member member) {
        if (!existById(idMember)){
            throw new IllegalArgumentException("Not member found by id: "+idMember);
        }
        log.info("Update the member id: {}", member.getId());
        member.setId(idMember);
        return memberRepo.save(member);
    }

    public boolean delete(Long id) {
        log.info("Deleting member by id {}:", id);
        if (!existById(id)) {
            throw new IllegalArgumentException("No member found by id:" + id);
        }
        memberRepo.deleteById(id);
        return true;
    }
}
