package fr.univlorrainem1archi.friendsfiestas_v1.member.repository;

import fr.univlorrainem1archi.friendsfiestas_v1.member.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepo extends JpaRepository<Member,Long> {
    List<Member> findMembersBySalon(Long salonId);
}
