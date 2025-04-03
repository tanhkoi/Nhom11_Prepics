package com.example.prepics;


import com.example.prepics.entity.Collection;
import com.example.prepics.entity.User;
import com.example.prepics.models.ResponseProperties;
import com.example.prepics.repositories.UserRepository;
import com.example.prepics.services.api.UserApiService;
import com.example.prepics.services.entity.CollectionService;
import com.example.prepics.services.entity.serviceImpl.UserServiceImpl;
import com.sun.jdi.InternalException;
import org.modelmapper.ValidationException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class UserApiServiceTest {
    @InjectMocks
    UserApiService userApiService;
    @Mock
    UserRepository userRepository;
    @Mock
    Authentication authentication;
    @Mock
    UserServiceImpl userService;
    @Mock
    private User mockUser;
    @Mock
    private CollectionService collectionService;

    @BeforeEach
    void init(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registerUserSuccess(){
        when(authentication.getPrincipal()).thenReturn(mockUser);
        when(mockUser.getEmail()).thenReturn("test@example.com");
        when(userService.findByEmail(User.class, "test@example.com"))
                .thenReturn(Optional.of(mockUser));
        ResponseEntity<?> response = userApiService.loginUserWithGoogle(authentication);
        assertEquals(200, response.getStatusCodeValue());
        verify(userService, never()).create(any(User.class));
    }

    @Test
    void registerUserFail(){
        when(authentication.getPrincipal()).thenReturn(mockUser);
        when(userService.findByEmail(User.class, "test@example.com"))
                .thenReturn(Optional.empty());
        when(userService.create(mockUser)).thenReturn(Optional.empty());
        ResponseEntity<?> response = userApiService.registerUserWithEmailAndPasswork(authentication, "Test User");
        assertEquals(500, response.getStatusCodeValue());
    }

    @Test
    void testRegisterUser_Fail_CollectionCreation() throws ChangeSetPersister.NotFoundException {
        when(authentication.getPrincipal()).thenReturn(mockUser);
        when(userService.findByEmail(User.class, mockUser.getEmail())).thenReturn(Optional.empty());
        when(userService.create(mockUser)).thenReturn(Optional.of(mockUser));
        when(collectionService.create(any(Collection.class))).thenReturn(Optional.empty());

        ResponseEntity<?> response = userApiService.registerUserWithEmailAndPasswork(authentication, "Test User");

        assertEquals(500, response.getStatusCodeValue());
        assertTrue(response.getBody().toString().contains("Internal Server Error"));
    }


    //Loi chung
    @Test
    void testGenericError() {
        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenThrow(new RuntimeException("Unexpected error"));
        ResponseEntity<?> response = userApiService.registerUserWithEmailAndPasswork(authentication, "Test User");
        assertEquals(500, response.getStatusCodeValue());
        assertTrue(response.getBody().toString().contains("Internal Server Error"));
    }
}
