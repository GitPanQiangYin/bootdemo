package com.boot.bootdemo.entity;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@Document(indexName = "paper")
public class Paper implements Serializable {
    @Id
    @Field(store = true)
    private String id;
    private String code;
    private String paperName;
    private int paperNum;
    private String paperDetail;
    private String keyWord;



}