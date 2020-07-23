package com.boot.bootdemo.dao;



import com.boot.bootdemo.entity.Paper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PaperDao {
    int addPaper(Paper paper);

    int deletePaperById(long id);

    int updatePaper(Paper paper);

    Paper queryById(long id);

    List<Paper> queryAllPaper(@Param("pageIndex") Integer pageIndex, @Param("pageSize")Integer pageSize);


    List<Paper> selectAll();
}