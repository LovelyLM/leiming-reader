package com.leiming.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.leiming.dao.BookDao;
import com.leiming.dao.EvaluationDao;
import com.leiming.dao.MemberDao;
import com.leiming.entity.Book;
import com.leiming.entity.Evaluation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class EvaluationService {
    @Autowired
    private EvaluationDao evaluationDao;
    @Autowired
    private MemberDao memberDao;
    @Autowired
    private BookDao bookDao;

    public List<Evaluation> evaluationList(Long bookId){
        QueryWrapper<Evaluation> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("book_id", bookId);
        queryWrapper.eq("state", "enable");
        queryWrapper.orderByDesc("create_time");
        List<Evaluation> evaluationList = evaluationDao.selectList(queryWrapper);
        for (Evaluation e:evaluationList) {
            e.setMember(memberDao.selectById(e.getMemberId()));
            e.setBook(bookDao.selectById(e.getBookId()));
        }
        return evaluationList;
    }
    public Evaluation evaluation(Evaluation evaluation){
        evaluation.setCreateTime(new Date());
        evaluation.setEnjoy(0);
        evaluation.setState("enable");
        evaluationDao.insert(evaluation);
        return evaluation;
    }
}
