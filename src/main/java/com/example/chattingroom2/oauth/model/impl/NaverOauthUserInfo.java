package com.example.chattingroom2.oauth.model.impl;

import com.example.chattingroom2.oauth.model.Oauth2UserInfo;

import java.util.Map;

public class NaverOauthUserInfo extends Oauth2UserInfo {

    public NaverOauthUserInfo(Map<String, Object> attributes) {super(attributes);}

    @Override
    public String getSocialId() {
        return (String) ((Map) attributes.get("response")).get("id");
    }

    @Override
    public String getName() {
        return (String) ((Map) attributes.get("response")).get("name");
    }

    @Override
    public String getEmail() {
        return (String) ((Map) attributes.get("response")).get("email");
    }
}
