package com.algaworks.algaposts.postservice.domain.repository;

import com.algaworks.algaposts.postservice.domain.model.Post;
import com.algaworks.algaposts.postservice.domain.model.PostId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, PostId> {
}
