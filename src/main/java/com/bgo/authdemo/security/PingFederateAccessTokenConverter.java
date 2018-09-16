/*
 * Copyright 2012-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.bgo.authdemo.security;

import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SigningKeyResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.util.JsonParser;
import org.springframework.security.oauth2.common.util.JsonParserFactory;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.jwk.JwkException;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Converter to validate the access token from PingFederate using a non-standard key retrieval endpoint
 */
@Profile("ping")
@Component
@Slf4j
public class PingFederateAccessTokenConverter extends JwtAccessTokenConverter {

    private final JsonParser jsonParser = JsonParserFactory.create();

    private final SigningKeyResolver resolver;

    public PingFederateAccessTokenConverter(SigningKeyResolver resolver) {
        this.resolver = resolver;
    }

    @Override
    public Map<String, Object> decode(String token) {
        Jwt jwt = Jwts.parser().setSigningKeyResolver(resolver).parse(token);
        org.springframework.security.jwt.Jwt jwt2 = JwtHelper.decode(token);
        Map<String, Object> claims = this.jsonParser.parseMap(jwt2.getClaims());
        if (claims.containsKey(EXP) && claims.get(EXP) instanceof Integer) {
            Integer expiryInt = (Integer) claims.get(EXP);
            claims.put(EXP, new Long(expiryInt));
        }
        this.getJwtClaimsSetVerifier().verify(claims);
        return claims;
    }

    @Override
    public String encode(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        throw new JwkException("JWT signing (JWS) is not supported.");
    }

}