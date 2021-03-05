package com.leiming.controller.admin;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.leiming.entity.Book;
import com.leiming.service.BookService;
import com.leiming.service.MyException;
import com.leiming.utils.LMResponse;
import com.leiming.utils.LMResultBuilder;
import com.leiming.utils.ResultCode;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author LovelyLM
 * @date 2021-03-04 23:40
 */

@Controller
@RequestMapping("admin/book")
public class AdminBookController {
    @Autowired
    private BookService bookService;

    @GetMapping("index.html")
    public ModelAndView showBook(){
        return new ModelAndView("admin/book");
    }

    @PostMapping("upload")
    @ResponseBody
    public Map upload(@RequestParam("img") MultipartFile file, HttpServletRequest request) throws IOException {
        //上传目录
        String path = request.getServletContext().getResource("/").getPath() + "upload/";
        //新文件名
        String fileName =  DateUtil.format(new Date(), "yyyyMMddHHmmss");
        //扩展名
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String filePath = path + fileName + suffix;
        file.transferTo(new File(filePath));
        Map map = new HashMap(2);
        map.put("errno", 0);
        map.put("data", new String[]{"/upload/"+fileName + suffix});
        return map;
    }

    @PostMapping("create")
    @ResponseBody
    public LMResponse createBook(Book book){
        try{
            book.setEvaluationScore(0F);
            book.setEvaluationQuantity(0);
            Document document = Jsoup.parse(book.getDescription());
            Element img = document.select("img").first();
            book.setCover(img.attr("src"));
            bookService.createBook(book);
            return LMResultBuilder.success("", ResultCode.SUCCESS);
        }catch (MyException e){
            System.out.println(e);
            return LMResultBuilder.faile(ResultCode.SYSTEM_INNER_ERROR);
        }
    }
    @GetMapping("/list")
    @ResponseBody
    public Map pageBook(Integer page, Integer limit){

        IPage<Book> bookIPage = bookService.paging(0L, "", page, limit);
        Map map = new HashMap();
        map.put("code", 0);
        map.put("msg", "success");
        map.put("count", bookIPage.getTotal());
        map.put("data", bookIPage.getRecords());

        return map;
    }
    @PostMapping("/update")
    @ResponseBody
    public LMResponse pageBook(Book book){

        bookService.updateBook(book);

        return LMResultBuilder.success(ResultCode.SUCCESS);
    }

    @GetMapping("/id/{id}")
    @ResponseBody
    public LMResponse getBookById(@PathVariable Long id){


        return LMResultBuilder.success(bookService.selectById(id),ResultCode.SUCCESS);
    }
    @GetMapping("/delete/{id}")
    @ResponseBody
    public LMResponse deleteBook(@PathVariable Long id){
        bookService.deleteBookById(id);
        return LMResultBuilder.success(ResultCode.SUCCESS);
    }
}
