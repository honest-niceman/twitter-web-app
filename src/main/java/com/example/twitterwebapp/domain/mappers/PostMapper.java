package com.example.twitterwebapp.domain.mappers;

import com.example.twitterwebapp.domain.dtos.PostFullDto;
import com.example.twitterwebapp.domain.dtos.PostWithAttachmentDto;
import com.example.twitterwebapp.domain.entities.Post;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface PostMapper {

    Post postFullDtoToPost(PostFullDto postFullDto);

    @Mapping(source = "user.id", target = "userId")
    PostFullDto postToPostFullDto(Post post);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Post updatePostFromPostFullDto(PostFullDto postFullDto,
                                   @MappingTarget Post post);

    Post postWithAttachmentDtoToPost(PostWithAttachmentDto postWithAttachmentDto);

    PostWithAttachmentDto postToPostWithAttachmentDto(Post post);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Post updatePostFromPostWithAttachmentDto(PostWithAttachmentDto postWithAttachmentDto,
                                             @MappingTarget Post post);
}
