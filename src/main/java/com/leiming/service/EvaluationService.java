package com.leiming.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leiming.dao.BookDao;
import com.leiming.dao.EvaluationDao;
import com.leiming.dao.MemberDao;
import com.leiming.entity.Book;
import com.leiming.entity.Evaluation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class EvaluationService {
    @Autowired
    private EvaluationDao evaluationDao;
    @Autowired
    private MemberDao memberDao;
    @Autowired
    private BookDao bookDao;

    public Boolean disable(Evaluation evaluation){

        evaluation.setState("disable");
       return evaluationDao.updateById(evaluation) == 1;

    }
    public IPage<Evaluation> getPage(Integer page, Integer limit){
        Page<Evaluation> bookPage = new Page<>(page, limit);
        Page<Evaluation> evaluationPage = evaluationDao.selectPage(bookPage, null);
        evaluationPage.getRecords().forEach(e->{
            e.setBook(bookDao.selectById(e.getBookId()));
            e.setMember(memberDao.selectById(e.getMemberId()));
        });
        return evaluationPage;
    }

    public Boolean deleteById(Long id){
        return evaluationDao.deleteById(id) == 1;
    }

    public Boolean updateById(Evaluation evaluation){
        return evaluationDao.updateById(evaluation) == 1;
    }

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
    public Evaluation enjoy(Long id){
        Evaluation evaluation = evaluationDao.selectById(id);
        log.info(String.valueOf(evaluation));
        evaluation.setEnjoy(evaluation.getEnjoy() + 1);
        evaluationDao.updateById(evaluation);
        return evaluation;
    }

    @Transactional
    public void updateEvaluation(){
        evaluationDao.updateEvaluation();
    }
}
