package com.example.chattingroom2.oauth.model.impl;

import com.example.chattingroom2.oauth.model.Oauth2UserInfo;

import java.util.Map;

public class GoogleOauthUserInfo extends Oauth2UserInfo {

    public GoogleOauthUserInfo(Map<String, Object> attributes) {super(attributes);}

    @Override
    public String getSocialId() {return (String) attributes.get("sub");}

    @Override
    public String getName() {return (String) attributes.get("name");}

    @Override
    public String getEmail() {return (String) attributes.get("email");}
}
