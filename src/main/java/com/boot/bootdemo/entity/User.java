package com.boot.bootdemo.entity;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "user_t")
@Document(indexName = "user",type = "test")
@Data
public class User implements Serializable {
    private static final long serialVersionUID = -3320971805590503443L;
    @Id
    @GeneratedValue
    private long id;
    private String username;
    private String password;
    private Integer age;
    private String salt;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role_t", joinColumns = { @JoinColumn(name = "uid") }, inverseJoinColumns = {
            @JoinColumn(name = "rid") })
    private List<SysRole> roles;


}
