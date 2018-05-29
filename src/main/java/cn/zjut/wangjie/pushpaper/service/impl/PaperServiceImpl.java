package cn.zjut.wangjie.pushpaper.service.impl;


import cn.zjut.wangjie.pushpaper.mapper.CollectionDao;
import cn.zjut.wangjie.pushpaper.mapper.PaperInfoDao;
import cn.zjut.wangjie.pushpaper.mapper.RecommendDao;
import cn.zjut.wangjie.pushpaper.mapper.UserDao;
import cn.zjut.wangjie.pushpaper.pojo.PageDTO;
import cn.zjut.wangjie.pushpaper.pojo.PaperInfo;
import cn.zjut.wangjie.pushpaper.pojo.Recommend;
import cn.zjut.wangjie.pushpaper.pojo.User;
import cn.zjut.wangjie.pushpaper.service.PaperService;
import cn.zjut.wangjie.pushpaper.service.elasticsearch.ELPaperService;
import cn.zjut.wangjie.pushpaper.util.SendEmailUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
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
    @Autowired
    private CollectionDao collectionDao;
    @Autowired
    private RecommendDao recommendDao;
    @Autowired
    private ELPaperService elPaperService;


	@Value("${mail.title.newPaperPush}")
	private String newPaperPushTitle;
    @Value("${mail.title.recommendPaperPush}")
    private String recommendPaperPushTitle;

	@Override
	public List<PaperInfo> getPaperList(PageDTO pageDTO) {

		/*Page page = PageHelper.startPage(pageDTO.getCurrentPage(),pageDTO.getPageSize());
		List<PaperInfo> paperInfoList = paperInfoDao.*/
		return paperInfoDao.getPaperList(pageDTO);
	}

	@Override
	public List<String> getAllPaperArticle() {
		return paperInfoDao.getAllPaperArticle();
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
	public List<PaperInfo> getAllPaper() {
		return paperInfoDao.getAllPaper();
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
        pushToEmail(userList,newPaperPushTitle,emailContent);
	}

	private void pushToEmail(List<User> userList , String title , String content){
		for (User user : userList ) {
			SendEmailUtil.sendEmail(user.getEmail(),title,content);

		}
	}

    private void pushToEmail(User user , String title , String content){

            SendEmailUtil.sendEmail(user.getEmail(),title,content);
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
    private String generatePushContent(PaperInfo paperInfo){
        StringBuilder paperSB = new StringBuilder();

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

        return paperSB.toString();

    }

	@Override
	public void pushRecommendPaper() {
		List<User> userList = userDao.getAllUser();
		for (User user:userList
			 ) {
			List<String> searchContentList = redisTemplate.opsForList().range("search_record_"+user.getUid(),0,20);
			List<Integer> collectionPaperIds = collectionDao.getCollectionIdsByUid(user.getUid());
            List<Integer> recommendPaperIds = recommendDao.getRecommendedPaperIdsByUid(user.getUid());
            List<PaperInfo> recommendPaperList = elPaperService.recommend(searchContentList,collectionPaperIds,recommendPaperIds);
            if (recommendPaperList!=null){
                String recommendContent = generatePushContent(recommendPaperList.get(0));
                pushToEmail(user,recommendPaperPushTitle,recommendContent);
                Recommend recommend = new Recommend();
                recommend.setPaperId(recommendPaperList.get(0).getPaperId());
                recommend.setUserId(user.getUid());
                recommend.setAddAt(System.currentTimeMillis());
                recommendDao.addRecommend(recommend);
            }
		}
	}
}
