package cn.zjut.wangjie.pushpaper.scheduledTasks;

import cn.zjut.wangjie.pushpaper.service.PaperService;
import cn.zjut.wangjie.pushpaper.util.SendEmailUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @program: bonus-point-cloud
 * @description: 定时检测过期积分
 * @author: WangJie
 * @create: 2018-05-10 09:54
 **/
@Component
@Slf4j
public class EmailTask {


    @Value("${mail.from}")
    private String from;
    @Value("${mail.authorizationCode}")
    private String authorizationCode;
    @Value("${mail.host}")
    private String host;
    @Value("${mail.title}")
    private String title;
    @Autowired
    private PaperService paperService;

    /**
     * m每天0点0分0秒处理过期积分
     * @author wangjie
     */
    @Scheduled(cron = "0 39 16 * * ?")
    public void sendPaperInfoEmail(){

        SendEmailUtil.sendEmail("17826873177@163.com",title,paperService.turnPaperInfoToString(10));

    }



}
