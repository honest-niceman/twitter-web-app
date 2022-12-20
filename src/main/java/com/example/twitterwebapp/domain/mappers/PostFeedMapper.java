package com.example.twitterwebapp.domain.mappers;

import com.example.twitterwebapp.domain.dtos.PostFeedDto;
import com.example.twitterwebapp.domain.entities.Post;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface PostFeedMapper {
    @Mapping(source = "attachmentUrl", target = "attachment.attachmentUrl")
    @Mapping(source = "userPhoto", target = "user.photo")
    @Mapping(source = "username", target = "user.username")
    Post toEntity(PostFeedDto postFeedDto);

    @InheritInverseConfiguration(name = "toEntity")
    PostFeedDto toDto(Post post);

    @InheritConfiguration(name = "toEntity")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Post partialUpdate(PostFeedDto postFeedDto, @MappingTarget Post post);

    List<PostFeedDto> toDto(List<Post> post);

    List<Post> toEntity(List<PostFeedDto> postFeedDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)List<Post>
    partialUpdate(List<PostFeedDto> postFeedDto, @MappingTarget List<Post> post);
}
