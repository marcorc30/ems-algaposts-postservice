package com.algaworks.algaposts.postservice.infrastructure.rabbitmq;

import com.algaworks.algaposts.postservice.api.model.PostOutput;
import com.algaworks.algaposts.postservice.api.service.PostService;
import com.algaworks.algaposts.postservice.domain.model.Post;
import com.algaworks.algaposts.postservice.domain.model.PostId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import static com.algaworks.algaposts.postservice.infrastructure.rabbitmq.RabbitMQConfig.QUEUE_PROCESSING;

@Component
@RequiredArgsConstructor
@Slf4j
public class RabbitMQListener {

    private final PostService postService;

    @RabbitListener(queues = QUEUE_PROCESSING)
    public void handle(@Payload PostOutput postOutput){

      log.info("Quantidade de palavras {} ", postOutput.getWordCount());
      log.info("Valor Estimado {} ", postOutput.getCalculatedValue());


        Post post = Post.builder()
                .id(new PostId(postOutput.getId()))
                .author(postOutput.getAuthor())
                .body(postOutput.getBody())
                .title(postOutput.getTitle())
                .calculatedValue(postOutput.getCalculatedValue())
                .wordCount(postOutput.getWordCount())
                .build();


      postService.gravar(post);

    }


}
