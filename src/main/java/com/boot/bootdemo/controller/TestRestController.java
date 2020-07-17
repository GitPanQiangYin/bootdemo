package com.boot.bootdemo.controller;

import com.boot.bootdemo.MyException.BizException;
import com.boot.bootdemo.entity.User;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class TestRestController {

    @RequestMapping("/insert")
    public boolean insert() {
        System.out.println("开始新增...");
        //如果姓名为空就手动抛出一个自定义的异常！
        User user = new User();
        if(user.getUsername()==null){
            throw  new BizException("-1","用户姓名不能为空！");
        }
        return true;
    }

    @PutMapping("/update")
    public boolean update() {
        System.out.println("开始更新...");
        //这里故意造成一个空指针的异常，并且不进行处理
        String str=null;
        str.equals("111");
        return true;
    }

    @DeleteMapping("/delete")
    public boolean delete()  {
        System.out.println("开始删除...");
        //这里故意造成一个异常，并且不进行处理
        Integer.parseInt("abc123");
        return true;
    }

    @RequestMapping("/findByUser")
    public List<User> findByUser() {
        System.out.println("开始查询...");
        List<User> userList =new ArrayList<>();
        User user2=new User();
        user2.setId(1L);
        user2.setUsername("zj");
        user2.setAge(18);
        userList.add(user2);
        return userList;
    }

}
