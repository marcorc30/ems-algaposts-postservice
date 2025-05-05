package com.algaworks.algaposts.postservice.api.controller;

import com.algaworks.algaposts.postservice.api.Exception.ModelNaoEncontradoException;
import com.algaworks.algaposts.postservice.api.model.PostInput;
import com.algaworks.algaposts.postservice.api.model.PostOutput;
import com.algaworks.algaposts.postservice.api.service.PostService;
import com.algaworks.algaposts.postservice.common.IdGenerator;
import com.algaworks.algaposts.postservice.domain.model.Post;
import com.algaworks.algaposts.postservice.domain.model.PostId;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.info.SslInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.algaworks.algaposts.postservice.infrastructure.rabbitmq.RabbitMQConfig.EXCHANGE_POST;

@RestController
@RequestMapping("/api/posts")
@Slf4j
@RequiredArgsConstructor
public class PostController {

    private final RabbitTemplate rabbitTemplate;
    private final PostService postService;

    @PostMapping
    @SneakyThrows
    public void criar(@RequestBody PostInput postInput){

        Post post = Post.builder()
                .id(new PostId(IdGenerator.generate()))
                .author(postInput.getAuthor())
                .title(postInput.getTitle())
                .body(postInput.getBody())
                .wordCount(665)
                .calculatedValue(66.5)
                .build();


        String exchange = EXCHANGE_POST;
        String routenKey = "";
        PostOutput postOutput = toDto(post);

//        ObjectMapper mapper = new ObjectMapper();
//        String json = mapper.writeValueAsString(postOutput);
//        rabbitTemplate.convertAndSend(exchange, routenKey, json);

        rabbitTemplate.convertAndSend(exchange, routenKey, postOutput, message -> {
            message.getMessageProperties().setHeader("postId", postOutput.getId());
            return message;
        });



//        postService.gravar(post);


    }

    @GetMapping("{id}")
    public PostOutput getPost(@PathVariable UUID id){

        Post post = postService
                .getPost(new PostId(id))
                .orElseThrow(() -> new ModelNaoEncontradoException("Post não encontrado"));

        return toDto(post);

    }

    @GetMapping
    public Page<PostOutput> getAll(@PageableDefault Pageable page){

        Page<Post> posts = postService.getAllPosts(page);

        Page<PostOutput> postOutputs = posts.map(this::toDto);

        return postOutputs;
    }


    private PostOutput toDto(Post post) {
        return PostOutput.builder()
                .id(post.getId().getValue())
                .author(post.getAuthor())
                .body(post.getBody())
                .title(post.getTitle())
                .calculatedValue(post.getCalculatedValue())
                .wordCount(post.getWordCount())
                .build();
    }


}
