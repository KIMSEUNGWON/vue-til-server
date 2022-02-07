package hello.vuetilserver.controller;

import hello.vuetilserver.api.Result;
import hello.vuetilserver.controller.exception.FriendsDuplicatedException;
import hello.vuetilserver.domain.Member;
import hello.vuetilserver.domain.dto.FriendsAddDto;
import hello.vuetilserver.domain.dto.FriendsDto;
import hello.vuetilserver.service.FriendsService;
import hello.vuetilserver.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/friends")
@Slf4j
public class FriendsController {

    private final MemberService memberService;
    private final FriendsService friendsService;

    @GetMapping("/")
    public Result friendsList(@AuthenticationPrincipal Member member) {
        log.info("member = " + member.getUsername());

        List<FriendsDto> allFriendsList = friendsService.getAllFriendsList(member);
        return new Result<>(allFriendsList);
    }

    @PostMapping("/add")
    public FriendsDto friendsAdd(@RequestBody FriendsAddDto friendsAddDto, @AuthenticationPrincipal Member member) {
        log.info("friendsDto = " + friendsAddDto.getUsername());

        Member friend = memberService.findMember(friendsAddDto.getUsername());
        FriendsDto result = friendsService.addFriend(friend.getId(), member);
        return result;
    }

    @GetMapping("/{friendsUsername}")
    public FriendsDto friendsFind(@PathVariable String friendsUsername, @AuthenticationPrincipal Member member) {
        log.info("friendsUsername = " + friendsUsername);

        FriendsDto friendsDto = friendsService.getOneFriends(friendsUsername, member);
        return friendsDto;
    }

    @DeleteMapping("/{friendsUsername}")
    public String friendsDelete(@PathVariable String friendsUsername, @AuthenticationPrincipal Member member) {
        log.info("friendsUsername = " + friendsUsername);

        String result = friendsService.deleteOneFriends(friendsUsername, member);

        return result;
    }
}
