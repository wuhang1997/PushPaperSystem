package cn.zjut.wangjie.paperPushSystem.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;



import cn.zjut.wangjie.paperPushSystem.pojo.PageDTO;
import cn.zjut.wangjie.paperPushSystem.service.PaperService;

@Controller
@RequestMapping("/paperController")
@PropertySource(value = { "classpath:base.properties"})
public class PaperController {
	@Autowired
	private PaperService paperService;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Value("${pageSize}")
	private int pageSize;
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
	public String paperPage(@PathVariable(value = "website") String website,@RequestParam(value="currentPage") Integer currentPage ) {
		
		PageDTO pageDTO =(PageDTO) request.getSession().getAttribute("pageDTO");
		if(pageDTO!=null) {
			pageDTO.setCurrentPage(currentPage);
			pageDTO.calculatBegin();
			pageDTO.setContentList(paperService.getPaperList(pageDTO));
			request.getSession().setAttribute("pageDTO", pageDTO);
		}
		return "paperListShow";
	}
	@RequestMapping("/{paperId}/paperInfoShow.action")
	public String showPdf(@PathVariable(value="paperId") Integer paperId) {
		
		return null;
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
             
             InputStream inputStream = new FileInputStream(new File(file));

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
