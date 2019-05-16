package com.fh.shop.repository;

import com.fh.shop.entity.product.Product;
import com.fh.shop.entity.product.ProductResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

/**
 * 接口继承了JpaRepository就会获取jpa里面的所有增删改查的方法，传个bean<Product,Integer>就是当你返回list或者对象的时候
 * 知道返回的是哪个bean，以及传个Id的类型，死格式
 *
 * 接口必须继承  类型参数化为【实体类型和实体类中的Id类型的Repository
 */
public interface ProductRepository extends JpaRepository<Product,Integer> {
    //区间查询 根据方法名自动生成sql语句 方法名必须以findBy开头
    public List<Product> findByPriceBetween(float minPrice , float maxPrice);
    public List<Product> findByPriceGreaterThan(float price);
    public List<Product> findByProductNameLikeAndPriceGreaterThanAndPriceLessThanAndIdIn(
            String productName ,float minPrice , float maxPrice , List<Integer> ids
    );

    /**
     *  hql语句
     *
     * 自定义方法名（必须加@query） 条件查询 通过？占位符查询，占位符下标从1开始，就是说参数里面的字段位置
     *
     * 直接写hql语句，from后面跟的是实体类对象，sql语句才跟表名字
     * @param productName
     * @param minPrice
     * @param maxPrice
     * @param ids
     * @return
     */
    @Query("select productName from Product where productName like concat('%',?1,'%') and price >=?2 " +
            "and price<=?3 and id in (?4)")
    public List<String> selectProductList(String productName ,float minPrice , float maxPrice , List<Integer> ids);

    /**
     * hql语句
     *
     * 自定义方法名（必须加@query）条件查询 :占位符 代替占位符？
     *
     * @param （里面的参数要和where后面的字段一致）
     *
     * 返回的是个对象数组
     */
    @Query("select productName,price from Product where productName like concat('%',:productName,'%') " +
            "and price >=:minPrice and price<=:maxPrice and id in (:idList)")
    public List selectProList(@Param("productName") String productName , @Param("minPrice")float minPrice ,
                              @Param("maxPrice")float maxPrice , @Param("idList") List<Integer> ids);


    /**
     * hql语句
     *
     * 自定义方法名（必须加@query）条件查询 :占位符 代替占位符？
     *
     * @param （里面的参数要和where后面的字段一致）
     *
     * 返回的是个map集合  as后面的为别名，相当于map的key，输出key的时候就是输出的别名
     */
    @Query("select new Map(productName as productName,price as price) from Product where productName " +
            "like concat('%',:productName,'%') and price >=:minPrice and price<=:maxPrice and id in (:idList)")
    public List<Map<String,Object>> selectProNewList(@Param("productName") String productName ,
                                                     @Param("minPrice")float minPrice ,
                                                     @Param("maxPrice")float maxPrice ,
                                                     @Param("idList") List<Integer> ids);

    /**
     * hql语句
     *
     * 自定义方法名（必须加@query）条件查询 :占位符 代替占位符？
     *
     * @param （里面的参数要和where后面的字段一致）
     *
     * 参数是个实体bean
     */

    @Query("select new Map(productName as productName,price as price) from Product where productName" +
            " like concat('%',:#{#productResult.productName},'%') " +
            "and price >=:#{#productResult.minPrice} and price<=:#{#productResult.maxPrice} " +
            "and id in (:#{#productResult.idList})")
    public List<Map<String,Object>> selectProduct(@Param("productResult") ProductResult productResult);


    /**
     * sql语句
     *
     * 模糊查询 产品名
     *
     * 通过  nativeQuery=true value="select 表字段 from 表名" sql语句
     */

    @Query(nativeQuery = true,value="select productName  from product where productName like concat('%',:productName,'%')")
    public List<String> selectProductName(@Param("productName") String productName);

    /**
     * 通过方法名生成sql语句  删除
     *
     * 删除的时候会先查询下数据库，之后才删除，浪费性能
     */
    void deleteById(Integer id);

    /**
     * 为了在删除的时候不再先查询再删除降低性能， 只能自定义删除方法了
     */

    @Modifying
    @Query("delete from Product where id=?1")//1代表第一个参数
    void deleteProductById(Integer id);

    /**
     * 修改，直接自定义方法把，省的 又是查询后才能修改，降低性能
     */

    @Modifying
    @Query("update Product set productName=:#{#product.productName},price=:#{#product.price}," +
            "brand.id=:#{#product.brand.id} where id=:#{#product.id}")
    void updateProduct(@Param("product")Product product);
}
