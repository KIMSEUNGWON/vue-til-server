package hello.vuetilserver.service;

import hello.vuetilserver.controller.exception.FriendsDuplicatedException;
import hello.vuetilserver.controller.exception.FriendsNotExistException;
import hello.vuetilserver.controller.exception.MemberNotExistException;
import hello.vuetilserver.domain.Member;
import hello.vuetilserver.domain.dto.FriendsDto;
import hello.vuetilserver.domain.dto.MemberLoginDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
class FriendsServiceTest {

    @Autowired
    FriendsService friendsService;
    @Autowired
    MemberService memberService;

    @Test
    void getAllFriendsListTest() {
        //given
        Member member = memberService.findMember(new MemberLoginDto("c@c.com", "123"));
        Member friend = memberService.findMember(new MemberLoginDto("a@a.com", "123"));

        //when
        List<FriendsDto> allFriendsList = friendsService.getAllFriendsList(member);

        //then
        assertThat(allFriendsList.get(0).getUsername()).isEqualTo(friend.getUsername());
        assertThat(allFriendsList.size()).isEqualTo(1);
    }

    @Test
    void getAllFriendsListEmptyTest() {
        //given
        Member member = memberService.findMember(new MemberLoginDto("a@a.com", "123"));

        //when
        List<FriendsDto> allFriendsList = friendsService.getAllFriendsList(member);

        //then
        assertThat(allFriendsList.size()).isEqualTo(0);
        assertThat(allFriendsList).isEmpty();
    }

    @Test
    void addFriendTest() {
        //given
        Member member = memberService.findMember(new MemberLoginDto("a@a.com", "123"));
        Member friend = memberService.findMember(new MemberLoginDto("c@c.com", "123"));

        //when
        FriendsDto friendsDto = friendsService.addFriend(friend.getId(), member);

        //then
        assertThat(friendsDto.getUsername()).isEqualTo(friend.getUsername());
    }

    @Test
    void addDuplicatedFriendTest() {
        //given
        Member member = memberService.findMember(new MemberLoginDto("a@a.com", "123"));
        Member friend = memberService.findMember(new MemberLoginDto("c@c.com", "123"));

        //when
        FriendsDto friendsDto = friendsService.addFriend(friend.getId(), member);

        //then
        assertThrows(FriendsDuplicatedException.class, () -> {
            friendsService.addFriend(friend.getId(), member);
        });
    }

    @Test
    void getOneFriendsTest() {
        //given
        Member member = memberService.findMember(new MemberLoginDto("c@c.com", "123"));
        String friendsUsername = "a@a.com";

        //when
        FriendsDto friendsDto = friendsService.getOneFriends(friendsUsername, member);

        //then
        assertThat(friendsDto.getUsername()).isEqualTo(friendsUsername);
        assertThat(friendsDto.getNickname()).isEqualTo("aaa");
    }

    @Test
    void getOneFriendsFailTest() {
        //given
        Member member = memberService.findMember(new MemberLoginDto("c@c.com", "123"));
        String friendsUsername = "b@b.com";

        //when, then
        //https://covenant.tistory.com/256
        assertThrows(FriendsNotExistException.class, () -> {
            friendsService.getOneFriends(friendsUsername, member);
        });
    }

    @Test
    void deleteOneFriendsTest() {
        //given
        Member member = memberService.findMember(new MemberLoginDto("c@c.com", "123"));
        String friendsUsername = "a@a.com";

        //when
        friendsService.deleteOneFriends(friendsUsername, member);

        //then
        assertThrows(FriendsNotExistException.class, () -> {
            friendsService.getOneFriends(friendsUsername, member);
        });
    }
}