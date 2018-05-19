package cn.zjut.wangjie.pushpaper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PushPaperApplication {
    static {
        System.setProperty("es.set.netty.runtime.available.processors", "false");
    }
    public static void main(String[] args) {
       // System.setProperty("es.set.netty.runtime.available.processors","false");
        SpringApplication.run(PushPaperApplication.class, args);
    }
}
