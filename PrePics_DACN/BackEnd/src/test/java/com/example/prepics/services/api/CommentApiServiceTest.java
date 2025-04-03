package com.example.prepics.services.api;

import com.example.prepics.entity.Comment;
import com.example.prepics.entity.User;
import com.example.prepics.services.entity.CommentService;
import com.example.prepics.services.entity.UserService;
import com.example.prepics.utils.ResponseBodyServer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CommentApiServiceTest {

    @Mock
    private User mockUser;

    @Mock
    private Comment mockComment;

    @Mock
    private CommentService commentService;

    @Mock
    private UserService userService;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private CommentApiService commentApiService;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @BeforeEach
    void setUp() {
        mockUser = new User();
        mockUser.setId("123");
        mockUser.setEmail("test@example.com");
        mockComment = new Comment();
        mockComment.setId(123L);
        mockComment.setUserId(mockUser.getId());
    }

    @Test
    void testDeleteComment_Success() throws ChangeSetPersister.NotFoundException {
        when(userService.findByEmail(User.class, mockUser.getEmail())).thenReturn(Optional.of(mockUser));
        when(authentication.getPrincipal()).thenReturn(mockUser);
        when(commentService.findById(Comment.class, mockComment.getId())).thenReturn(Optional.of(mockComment));
        when(commentService.delete(anyLong())).thenReturn(Optional.of(mockComment));

        ResponseEntity<?> response = commentApiService.deleteComment(authentication, mockComment.getId());

        assertEquals(200, response.getStatusCodeValue());

        ResponseBodyServer responseBody = (ResponseBodyServer) response.getBody();
        assertNotNull(responseBody);
        assertEquals("Success", responseBody.getMessage());
    }

    @Test
    void testDeleteComment_NotFound() throws ChangeSetPersister.NotFoundException {
        when(authentication.getPrincipal()).thenReturn(mockUser);
        when(commentService.findById(Comment.class, mockComment.getId())).thenReturn(Optional.empty());

        ResponseEntity<?> response = commentApiService.deleteComment(authentication, mockComment.getId());

        assertEquals(NOT_FOUND.value(), response.getStatusCodeValue());
    }

    @Test
    void testDeleteComment_Unauthorized() throws ChangeSetPersister.NotFoundException {
        User anotherUser = new User();
        anotherUser.setId("456");
        anotherUser.setEmail("anotherTest@example.com");
        mockComment.setUserId(anotherUser.getId());

        when(userService.findByEmail(User.class, mockUser.getEmail())).thenReturn(Optional.of(mockUser));
        when(authentication.getPrincipal()).thenReturn(mockUser);
        when(commentService.findById(Comment.class, mockComment.getId())).thenReturn(Optional.of(mockComment));

        ResponseEntity<?> response = commentApiService.deleteComment(authentication, mockComment.getId());

        assertEquals(UNAUTHORIZED.value(), response.getStatusCodeValue());
    }

    @Test
    void testDeleteComment_InvalidAuthentication() {

        when(authentication.getPrincipal()).thenThrow(new RuntimeException("Invalid Auth"));

        ResponseEntity<?> response = commentApiService.deleteComment(authentication, mockComment.getId());

        assertEquals(BAD_REQUEST.value(), response.getStatusCodeValue());
    }

    @Test
    void testDeleteComment_DatabaseError() throws ChangeSetPersister.NotFoundException {
        when(userService.findByEmail(User.class, mockUser.getEmail())).thenReturn(Optional.of(mockUser));
        when(authentication.getPrincipal()).thenReturn(mockUser);
        when(commentService.findById(Comment.class, mockComment.getId())).thenReturn(Optional.of(mockComment));
        doThrow(new RuntimeException("DB Error")).when(commentService).delete(mockComment.getId());

        ResponseEntity<?> response = commentApiService.deleteComment(authentication, mockComment.getId());

        assertEquals(BAD_REQUEST.value(), response.getStatusCodeValue());
    }
}