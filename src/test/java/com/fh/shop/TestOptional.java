package com.fh.shop;

import org.junit.Test;

import java.util.Optional;

public class TestOptional {

    @Test
    public void test1(){

        String userName="";
        Optional<String> userName1 = Optional.ofNullable(userName);//不论user Name是否为空都不报错
        userName1.ifPresent(x-> System.out.println(x.length()));//ifPresent,如果存在；即不为空


    }
}
