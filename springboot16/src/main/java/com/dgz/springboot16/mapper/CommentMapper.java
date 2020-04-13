package com.dgz.springboot16.mapper;

import com.dgz.springboot16.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CommentMapper extends MongoRepository<Comment,String> {

    //方法名必须以这种格式,Parentid为实体类字段
    Page<Comment> findByParentid(String parentid, Pageable pageable);


}
