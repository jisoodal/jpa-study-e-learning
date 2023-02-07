package jpabook.jpashop.repository;

import static org.junit.Assert.*;

import jpabook.jpashop.entity.Member;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

/*
    SpringBootTest만 사용하면 모든 application context를 다 로드하여 프로젝트가 무거워짐
    따라서 Junit4에서 지원하는 RunWith를 사용하여
    @Autowired, @MockBean에 해당되는 application context만 로드하게끔 설정
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    @Transactional
    @Rollback(value = false)
    public void testMember() throws Exception {
        //given
        Member member = new Member();
        member.setUsername("memberA");

        //when
        Long savedId = memberRepository.save(member);
        Member findMember = memberRepository.find(savedId);

        //then
        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
        Assertions.assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
        /*
            둘 다 한 트랜젝션에 묶여있음.
            따라서 같은 영속성 컨텍스트 안에 있다.
            식별자가 같으면 같은 entity라고 본다.
         */
        Assertions.assertThat(findMember).isEqualTo(member);
    }
}