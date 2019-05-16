package com.fh.shop;

import com.fh.shop.biz.ProductService;
import com.fh.shop.entity.product.Product;
import com.fh.shop.entity.product.ProductResult;
import com.fh.shop.repository.ProductRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * junit测试：
 * 方法没有返回值，没有参数
 * 加载类
 * 加载配置文件
 * 继承AbstractJUnit4SpringContextTests
 */



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-common-jpa.xml"})
public class TestRepository extends AbstractJUnit4SpringContextTests {

    @Resource
    private ProductRepository productRepository;

    @Resource
    private ProductService productService;
    /**
     * 查询全部
     * 接口继承了JpaRepository<Product,Integer> 就会获取里面的所有方法，因为参数为Product，所有查询出来的返回值list的泛型也是Product
     *
     * 输出时，加上大括号{}必须得换行，不换行的话可以不加{}
     */
    @Test
    public void Test1(){
        List<Product> proList = productRepository.findAll();
        proList.forEach(x-> System.out.println(x.getProductName()+":"+x.getPrice()+":"+x.getBrand().getBrandName()));
    }

    /**
     * 通过id查询 返回对象
     *
     * 接口继承了JpaRepository<Product,Integer> 就会获取里面的所有方法，因为参数为Product，所有查询出来的返回值也是Product
     */
    @Test
    public void test2(){
        Product product = productRepository.findOne(1);
        System.out.println(product);
    }

    /**
     * 通过方法名，自动生成sql语句 ，方法名以findBy*开头
     *
     * 查询 价格在15-2000的商品  区间查询
     */
    @Test
    public void  test3(){
        List<Product> byPriceBetween = productRepository.findByPriceBetween(15, 2000);
        byPriceBetween.forEach(x-> System.out.println(x.getProductName()+":"+x.getPrice()));
    }

    /**
     * 通过方法名，自动生成sql语句 ，方法名以findBy*开头
     *
     * 查询 价格大于1000的商品
     *
     * 输出时，加上大括号{}必须得换行，不换行的话可以不加{}
     */

    @Test
    public void test4(){
        List<Product> byPriceGreaterThan = productRepository.findByPriceGreaterThan(1000);
        byPriceGreaterThan.forEach(x->{
            System.out.println(x.getProductName()+":"+x.getPrice());
        });
    }
    /**
     * 模糊查询 findBy
     */
    @Test
    public void test5(){
        List<Integer> integers = Arrays.asList(1, 2);//数组转list集合。1，2相当于一个数组，也可以将其提取出来 Integer[] a={1,2}
        List<Product> s = productRepository.findByProductNameLikeAndPriceGreaterThanAndPriceLessThanAndIdIn("%p%", 10, 2000, integers);
        s.forEach(x-> System.out.println(x.getProductName()));
    }
    /**
     * 自定义方法名(查询产品名)  条件查询 占位符
     */
    @Test
    public void test6(){
        List<Integer> integers = Arrays.asList(1, 3);//数组转list集合。1，3相当于一个数组，也可以将其提取出来 Integer[] a={1,3}
        List<String> productName = productRepository.selectProductList("p", 15, 2000, integers);
       System.out.println(productName);
    }

    /**
     * 自定义方法名  条件查询 :占位符代替了?占位符
     * 通过在参数里面加了注解@param（"where条件后面的字段"）
     */
    @Test
    public void test7(){
        List<Integer> integers = Arrays.asList(2, 3);
        List list = productRepository.selectProList("s", 15, 3000, integers);
        //查出来list里面是两条数据，而每条数据又是对象数组（可以通过断点查看是否），所以要强转为数组
        for (int i = 0; i < list.size(); i++) {
            Object[] object = (Object[]) list.get(i);
            System.out.println(object[0]+":"+object[1]);
        }
    }
    /**
     * 自定义方法名  条件查询 :占位符代替了?占位符
     * 通过在参数里面加了注解@param（"where条件后面的字段"）
     *  @param （里面的参数要和where后面的字段一致）
     *
     *  返回的是个map集合
     */
    @Test
    public void test8(){
            Integer[] a={2, 3};
        List<Integer> integers = Arrays.asList(a);//将数组转换为list集合，如果参数是string类型的，list泛型也会是string
       // List<Integer> integers = Arrays.asList(2,3);//两种方法
        List<Map<String, Object>> mapList = productRepository.selectProNewList("s", 15, 3000,
                integers);
        //循环mapList y为key z为value
        //x为maoList循环后的对象，通过对象再次循环拿到key  value
        mapList.forEach(x->x.forEach((y,z)-> System.out.println(y+":"+z)));
    }

