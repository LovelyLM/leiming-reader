package com.leiming.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.leiming.dao.CategoryDao;
import com.leiming.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
public class CategoryService {
    @Autowired
    private CategoryDao categoryDao;

    /**
     * 查询所有分类
     * @return
     */
    public List<Category> selectAll(){
        return categoryDao.selectList(new QueryWrapper<>());
    }

}
