package com.example.twitterwebapp.domain.mappers;

import com.example.twitterwebapp.domain.dtos.CommentWithPostDto;
import com.example.twitterwebapp.domain.entities.Comment;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface CommentMapper {

    @Mapping(source = "postId", target = "post.id")
    @Mapping(source = "username", target = "user.username")
    @Mapping(source = "userId", target = "user.id")
    Comment toEntity(CommentWithPostDto commentWithPostDto);

    @InheritInverseConfiguration(name = "toEntity")
    CommentWithPostDto toDto(Comment comment);

    @InheritConfiguration(name = "toEntity")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Comment partialUpdate(CommentWithPostDto commentWithPostDto, @MappingTarget Comment comment);
}
