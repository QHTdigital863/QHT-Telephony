package com.qht.crm.mapper;

import com.qht.crm.entity.Comment;
import com.qht.crm.entity.Post;
import com.qht.crm.entity.dto.CommentDTO;
import com.qht.crm.entity.dto.PostDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface PostMapper {

    @Mapping(target = "postId", source = "postId")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "authorFirstName", source = "author.firstName")
    @Mapping(target = "authorLastName", source = "author.lastName")
    @Mapping(target = "organization", source = "organization")
    PostDTO mapPostToDTO(Post post);

    @Mapping(target = "comments", source = "comments")
    Set<CommentDTO> mapCommentsToDTO(Set<Comment> comments);
}
