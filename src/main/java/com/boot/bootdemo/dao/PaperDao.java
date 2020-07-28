package com.boot.bootdemo.dao;



import com.boot.bootdemo.entity.Paper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PaperDao {
    int addPaper(Paper paper);

    int deletePaperById(String id);

    int updatePaper(Paper paper);

    Paper queryById(String id);

    List<Paper> queryAllPaper(@Param("pageIndex") Integer pageIndex, @Param("pageSize")Integer pageSize);

    List<Paper> selectAll();

}