package com.example.twitterwebapp.domain.mappers;

import com.example.twitterwebapp.domain.dtos.ThreadWithUserAndPostsDto;
import com.example.twitterwebapp.domain.dtos.ThreadWithUserDto;
import com.example.twitterwebapp.domain.entities.Thread;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

@Component
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface ThreadMapper {
    @Mapping(source = "userId", target = "user.id")
    Thread threadWithUserAndPostsDtoToThread(ThreadWithUserAndPostsDto threadWithUserAndPostsDto);

    @Mapping(source = "user.id", target = "userId")
    ThreadWithUserAndPostsDto threadToThreadWithUserAndPostsDto(Thread thread);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "userId", target = "user.id")
    Thread updateThreadFromThreadWithUserAndPostsDto(ThreadWithUserAndPostsDto threadWithUserAndPostsDto,
                                                     @MappingTarget Thread thread);

    @AfterMapping
    default void linkPosts(@MappingTarget Thread thread) {
        thread.getPosts().forEach(post -> post.setThread(thread));
    }

    @Mapping(source = "userId", target = "user.id")
    Thread threadWithUserDtoToThread(ThreadWithUserDto threadWithUserDto);

    @Mapping(source = "user.id", target = "userId")
    ThreadWithUserDto threadToThreadWithUserDto(Thread thread);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "userId", target = "user.id")
    Thread updateThreadFromThreadWithUserDto(ThreadWithUserDto threadWithUserDto,
                                             @MappingTarget Thread thread);
}
