package com.leiming.service;


import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leiming.dao.BookDao;
import com.leiming.entity.Book;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class BookService {
    @Resource
    private BookDao bookDao;
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

    public Book selectById(Long bookId){
        return bookDao.selectById(bookId);
    }
}
