package com.mrz.iheartaudio.service.impl;

import com.mrz.iheartaudio.dao.UserDao;
import com.mrz.iheartaudio.pojo.Order;
import com.mrz.iheartaudio.pojo.Product;
import com.mrz.iheartaudio.pojo.User;
import com.mrz.iheartaudio.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User getUserById(int id) {
        return userDao.getUserById(id);
    }

    @Override
    public User getUserByUserName(String user_name) {
        return userDao.getUserByUserName(user_name);
    }

    @Override
    public List<User> getAllUser() {
        return userDao.getAllUser();
    }

    @Override
    public void register(int id, String user_name, String password) {
         userDao.register(id,user_name,password);
    }

    @Override
    public Product getProduct(String id) {
        return userDao.getProduct(id);
    }

    @Override
    public void submitOrder(String product_id, String quantity, String price, String user_name, String mysum, String mydate, String address, String status, String product_name) {
         userDao.submitOrder(product_id,quantity,price,user_name,mysum,mydate,address,status,product_name);
    }

    @Override
    public Order getOrderById(int order_id) {
        return userDao.getOrderById(order_id);
    }

    @Override
    public List<Order> getOrderByUserName(String user_name) {
        return userDao.getOrderByUserName(user_name);
    }

    @Override
    public List<Order> getAllOrder() {
        return userDao.getAllOrder();
    }

    @Override
    public void modifyUserInfo(String name, String gender, String city, String address, String mail_num, String phone, String e_mail, String user_name) {
        userDao.modifyUserInfo(name,gender,city,address,mail_num,phone,e_mail,user_name);
    }

    @Override
    public void modifyOrder(String address, int order_id) {
        userDao.modifyOrder(address,order_id);
    }

    @Override
    public void modifyProduct(String name, String price, String place, String type, String producer, String singer, String date, String id) {
        userDao.modifyProduct(name,price,place,type,producer,singer,date,id);
    }

    @Override
    public void sendPro(int order_id) {
        userDao.sendPro(order_id);
    }
}
