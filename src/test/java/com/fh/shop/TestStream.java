package com.fh.shop;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * stream用来操作集合的
 */
public class TestStream {


    /**
     * 往list里添加
     */
    @Test
    public void test1(){
        //之前的写法
      /*  List<String> list = new ArrayList<String>();
        list.add("aaa");
        list.add("bbb");
        list.add("ccc");
        for (int i = 0; i < list.size(); i++) {
            String s = list.get(i);
            System.out.println(s);
        }*/

        //现在的写法
        List<String> list = new ArrayList<String>(){
            {
                add("aaa");
                add("");
                add("ccc");
                add("ccc");
            }
        };
        //list.forEach(System.out::println);
        //filter过滤条件
        //distinct去重
        //skip跳过
        //list.stream().filter(x->!x.isEmpty()).distinct().forEach(System.out::println);

        //list.stream().skip(1).forEach(System.out::println);

    /**+
     * map
     * stream里的map的作用是 起到了转换的作用
     *
     * list里面都是string类型的  转换成length类型的
     *
     * String::length 是stream里面固定格式
     */
    list.stream().map(String::length).forEach(System.out::println);

        /**
         * 调用自己写的转换格式
         */
        list.stream().map(this::conver).forEach(System.out::println);

        String ss="1,2,3";
        String[] split = ss.split(",");
        //this代表当前类下的所有方法，将其转换为integer

        Arrays.stream(split).map(this::show).forEach(System.out::println);
    }

    private String conver(String x){
        return x+":"+x.length();
    }

    private int show (String x){
        return Integer.valueOf(x);
    }


}