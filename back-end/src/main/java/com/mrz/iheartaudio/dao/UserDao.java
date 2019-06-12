package com.mrz.iheartaudio.dao;

import com.mrz.iheartaudio.pojo.Order;
import com.mrz.iheartaudio.pojo.Product;
import com.mrz.iheartaudio.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserDao {
    User getUserById(@Param("id")int id);

    User getUserByUserName(@Param("user_name")String user_name);

    List<User> getAllUser();

    void register(@Param("id")int id,@Param("user_name")String user_name,@Param("password")String password);

    Product getProduct(@Param("id")String id);

    void submitOrder(@Param("product_id")String product_id,
                     @Param("quantity")String quantity,
                     @Param("price")String price,
                     @Param("user_name")String user_name,
                     @Param("mysum")String mysum,
                     @Param("mydate")String mydate,
                     @Param("address")String address,
                     @Param("status")String status,
                     @Param("product_name")String product_name);

    Order getOrderById(@Param("order_id")int order_id);
    List<Order> getOrderByUserName(@Param("user_name")String user_name);
    List<Order> getAllOrder();

    void modifyUserInfo(@Param("name")String name,
                        @Param("gender")String gender,
                        @Param("city")String city,
                        @Param("address")String address,
                        @Param("mail_num")String mail_num,
                        @Param("phone")String phone,
                        @Param("e_mail")String e_mail,
                        @Param("user_name")String user_name);

    void modifyOrder(@Param("address")String address,
                     @Param("order_id")int order_id);

    void modifyProduct(@Param("name")String name,
                       @Param("price")String price,
                       @Param("place")String place,
                       @Param("type")String type,
                       @Param("producer")String producer,
                       @Param("singer")String singer,
                       @Param("date")String date,
                       @Param("id")String id);

    void sendPro(@Param("order_id")int order_id);
}
