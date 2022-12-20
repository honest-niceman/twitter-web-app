package com.example.twitterwebapp.domain.mappers;

import com.example.twitterwebapp.domain.dtos.PostDto;
import com.example.twitterwebapp.domain.entities.Post;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface PostMapper {

    @Mapping(source = "attachmentUrl", target = "attachment.attachmentUrl")
    Post toEntity(PostDto postDto);

    @InheritInverseConfiguration(name = "toEntity")
    PostDto toDto(Post post);

    @InheritConfiguration(name = "toEntity")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Post partialUpdate(PostDto postDto, @MappingTarget Post post);
}
