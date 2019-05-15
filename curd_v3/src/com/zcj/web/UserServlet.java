package com.zcj.web;

import com.zcj.domain.User;
import com.zcj.service.UserService;
import com.zcj.utils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = "/user", name = "UserServlet")
public class UserServlet extends HttpServlet {
    private UserService userService = new UserService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String action = request.getParameter("action");
            //2、确定调用的方法实现功能
            //使用反射调用方法
            Class<? extends UserServlet> clazz = this.getClass();
            Method method = clazz.getMethod(action, HttpServletRequest.class, HttpServletResponse.class);
            method.invoke(this, request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void queryAll(HttpServletRequest request, HttpServletResponse response) {
        //1、接收参数
        //2、封装实体
        //3、完成功能
        List<User> userList = userService.queryAll();
//        userList.stream().forEach((user1) -> {
//            System.out.println(user1);
//        });
        //4、处理结果
        request.setAttribute("userList", userList);
        try {
            request.getRequestDispatcher("/list.jsp").forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void edit(HttpServletRequest request, HttpServletResponse response) {
        //1、接收参数
        String id = request.getParameter("id");
        //2、封装实体
        //3、完成功能
        User user = userService.findByid(id);
        request.setAttribute("user", user);
        try {
            request.getRequestDispatcher("/update.jsp").forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //4、处理结果
    }

    public void update(HttpServletRequest request, HttpServletResponse response) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        User user = BeanUtils.populate(parameterMap, User.class);
        boolean success = userService.updateUser(user);
        //重定向
        try {
            response.sendRedirect(request.getContextPath() + "/user?action=queryAll");
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void add(HttpServletRequest request, HttpServletResponse response) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        User user = BeanUtils.populate(parameterMap, User.class);
        boolean success = userService.add(user);
        try {
            request.getRequestDispatcher("user?action=queryAll").forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void delete(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        boolean success = userService.delete(id);
        try {
            response.sendRedirect(request.getContextPath()+"/user?action=queryAll");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
