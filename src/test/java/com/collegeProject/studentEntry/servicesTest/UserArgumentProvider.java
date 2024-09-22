package com.collegeProject.studentEntry.servicesTest;

import com.collegeProject.studentEntry.entity.User;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public class UserArgumentProvider implements ArgumentsProvider {

    public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
        return Stream.of(
                Arguments.of(User.builder().userName("jay").password("jay").build()),
                Arguments.of(User.builder().userName("say").password("say").build())
        );
    }
}
