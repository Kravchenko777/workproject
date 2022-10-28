package com.max.controllers;

import com.max.exceptions.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@EnableResponseWrapper(wrapperClass = Wrapper.class)
public class TestController {

    @GetMapping("/api/test1")
    public boolean getTest1() throws MyExeption1 {
        throw new MyExeption1("ошибочка");
       // return true;
    }

    @GetMapping("/api/test2")
    public boolean getTest2() throws MyExeption2{
        throw new MyExeption2("ошибочка");
        // return true;
    }


    @GetMapping("/api/test3")
    public HashMap<String,String> getTest3(){
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("1","123123");
        hashMap.put("2","2222222");
        hashMap.put("3","4444444");
        return hashMap;
    }
}
