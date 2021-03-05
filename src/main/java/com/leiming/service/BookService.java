package com.leiming.service;


import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leiming.dao.BookDao;
import com.leiming.dao.EvaluationDao;
import com.leiming.dao.MemberReadStateDao;
import com.leiming.entity.Book;
import com.leiming.entity.Evaluation;
import com.leiming.entity.MemberReadState;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author 10796
 */
@Service
public class BookService {
    @Resource
    private BookDao bookDao;
    @Resource
    private EvaluationDao evaluationDao;
    @Resource
    private MemberReadStateDao memberReadStateDao;

    /**
     * 查询所有图书分页
     * @param categoryId
     * @param order
     * @param pageNum
     * @param rows
     * @return
     */
    public IPage<Book> paging(Long categoryId,String order,Integer pageNum, Integer rows){
        Page<Book> page = new Page<>(pageNum, rows);
        QueryWrapper<Book> queryWrapper = new QueryWrapper<>();
        if (ObjectUtil.isNotNull(categoryId) && categoryId > 0){
            queryWrapper.eq("category_id", categoryId);
        }
        if (ObjectUtil.isNotNull(order)){
            if (ObjectUtil.equal(order, "quantity")){
                queryWrapper.orderByDesc("evaluation_quantity");
            } else if (ObjectUtil.equal(order, "score")){
                queryWrapper.orderByDesc("evaluation_score");
            }
        }
        return bookDao.selectPage(page, queryWrapper);
    }

    /**
     * 通过id查询图书
     * @param bookId
     * @return
     */
    public Book selectById(Long bookId){
        return bookDao.selectById(bookId);
    }

    /**
     * 创建图书
     * @param book
     * @return
     */
    @Transactional
    public Book createBook(Book book){
        bookDao.insert(book);
        return book;
    }
    @Transactional
    public Boolean updateBook(Book book){
        return bookDao.updateById(book) == 1;
    }

    @Transactional
    public Boolean deleteBookById(Long id){
        QueryWrapper<MemberReadState> memberReadStateQueryWrapper = new QueryWrapper();
        memberReadStateQueryWrapper.eq("book_id", id);
        memberReadStateDao.delete(memberReadStateQueryWrapper);
        QueryWrapper<Evaluation> evaluationQueryWrapper = new QueryWrapper();
        evaluationQueryWrapper.eq("book_id", id);
        evaluationDao.delete(evaluationQueryWrapper);
        return bookDao.deleteById(id) == 1;
    }
}
