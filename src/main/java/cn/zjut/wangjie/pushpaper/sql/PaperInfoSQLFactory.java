package cn.zjut.wangjie.pushpaper.sql;

import cn.zjut.wangjie.pushpaper.pojo.PageDTO;
import cn.zjut.wangjie.pushpaper.pojo.PaperInfo;
import lombok.extern.java.Log;
import org.apache.ibatis.jdbc.SQL;

/**
 * @program: PushPaper
 * @description:
 * @author: WangJie
 * @create: 2018-05-03 16:45
 **/
@Log
public class PaperInfoSQLFactory {

    public String addPaperInfoSQL(PaperInfo paperInfo){
        SQL sql = new SQL(){
            {
                INSERT_INTO("paperinfo");
                VALUES("paper_url","#{paperUrl}");
                VALUES("article","#{article}");
                VALUES("paper_abstract","#{paperAbstract}");
                VALUES("authors","#{authors}");
                VALUES("has_pdf","#{hasPDF}");
                VALUES("pdf_url","#{pdfUrl}");
                VALUES("supp_pdf_url","#{suppPDFUrl}");
                VALUES("website","#{website}");
                VALUES("add_time","#{addTime}");
                VALUES("click","#{click}");
                VALUES("pdf_file","#{pdfFile}");
                VALUES("supp_pdf_file","#{suppPDFFile}");
                VALUES("complete","#{complete}");
                VALUES("year","#{year}");
                VALUES("score","#{score}");

            }
        };
        log.info("\nsql----------------------\n"+sql.toString());
        return sql.toString();
    }
    public String countPaper(PageDTO pageDTO){
        SQL sql = new SQL(){
            {
                SELECT("count(*)");
                FROM("paperinfo");
                if (pageDTO.getContentFlag()!=null){
                    WHERE("website = '" + pageDTO.getContentFlag()+"'");
                }
                if (pageDTO.getYear()!=null){
                    WHERE("year="+pageDTO.getYear());
                }
            }
        };
        return sql.toString();
    }

    public String updatePaperInfo(PaperInfo paperInfo){
        SQL sql = new SQL(){
            {

                UPDATE("paperinfo");
                if (paperInfo.getArticle()!=null){
                    SET("article=#{article}");
                }
                if (paperInfo.getAuthors() != null){
                    SET("authors=#{authors}");
                }
                if (paperInfo.getPaperAbstract()!=null){
                    SET("paper_abstract=#{paperAbstract}");
                }
                if (paperInfo.getHasPDF()!=null){
                    SET("has_pdf=#{hasPDF}");
                }
                if (paperInfo.getPdfUrl()!=null){
                    SET("pdf_url=#{PdfUrl}");
                }
                if (paperInfo.getSuppPDFUrl()!=null){
                    SET("supp_pdf_url=#{suppPDFUrl}");
                }
                SET("complete=#{complete}");

                WHERE("paper_url='"+paperInfo.getPaperUrl()+"'");
            }
        };
        return sql.toString();
    }


}
