package cn.zjut.wangjie.pushpaper.mapper;


import cn.zjut.wangjie.pushpaper.pojo.PageDTO;
import cn.zjut.wangjie.pushpaper.pojo.PaperInfo;
import cn.zjut.wangjie.pushpaper.sql.PaperInfoSQLFactory;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

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


}
