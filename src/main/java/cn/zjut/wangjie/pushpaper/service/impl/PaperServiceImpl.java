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

import java.util.Collection;
import java.util.List;
import java.util.Set;

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

		return paperInfoDao.getPaperList(pageDTO);
	}

	@Override
	public List<String> getAllPaperArticle() {
		return paperInfoDao.getAllPaperArticle();
	}

	@Override
	public int countPaper(PageDTO pageDTO) {
		
		return paperInfoDao.countPaper(pageDTO);
	}

	@Override
	public PaperInfo getPaperInfoById(Integer id) {
		return paperInfoDao.getPaperInfoById(id);
	}

	@Override
	public PaperInfo getpaperInfoByPaperUrl(String paperUrl) {
		return paperInfoDao.getPaperInfoByPaperUrl(paperUrl);
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

        List<PaperInfo> paperInfoList = paperInfoDao.getPaperByPaperIds(newPaperToPushList);
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

	@Override
	public int addClick(Integer paperId) {
		long time = (long)redisTemplate.opsForValue().get("rank"+paperId);
		double score = (double)redisTemplate.opsForZSet().score("paperRank", paperId);
		long nowTime = System.currentTimeMillis();
		double newScore = 1+(score+0.0001)*Math.exp(-0.1*(Math.abs((nowTime-time)/1000)));
		redisTemplate.opsForZSet().add("paperRank", paperId,newScore);
		redisTemplate.opsForValue().set("rank"+paperId,nowTime);


		return paperInfoDao.addClick(paperId);
	}

	@Override
	public List<PaperInfo> getAllPaperScore() {
		return paperInfoDao.getAllPaperScore();
	}

	@Override
	public List<PaperInfo> getPaperByPaperIds(Collection paperIds) {
		return paperInfoDao.getPaperByPaperIds(paperIds);
	}

    @Override
    public int updatePaperScore(Integer paperId, Double score) {
        return paperInfoDao.updatePaperScore(paperId,score);
    }

	@Override
	public List<PaperInfo> listUnCompletePaper() {

		return paperInfoDao.listUnCompletePaper();
	}

	@Override
	public boolean isPaperExist(String paperUrl) {
		return paperInfoDao.isPaperExist(paperUrl);
	}

	@Override
	public int updatePaperInfo(PaperInfo paperInfo) {
		return paperInfoDao.updatePaperInfo(paperInfo);
	}
}
