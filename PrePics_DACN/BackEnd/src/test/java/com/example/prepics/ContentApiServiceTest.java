package com.example.prepics;

import com.example.prepics.entity.Collection;
import com.example.prepics.entity.Content;
import com.example.prepics.entity.InCols;
import com.example.prepics.entity.User;
import com.example.prepics.services.api.ContentApiService;
import com.example.prepics.services.cloudinary.CloudinaryService;
import com.example.prepics.services.entity.CollectionService;
import com.example.prepics.services.entity.ContentService;
import com.example.prepics.services.entity.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.net.MalformedURLException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ContentApiServiceTest {
    @InjectMocks
    ContentApiService contentApiService;

    @Mock
    ContentService contentService;

    @Mock
    CloudinaryService cloudinaryService;

    @Mock
    Authentication authentication;

    @Mock
    UserService userService;

    @Mock
    CollectionService collectionService;

    @Mock
    User user;

    @BeforeEach
    void init(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getContentWithSizeUrlSuccess() throws ChangeSetPersister.NotFoundException, IOException {
        Content content = new Content();
        content.setId("123");
        String validUrl = "https://www.bing.com/images/search?q=%E1%BA%A3nh&FORM=IQFRBA&id=4DD5D71206BED980357DFAF9819E3C9028C680C1";
        when( contentService.findById(Content.class, content.getId())).thenReturn(Optional.of(content));
        when(cloudinaryService.generateTransformedUrl(content.getId(),1,1,"img")).thenReturn(validUrl);
        byte[] response=contentApiService.getContentWithSizeUrl(content.getId(),"1","1","img");
        assertNotNull(response);
    }

    @Test
    void getContentWithEmptyFile() throws ChangeSetPersister.NotFoundException, IOException {
        Content content = new Content();
        content.setId("123");
        byte[] emptyByteArray = new byte[0];
        String validUrl = "https://www.bing.com/images/search?q=%E1%BA%A3nh&FORM=IQFRBA&id=4DD5D71206BED980357DFAF9819E3C9028C680C1";
        when( contentService.findById(Content.class, content.getId())).thenReturn(Optional.of(content));
        when(cloudinaryService.generateTransformedUrl(content.getId(),1,1,"img")).thenReturn(validUrl);
        byte[] response = contentApiService.getContentWithSizeUrl(content.getId(),"1","1","img");
        assertNotEquals(response,emptyByteArray);
    }

    @Test
    void getContentWithInvalidUrl() throws ChangeSetPersister.NotFoundException, IOException {
        Content content = new Content();
        content.setId("123");
        String validUrl = "aaa";
        when( contentService.findById(Content.class, content.getId())).thenReturn(Optional.of(content));
        when(cloudinaryService.generateTransformedUrl(content.getId(),1,1,"img")).thenReturn(validUrl);

        assertThrows(MalformedURLException.class, () ->
                contentApiService.getContentWithSizeUrl(content.getId(),"1","1","img"));
    }

    @Test
    void populateLikedContentSuccess() throws ChangeSetPersister.NotFoundException {
        Collection collection = new Collection();
        Set<InCols> inColsSet = new HashSet<>();
        inColsSet.add(new InCols()); // Add a contentId
        collection.setInCols(inColsSet);
        user.setId("123");
        Content content = new Content();
        content.setId("123");
        Content content2 = new Content();
        content.setId("1234");
        List<Content> contents = new ArrayList<>();
        contents.add(content);
        contents.add(content2);
        when(contentService.findAll(Content.class)).thenReturn(Optional.of(contents));
        when(authentication.getPrincipal()).thenReturn(user);
        when(user.getEmail()).thenReturn("test@example.com");
        when(userService.findByEmail(User.class, "test@example.com")).thenReturn(Optional.of(user));
        when(collectionService.getUserCollectionByName(user.getId(),"liked")).thenReturn(Optional.of(collection));
        ResponseEntity<?> response = contentApiService.findAllContent(authentication);
        assertNotNull(response);
    }

}
