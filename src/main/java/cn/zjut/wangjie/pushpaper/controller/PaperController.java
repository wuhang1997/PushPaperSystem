package cn.zjut.wangjie.pushpaper.controller;


import cn.zjut.wangjie.pushpaper.pojo.*;
import cn.zjut.wangjie.pushpaper.service.*;
import cn.zjut.wangjie.pushpaper.service.elasticsearch.ELPaperService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

@Controller
@RequestMapping("/paperController")
public class PaperController {
	@Autowired
	private PaperService paperService;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private ELPaperService elPaperService;
	@Autowired
    private CollectionService collectionService;
	@Autowired
	private CommentService commentService;
	@Autowired
	private RedisTemplate redisTemplate;
	@Autowired
	private PaperBrowseHistoryService paperBrowseHistoryService;
	@Autowired
    private NoteService noteService;
	@Value("${page.pageSize}")
	private int pageSize;
	@Value("${paperPath}")
	private String paperPath;
	
	@RequestMapping("/{website}/paperShow.action")
	public String paperList(@PathVariable(value = "website") String website) {

		PageDTO pageDTO = new PageDTO();
		pageDTO.setBegin(0);
		pageDTO.setContentFlag(website);
		pageDTO.setCurrentPage(1);
		pageDTO.setPageSize(pageSize);
		int total =paperService.countPaperByWebsite(website);
		pageDTO.calculatTotalPage(total);
		pageDTO.setContentList(paperService.getPaperList(pageDTO));
		request.getSession().setAttribute("pageDTO", pageDTO);
		return "paperListShow";
	}
	@RequestMapping("/{website}/paperPage.action")
	public String paperPage(@PathVariable(value = "website") String website, @RequestParam(value="currentPage") Integer currentPage ) {
		
		PageDTO pageDTO =(PageDTO) request.getSession().getAttribute("pageDTO");
		if(pageDTO==null) {
			pageDTO = new PageDTO();
			pageDTO.setPageSize(pageSize);
		}

		pageDTO.setCurrentPage(currentPage);
		pageDTO.calculatBegin();
		if ("elasticsearch".equals(pageDTO.getContentFlag())){
			pageDTO = elPaperService.searchPaperInfoListByPage(pageDTO,pageDTO.getSearch());
		}else{
			pageDTO.setContentFlag(website);
			pageDTO.setContentList(paperService.getPaperList(pageDTO));
		}
		request.getSession().setAttribute("pageDTO", pageDTO);
		return "paperListShow";
	}
	@PostMapping("search-keywords")
	public String paperSearch(String search ){
		PageDTO pageDTO = new PageDTO();
		pageDTO.setBegin(0);
		pageDTO.setContentFlag("elasticsearch");
		pageDTO.setSearch(search);
		pageDTO.setCurrentPage(1);
		pageDTO.setPageSize(pageSize);
		pageDTO = elPaperService.searchPaperInfoListByPage(pageDTO,search);
		request.getSession().setAttribute("pageDTO", pageDTO);
		User user = (User)request.getSession().getAttribute("user");
		if (user != null){
			redisTemplate.opsForList().leftPush("search_record_"+user.getUid(),search);
		}

		return "paperListShow";
	}
	@RequestMapping("showPaperInfo")
	public String showPaperInfo(@RequestParam(value="id") Integer paperId) {
		PaperInfo paperInfo = paperService.getPaperInfoById(paperId);
		request.getSession().setAttribute("paper", paperInfo);
		User user = (User)request.getSession().getAttribute("user");
		if (user != null) {
            Collection collection = new Collection();
            collection.setPaperId(paperId);
            collection.setUserId(user.getUid());
            boolean isCollected = collectionService.isCollection(collection);
            request.setAttribute("isCollected", isCollected);
            PaperBrowseHistory paperBrowseHistory = new PaperBrowseHistory();
            paperBrowseHistory.setAddAt(System.currentTimeMillis());
            paperBrowseHistory.setUserId(user.getUid());
            paperBrowseHistory.setPaperId(paperId);
            paperBrowseHistoryService.add(paperBrowseHistory);
            //获取评论
            List<Comment> comments = commentService.listCommentByPaperId(paperId);
            request.setAttribute("commentList", comments);
            //获取笔记
            List<Note> noteList = noteService.listNoteByPaperIdAndUserId(paperId, user.getUid());
            request.setAttribute("noteList", noteList);
        }
        //点击量加一
		paperService.addClick(paperId);

		return "paperInfoShow";
	}
	@RequestMapping(value = "/{file}/download.action")
    public ResponseEntity<byte[]> downloadFile(@PathVariable(value = "file") String file){
    	 String[] str = file.split("/");
    	 String fileName = str[str.length-1];
    	 response.setCharacterEncoding("utf-8");
         response.setContentType("multipart/form-data");
         response.setHeader("Content-Disposition", "attachment;fileName="
                 + fileName);
         try {
             
             InputStream inputStream = new FileInputStream(new File(paperPath+file));

             OutputStream os = response.getOutputStream();
             byte[] b = new byte[2048];
             int length;
             while ((length = inputStream.read(b)) > 0) {
                 os.write(b, 0, length);
             }

             // 这里主要关闭。
             os.close();

             inputStream.close();
         } catch (FileNotFoundException e) {
             e.printStackTrace();
         } catch (IOException e) {
             e.printStackTrace();
         }
         //  返回值要注意，要不然就出现下面这句错误！
         //java+getOutputStream() has already been called for this response
         return null; 
    }  

	
	
}
