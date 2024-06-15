package com.example.chattingroom2.service;

import com.example.chattingroom2.entity.Member;
import com.example.chattingroom2.oauth.Oauth2UserInfoFactory;
import com.example.chattingroom2.oauth.model.Oauth2UserInfo;
import com.example.chattingroom2.oauth.model.UserPrincipal;
import com.example.chattingroom2.repository.MemberRepository;
import com.example.chattingroom2.type.Provider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomOauth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        return saveOrRetrieve(userRequest);
    }

    private OAuth2User saveOrRetrieve(OAuth2UserRequest userRequest) {
        DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);
        Provider provider = Provider.valueOf(userRequest.getClientRegistration().getRegistrationId().toUpperCase());
        Oauth2UserInfo oAuth2UserInfo = Oauth2UserInfoFactory.getOAuth2UserInfo(provider, oAuth2User.getAttributes());
        Optional<Member> optionalMember = memberRepository.findByEmail(oAuth2UserInfo.getEmail());

        Member member = optionalMember.orElseGet(() ->
                memberRepository.save(Member.builder()
                                .socialId(oAuth2UserInfo.getSocialId())
                                .name(oAuth2UserInfo.getName())
                                .email(oAuth2UserInfo.getEmail())
                                .provider(provider)
                                .build()));

        return memberToOAuth2User(member);
    }

    private OAuth2User memberToOAuth2User(Member member) {
        return UserPrincipal.builder().id(member.getId())
                .socialId(member.getSocialId())
                .name(member.getName())
                .email(member.getEmail())
                .provider(member.getProvider())
                .build();
    }
}
