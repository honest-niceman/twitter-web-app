package com.example.twitterwebapp.domain.mappers;

import com.example.twitterwebapp.domain.dtos.UserDto;
import com.example.twitterwebapp.domain.dtos.UserRegistrationDto;
import com.example.twitterwebapp.domain.entities.Comment;
import com.example.twitterwebapp.domain.entities.Thread;
import com.example.twitterwebapp.domain.entities.User;
import org.mapstruct.*;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface UserMapper {
    User userDtoToUser(UserDto userDto);

    @Mapping(target = "threadIds", expression = "java(threadsToThreadIds(user.getThreads()))")
    @Mapping(target = "commentIds", expression = "java(commentsToCommentIds(user.getComments()))")
    UserDto userToUserDto(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User updateUserFromUserDto(UserDto userDto,
                               @MappingTarget User user);

    User userRegistrationDtoToUser(UserRegistrationDto userRegistrationDto);

    UserRegistrationDto userToUserRegistrationDto(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User updateUserFromUserRegistrationDto(UserRegistrationDto userRegistrationDto,
                                           @MappingTarget User user);

    default Set<Long> commentsToCommentIds(Set<Comment> comments) {
        return comments.stream().map(Comment::getId).collect(Collectors.toSet());
    }

    default Set<Long> threadsToThreadIds(Set<Thread> threads) {
        return threads.stream().map(Thread::getId).collect(Collectors.toSet());
    }
}
