package com.leiming.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.ToString;

import java.util.Date;
import java.io.Serializable;

/**
 * (MemberReadState)实体类
 *
 * @author makejava
 * @since 2021-03-03 20:46:17
 */
@Data
public class MemberReadState implements Serializable {
    private static final long serialVersionUID = 640917149087916503L;
    /**
     * 会员阅读状态
     */
    @TableId
    private Long rsId;
    /**
     * 图书编号
     */
    private Long bookId;
    /**
     * 会员编号
     */
    private Long memberId;
    /**
     * 阅读状态 1-想看 2-看过
     */
    private Integer readState;
    /**
     * 创建时间
     */
    private Date createTime;


}
