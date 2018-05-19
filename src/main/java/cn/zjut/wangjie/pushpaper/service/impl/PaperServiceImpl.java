package cn.zjut.wangjie.pushpaper.service.impl;


import cn.zjut.wangjie.pushpaper.mapper.PaperInfoDao;
import cn.zjut.wangjie.pushpaper.mapper.UserDao;
import cn.zjut.wangjie.pushpaper.pojo.PageDTO;
import cn.zjut.wangjie.pushpaper.pojo.PaperInfo;
import cn.zjut.wangjie.pushpaper.pojo.User;
import cn.zjut.wangjie.pushpaper.service.PaperService;
import cn.zjut.wangjie.pushpaper.util.SendEmailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaperServiceImpl implements PaperService {
    @Autowired
	private PaperInfoDao paperInfoDao;
    @Autowired
	private RedisTemplate redisTemplate;
    @Autowired
	private UserDao userDao;
	@Value("${mail.from}")
	private String from;
	@Value("${mail.authorizationCode}")
	private String authorizationCode;
	@Value("${mail.host}")
	private String host;
	@Value("${mail.title.newPaperPush}")
	private String newPaperPushTitle;

	@Override
	public List<PaperInfo> getPaperList(PageDTO pageDTO) {
		
		return paperInfoDao.getPaperList(pageDTO);
	}
	@Override
	public int countPaperByWebsite(String website) {
		
		return paperInfoDao.countPaperByWebsite(website);
	}

	@Override
	public PaperInfo getPaperInfoById(Integer id) {
		return paperInfoDao.getPaperInfoById(id);
	}



	@Override
	public void pushNewPaperToAllUser() {
        List<Integer> newPaperToPushList = redisTemplate.opsForList().range("newPaperToPush",0,-1);
        if (newPaperToPushList == null){
            return;
        }
        List<PaperInfo> paperInfoList = paperInfoDao.getpushPaper(newPaperToPushList);
        String emailContent = generatePushContent(paperInfoList);
        List<User> userList = userDao.getAllUser();
	}

	private void pushToEmail(List<User> userList , String newPaperPushTitle , String content){
		for (User user : userList ) {
			SendEmailUtil.sendEmail(user.getEmail(),newPaperPushTitle,content);

		}
	}
	private String generatePushContent(List<PaperInfo> paperInfoList){
		StringBuilder paperSB = new StringBuilder();
		for (PaperInfo paperInfo:paperInfoList
			 ) {
			paperSB.append("Article:");
			paperSB.append("\n    ");
			paperSB.append(paperInfo.getArticle());
			paperSB.append("\n");
			paperSB.append("Authors:");
			paperSB.append("\n    ");
			paperSB.append(paperInfo.getAuthors());
			paperSB.append("\n");
			paperSB.append("From:");
			paperSB.append("\n    ");
			paperSB.append(paperInfo.getWebsite());
			paperSB.append("\n");
			paperSB.append("点击前往：");
			paperSB.append("\n");
			paperSB.append("http://localhost:8090/paperController/showPaperInfo?id="+paperInfo.getPaperId());
			paperSB.append("\n");
			paperSB.append("\n");
		}
		return paperSB.toString();

	}

	@Override
	public void pushRecommendPaper() {
		List<User> userList = userDao.getAllUser();
		for (User user:userList
			 ) {
			List<String> searchContentList = redisTemplate.opsForList().range("search_record_"+user.getUid(),0,20);
			
		}
	}
}
