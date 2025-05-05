package com.algaworks.algaposts.postservice.api.service;

import com.algaworks.algaposts.postservice.api.model.PostInput;
import com.algaworks.algaposts.postservice.api.model.PostOutput;
import com.algaworks.algaposts.postservice.domain.model.Post;
import com.algaworks.algaposts.postservice.domain.model.PostId;
import com.algaworks.algaposts.postservice.domain.repository.PostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepostory;

    @Transactional
    public void gravar(Post post){
        postRepostory.save(post);
    }

    public Optional<Post> getPost(PostId id){
        return postRepostory.findById(id);
    }

    public Page<Post> getAllPosts(Pageable page){

        return postRepostory.findAll(page);
    }
}
