package com.algaworks.algaposts.postservice.common;

import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedEpochGenerator;

import java.util.UUID;

public class IdGenerator {
//    private UUID uuid;

    private static final TimeBasedEpochGenerator timeBasedEpochGenerator = Generators.timeBasedEpochGenerator();

    public static UUID generate(){
//        return UUID.randomUUID();
        return timeBasedEpochGenerator.generate();
    }

}
