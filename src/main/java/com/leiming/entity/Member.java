package com.leiming.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * (Member)实体类
 *
 * @author makejava
 * @since 2021-02-10 19:45:26
 */
@Data
@TableName("member")
public class Member implements Serializable {
    private static final long serialVersionUID = 300706281676024808L;
    /**
    * 会员编号
    */
    @TableId(type = IdType.AUTO)
    private Long memberId;
    /**
    * 用户名
    */
    private String username;
    /**
    * 密码
    */
    private String password;
    /**
    * 盐值
    */
    private String salt;
    /**
    * 创建时间
    */
    private Date createTime;
    /**
    * 昵称
    */
    private String nickname;


}