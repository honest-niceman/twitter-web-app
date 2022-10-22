package com.example.twitterwebapp.domain.mappers;

import com.example.twitterwebapp.domain.dtos.PostFullDto;
import com.example.twitterwebapp.domain.dtos.PostWithThreadDto;
import com.example.twitterwebapp.domain.entities.Comment;
import com.example.twitterwebapp.domain.entities.Post;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface PostMapper {
    @Mapping(source = "threadUserId", target = "thread.user.id")
    @Mapping(source = "threadId", target = "thread.id")
    @Mapping(source = "attachmentAttachmentUrl", target = "attachment.attachmentUrl")
    @Mapping(source = "attachmentId", target = "attachment.id")
    Post postFullDtoToPost(PostFullDto postFullDto);

    @InheritInverseConfiguration(name = "postFullDtoToPost")
    @Mapping(target = "commentIds", expression = "java" + "(commentsToCommentIds(post.getComments()))")
    PostFullDto postToPostFullDto(Post post);

    @InheritConfiguration(name = "postFullDtoToPost")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Post updatePostFromPostFullDto(PostFullDto postFullDto,
                               @MappingTarget Post post);

    @Mapping(source = "threadId", target = "thread.id")
    Post postWithThreadDtoToPost(PostWithThreadDto postWithThreadDto);

    @Mapping(source = "thread.id", target = "threadId")
    PostWithThreadDto postToPostWithThreadDto(Post post);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "threadId", target = "thread.id")
    Post updatePostFromPostWithThreadDto(PostWithThreadDto postWithThreadDto,
                                         @MappingTarget Post post);

    default Set<UUID> commentsToCommentIds(Set<Comment> comments) {
        return comments.stream().map(Comment::getId).collect(Collectors.toSet());
    }
}
