package br.com.guilherme.ecommerce_manager_api.service;

import br.com.guilherme.ecommerce_manager_api.BaseTest;
import br.com.guilherme.ecommerce_manager_api.application.usuario.UsuarioServiceImpl;
import br.com.guilherme.ecommerce_manager_api.domain.entity.UsuarioEntity;
import br.com.guilherme.ecommerce_manager_api.domain.exception.NotFoundException;
import br.com.guilherme.ecommerce_manager_api.factory.UsuarioFactory;
import br.com.guilherme.ecommerce_manager_api.infrasctruture.persistence.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UsuarioServiceImplTest extends BaseTest {

    @Mock
    private UsuarioRepository repository;

    @InjectMocks
    private UsuarioServiceImpl service;

    @Test
    void shouldFindUserById() {
        UsuarioEntity usuario = UsuarioFactory.buildUser();
        when(repository.findById(1L)).thenReturn(Optional.of(usuario));

        UsuarioEntity result = service.findById(1L);

        assertNotNull(result);
        assertEquals(usuario.getId(), result.getId());
        verify(repository).findById(1L);
    }

    @Test
    void shouldThrowWhenUserNotFoundById() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> service.findById(1L));
        verify(repository).findById(1L);
    }

    @Test
    void shouldLoadUserById() {
        UsuarioEntity usuario = UsuarioFactory.buildUser();
        when(repository.findById(1L)).thenReturn(Optional.of(usuario));

        Optional<UserDetails> result = service.loadUserById(1L);

        assertTrue(result.isPresent());
        assertEquals(usuario.getUsername(), result.get().getUsername());
    }

    @Test
    void shouldLoadUserByUsername() {
        UsuarioEntity usuario = UsuarioFactory.buildUser();
        when(repository.findByLogin("admin01")).thenReturn(Optional.of(usuario));

        UserDetails result = service.loadUserByUsername("admin01");

        assertNotNull(result);
        verify(repository).findByLogin("admin01");
    }

    @Test
    void shouldThrowWhenUserNotFoundByUsername() {
        when(repository.findByLogin("usuario")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> service.loadUserByUsername("usuario"));
        verify(repository).findByLogin("usuario");
    }
}
