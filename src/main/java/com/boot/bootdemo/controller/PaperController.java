package com.boot.bootdemo.controller;


import com.boot.bootdemo.entity.Paper;
import com.boot.bootdemo.service.PaperService;
import com.boot.bootdemo.util.JsonUtils;
import com.boot.bootdemo.util.RedisUtil1;
import com.boot.bootdemo.util.RedisUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
        @Autowired
        StringRedisTemplate stringRedisTemplate;

    @RequestMapping("/allPaper")
    public String list(Model model) {

        long pageIndex = 1;
        long pageSize = 2;
        String key = "allPaper";
        List<Paper> list = new ArrayList<>();
        if (redisTemplate.hasKey(key)) {
            list = paperService.selectAll();

                //获得最大分值
                Double maxScore =  redisUtil.sMaxScore("sortId");
                List list1 = redisUtil.sRangeByScore("sortId",0.0,maxScore,pageIndex,pageSize);
                list =redisUtil.hMultiGet(key,list);


        }else {
            list = paperService.selectAll();
        for (Paper paper : list) {
            //初始化user_key的索引，利用sortset来保存hk每个对象的hk
            redisTemplate.opsForZSet().add("sortId", key + paper.getPaperId(), paper.getPaperId());
            //利用hash来保存user，这里的hk要和sortset保存的value一致，后续分页的时候有用
            redisTemplate.opsForHash().put(key, key + paper.getPaperId(), paper);
        }
        }


       //list = paperService.queryAllPaper((pageIndex-1)*pageSize,pageSize*pageIndex);
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