    /**
     * 自定义方法名  条件查询 :占位符代替了?占位符
     * 通过在参数里面加了注解@param（"where条件后面的字段"）
     *  @param （里面的参数要和where后面的字段一致）
     *
     *  参数是个实体bean ,先给实体Bean里面赋值 再查询
     *
     *  返回的是个map集合
     */

    @Test
    public void test9(){
        ProductResult productResult = new ProductResult();
        List<Integer> integers = Arrays.asList(1, 2, 3);
        productResult.setProductName("s");
        productResult.setMinPrice(1000);
        productResult.setMaxPrice(3000);
        productResult.setIdList(integers);
        List<Map<String, Object>> mapList = productRepository.selectProduct(productResult);
        mapList.forEach(x->x.forEach((y,z)-> System.out.println(y+":"+z)));
    }

    /**
     * 模糊查询 产品名
     *
     * 通过  nativeQuery=true value="select 表字段 from 表名" sql语句
     */

    @Test
    public void test10(){
        List<String> productName = productRepository.selectProductName("s");
        System.out.println(productName);
    }
    /**
     * 查询分页列表  先查询出分页所需
     *
     * new PageRequest(0, 1)里面的0代表的是在jpa里面页码从0开始，即第一页，第二个参数1代表的是一页条数 即一页一条  输出会是1 of 1 代表第一页，每页一条
     *
     * 输出的page里面有 total 即总条数；content 即当前页的几条数据内容
     *
     */

    @Test
    public void test11(){
        Sort sort = new Sort(Sort.Direction.DESC, "id");//排序
        Page<Product> page = productRepository.findAll(new PageRequest(0, 2,sort));

        long totalCount = page.getTotalElements();//总条数
        int totalPages = page.getTotalPages();//总页数
        List<Product> content = page.getContent();//当前页的数据内容
        content.forEach(x-> System.out.println(x.getProductName()+":"+x.getBrand().getBrandName()));
        System.out.println(totalCount+":"+totalPages);
    }
    /**
     * 增加  JpaRepository里自带的添加方法
     */
    @Test
    public void test12(){
        Date date = new Date();
        Product product = new Product();
        product.setProductName("oppo");
        product.setPrice(2300f);//价格是float
        product.setInsertTime(date);
        product.setUpdateTime(date);
        product.getBrand().setId(1);
        productRepository.save(product);
    }
    /**
     * 删除  JpaRepository里自带的删除方法
     *
     * 这个方法在删除的时候首先会查询下数据库，之后才删除，降低性能，可以看输出的sql
     */
    @Test
    public void test13(){
        productRepository.delete(7);
    }
    /**
     * 删除  通过方法名生成sql语句
     *
     * 事务，横切的是service层 ,调用service层的方法
     *
     * 这个方法在删除的时候首先会查询下数据库，之后才删除，降低性能，可以看输出的sql
     */
    @Test
    public  void test14(){
        productService.deleteById(6);
    }

    /**
     * 为了在删除的时候不再先查询再删除降低性能， 只能自定义删除方法了,调用service层的删除方法
     */
    @Test
    public void test15(){
        productService.deleteProductById(5);
    }

    /**
     * 修改  自定义的修改方法，为了修改时不查询后再修改  所有自定义了方法
     *
     */

    @Test
    public void test16(){
        Product product = new Product();
        product.setId(1);
        product.setProductName("iphone8");
        product.getBrand().setId(2);
        product.setPrice(5000f);
        productService.updateProduct(product);
    }
}
