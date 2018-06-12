package cn.zjut.wangjie.pushpaper;

import cn.zjut.wangjie.pushpaper.service.PaperService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * @program: pushpaper
 * @description:
 * @author: WangJie
 * @create: 2018-05-24 18:00
 **/
@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest
@Slf4j
public class PaperPushTest {
    @Autowired
    private PaperService paperService;

    @Test
    public void testPushNewPaper() {
        paperService.pushNewPaperToAllUser();
    }


}
