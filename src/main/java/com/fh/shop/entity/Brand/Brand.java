package com.fh.shop.entity.Brand;

import javax.annotation.Generated;
import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Brand implements Serializable {

    private static final long serialVersionUID = 2111007266970844530L;

    @Id
    //下面注解说明id是自增的
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String brandName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }
}
