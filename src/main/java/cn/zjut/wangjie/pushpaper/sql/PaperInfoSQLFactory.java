package cn.zjut.wangjie.pushpaper.sql;

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
                VALUES("has_pdf","#{hasPdf}");
                VALUES("pdf_url","#{pdfUrl}");
                VALUES("supp_pdf_url","#{suppPdfUrl}");
                VALUES("website","#{website}");
                VALUES("add_time","#{addTime}");
                VALUES("click","#{click}");
                VALUES("pdf_file","#{pdfFile}");
                VALUES("supp_pdf_file","#{suppPdfFile}");

            }
        };
        log.info("\nsql----------------------\n"+sql.toString());
        return sql.toString();
    }


}
