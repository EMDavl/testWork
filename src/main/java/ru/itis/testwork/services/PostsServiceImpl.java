package ru.itis.testwork.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.itis.testwork.dto.PostCreation;
import ru.itis.testwork.dto.PostDto;
import ru.itis.testwork.dto.PostEditDto;
import ru.itis.testwork.dto.PostPage;
import ru.itis.testwork.exceptions.IllegalPageRequestException;
import ru.itis.testwork.exceptions.PostValidationException;
import ru.itis.testwork.models.Post;
import ru.itis.testwork.repositories.PostRepository;
import ru.itis.testwork.utils.PostUtil;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PostsServiceImpl implements PostsService {

    private final PostRepository postRepository;

    @Value("${pagination.page_size}")
    private Integer pageSize;

    @Override
    public PostPage getPage(int page) {
        checkIsPageNonNegative(page);

        Pageable pageReq = PageRequest.of(page, pageSize);
        Page<Post> posts = postRepository.findAll(pageReq);

        checkIsValidPage(page, posts.getTotalPages());

        return PostPage.from(posts);
    }

    @Override
    public PostDto createPost(PostCreation toBeCreated) {
        checkIsNotEmpty(toBeCreated.getText(), toBeCreated.getTitle());

        Post post = Post.builder().text(toBeCreated.getText())
                .lastChanges(LocalDateTime.now())
                .title(toBeCreated.getTitle()).build();

        return PostDto.from(postRepository.save(post));
    }

    @Override
    public PostDto editPost(PostEditDto toBeEdited) {
        checkIsNotEmpty(toBeEdited.getText(), toBeEdited.getTitle());
        Post post = getPost(toBeEdited.getId());

        post.setText(toBeEdited.getText());
        post.setTitle(toBeEdited.getTitle());
        post.setLastChanges(LocalDateTime.now());

        return PostDto.from(postRepository.save(post));
    }

    @Override
    public PostDto deletePost(long id) {

        Post toBeDeleted = getPost(id);
        postRepository.delete(toBeDeleted);

        return PostDto.from(toBeDeleted);
    }

    private void checkIsPageNonNegative(int page) {
        if (page < 0)
            throw new IllegalPageRequestException("Page number must be non-negative");
    }

    private void checkIsValidPage(int page, int totalPages) {
        if (page > totalPages)
            throw new IllegalPageRequestException("Page number must be less than total pages");
    }

    private void checkIsNotEmpty(String text, String title) {
        if(PostUtil.isEmpty(text) || PostUtil.isEmpty(title))
            throw new PostValidationException("Fields must not be empty");
    }


    private Post getPost(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Wrong id"));
    }
}
