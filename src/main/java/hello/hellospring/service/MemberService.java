package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional; //optional은 null이 올 수 있는 값을 감싸는 클래스

//@Service
public class MemberService {

    private final MemberRepository memberRepository;

    //@Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    //회원가입
    public  Long join(Member member) {
        //같은 이름이 있는 중복 회원X
        //Optional<Member> result = memberRepository.findByName(member.getName());
        validateDuplicateMember(member);

        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m -> { //Optional 객체가 값을 가지고 있으면 실행, 값이 없으면 넘어감
            throw new IllegalStateException("이미 존재하는 회원입니다."); //예외 처리 코드
        });
    }
    //전체 회원 조회
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    public  Optional<Member> findOne(Long memberId){
        return memberRepository.findById(memberId);
    }

}
