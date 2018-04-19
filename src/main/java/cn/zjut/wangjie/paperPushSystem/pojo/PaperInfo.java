package cn.zjut.wangjie.paperPushSystem.pojo;

import java.sql.Timestamp;

public class PaperInfo {
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
	private String pdfFile;
	private String suppPDFFile;
	
	public Integer getPaperId() {
		return paperId;
	}
	public void setPaperId(Integer paperId) {
		this.paperId = paperId;
	}
	public String getPaperUrl() {
		return paperUrl;
	}
	public void setPaperUrl(String paperUrl) {
		this.paperUrl = paperUrl;
	}
	public String getArticle() {
		return article;
	}
	public void setArticle(String article) {
		this.article = article;
	}
	public String getAuthors() {
		return authors;
	}
	public void setAuthors(String authors) {
		this.authors = authors;
	}
	public String getPaperAbstract() {
		return paperAbstract;
	}
	public void setPaperAbstract(String paperAbstract) {
		this.paperAbstract = paperAbstract;
	}
	public boolean isHasPDF() {
		return hasPDF;
	}
	public void setHasPDF(boolean hasPDF) {
		this.hasPDF = hasPDF;
	}
	public String getPdfUrl() {
		return pdfUrl;
	}
	public void setPdfUrl(String pdfUrl) {
		this.pdfUrl = pdfUrl;
	}
	public String getSuppPDFUrl() {
		return suppPDFUrl;
	}
	public void setSuppPDFUrl(String suppPDFUrl) {
		this.suppPDFUrl = suppPDFUrl;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public Timestamp getAddTime() {
		return addTime;
	}
	public void setAddTime(Timestamp addTime) {
		this.addTime = addTime;
	}
	
	public Integer getClick() {
		return click;
	}
	public void setClick(Integer click) {
		this.click = click;
	}
	
	public String getPdfFile() {
		return pdfFile;
	}
	public void setPdfFile(String pdfFile) {
		this.pdfFile = pdfFile;
	}
	public String getSuppPDFFile() {
		return suppPDFFile;
	}
	public void setSuppPDFFile(String suppPDFFile) {
		this.suppPDFFile = suppPDFFile;
	}
	@Override
	public String toString() {
		return "PaperInfo [paperId=" + paperId + ", paperUrl=" + paperUrl + ", article=" + article + ", authors="
				+ authors + ", paperAbstract=" + paperAbstract + ", hasPDF=" + hasPDF + ", pdfUrl=" + pdfUrl
				+ ", suppPDFUrl=" + suppPDFUrl + ", website=" + website + ", addTime=" + addTime + ", click=" + click
				+ ", pdfFile=" + pdfFile + ", suppPDFFile=" + suppPDFFile + "]";
	}
}