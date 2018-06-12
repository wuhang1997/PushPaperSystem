package cn.zjut.wangjie.pushpaper.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@Document(indexName = "index_paper_push", type = "paperInfo")
public class PaperInfo implements Serializable {
	@Id
	private Integer paperId;
	private String paperUrl;
	private String article;
	private String authors;
	private String paperAbstract;
	private Boolean hasPDF;
	private String pdfUrl;
	private String suppPDFUrl;
	private String website;
	private Long addTime;
	private Integer click;
	private Double score;
	private String pdfFile;
	private String suppPDFFile;
	private Integer year;
	private Integer complete;

	public void checkComplete(){
		if (article!= null && authors != null && paperAbstract!=null && pdfUrl!=null){
			complete = 1;
		}else {
			complete = 0;
		}
	}

}