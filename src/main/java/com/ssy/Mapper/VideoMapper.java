package com.ssy.Mapper;

import com.ssy.Entity.Video;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface VideoMapper {
    @Insert("insert into t_video(title,author) values(#{title},#{author}) ")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int UploadVideo(Video video);
    @Insert({
            "<script>",
            "INSERT INTO t_keyword (videoid, keyword) VALUES ",
            "<foreach collection='keywords' item='keyword' index='index' separator=','>",
            "(#{videoid}, #{keyword})",
            "</foreach>",
            "</script>"
    })
    int Keywordinsert(int videoid,List<String> keywords);

    @Select({
            "<script>",
            "SELECT DISTINCT videoid FROM t_keyword WHERE keyword IN ",
            "<foreach collection='keywords' item='keyword' open='(' separator=',' close=')'>",
            "#{keyword}",
            "</foreach>",
            "LIMIT 20",
            "</script>"
    })
    List<Integer> getVideo(List<String> keywords);

    @Select("select title,author from t_video where id=#{id}")
    Video getVideoById(int id);
    @Select("select count(*) from t_favourites where userid=#{userid} and videoid=#{videoid}")
    int isFavourites(int userid,int videoid);
    @Delete("delete from t_favourites where userid=#{userid} and videoid=#{videoid}")
    int quxiaodianzan(int userid ,int videoid);
    @Insert("insert into t_favourites(userid,videoid) values (#{userid},#{videoid})")
    int dianzan(int userid,int videoid);
    @Select("select comment from t_comment where videoid=#{videoid}")
    List<String> comment(int videoid);

    @Insert("insert into t_comment(video,comment,commentid) values (#{videoid},#{comment},#{userid})")
    int makeComment(int videoid,int userid,String comment);
    @Select("select keyword from t_keyword where videoid=#{videoid}")
    List<String> videoLoken(int videoid);
}
