package ru.itis.testwork.controllers;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itis.testwork.dto.PostCreation;
import ru.itis.testwork.dto.PostDto;
import ru.itis.testwork.dto.PostEditDto;
import ru.itis.testwork.dto.PostPage;
import ru.itis.testwork.services.PostsService;

@RestController
@RequestMapping("/posts")
@Schema(description = "Взаимодействие с постами")
@RequiredArgsConstructor
public class PostsController {

    private final PostsService postsService;

    @GetMapping
    @Schema(name = "Получение постов", description = "Получение страницы с постами с пагинацией")
    public PostPage getPostsPage(
            @Parameter(name = "Номер страницы") @RequestParam(value = "page", defaultValue = "0", required = false) int page){
        return postsService.getPage(page);
    }

    @PostMapping
    public PostDto createPost(
            @Parameter(name = "Создание поста", description = "В теле text и title") @RequestBody PostCreation toBeCreated){
        return postsService.createPost(toBeCreated);
    }

    @PutMapping
    public PostDto editPost(
            @Parameter(name = "Изменение поста", description = "В теле id, text и title") @RequestBody PostEditDto toBeEdited){
        return postsService.editPost(toBeEdited);
    }

    @DeleteMapping
    public PostDto deletePost(
            @Parameter(name = "Удаление поста", description = "Требуется валидный Id поста") @RequestBody Long postId){
        return postsService.deletePost(postId);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleException(IllegalArgumentException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

}
