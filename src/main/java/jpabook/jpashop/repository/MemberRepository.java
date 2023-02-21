package jpabook.jpashop.repository;

import java.util.List;
import javax.persistence.EntityManager;
import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

/*
    @Repository를 붙이면
    컴포넌트 스캔하면서 자동으로 빈에 등록이 된다.
 */
@Repository
@RequiredArgsConstructor
public class MemberRepository {

    /*
        spring-boot-starter-jpa 라이브러리를 추가해줬기 때문에
        @PersistenceContext가 있으면 Spring boot가 EntityManager를 주입해줌

        (신규) spring boot jpa가 EntityManager에
        @PersistenceContext 대신 @Autowired를 사용할 수 있도록 지원해준다.
        따라서 서비스와 일관성 있게 @RequiredArgsConstructor 사용 가능!
        spring data jpa를 쓰는게 아니면 @PersistenceContext 써야한다.
    */
    // @PersistenceContext
    private final EntityManager em;

    /*
        커맨드 쿼리 분리 원칙.
        Member 엔티티를 그대로 반환하는 것은 어떤 사이드 이펙이 발생할지 모르는 커맨드성 방식
        따라서 id 정도만 리턴하여 필요하면 그때 조회하도록
     */
    public void save(Member member) {
        em.persist(member);
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    // 지금은 findAll 메서드가 있지만, 강의와 동일하게 작성
    public List<Member> findAll() {
        /*
            SQL과 기능적으로는 거의 같지만,
            SQL은 테이블을 대상으로 쿼리하는 반면 JPQL은 엔티티를 대상으로 쿼리한다.
         */
        return em.createQuery("select m from Member m", Member.class)
            .getResultList();
    }

    // findBy컬럼 기능도 현재는 있다.
    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
            .setParameter("name", name)
            .getResultList();
    }
}
