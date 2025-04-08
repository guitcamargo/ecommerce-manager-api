package br.com.guilherme.ecommerce_manager_api.service;

import br.com.guilherme.ecommerce_manager_api.BaseTest;
import br.com.guilherme.ecommerce_manager_api.application.auth.AuthServiceImpl;
import br.com.guilherme.ecommerce_manager_api.config.security.JwtService;
import br.com.guilherme.ecommerce_manager_api.domain.entity.UsuarioEntity;
import br.com.guilherme.ecommerce_manager_api.dto.auth.AuthRequestDTO;
import br.com.guilherme.ecommerce_manager_api.dto.auth.AuthResponseDTO;
import br.com.guilherme.ecommerce_manager_api.factory.UsuarioFactory;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AuthServiceImplTest extends BaseTest {

    @InjectMocks
    private AuthServiceImpl service;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtService jwtService;

    @Test
    void shouldAuthenticateAndReturnToken() {
        AuthRequestDTO request = new AuthRequestDTO("login", "pass");
        var user = UsuarioFactory.buildUser();
        String expectedToken = "jwt.token";

        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null);

        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(jwtService.generateToken(user)).thenReturn(expectedToken);

        AuthResponseDTO response = service.auth(request);

        assertThat(response).isNotNull();
        assertThat(response.tipo()).isEqualTo("Bearer");
        assertThat(response.token()).isEqualTo(expectedToken);

        verify(authenticationManager).authenticate(any());
        verify(jwtService).generateToken(user);
    }

    @Test
    void shouldExtractLoggedUserFromSecurityContext() {
        UsuarioEntity user = UsuarioFactory.buildUser();
        Authentication auth = new UsernamePasswordAuthenticationToken(user, null);
        SecurityContextHolder.getContext().setAuthentication(auth);

        UsuarioEntity result = service.extractUserLogged();

        assertThat(result).isEqualTo(user);
    }

    @Test
    void shouldThrowExceptionWhenAuthenticationFails() {
        AuthRequestDTO request = new AuthRequestDTO("invalid", "wrongpass");

        when(authenticationManager.authenticate(any()))
                .thenThrow(new RuntimeException("Authentication failed"));

        var exception = assertThrows(
                RuntimeException.class,
                () -> service.auth(request)
        );

        assertThat(exception).isInstanceOf(RuntimeException.class);
        assertThat(exception.getMessage()).isEqualTo("Authentication failed");

        verify(authenticationManager).authenticate(any());
        verifyNoInteractions(jwtService);
    }
}