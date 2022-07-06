package com.example.demo.security.service;

import com.example.demo.member.service.impl.MemberServiceImpl;
import com.example.demo.member.vo.ProfileVO;
import com.example.demo.security.mapper.UserMapper;
import com.example.demo.security.vo.OAuthAttributes;
import com.example.demo.security.vo.UserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

/**
 * Created by Hyunsik Lee on 2022-06-10. Blog : https://hs95blue.github.io/ Github :
 * https://github.com/hs95blue
 */

@RequiredArgsConstructor
@Service
public class CustomOAuth2MemberService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

  private final UserMapper userMapper;
  private final HttpSession httpSession;

  @Autowired
  MemberServiceImpl memberService;


  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
    System.out.println("CustomOAuth2MemberService 진입 :: ");
    OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
    OAuth2User oAuth2User = delegate.loadUser(userRequest);

    // 현재 로그인 진행 중인 서비스를 구분하는 코드
    String registrationId = userRequest
        .getClientRegistration()
        .getRegistrationId();

    // oauth2 로그인 진행 시 키가 되는 필드값
    String userNameAttributeName = userRequest.getClientRegistration()
        .getProviderDetails()
        .getUserInfoEndpoint()
        .getUserNameAttributeName();

    // OAuthAttributes: attribute를 담을 클래스 (개발자가 생성)
    OAuthAttributes attributes = OAuthAttributes
        .of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

    UserVO userVO = saveOrUpdate(attributes);

    return new DefaultOAuth2User(
        Collections.singleton(new SimpleGrantedAuthority(userVO.getRole())),
        attributes.getAttributes(),
        attributes.getNameAttributeKey()
    );
  }

  private UserVO saveOrUpdate(OAuthAttributes attributes) {
    UserVO userVO;

    if(userMapper.findByEmail(attributes.getEmail())!=null) {
      userVO = userMapper.findByEmail(attributes.getEmail());
    }
    else {
      userVO = attributes.toEntity();
      userMapper.save(userVO);
      ProfileVO vo = new ProfileVO();

      vo.setMemberId(userVO.getId());
      memberService.insertProfile(vo);
      userVO = userMapper.findByEmail(attributes.getEmail());
    }

    return userVO;
  }
}

