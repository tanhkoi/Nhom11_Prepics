package com.example.prepics.services.api;

import com.example.prepics.entity.*;
import com.example.prepics.services.entity.CollectionService;
import com.example.prepics.services.entity.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ContentApiServiceTest {
    @Mock
    private User mockUser;

    @Mock
    private InCols mockInCols;

    @Mock
    private Collection mockCollection;

    @Mock
    private Content mockContent;

    @Mock
    private Content mockContent2;

    @Mock
    private CollectionService collectionService;

    @Mock
    private UserService userService;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private ContentApiService contentApiService;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSuccess() throws ChangeSetPersister.NotFoundException {
        when(userService.findByEmail(User.class, mockUser.getEmail())).thenReturn(Optional.of(mockUser));
        when(authentication.getPrincipal()).thenReturn(mockUser);
        when(mockUser.getId()).thenReturn("123");
        when(userService.findByEmail(User.class, "test@example.com"))
                .thenReturn(Optional.of(mockUser));

        when(mockInCols.getContentId()).thenReturn("123");
        when(mockCollection.getInCols()).thenReturn(Set.of(mockInCols));
        when(collectionService.getUserCollectionByName("123", "liked"))
                .thenReturn(Optional.of(mockCollection));

        when(mockContent.getId()).thenReturn("123");
        when(mockContent2.getId()).thenReturn("456");

        List<Content> contents = List.of(mockContent, mockContent2);

        ResponseEntity<?> response = contentApiService.populateLikedContent(authentication, contents);

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testAuthNull() throws ChangeSetPersister.NotFoundException {
        List<Content> contents = List.of(mockContent, mockContent2);

        ResponseEntity<?> response = contentApiService.populateLikedContent(null, contents);

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testNoLikedCollection() throws ChangeSetPersister.NotFoundException {
        List<Content> contents = List.of(mockContent, mockContent2);

        when(userService.findByEmail(User.class, mockUser.getEmail())).thenReturn(Optional.of(mockUser));
        when(authentication.getPrincipal()).thenReturn(mockUser);
        when(mockUser.getId()).thenReturn("123");
        when(collectionService.getUserCollectionByName("123", "liked"))
                .thenReturn(Optional.empty());

        ResponseEntity<?> response = contentApiService.populateLikedContent(authentication, contents);

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testNoMatchingIds() throws ChangeSetPersister.NotFoundException {
        List<Content> contents = List.of(mockContent, mockContent2);

        when(userService.findByEmail(User.class, mockUser.getEmail())).thenReturn(Optional.of(mockUser));
        when(mockInCols.getContentId()).thenReturn("3");
        when(mockCollection.getInCols()).thenReturn(Set.of(mockInCols));

        when(authentication.getPrincipal()).thenReturn(mockUser);
        when(mockUser.getId()).thenReturn("123");
        when(collectionService.getUserCollectionByName("123", "liked"))
                .thenReturn(Optional.of(mockCollection));

        ResponseEntity<?> response = contentApiService.populateLikedContent(authentication, contents);

        assertEquals(200, response.getStatusCodeValue());
    }

}
