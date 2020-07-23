package com.boot.bootdemo.service;


import com.boot.bootdemo.dao.PaperDao;
import com.boot.bootdemo.entity.Paper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PaperServiceImpl implements PaperService {
    @Autowired
    private PaperDao paperDao;

    @Override
    public int addPaper(Paper paper) {
        return paperDao.addPaper(paper);
    }

    @Override
    public int deletePaperById(long id) {
        return paperDao.deletePaperById(id);
    }

    @Override
    public int updatePaper(Paper paper) {
        return paperDao.updatePaper(paper);
    }

    @Override
    public Paper queryById(long id) {
        return paperDao.queryById(id);
    }

    @Override
    @Transactional
    @Cacheable(value = "AllPaper",key = "#pageIndex+#pageSize")
    public List<Paper> queryAllPaper(Integer pageIndex,Integer pageSize) {
        return paperDao.queryAllPaper(pageIndex,pageSize);
    }

    @Override
    public List<Paper> selectAll() {
        return paperDao.selectAll();
    }

}