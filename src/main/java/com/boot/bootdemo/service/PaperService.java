package com.boot.bootdemo.service;



import com.boot.bootdemo.entity.Paper;
import com.boot.bootdemo.service.base.BaseService;

import java.util.List;

public interface PaperService extends BaseService<Paper> {
    int addPaper(Paper paper);

    int deletePaperById(String id);

    int updatePaper(Paper paper);

    Paper queryById(String id);

    List<Paper> queryAllPaper(Integer pageIndex,Integer pageSize);
    /*
    * @description：查询全部
    * @param ：
    * @return {{@link java.util.List<com.boot.bootdemo.entity.Paper>}}
    * @author Administrator
    * @date 2020/7/22 15:45
    */
    List<Paper> selectAll();

    void updateES();

    List<Paper> findPaperByEs(Paper paper);

}