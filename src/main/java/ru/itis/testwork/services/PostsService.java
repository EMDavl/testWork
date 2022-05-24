package ru.itis.testwork.services;

import ru.itis.testwork.dto.PostCreation;
import ru.itis.testwork.dto.PostDto;
import ru.itis.testwork.dto.PostEditDto;
import ru.itis.testwork.dto.PostPage;

public interface PostsService {

    PostPage getPage(int page);
    PostDto createPost(PostCreation toBeCreated);
    PostDto editPost(PostEditDto toBeEdited);
    PostDto deletePost(long id);

}
