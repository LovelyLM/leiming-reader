package com.leiming.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName
public class Book {
    @TableId(type = IdType.AUTO)
    private Long bookId;
    private String bookName;
    private String subTitle;
    private String author;
    private String cover;
    private String description;
    private Long categoryId;
    private Float evaluationScore;
    private Integer evaluationQuantity;
}
