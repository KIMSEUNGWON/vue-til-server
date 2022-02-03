package hello.vuetilserver.service;

import hello.vuetilserver.domain.Member;
import hello.vuetilserver.domain.dto.MemberDto;
import hello.vuetilserver.domain.dto.MemberLoginDto;
import hello.vuetilserver.domain.dto.MemberLoginSuccessDto;
import hello.vuetilserver.domain.dto.MemberUpdateDto;
import hello.vuetilserver.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    MemberService memberService;

    @Test
    void saveTest() {
        //given
        MemberDto memberDto = new MemberDto("c@c.com", "123", "ccc");

        //when
        memberService.save(memberDto);
        Member savedMember = memberRepository.findMemberByUsername("c@c.com").orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저 입니다."));

        //then
        assertThat(savedMember.getUsername()).isEqualTo(memberDto.getUsername());
        assertThat(savedMember.getNickname()).isEqualTo(memberDto.getNickname());
    }

    @Test
    void findMemberTest() {
        //given
        MemberLoginDto memberLoginDto = new MemberLoginDto("a@a.com", "123");

        //when
        Member foundedMember = memberService.findMember(memberLoginDto);
        Member member = memberRepository.findMemberByUsername(memberLoginDto.getUsername()).get();

        //then
        assertThat(foundedMember).isEqualTo(member);
    }

    @Test
    void findAllMembers() {
        //given
        List<MemberLoginSuccessDto> allMembers = memberService.findAllMembers();

        //when

        //then
        assertThat(allMembers.get(0).getUsername()).isEqualTo("a@a.com");
        assertThat(allMembers.size()).isEqualTo(2);
    }

    @Test
    void findMemberByIdTest() {
        //given
        Member member = memberRepository.findById(1L).get();
        String id = "1";

        //when
        MemberLoginSuccessDto memberById = memberService.findMemberById(id, member);

        //then
        assertThat(memberById.getUsername()).isEqualTo(member.getUsername());
        assertThat(memberById.getNickname()).isEqualTo(member.getNickname());
    }

    @Test
    void updateMemberTest() {
        //given
        Member member = memberRepository.findById(1L).get();
        MemberUpdateDto memberUpdateDto = new MemberUpdateDto("aa@a.com", "123", "1233", "1233", "aa");

        //when
        MemberLoginSuccessDto memberLoginSuccessDto = memberService.updateMember(member.getUsername(), memberUpdateDto, member);
        MemberLoginSuccessDto memberById = memberService.findMemberById(String.valueOf(member.getId()), member);

        //then
        assertThat(memberLoginSuccessDto.getUsername()).isEqualTo(memberById.getUsername());
        assertThat(memberLoginSuccessDto.getNickname()).isEqualTo(memberById.getNickname());
    }

    @Test
    void deleteMemberTest() {
        //given
        Member member = memberRepository.findById(1L).get();
        String username = member.getUsername();

        //when
        MemberLoginSuccessDto memberLoginSuccessDto = memberService.deleteMember(username, member);

        //then
        assertThat(memberLoginSuccessDto.getUsername()).isEqualTo(member.getUsername());
    }
}