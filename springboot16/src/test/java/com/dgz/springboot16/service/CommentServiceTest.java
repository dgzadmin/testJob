package com.dgz.springboot16.service;

import com.dgz.springboot16.Springboot16Application;
import com.dgz.springboot16.entity.Comment;
import com.dgz.springboot16.mapper.CommentMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Springboot16Application.class)
public class CommentServiceTest {

    /*Mongodb中先插入数据,方便测试*/
    /*try { db.comment.insertMany([ {"_id":"1","articleid":"100001","content":"我们不应该把清晨浪费在手机上，健康很重要，一杯温水幸福你我 他。","userid":"1002","nickname":"相忘于江湖","createdatetime":new Date("2019-08- 05T22:08:15.522Z"),"likenum":NumberInt(1000),"state":"1"}, {"_id":"2","articleid":"100001","content":"我夏天空腹喝凉开水，冬天喝温开水","userid":"1005","nickname":"伊人憔 悴","createdatetime":new Date("2019-08-05T23:58:51.485Z"),"likenum":NumberInt(888),"state":"1"}, {"_id":"3","articleid":"100001","content":"我一直喝凉开水，冬天夏天都喝。","userid":"1004","nickname":"杰克船 长","createdatetime":new Date("2019-08-06T01:05:06.321Z"),"likenum":NumberInt(666),"state":"1"}, {"_id":"4","articleid":"100001","content":"专家说不能空腹吃饭，影响健康。","userid":"1003","nickname":"凯 撒","createdatetime":new Date("2019-08-06T08:18:35.288Z"),"likenum":NumberInt(2000),"state":"1"}, {"_id":"5","articleid":"100001","content":"研究表明，刚烧开的水千万不能喝，因为烫 嘴。","userid":"1003","nickname":"凯撒","createdatetime":new Date("2019-08- 06T11:01:02.521Z"),"likenum":NumberInt(3000),"state":"1"} ]); } catch (e) { print (e); }*/

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private CommentService commentService;

    @Autowired
    private MongoTemplate mongoTemplate;


    /**
     * 插入数据
     */
    @Test
    public void saveComment() {
        Comment comment = new Comment();
        comment.setArticleid("100000");
        comment.setContent("测试添加的数据");
        comment.setCreatedatetime(LocalDateTime.now());
        comment.setUserid("1003");
        comment.setNickname("凯撒大帝");
        comment.setState("1");
        comment.setLikenum(0);
        comment.setReplynum(0);
        commentService.saveComment(comment);
    }

    @Test
    public void updateComment() {
    }

    @Test
    public void deleteCommentById() {
    }

    /**
     * 查询全部数据
     */
    @Test
    public void findCommentList() {
        List<Comment> commentList = commentService.findCommentList();
        for (Comment comment : commentList) {
            System.out.println(comment);
        }
    }

    /**
     * 根据id查询数据
     */
    @Test
    public void findCommentById() {
        System.out.println(commentService.findCommentById("1"));
    }

    /**
     * 测试根据父id查询子评论的分页列表,并且实现分页
     */
    @Test
    public void findCommentListPageByParentid() {
        Page<Comment> pageResponse = commentService.findCommentListPageByParentid("1003", 1, 2);
        System.out.println("----总记录数：" + pageResponse.getTotalElements());
        System.out.println("----当前页数据：" + pageResponse.getContent());
    }

    /**
     * 以修改的方式可以实现点赞-但是效率低
     */
    @Test
    public void updateCommentThumbupToIncrementingOld() {
        Comment comment = commentMapper.findById("5").get();
        comment.setLikenum(comment.getLikenum() + 1); //给点赞数加一
        commentMapper.save(comment);
    }

    /**
     * 利用递增递增$inc实现点赞数+1
     */
    @Test
    public void updateCommentLikenum() {
        //查询对象
        Query query=Query.query(Criteria.where("_id").is("5"));
        //更新对象
        Update update=new Update();
        //局部更新，相当于$set update.set(key,value);
        //递增$inc
        //update.inc("likenum",1);
        update.inc("likenum");
        //参数1：查询对象
        //参数2：更新对象
        //参数3：集合的名字或实体类的类型:Comment.class
        mongoTemplate.updateFirst(query,update,"comment");
    }


    }