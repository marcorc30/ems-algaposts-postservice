package com.algaworks.algaposts.postservice.domain.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Embeddable
@NoArgsConstructor
@Builder
public class PostId {

    private UUID value;

    public PostId(UUID value){
        this.value = value;
    }

    public PostId(PostId value){
        this.value = value.getValue();
    }


    @Override
    public String toString() {
        return value.toString();
    }
}
