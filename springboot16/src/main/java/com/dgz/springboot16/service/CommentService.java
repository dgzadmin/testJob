package com.dgz.springboot16.service;

import com.dgz.springboot16.entity.Comment;
import com.dgz.springboot16.mapper.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;


    /**
     * 保存一个评论
     *
     * @param comment
     */
    public void saveComment(Comment comment) {
        //如果需要自定义主键，可以在这里指定主键；如果不指定主键，MongoDB会自动生成主键
        // 设置一些默认初始值。。。 //调用dao
        commentMapper.save(comment);
    }


    /**
     * 更新评论
     *
     * @param comment
     */
    public void updateComment(Comment comment) {
        //调用dao
        commentMapper.save(comment);
    }

    /**
     * 根据id删除评论
     *
     * @param id
     */
    public void deleteCommentById(String id) {
        //调用dao
        commentMapper.deleteById(id);
    }

    /**
     * 查询所有评论
     *
     * @return
     */
    public List<Comment> findCommentList() {
        //调用dao
        return commentMapper.findAll();
    }

    /**
     * 根据id查询评论
     *
     * @param id
     * @return
     */
    public Comment findCommentById(String id) {
        //调用dao
        return commentMapper.findById(id).get();
    }


    /**
     * 根据父id查询分页列表
     * @param parentid
     * @param page
     * @param size
     * @return
     */
    public Page<Comment> findCommentListPageByParentid(String parentid, int page, int size) {
        return commentMapper.findByParentid(parentid, PageRequest.of(page - 1, size));
    }


}