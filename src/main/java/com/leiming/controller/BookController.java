package com.leiming.controller;


import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.leiming.dao.MemberReadStateDao;
import com.leiming.entity.Book;
import com.leiming.entity.Member;
import com.leiming.entity.MemberReadState;
import com.leiming.service.BookService;
import com.leiming.service.CategoryService;
import com.leiming.service.EvaluationService;
import com.leiming.service.MemberReadStateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
public class BookController {
    @Resource
    private CategoryService categoryService;
    @Resource
    private BookService bookService;
    @Resource
    private EvaluationService evaluationService;
    @Resource
    private MemberReadStateService memberReadStateService;
    @Resource
    private MemberReadStateDao memberReadStateDao;

    /**
     * 显示首页
     * @return
     */
    @GetMapping("/")
    public ModelAndView showIndex(){
        return new ModelAndView("/index","categoryList",categoryService.selectAll());
    }

    /**
     * 图书列表
     * @param pageNum
     * @param rows
     * @return
     */
    @GetMapping("/books")
    @ResponseBody
    public IPage<Book> selectBook(Long categoryId,String order,Integer pageNum, Integer rows){
        if (pageNum == null || pageNum < 1) {
            pageNum = 1;
        }
        return bookService.paging(categoryId, order, pageNum, rows == null?10:rows);
    }

    @GetMapping("book/{id}")
    public ModelAndView showDetail(@PathVariable("id") Long id, HttpSession session){
        Member member =  (Member)session.getAttribute("loginMember");
        ModelAndView view = new ModelAndView("/detail");
        if (ObjectUtil.isNotEmpty(member)){
            MemberReadState memberReadState = memberReadStateService.selectByMemberAndBookId(member.getMemberId(), id);
            view.addObject("memberReadState", memberReadState);
        }
        view.addObject("book",bookService.selectById(id));

        view.addObject("evaluationList", evaluationService.evaluationList(id));
        return view;
    }

    @PostMapping("update_member_reader_state")
    @ResponseBody
    public Map updateMemberReaderState(MemberReadState memberReadState) {
        QueryWrapper<MemberReadState> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("book_id", memberReadState.getBookId());
        queryWrapper.eq("member_id", memberReadState.getMemberId());
        MemberReadState selectMemberReadState = memberReadStateDao.selectOne(queryWrapper);
        Map result = new HashMap(2);
        result.put("code", "0");
        result.put("msg", "success");
        if (ObjectUtil.isEmpty(selectMemberReadState)) {
            memberReadState.setCreateTime(new Date());
            memberReadStateDao.insert(memberReadState);
        } else {
            selectMemberReadState.setReadState(memberReadState.getReadState());
            memberReadStateDao.updateById(selectMemberReadState);

        }
        return result;
    }

}
