package com.boot.bootdemo.controller;


import com.boot.bootdemo.entity.Paper;
import com.boot.bootdemo.service.PaperService;
import com.boot.bootdemo.util.JsonUtils;
import com.boot.bootdemo.util.StringUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
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

/*        @Autowired
        RedisUtil1 redisUtil;
        @Autowired
        RedisUtils redisUtils;*/

    @RequestMapping("/allPaper")
    public String list(Model model,Paper paper) {
        Integer pageIndex = 1;
        Integer pageSize = 2;
        String key = "allPaper";
        List<Paper> list = new ArrayList<>();
        String keyWord = paper.getKeyWord();
        if (StringUtil.isEmpty(keyWord)){
            list = paperService.queryAllPaper(pageIndex,pageSize);
            paperService.updateES();
        }else{
            list = paperService.findPaperByEs(paper);
        }
       /* if (redisTemplate.hasKey(key)) {
            list = paperService.selectAll();

                //获得最大分值
                Double maxScore =  redisUtil.sMaxScore("sortId");
                List list1 = redisUtil.sRangeByScore("sortId",0.0,maxScore,pageIndex,pageSize);
                list =redisUtil.hMultiGet(key,list);


        }else {
            list = paperService.selectAll();
        for (Paper paper : list) {
            //初始化user_key的索引，利用sortset来保存hk每个对象的hk
            redisTemplate.opsForZSet().add("sortId", key + paper.getid(), paper.getid());
            //利用hash来保存user，这里的hk要和sortset保存的value一致，后续分页的时候有用
            redisTemplate.opsForHash().put(key, key + paper.getid(), paper);
        }
        }*/


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

    @RequestMapping("/del/{id}")
    public String deletePaper(@PathVariable("id") String id) {
        paperService.deletePaperById(id);
        return "redirect:/paper/allPaper";
    }

    @RequestMapping("toUpdatePaper")
    public String toUpdatePaper(Model model, String id) {
        model.addAttribute("paper", paperService.queryById(id));
        return "updatePaper";
    }

    @RequestMapping("/updatePaper")
    public String updatePaper(Model model, Paper paper) {
        paperService.updatePaper(paper);
        paper = paperService.queryById(paper.getId());
        model.addAttribute("paper", paper);
        return "redirect:/paper/allPaper";
    }
}