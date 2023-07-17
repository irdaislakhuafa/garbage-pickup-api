package com.irdaislakhuafa.garbagepickupapi.services.impl;

import com.irdaislakhuafa.garbagepickupapi.exceptions.custom.DataNotFound;
import com.irdaislakhuafa.garbagepickupapi.exceptions.custom.InvalidPassword;
import com.irdaislakhuafa.garbagepickupapi.exceptions.custom.UserNotAvailable;
import com.irdaislakhuafa.garbagepickupapi.models.entities.User;
import com.irdaislakhuafa.garbagepickupapi.models.gql.JwtTokenResponse;
import com.irdaislakhuafa.garbagepickupapi.models.gql.request.user.UserLoginRequest;
import com.irdaislakhuafa.garbagepickupapi.repository.UserRepository;
import com.irdaislakhuafa.garbagepickupapi.services.AuthService;
import com.irdaislakhuafa.garbagepickupapi.services.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Supplier;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService<User> jwtService;
    private final SimpleDateFormat dateFormatter;
    private final BCryptPasswordEncoder passwordEncoder;

    @Value(value = "${jwt.token.expired-in.minute}")
    private int tokenExpiredInMinute;

    @Override
    public JwtTokenResponse login(UserLoginRequest request) {
        log.info("trying to login");
        final var user = this.userRepository.findByEmail(request.getEmail());

        // if user doesn't exists
        if (!user.isPresent()) {
            throw new DataNotFound("user with this email doesn't exists, please register first");
        }

        // if user is disabled/deleted
        if (!user.get().isEnabled()) {
            throw new UserNotAvailable("the user currently not available or disabled, please contact admin");
        }

        // is password match?
        if (!passwordEncoder.matches(request.getPassword(), user.get().getPassword())) {
            throw new InvalidPassword("password is not match");
        }

        // TODO: use authentication manager, for now i can't do it in spring boot 3.1.x
        // final var authentication = new UsernamePasswordAuthenticationToken(
        // user.get().getEmail(),
        // user.get().getPassword(),
        // user.get().getAuthorities());

        /*
         * will throws org.springframework.security.core.AuthenticationException if
         * authentication is failed
         */
        // try {
        // this.authenticationManager.authenticate(authentication);
        // } catch (Exception e) {
        // log.info(authentication + "");
        // e.printStackTrace();
        // }

        // generate jwt token if all validation above is success
        final Supplier<String> generateTokenValidUntil = () -> {
            final var expiredAt = new Date(System.currentTimeMillis() + ((1000L * 60) * this.tokenExpiredInMinute));
            return dateFormatter.format(expiredAt);
        };

        final var tokenString = this.jwtService.generateTokenString(user.get());
        final var result = JwtTokenResponse.builder()
                .message("token is valid until " + generateTokenValidUntil.get())
                .token(tokenString)
                .build();

        return result;
    }
}