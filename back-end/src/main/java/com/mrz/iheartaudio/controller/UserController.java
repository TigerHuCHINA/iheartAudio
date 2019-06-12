package com.mrz.iheartaudio.controller;

import com.mrz.iheartaudio.pojo.Order;
import com.mrz.iheartaudio.pojo.Product;
import com.mrz.iheartaudio.pojo.User;
import com.mrz.iheartaudio.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping("/login")
    public Map<String,Object> login(@Param("user_name")String user_name, @Param("password")String password){
        String msg = "";
        Map<String,Object> map = new HashMap<>();
        User currentUser = userService.getUserByUserName(user_name);
        if(currentUser == null){
            msg = "用户不存在！";
            map.put("msg", msg);
            map.put("code", 506);
        }
        else {
            if (!password.equals(currentUser.getPassword())){
                msg = "密码错误！";
                map.put("msg", msg);
                map.put("code", 506);
            }
            else {
                msg = "登录成功！";
                map.put("msg", msg);
                map.put("res",userService.getUserByUserName(user_name));
                map.put("code", 200);
            }
        }
        return map;
    }
    @ResponseBody
    @RequestMapping("/register")
    public Map<String,Object> register(@Param("id")int id, @Param("user_name")String user_name, @Param("password")String password){
        String msg = "";
        Map<String,Object> map = new HashMap<>();
        while(userService.getUserById(id)!= null){
            id++;
        }
        if(userService.getUserByUserName(user_name)!=null){
            msg = "该用户名已注册";
            map.put("msg", msg);
            map.put("code",506);
        }
        else {
            userService.register(id,user_name,password);
            if(userService.getUserById(id)!= null){
                msg = "注册成功";
                map.put("msg", msg);
                map.put("result", userService.getUserById(id));
                map.put("code",200);
            }
            else {
                msg = "注册失败";
                map.put("msg", msg);
                map.put("code",506);
            }
        }
        return map;
    }

    @ResponseBody
    @RequestMapping("/product")
    public Map<String,Object> getProduct(@Param("id")String id){
        Map<String,Object> map = new HashMap<>();
        String msg = "";
        Product cuPro = userService.getProduct(id);
        if(cuPro != null){
            msg = "获取成功！";
            map.put("msg",msg);
            map.put("result",cuPro);
            map.put("code",200);
        }
        else {
            msg = "获取失败！";
            map.put("msg",msg);
            map.put("result",null);
            map.put("code",506);
        }
        return map;
    }

    @ResponseBody
    @RequestMapping("/submitOrder")
    public Map<String,Object> submitOrder(@Param("product_id")String product_id,
                                          @Param("quantity")String quantity,
                                          @Param("price")String price,
                                          @Param("user_name")String user_name,
                                          @Param("mysum")String mysum,
                                          @Param("mydate")String mydate,
                                          @Param("address")String address,
                                          @Param("status")String status,
                                          @Param("product_name")String product_name){
        Map<String,Object> map = new HashMap<>();
        String msg = "";  
        userService.submitOrder(product_id,quantity,price,user_name,mysum,mydate,address,status,product_name);
        msg = "提交成功！";
        map.put("msg",msg);
        map.put("code",200);
        return map;
    }

    @ResponseBody
    @RequestMapping("/getOrderByUser")
    public Map<String,Object> getOrderByUser(@Param("user_name")String user_name){
        String msg = "";
        Map<String,Object> map = new HashMap<>();
        List<Order> list = userService.getOrderByUserName(user_name);
        if(list == null || list.size() == 0){
            msg = "记录为空！";
            map.put("msg",msg);
            map.put("code",506);
        }
        else {
            msg = "获取成功！";
            map.put("result",list);
            map.put("code",200);
        }
        return map;
    }


    @ResponseBody
    @RequestMapping("/getAllOrder")
    public Map<String,Object> getAllOrder(){
        String msg = "";
        Map<String,Object> map = new HashMap<>();
        List<Order> list = userService.getAllOrder();
        if(list == null || list.size() == 0){
            msg = "记录为空！";
            map.put("msg",msg);
            map.put("code",506);
        }
        else {
            msg = "获取成功！";
            map.put("result",list);
            map.put("code",200);
        }
        return map;
    }


    @ResponseBody
    @RequestMapping("/modifyUserInfo")
    public Map<String,Object> modifyUserInfo(@Param("name")String name,
                                             @Param("gender")String gender,
                                             @Param("city")String city,
                                             @Param("address")String address,
                                             @Param("mail_num")String mail_num,
                                             @Param("phone")String phone,
                                             @Param("e_mail")String e_mail,
                                             @Param("user_name")String user_name){
        Map<String,Object> map = new HashMap<>();
        String msg = "";
        userService.modifyUserInfo(name,gender,city,address,mail_num,phone,e_mail,user_name);
        String cuAddress = userService.getUserByUserName(user_name).getAddress();
        if(cuAddress.equals(address)){
            msg = "修改成功!";
            map.put("msg",msg);
            map.put("code", 200);
        } else {
            msg = "修改失败!";
            map.put("msg",msg);
            map.put("result",cuAddress);
            map.put("code", 506);
        }
        return map;
    }

    @ResponseBody
    @RequestMapping("/getUserByUserName")
    public Map<String,Object> getUserByUserName(@Param("user_name")String user_name){
        String msg = "";
        Map<String,Object> map = new HashMap<>();
        User curUser = userService.getUserByUserName(user_name);
        if(curUser != null){
            msg = "获取成功！";
            map.put("msg",msg);
            map.put("result",curUser);
            map.put("code", 200);
        } else {
            msg = "获取失败!";
            map.put("msg",msg);
            map.put("result",null);
            map.put("code", 506);
        }
        return map;
    }

    @ResponseBody
    @RequestMapping("/modifyOrder")
    public Map<String,Object> modifyOrder(@Param("address")String address,
                                          @Param("order_id")int order_id){
        String msg = "";
        Map<String,Object> map = new HashMap<>();
        userService.modifyOrder(address,order_id);
        String cuOrder = userService.getOrderById(order_id).getAddress();
        if(cuOrder.equals(address)){
            msg = "修改成功!";
            map.put("msg",msg);
            map.put("code", 200);
        } else {
            msg = "修改失败!";
            map.put("msg",msg);
            map.put("result",cuOrder);
            map.put("code", 506);
        }
        return map;
    }

    @ResponseBody
    @RequestMapping("/modifyProduct")
    public Map<String,Object> modifyProduct(@Param("name")String name,
                                            @Param("price")String price,
                                            @Param("place")String place,
                                            @Param("type")String type,
                                            @Param("producer")String producer,
                                            @Param("singer")String singer,
                                            @Param("date")String date,
                                            @Param("id")String id){
        String msg = "";
        Map<String,Object> map = new HashMap<>();
        userService.modifyProduct(name,price,place,type,producer,singer,date,id);
        msg = "修改成功！";
        map.put("msg",msg);
        map.put("code", 200);
        return map;
    }

    @ResponseBody
    @RequestMapping("/sendPro")
    public Map<String,Object> sendPro(@Param("order_id")int order_id){
        String msg = "";
        Map<String,Object> map = new HashMap<>();
        userService.sendPro(order_id);
        msg = "修改成功！";
        map.put("msg",msg);
        map.put("code", 200);
        return map;
    }

    @ResponseBody
    @RequestMapping("/getAllUser")
    public Map<String,Object> getAllUser(){
        String msg = "";
        Map<String,Object> map = new HashMap<>();
        map.put("code",200);
        List<User> list = userService.getAllUser();
        if(list == null || list.size() == 0){
            map.put("code",506);
        }
        map.put("user",list);
        return map;
    }

    @ResponseBody
    @RequestMapping("/register/{id}")
    public Map<String ,Object> login(@PathVariable() String id){
        Map<String, Object> map = new HashMap<>(16);
        String code = "404";
        String msg = "您获得了后台接口的数据";
        List<Object> list = Arrays.asList(new String[]{"1", "2", "3", "4"});

        map.put("code", code);
        map.put("msg", msg);
        map.put("id", id);
        map.put("result", list);
        return map;
        }
    }

