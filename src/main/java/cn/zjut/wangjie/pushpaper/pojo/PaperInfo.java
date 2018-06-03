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
	private boolean hasPDF; 
	private String pdfUrl;
	private String suppPDFUrl;
	private String website;
	private Timestamp addTime;
	private Integer click;
	private Double score;
	private String pdfFile;
	private String suppPDFFile;

}