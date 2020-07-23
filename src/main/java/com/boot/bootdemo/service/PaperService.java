package com.boot.bootdemo.service;



import com.boot.bootdemo.entity.Paper;

import java.util.List;

public interface PaperService {
    int addPaper(Paper paper);

    int deletePaperById(long id);

    int updatePaper(Paper paper);

    Paper queryById(long id);

    List<Paper> queryAllPaper(Integer pageIndex,Integer pageSize);
    /*
    * @description：查询全部
    * @param ：
    * @return {{@link java.util.List<com.boot.bootdemo.entity.Paper>}}
    * @author Administrator
    * @date 2020/7/22 15:45
    */
    List<Paper> selectAll();
}