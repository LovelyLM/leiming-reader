package com.leiming.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.leiming.entity.MemberReadState;
import com.leiming.dao.MemberReadStateDao;
import com.leiming.service.MemberReadStateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * (MemberReadState)表服务实现类
 *
 * @author LOvelyLM
 * @since 2021-03-03 20:46:18
 */
@Slf4j
@Service("memberReadStateService")
@Transactional
public class MemberReadStateService{
    @Resource
    private MemberReadStateDao memberReadStateDao;

    public MemberReadState selectByMemberAndBookId(Long memberId, Long bookId){
        QueryWrapper<MemberReadState> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("book_id", bookId);
        queryWrapper.eq("member_id", memberId);
        MemberReadState memberReadState = memberReadStateDao.selectOne(queryWrapper);
        return memberReadState;
    }

}
