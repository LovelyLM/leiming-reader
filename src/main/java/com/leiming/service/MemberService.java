package com.leiming.service;


import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.crypto.digest.MD5;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.leiming.dao.MemberDao;
import com.leiming.entity.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
@Transactional
@Slf4j
public class MemberService {
    @Autowired
    private MemberDao memberDao;

    /**
     * 注册会员
     * @param member 会员实体
     * @return
     */
    public Member createMember(Member member){
        //条件构造器，构造一个与数据库字段“username”相同的条件并查询
        QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", member.getUsername());
        List<Member> memberList = memberDao.selectList(queryWrapper);
        //若结果不为空，则用户名重复，抛出异常
        if (ObjectUtil.isNotEmpty(memberList)){
            throw new MyException("M01", "用户名已存在");
        }
        member.setCreateTime(new Date());
        //加salt值，使用10位随机字符串
        String salt = RandomUtil.randomString(10);
        String password = DigestUtil.md5Hex(member.getPassword() + salt);
        member.setPassword(password);
        member.setSalt(salt);
        memberDao.insert(member);
        return member;
    }


    public Member checkLogin(Member member){
        QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", member.getUsername());
        Member selectMember = memberDao.selectOne(queryWrapper);
        if (ObjectUtil.isEmpty(selectMember)){
            throw  new MyException("M02","用户不存在");
        }
        if (!StrUtil.equals(selectMember.getPassword(),DigestUtil.md5Hex(member.getPassword() + selectMember.getSalt()))){
            throw new MyException("M03", "密码错误");
        }
        return selectMember;
    }
}
