package ca.com.idealimport.service.token.control;

import ca.com.idealimport.service.token.Token;
import ca.com.idealimport.service.token.TokenType;
import ca.com.idealimport.service.token.control.repository.TokenRepository;
import ca.com.idealimport.service.users.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class TokenControl {
    private final TokenRepository tokenRepository;
    public void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getUserId());
        if (validUserTokens.isEmpty())
            return;
        var tokens = validUserTokens.stream().map(token -> {
            token.setExpired(true);
            token.setRevoked(true);
            return token;
        }).toList();
        tokenRepository.saveAll(tokens);
    }

    @Transactional
    public void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }
}
