package com.boot.bootdemo.controller;


import com.boot.bootdemo.entity.Paper;
import com.boot.bootdemo.service.PaperService;
import com.boot.bootdemo.util.RedisUtil1;
import com.boot.bootdemo.util.RedisUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/paper")
public class PaperController {
        @Autowired
        private PaperService paperService;
        @Autowired
        RedisTemplate redisTemplate;
        @Autowired
        RedisUtil1 redisUtil;
        @Autowired
        RedisUtils redisUtils;

    @RequestMapping("/allPaper")
    public String list(Model model) {

        long page1 = 1;
        long pageSize = 3;
        String key = "user_key_";
        List<Paper> list = null;
        if (redisTemplate.hasKey(key)) {
            Object o  = redisUtils.get("sortId");
            //获得最大分值
            Double maxScore =  redisUtil.sMaxScore("sortId");
            // 分页取数据
            List list1 = redisUtil.sRangeByScore("sortId",0.0,maxScore,page1,pageSize);
            list =redisUtil.hMultiGet(key,list1);

        }else {
            list = paperService.queryAllPaper();
            for (Paper paper : list) {
                //初始化user_key的索引
                double i = list.indexOf(paper);
                redisTemplate.opsForZSet().add("sortId", key + paper.getPaperId(), i);
                redisTemplate.opsForHash().put(key, key + paper.getPaperId(), paper);
            }

        }


       // List<Paper> list = paperService.queryAllPaper();
        model.addAttribute("list", list);
        return "allPaper";

    }

    @RequestMapping("toAddPaper")
    public String toAddPaper() {
        return "addPaper";
    }

    @RequestMapping("/addPaper")
    public String addPaper(Paper paper) {
        paperService.addPaper(paper);
        return "redirect:/paper/allPaper";
    }

    @RequestMapping("/del/{paperId}")
    public String deletePaper(@PathVariable("paperId") Long id) {
        paperService.deletePaperById(id);
        return "redirect:/paper/allPaper";
    }

    @RequestMapping("toUpdatePaper")
    public String toUpdatePaper(Model model, Long id) {
        model.addAttribute("paper", paperService.queryById(id));
        return "updatePaper";
    }

    @RequestMapping("/updatePaper")
    public String updatePaper(Model model, Paper paper) {
        paperService.updatePaper(paper);
        paper = paperService.queryById(paper.getPaperId());
        model.addAttribute("paper", paper);
        return "redirect:/paper/allPaper";
    }
}