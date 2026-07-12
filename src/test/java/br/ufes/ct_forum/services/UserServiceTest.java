package br.ufes.ct_forum.services;

import br.ufes.ct_forum.dtos.CreateUserDto;
import br.ufes.ct_forum.exceptions.EmailAlreadyExists;
import br.ufes.ct_forum.exceptions.PasswordsDoNotMatch;
import br.ufes.ct_forum.models.User;
import br.ufes.ct_forum.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private Argon2PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private CreateUserDto validDto;

    @BeforeEach
    void setUp() {
        validDto = new CreateUserDto("Paulo", "paulo@ufes.br", "senha123", "senha123");
    }

    @Test
    void save_Success_ShouldEncryptPasswordAndSaveUser() {
        Mockito.when(userRepository.findByEmail(validDto.email())).thenReturn(Optional.empty());
        Mockito.when(passwordEncoder.encode(validDto.password())).thenReturn("senhaCriptografada");

        userService.save(validDto);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        Mockito.verify(userRepository).save(userCaptor.capture());

        User savedUser = userCaptor.getValue();
        assertEquals("Paulo", savedUser.getName());
        assertEquals("paulo@ufes.br", savedUser.getEmail());
        assertEquals("senhaCriptografada", savedUser.getPasswordHash());
    }

    @Test
    void save_PasswordsDoNotMatch_ShouldThrowPasswordsDoNotMatchException() {
        CreateUserDto invalidDto = new CreateUserDto("Paulo", "paulo@ufes.br", "senha123", "outraSenha");

        assertThrows(PasswordsDoNotMatch.class, () -> userService.save(invalidDto));

        Mockito.verify(userRepository, Mockito.never()).save(any(User.class));
    }

    @Test
    void save_EmailAlreadyExists_ShouldThrowEmailAlreadyExistsException() {
        Mockito.when(userRepository.findByEmail(validDto.email())).thenReturn(Optional.of(new User()));

        assertThrows(EmailAlreadyExists.class, () -> userService.save(validDto));

        Mockito.verify(userRepository, Mockito.never()).save(any(User.class));
    }
}
