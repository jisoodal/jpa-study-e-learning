package jpabook.jpashop.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import jpabook.jpashop.entity.Member;
import org.springframework.stereotype.Repository;

@Repository
public class MemberRepository {

    /*
        spring-boot-starter-jpa 라이브러리를 추가해줬기 때문에
        @PersistenceContext가 있으면 Spring boot가 EntityManager를 주입해줌
    */
    @PersistenceContext
    private EntityManager em;

    /*
        커맨드 쿼리 분리 원칙.
        Member 엔티티를 그대로 반환하는 것은 어떤 사이드 이펙이 발생할지 모르는 커맨드성 방식
        따라서 id 정도만 리턴하여 필요하면 그때 조회하도록
     */
    public Long save(Member member) {
        em.persist(member);
        return member.getId();
    }

    public Member find(Long id) {
        return em.find(Member.class, id);
    }
}
