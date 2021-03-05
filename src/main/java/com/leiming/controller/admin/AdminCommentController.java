package com.leiming.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.leiming.entity.Evaluation;
import com.leiming.service.EvaluationService;
import com.leiming.service.MyException;
import com.leiming.utils.LMResponse;
import com.leiming.utils.LMResultBuilder;
import com.leiming.utils.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author LovelyLM
 * @date 2021-03-06 3:30
 */
@RestController
@RequestMapping("admin/comment")
public class AdminCommentController {
    @Autowired
    private EvaluationService evaluationService;


    @PostMapping("disable")
    public LMResponse disable(Evaluation evaluation){
        Boolean disable = evaluationService.disable(evaluation);
        if (disable){
            return LMResultBuilder.success(ResultCode.SUCCESS);
        }else {
            return LMResultBuilder.faile(ResultCode.SYSTEM_INNER_ERROR);
        }


    }
    @GetMapping("list")
    public Map getList(Integer page, Integer limit){
        Map map = new HashMap();
        try {
            IPage<Evaluation> evaluationIPage = evaluationService.getPage(page, limit);
            map.put("code", 0);
            map.put("msg", "success");
            map.put("count", evaluationIPage.getTotal());
            map.put("data", evaluationIPage.getRecords());
            return map;
        }catch (MyException e){
            System.out.println(e);
            map.put("code", -1);
            map.put("msg", "fail");
            map.put("count", 0);
            map.put("data", e.getMsg());
            return map;
        }

    }

    @PostMapping("update}")
    public LMResponse update(Evaluation evaluation){
        evaluationService.updateById(evaluation);
        return LMResultBuilder.success(ResultCode.SUCCESS);
    }
    @PostMapping("delete/{id}")
    public LMResponse delete(@PathVariable Long id){
        evaluationService.deleteById(id);
        return LMResultBuilder.success(ResultCode.SUCCESS);
    }
}
