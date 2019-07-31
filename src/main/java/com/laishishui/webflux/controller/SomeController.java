package com.laishishui.webflux.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Create by tachai on 2019-07-30 17:39
 * gitHub https://github.com/TACHAI
 * Email tc1206966083@gmail.com
 */
@RestController
public class SomeController {

    @GetMapping("/common")
    public String commonHandler(){
        String result = doSome("common handler");
        return "common handler";
    }

    private String doSome(String msg){
        try {
            TimeUnit.SECONDS.sleep(5);
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        return msg;
    }

    @RequestMapping("/list")
    public Flux<String> fluxHandle(@RequestParam List<String> cities){
        // 将list转为Strem,再将Stream转为Flux
        return Flux.fromStream(cities.stream());
    }



    @RequestMapping("/time")
    public Flux<String> timeHandle(@RequestParam List<String> cities){
        // 将Flux的每个元素映射为一个doSome()耗时操作
        return Flux.fromStream(
                cities.stream().map(i->doSome("elem-"+i))
        );
    }


    // 火狐不支持
    @RequestMapping(value = "/sse",produces = "text/event-stream")
    public Flux<String> sseHandle(){
        // Flux表示包含0或n个元素的异步序列
        return Flux.just("beijing","shanghai","guangzhou");
    }

    @GetMapping("/mono")
    public Mono<String> monoHandler(){
        Mono.fromSupplier(()->doSome("mono handler"));
        // Mono表示包含0或1个元素的异步序列
        return Mono.just("mono handler");
    }
}
