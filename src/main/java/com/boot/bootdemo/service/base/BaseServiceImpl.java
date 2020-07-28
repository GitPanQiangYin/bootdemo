package com.boot.bootdemo.service.base;

import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author Administrator
 * @date 2020/7/27 12:01
 */
public class BaseServiceImpl<E> implements BaseService<E>{

    @Override
    public E setHighlight(E e, String[] fields, SearchHit searchHit) {
        Map<String, HighlightField> highLightMessages = searchHit.getHighlightFields();
        for (String field : fields) {
            if(highLightMessages.get(field) != null){
                String highField = searchHit.getHighlightFields().get(field).fragments()[0].toString();

                //存在map里
                if(field.indexOf(".") != -1){
                    String fieldName = field.split("[\\.]")[1];
                    try {
                        Method getMapMethod = e.getClass().getMethod("getMap");
                        Map map = (Map) getMapMethod.invoke(e);
                        map.put(fieldName,highField);
                        Method setMapMethod = e.getClass().getMethod("setMap", Map.class);
                        setMapMethod.invoke(e,map);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }else{
                    // 反射调用set方法将高亮内容设置进去
                    try {
                        String setMethodName = parSetName(field);
                        Class<? extends E> eClazz = (Class<? extends E>) e.getClass();
                        Method setMethod = eClazz.getMethod(setMethodName, String.class);
                        setMethod.invoke(e,highField);
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
            }
        }
        return e;
    }

    //根据字段名得到set方法名
    private static String parSetName(String fieldName) {
        if (null == fieldName || "".equals(fieldName)) {
            return null;
        }
        int startIndex = 0;
        if (fieldName.charAt(0) == '_')
            startIndex = 1;
        return "set" + fieldName.substring(startIndex, startIndex + 1).toUpperCase()
                + fieldName.substring(startIndex + 1);
    }
}
