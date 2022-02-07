package hello.vuetilserver;

import hello.vuetilserver.domain.Friends;
import hello.vuetilserver.domain.Member;
import hello.vuetilserver.domain.Posts;
import hello.vuetilserver.domain.dto.FriendsDto;
import hello.vuetilserver.domain.dto.MemberDto;
import hello.vuetilserver.domain.dto.PostsSaveDto;
import hello.vuetilserver.repository.MemberRepository;
import hello.vuetilserver.repository.PostsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

@RequiredArgsConstructor
@Transactional
@Configuration
public class InitDB {

    private final MemberRepository memberRepository;
    private final PostsRepository postsRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        MemberDto memberDto = new MemberDto("a@a.com", passwordEncoder.encode(("123")), "aaa");
        Member member = new Member(memberDto);
        memberRepository.save(member);

        MemberDto memberDto2 = new MemberDto("b@b.com", passwordEncoder.encode(("123")), "bbb");
        Member member2 = new Member(memberDto2);
        memberRepository.save(member2);

        PostsSaveDto postsSaveDto = new PostsSaveDto("title1", "contents1");
        Posts posts = new Posts(postsSaveDto, member);
        postsRepository.save(posts);

        PostsSaveDto postsSaveDto2 = new PostsSaveDto("title2", "contents2");
        Posts posts2 = new Posts(postsSaveDto2, member2);
        postsRepository.save(posts2);

        //friend
        MemberDto memberDto3 = new MemberDto("c@c.com", passwordEncoder.encode(("123")), "ccc");
        Member member3 = new Member(memberDto3);

        MemberDto memberDto4 = new MemberDto("d@d.com", passwordEncoder.encode(("123")), "ddd");
        Member member4 = new Member(memberDto4);

        member3.addFriend(new Friends(member3, member.getId()));
        member4.addFriend(new Friends(member4, member2.getId()));

        memberRepository.save(member3);
        memberRepository.save(member4);
    }
}
