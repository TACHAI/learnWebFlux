package com.laishihsui.servlet;

import javax.servlet.AsyncContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * Create by tachai on 2019-07-31 11:39
 * gitHub https://github.com/TACHAI
 * Email tc1206966083@gmail.com
 */
@WebServlet(value = "/async",asyncSupported = true)
public class AsyncServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        long startTime = System.currentTimeMillis();

        // 获取异步上下文，开启异步操作(完成异步线程间的通讯)
        AsyncContext asyncContext = request.startAsync();
        // 获取NIO的异步请求与响应

        ServletRequest asyncContextRequest = asyncContext.getRequest();
        ServletResponse asyncContextResponse = asyncContext.getResponse();
        // 异步执行耗时操作
        CompletableFuture.runAsync(()->doSome(asyncContextRequest,asyncContextResponse));


        //doSome(request,response);
        long endTime = System.currentTimeMillis();
        System.out.println("同步操作时web服务器耗时："+(endTime-startTime));
    }


    //定义一个耗时操作
    private void  doSome(ServletRequest request,ServletResponse response)   {
        try{
            TimeUnit.SECONDS.sleep(5);
            response.getWriter().println("Done!");

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
