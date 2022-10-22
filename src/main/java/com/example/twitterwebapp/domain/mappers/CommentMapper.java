package com.example.twitterwebapp.domain.mappers;

import com.example.twitterwebapp.domain.dtos.CommentDto;
import com.example.twitterwebapp.domain.entities.Comment;
import com.example.twitterwebapp.domain.dtos.CommentWithPostAndUserDto;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

@Component
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface CommentMapper {
    @Mapping(source = "previousCommentId", target = "previousComment.id")
    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "postId", target = "post.id")
    Comment commentDtoToComment(CommentDto commentDto);

    @InheritInverseConfiguration(name = "commentDtoToComment")
    CommentDto commentToCommentDto(Comment comment);

    @InheritConfiguration(name = "commentDtoToComment")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Comment updateCommentFromCommentDto(CommentDto commentDto,
                                        @MappingTarget Comment comment);

    @Mapping(source = "previousCommentId", target = "previousComment.id")
    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "postId", target = "post.id")
    Comment commentWithPostAndUserDtoToComment(CommentWithPostAndUserDto commentWithPostAndUserDto);

    @InheritInverseConfiguration(name = "commentWithPostAndUserDtoToComment")
    CommentWithPostAndUserDto commentToCommentWithPostAndUserDto(Comment comment);

    @InheritConfiguration(name = "commentWithPostAndUserDtoToComment")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Comment updateCommentFromCommentWithPostAndUserDto(CommentWithPostAndUserDto commentWithPostAndUserDto,
                                                       @MappingTarget Comment comment);
}
