package com.dgz.springboot16.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;


//把一个java类声明为mongodb的文档，可以通过collection参数指定这个类对应的文档。
@Document(collection="comment")  //可以省略，如果省略，则默认使用类名小写映射集合
//复合索引:@CompoundIndex( def = "{'userid': 1, 'nickname': -1}")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment implements Serializable{

    @Id   //主键该属性对应mongodb的字段的名字，如果一致，则无需该注解
    private String id;
    @Field("content")
    private String content;
    private Date publishtime;
    @Indexed  //添加了一个单字段的索引,注意,当mongodb已经存在名称相同索引会报错
    private String userid;
    private String nickname;
    private LocalDateTime createdatetime;
    private Integer likenum;
    private Integer replynum;//回复数
    private String state;//状态
    private String parentid;//上级ID
    private String articleid;

}
