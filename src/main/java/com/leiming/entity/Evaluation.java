package com.leiming.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * (Evaluation)实体类
 *
 * @author makejava
 * @since 2021-02-10 16:56:07
 */
@Data
@TableName("evaluation")
public class Evaluation implements Serializable {
    private static final long serialVersionUID = -51168702095654669L;

    @TableField(exist = false)
    private Book book;

    @TableField(exist = false)
    private Member member;

    /**
    * 评价编号
    */
    @TableId(type = IdType.AUTO)
    private Long evaluationId;
    /**
    * 短评内容
    */
    private String content;
    /**
    * 评分-5分制
    */
    private Integer score;
    /**
    * 创建时间
    */
    private Date createTime;
    /**
    * 会员编号
    */
    private Long memberId;
    /**
    * 图书编号
    */
    private Long bookId;
    /**
    * 点赞数量
    */
    private Integer enjoy;
    /**
    * 审核状态 enable-有效 disable-已禁用
    */
    private String state;
    /**
    * 禁用理由
    */
    private String disableReason;
    /**
    * 禁用时间
    */
    private Date disableTime;





}