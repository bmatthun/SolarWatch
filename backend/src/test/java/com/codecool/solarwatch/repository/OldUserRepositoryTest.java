package com.codecool.solarwatch.repository;

import com.codecool.solarwatch.model.entity.Role;
import com.codecool.solarwatch.model.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Field;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OldUserRepositoryTest {


    @Mock
    private ConcurrentMap<String, UserEntity> mockUsers;

    private OldUserRepository oldUserRepository;

    @BeforeEach
    public void setup() throws Exception {
        MockitoAnnotations.openMocks(this);
        oldUserRepository = new OldUserRepository();

        Field usersField = OldUserRepository.class.getDeclaredField("users");
        usersField.setAccessible(true);
        usersField.set(oldUserRepository, mockUsers);
    }


    @Test
    void testFindUserByName_UserExists() {
        UserEntity mockUser = new UserEntity("testUser", "password123", Set.of(Role.ROLE_USER));
        when(mockUsers.get("testUser")).thenReturn(mockUser);

        Optional<UserEntity> result = oldUserRepository.findUserByName("testUser");

        assertTrue(result.isPresent());
        assertEquals("testUser", result.get().username());
    }

    @Test
    void testFindUserByName_UserDoesNotExist() {
        when(mockUsers.get("nonExistingUser")).thenReturn(null);

        Optional<UserEntity> result = oldUserRepository.findUserByName("nonExistingUser");

        assertFalse(result.isPresent());
    }

    @Test
    void testCreateUser_Success() {
        UserEntity newUser = new UserEntity("newUser", "password123", Set.of(Role.ROLE_USER));
        when(mockUsers.isEmpty()).thenReturn(false);
        when(mockUsers.containsKey("newUser")).thenReturn(false);

        oldUserRepository.createUser(newUser);

        verify(mockUsers).put("newUser", newUser);
    }

    @Test
    void testCreateUser_UserAlreadyExists() {
        UserEntity existingUser = new UserEntity("existingUser", "password123", Set.of(Role.ROLE_USER));

        when(mockUsers.containsKey("existingUser")).thenReturn(true);

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            oldUserRepository.createUser(existingUser);
        });

        assertEquals("user existingUser already exists", thrown.getMessage());

        verify(mockUsers, never()).put(anyString(), any(UserEntity.class));
    }


    @Test
    void testCreateUser_FirstUserIsAdmin() {
        UserEntity firstUser = new UserEntity("adminUser", "password123", Set.of(Role.ROLE_USER));
        when(mockUsers.isEmpty()).thenReturn(true);

        oldUserRepository.createUser(firstUser);

        verify(mockUsers).put(eq("adminUser"), argThat(user ->
                user.username().equals("adminUser") &&
                        user.password().equals("password123") &&
                        user.roles().contains(Role.ROLE_ADMIN) &&
                        user.roles().contains(Role.ROLE_USER)
        ));
    }

    @Test
    void testUpdateUser_Success() {
        UserEntity updatedUser = new UserEntity("existingUser", "newPassword123", Set.of(Role.ROLE_USER));

        when(mockUsers.containsKey("existingUser")).thenReturn(true);  // Mocking that the user exists

        oldUserRepository.updateUser(updatedUser);

        verify(mockUsers).put("existingUser", updatedUser);
    }



    @Test
    void testUpdateUser_UserDoesNotExist() {
        UserEntity nonExistingUser = new UserEntity("nonExistingUser", "password123", Set.of(Role.ROLE_USER));
        when(mockUsers.containsKey("nonExistingUser")).thenReturn(false);

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            oldUserRepository.updateUser(nonExistingUser);
        });

        assertEquals("user nonExistingUser does not exist", thrown.getMessage());
    }
}
