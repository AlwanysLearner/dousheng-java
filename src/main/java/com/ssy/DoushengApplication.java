package com.ssy;

import com.ssy.Mapper.UserMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

@SpringBootApplication
@MapperScan("com.ssy.Mapper")
public class DoushengApplication {
    public static void main(String[] args){
        SpringApplication.run(DoushengApplication.class, args);
    }


}
