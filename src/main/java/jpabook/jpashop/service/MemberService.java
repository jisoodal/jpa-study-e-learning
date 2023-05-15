package jpabook.jpashop.service;

import java.util.List;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    /*
        @Autowired
        생성자가 하나인 경우 Autowired 없어도 스프링이 알아서 injection 해줌
        public MemberService(MemberRepository memberRepository) {
            this.memberRepository = memberRepository;
        }
     */

    /**
     * 회원 가입
     * @param member
     * @return Long
     */
    @Transactional      // 이렇게 따로 설정한 Transaction이 우선권을 가진다.
    public Long join(Member member) {
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        /*
             Member 리스트를 반환받는게 아니라, count를 조회하면 더 최적화 되겠지만 예제 그대로 따라함!
             +) 이런식으로 유효성 검사를 하면 멀티쓰레드 환경에서 문제가 됨. 동명이인이 동시에 가입하러 들어오면
             결국 둘 다 유효성 검사 통과하니까.
             => 따라서 DB에 유니크 조건까지 걸어줘야 함
         */
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    /**
     * 회원 전체 조회
     * @return List<Member>
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    /**
     * 회원 단 건 조회
     * @param memberId
     * @return Member
     */
    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }

    @Transactional
    public void update(Long id, String name) {
        Member member = memberRepository.findOne(id);
        member.setName(name);
    }
}
