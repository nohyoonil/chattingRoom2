package com.example.chattingroom2.oauth;

import com.example.chattingroom2.oauth.model.Oauth2UserInfo;
import com.example.chattingroom2.oauth.model.impl.GoogleOauthUserInfo;
import com.example.chattingroom2.oauth.model.impl.NaverOauthUserInfo;
import com.example.chattingroom2.type.Provider;

import java.util.Map;

public class Oauth2UserInfoFactory {
    public static Oauth2UserInfo getOAuth2UserInfo(Provider provider, Map<String, Object> attributes) {
        return switch (provider) {
            case GOOGLE -> new GoogleOauthUserInfo(attributes);
            case NAVER -> new NaverOauthUserInfo(attributes);
        };
    }
}
