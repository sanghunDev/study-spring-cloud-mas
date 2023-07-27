package com.app.global.config.jwt.service;

import com.app.domain.user.constant.Role;
import com.app.global.config.jwt.constant.GrantType;
import com.app.global.config.jwt.constant.ToKenType;
import com.app.global.config.jwt.dto.JwtTokenDto;
import com.app.global.error.ErrorCode;
import com.app.global.error.exception.AuthenticationException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
public class TokenManager {
    private final String accessTokenExpirationTime;
    private final String refreshTokenExpirationTime;
    private final String tokenSecret;

    /**
     * JwtTokenDto 생성
     *
     * @param email
     * @param role
     * @return
     */
    public JwtTokenDto createJwtTokenDto(String email, Role role) {
        // 만료시간 생성
        Date accessTokenExpireTime = createAccessTokenExpireTime();
        Date refreshTokenExpireTime = createRefreshTokenExpireTime();

        // 토큰 생성
        String accessToken = createAccessToken(email, role, accessTokenExpireTime);
        String refreshToken = createRefreshToken(email, refreshTokenExpireTime);
        return JwtTokenDto.builder()
                .grantType(GrantType.BEARER.getType())
                .accessToken(accessToken)
                .accessTokenExpireTime(accessTokenExpireTime)
                .refreshToken(refreshToken)
                .refreshTokenExpireTime(refreshTokenExpireTime)
                .build();
    }

    /**
     * accessToken 만료시간 반환
     *
     * @return
     */
    public Date createAccessTokenExpireTime() {
        return new Date(System.currentTimeMillis() + Long.parseLong(accessTokenExpirationTime));
    }

    /**
     * refreshToken 만료시간 반환
     *
     * @return
     */
    public Date createRefreshTokenExpireTime() {
        return new Date(System.currentTimeMillis() + Long.parseLong(refreshTokenExpirationTime));
    }

    /**
     * accessToken 생성
     */
    public String createAccessToken(String email, Role role, Date expirationTime) {
        return Jwts.builder()
                .setSubject(ToKenType.ACCESS.name())    // 토큰 제목
                .setIssuedAt(new Date())                // 토큰 발급 시간
                .setExpiration(expirationTime)          // 토큰 만료 시간
                .claim("email", email)      // 회원 아이디
                .claim("role", role)               // 유저 role
                .signWith(SignatureAlgorithm.HS512, tokenSecret.getBytes(StandardCharsets.UTF_8))   // 토큰을 생성할때 사용할 알고리즘 지정 (HS512)
                .setHeaderParam("typ", "JWT")  // 토큰 타입 JWT
                .compact();
    }

    /**
     * refreshToken 생성
     */
    public String createRefreshToken(String email, Date expirationTime) {
        return Jwts.builder()
                .setSubject(ToKenType.REFRESH.name())    // 토큰 제목
                .setIssuedAt(new Date())                // 토큰 발급 시간
                .setExpiration(expirationTime)          // 토큰 만료 시간
                .claim("email", email)      // 회원 아이디
                .signWith(SignatureAlgorithm.HS512, tokenSecret.getBytes(StandardCharsets.UTF_8))   // 토큰을 생성할때 사용할 알고리즘 지정 (HS512)
                .setHeaderParam("typ", "JWT")  // 토큰 타입 JWT
                .compact();
    }

    /**
     * 토큰 유효성 체크
     *
     * @param token
     */
    public void validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(tokenSecret.getBytes(StandardCharsets.UTF_8))
                    .parseClaimsJws(token);
        } catch (ExpiredJwtException e) {
            log.error("token 만료 ", e);
            throw new AuthenticationException(ErrorCode.TOKEN_EXPIRED);
        } catch (Exception e) {
            log.error("유효하지 않은 토큰 ", e);
            throw new AuthenticationException(ErrorCode.NOT_VALID_TOKEN);
        }
    }

    /**
     * payload 에 있는 Claims 정보 가져오는 메서드
     *
     * @param token
     * @return
     */
    public Claims getTokenClaims(String token) {
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(tokenSecret.getBytes(StandardCharsets.UTF_8))
                    .parseClaimsJws(token).getBody();
        } catch (Exception e) {
            log.info("유효하지 않은 토큰 ", e);
            throw new AuthenticationException(ErrorCode.NOT_VALID_TOKEN);
        }

        return claims;
    }

    /**
     * 토큰 시큐리티 연동
     */
    /*public Authentication getAuthentication(final String token) {
        // 토큰 복호화
        Claims claims = getTokenClaims(token);
        log.info("token_claims : {}", claims.toString());

        if (claims.get("role") == null) {
            throw new BadCredentialsException("권한 정보가 없는 토큰입니다.");
        }

        // 클레임에서 권한 정보 가져오기
        final Collection<? extends GrantedAuthority> authorities = Stream.of(
                        claims.get("role").toString())
                .map(SimpleGrantedAuthority::new)
                .toList();

        final String userUuid = claims.get("userUuid").toString();

        //token 에 담긴 정보에 맵핑되는 User 정보 디비에서 조회
        final User user = userService.findByUserUuidForAuthToken(userUuid);

        //Authentication 객체 생성
        return new UsernamePasswordAuthenticationToken(user, userUuid, authorities);
    }*/
}
