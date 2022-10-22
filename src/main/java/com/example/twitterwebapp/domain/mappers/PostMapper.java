package com.example.twitterwebapp.domain.mappers;

import com.example.twitterwebapp.domain.dtos.PostFullDto;
import com.example.twitterwebapp.domain.dtos.PostWithThreadAndAttachmentDto;
import com.example.twitterwebapp.domain.dtos.ThreadWithUserAndPostsDto;
import com.example.twitterwebapp.domain.entities.Post;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

@Component
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface PostMapper {

    Post postDtoToPost(ThreadWithUserAndPostsDto.PostDto postDto);

    ThreadWithUserAndPostsDto.PostDto postToPostDto(Post post);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Post updatePostFromPostDto(ThreadWithUserAndPostsDto.PostDto postDto,
                               @MappingTarget Post post);


    Post postFullDtoToPost(PostFullDto postFullDto);

    PostFullDto postToPostFullDto(Post post);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Post updatePostFromPostFullDto(PostFullDto postFullDto,
                                   @MappingTarget Post post);


    Post postWithThreadAndAttachementDtoToPost(PostWithThreadAndAttachmentDto postWithThreadAndAttachmentDto);

    PostWithThreadAndAttachmentDto postToPostWithThreadAndAttachementDto(Post post);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Post updatePostFromPostWithThreadAndAttachementDto(PostWithThreadAndAttachmentDto postWithThreadAndAttachmentDto,
                                                       @MappingTarget Post post);
}
