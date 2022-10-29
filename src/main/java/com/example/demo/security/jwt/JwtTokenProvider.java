package com.example.demo.security.jwt;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    private String secretKey = "prod";

    //토큰 유효시간 3시간
    private long tokenValidTime =  12 * 60 * 60 * 1000L;

    private final UserDetailsService userDetailsService;

    //객체 초기화, secretKey를 Base64로 인코딩한다.
    @PostConstruct
    protected  void init(){
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    // JWT 토큰 생성
    public String createToken(String userPk, List<String> roles) {
        Claims claims = Jwts.claims(); //claim 객체 생성, claim: JWT payload 에 저장되는 정보 단위
        claims.put("roles", roles); //사용자 권한 값 넣기
        claims.put("userid", userPk);
        Date now = new Date();
        return Jwts.builder()
                // header
                .setHeaderParam("typ", "JWT")// token 타입 (Header)
                .setSubject(userPk)
                // payload -> payload의 정보는 key / value 쌍으로 저장된다.
                .setClaims(claims) // 정보 저장
                .setIssuedAt(now) // 토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + tokenValidTime)) // set Expire Time
                // signature
                .signWith(SignatureAlgorithm.HS256, secretKey)  // 사용할 암호화 알고리즘과
                // signature 에 들어갈 secret값 세팅
                .compact();
    }

    // JWT 토큰에서 인증 정보 조회
    public Authentication getAuthentication(String token){
        // email을 가져와 해당 email에 대한 useDetails 객체를 받아온다.
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserPk(token));
        // 인증용 객체 생성시 email에 해당하는 user 객체를 넣어줌으로써 해당 유저의 권한 부여
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }


    // 토큰에서 회원 정보 추출
    public String getUserPk(String token){
        // 토큰값을 디코드 해서 데이터를 얻기
        // return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
        // 토큰 값에서 email 읽기
        Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
        return (String) claims.getBody().get("userid");
    }

    //Request 의 Header 에서 token 값을 가져옵니다. "X-AUTH-TOKEN" : "TOKEN값"
    public String resolveToken(HttpServletRequest request){
        return request.getHeader("X-AUTH-TOKEN");
    }

    //토큰의 유효성 + 만료일자 확인
    public boolean validateToken(String jwtToken) throws Exception {
        try{
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        }catch (ExpiredJwtException e){
            System.out.println("JWT의 유효시간 초과했습니다.");
            e.printStackTrace();
            throw new Exception("로그인 시간이 만료되었습니다.", e);
        }catch (UnsupportedJwtException e){
            System.out.println("JWT의 형식이 일치하지 않습니다.");
            e.printStackTrace();
            return false;
        }catch (MalformedJwtException e){
            System.out.println("JWT가 올바르게 구성되지 않았습니다.");
            e.printStackTrace();
            return false;
        }catch (SignatureException e){
            System.out.println("JWT의 기존 signature 검증이 실패했습니다.");
            e.printStackTrace();
            return false;
        }catch(PrematureJwtException e){
            System.out.println("nbf를 선언했을 경우 토큰 유효 시간전에 사용했습니다.");
            e.printStackTrace();
            return false;
        }catch(ClaimJwtException  e){
            System.out.println("JWT에서 권한 Claim 검사를 실패했습니다.");
            e.printStackTrace();
            return false;
        }

    }
}
