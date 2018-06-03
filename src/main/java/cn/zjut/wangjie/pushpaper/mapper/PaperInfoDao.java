package cn.zjut.wangjie.pushpaper.mapper;


import cn.zjut.wangjie.pushpaper.pojo.PageDTO;
import cn.zjut.wangjie.pushpaper.pojo.PaperInfo;
import cn.zjut.wangjie.pushpaper.sql.PaperInfoSQLFactory;
import org.apache.ibatis.annotations.*;

import java.util.Collection;
import java.util.List;


@Mapper
public interface PaperInfoDao {

	@InsertProvider(type = PaperInfoSQLFactory.class , method = "addPaperInfoSQL")
    @Options(keyColumn = "paper_id", keyProperty = "paperId", useGeneratedKeys = true)
    int addPaperInfo(PaperInfo paper);

	@Select(" select * from paperinfo where website=#{contentFlag} limit #{begin},#{pageSize}")
	List<PaperInfo> getPaperList(PageDTO pageDto);



    @Select("select count(*) from paperinfo where website=#{flag}")
	int countPaperByWebsite(String website);

    @Select("select * from paperinfo where paper_id = #{id}")
	PaperInfo getPaperInfoById(Integer id);

    @Select("select * from paperInfo ")
	List<PaperInfo> getAllPaper();

    @Select("<script>" +
                "select * from paperinfo where paper_id in" +
                    "<foreach item='id' index ='index' collection='paperIds' open='(' separator=',' close=')'>" +
                        "#{id}" +
                    "</foreach>" +
            "</script>")
    List<PaperInfo> getPaperByPaperIds(@Param("paperIds") Collection<Integer> paperIdList);

    @Select("select article from paperinfo")
    List<String> getAllPaperArticle();

    @Update("update paperinfo set click = click + 1 where paper_id =#{paperId}")
    int addClick(Integer paperId);

    @Select("select paper_id , score from paperinfo ")
    List<PaperInfo> getAllPaperScore();

    @Update("update paperinfo set score = #{score} where paper_id =#{paperId}")
    int updatePaperScore(@Param("paperId") Integer paperId ,@Param("score") Double score);
}
