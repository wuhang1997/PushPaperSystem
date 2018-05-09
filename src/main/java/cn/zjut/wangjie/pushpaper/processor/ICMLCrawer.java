package cn.zjut.wangjie.pushpaper.processor;


import cn.zjut.wangjie.pushpaper.mapper.PaperInfoDao;
import cn.zjut.wangjie.pushpaper.pojo.PaperInfo;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import javax.annotation.Resource;
import java.util.List;

@Component
public class ICMLCrawer implements PageProcessor{
	

	@Resource private PaperInfoDao paperInfoDao;
	private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);
	public void process(Page page) {
		// TODO Auto-generated method stub
	
		List<String> urls = page.getHtml().xpath("//a[@class='btn btn-default btn-xs href_PDF']").links().all();
		
		if (urls != null) {
			for (String strUrl : urls) {
				page.addTargetRequest(strUrl);
			}
		}
		if (!page.getUrl().regex("http://proceedings\\.mlr\\.press*html").match()) {
			PaperInfo paperInfo = new PaperInfo();
			paperInfo.setArticle(page.getHtml().xpath("//article[@class='post-content']/h1/text()").toString());
			paperInfo.setPaperUrl(page.getUrl().toString());
			paperInfo.setAuthors(page.getHtml().xpath("//div[@class='authors']/text()").toString());
			paperInfo.setPaperAbstract(page.getHtml().xpath("//div[@class='abstract']/text()").toString());
			
			/*page.putField("url", page.getUrl().toString());
			page.putField("article", page.getHtml().xpath("//article[@class='post-content']/h1/text()").toString());
			page.putField("authors", page.getHtml().xpath("//div[@class='authors']/text()").toString());
			page.putField("abstract", page.getHtml().xpath("//div[@class='abstract']/text()").toString());*/
			List<String> pdfUrls=page.getHtml().xpath("//div[@id='extras']").links().all();
			if(pdfUrls==null||pdfUrls.size()==0) {
				//page.putField("hasPDF", "0");
				paperInfo.setHasPDF(false);
			}else {
				paperInfo.setHasPDF(true);
				
				//page.putField("hasPdf", "1");
				paperInfo.setPdfUrl(pdfUrls.get(0));
				//page.putField("pdf", pdfUrls.get(0));
				if(pdfUrls.size()>1) {
					//page.putField("supp-pdf", pdfUrls.get(1));
					paperInfo.setSuppPDFUrl(pdfUrls.get(1));
				}
			}
			paperInfoDao.addPaperInfo(paperInfo);
					
		}

	}
	

	public Site getSite() {
		return site;
	}


}
