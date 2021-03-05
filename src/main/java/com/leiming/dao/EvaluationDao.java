package com.leiming.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.leiming.entity.Evaluation;

/**
 * @author 10796
 */
public interface EvaluationDao extends BaseMapper<Evaluation> {
    void updateEvaluation();
}
