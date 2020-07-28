package com.boot.bootdemo.service.base;

import org.elasticsearch.search.SearchHit;

/**
 * @author Administrator
 * @date 2020/7/27 12:00
 */
public interface BaseService<E> {

    //将高亮内容赋回到实体类内
    E setHighlight(E e, String[] fields, SearchHit searchHit);
}
