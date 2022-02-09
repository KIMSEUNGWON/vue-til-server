package hello.vuetilserver.service;

import hello.vuetilserver.controller.exception.FriendsDuplicatedException;
import hello.vuetilserver.controller.exception.FriendsNotExistException;
import hello.vuetilserver.controller.exception.MemberNotExistException;
import hello.vuetilserver.domain.Friends;
import hello.vuetilserver.domain.Member;
import hello.vuetilserver.domain.dto.FriendsDto;
import hello.vuetilserver.repository.FriendsRepository;
import hello.vuetilserver.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class FriendsService {

    private final FriendsRepository friendsRepository;
    private final MemberRepository memberRepository;

    public List<FriendsDto> getAllFriendsList(Member member) {
        log.info("모든 친구 조회 메서드");
        log.info("member = " + member.getUsername());

        List<Friends> friendsList = friendsRepository.findAll(member).orElse(null);

        List<FriendsDto> result = friendsList.stream()
                .map(f -> {
                    Member friend = memberRepository.getById(f.getFriendedMemberId());
                    return new FriendsDto(friend.getUsername(), friend.getNickname(), member.getUsername());
                }).collect(Collectors.toList());
        return result;
    }

    @Transactional
    public FriendsDto addFriend(Long friendId, Member member) {
        log.info("친구 추가 메서드");

        //중복 추가인지 확인
        Optional<Friends> friendsByFriendedMemberIdAndFriendingMemberId = friendsRepository.findFriendsByFriendedMemberIdAndFriendingMemberId(friendId, member);
        if (friendsByFriendedMemberIdAndFriendingMemberId.isPresent()) {
            throw new FriendsDuplicatedException("중복된 친구 추가 입니다.");
        }

        Friends saveFriend = friendsRepository.save(new Friends(member, friendId));
        //JPA 영속성 컨텍스트가 존재하지 않아서 DB의 값을 변경하지 못하고 에러가 나서 영속성 컨텍스트를 새로 만듦
        Member member1 = memberRepository.findMemberByUsername(member.getUsername()).orElseThrow(() -> new MemberNotExistException("존재하지 않는 멤버입니다."));
        member1.addFriend(saveFriend);
        Member friend = memberRepository.getById(saveFriend.getFriendedMemberId());

        FriendsDto friendsDto = new FriendsDto(friend.getUsername(), friend.getNickname(), member.getUsername());
        return friendsDto;
    }

    public FriendsDto getOneFriends(String friendsUsername, Member member) {
        log.info("특정 친구 조회 메서드");

        Member friend = memberRepository.findMemberByUsername(friendsUsername).orElseThrow(() -> new MemberNotExistException("존재하지 않는 멤버입니다."));
        Friends friends = friendsRepository.findFriendsByFriendedMemberIdAndFriendingMemberId(friend.getId(), member).orElseThrow(() -> new FriendsNotExistException("존재하지 않는 친구입니다."));
        FriendsDto friendsDto = new FriendsDto(friend.getUsername(), friend.getNickname(), member.getUsername());

        return friendsDto;
    }

    public String deleteOneFriends(String friendsUsername, Member member) {
        log.info("특정 친구 삭제 메서드");

        Member friend = memberRepository.findMemberByUsername(friendsUsername).orElseThrow(() -> new MemberNotExistException("존재하지 않는 멤버입니다."));
        Friends friends = friendsRepository.findFriendsByFriendedMemberIdAndFriendingMemberId(friend.getId(), member).orElseThrow(() -> new FriendsNotExistException("존재하지 않는 친구입니다."));
        friendsRepository.delete(friends);

        return friendsUsername;
    }
}
