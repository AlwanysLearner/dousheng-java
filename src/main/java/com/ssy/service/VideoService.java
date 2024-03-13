package com.ssy.service;

import com.ssy.Entity.Video;
import com.ssy.Mapper.UserMapper;
import com.ssy.Mapper.VideoMapper;
import com.ssy.util.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class VideoService {
    @Autowired
    private VideoMapper videoMapper;
    @Autowired
    private RedisService redisService;

    @Transactional
    public int upload(int userid, String title, List<String> keywords){
        Video video=new Video();
        video.setAuthor(userid);
        video.setTitle(title);
        videoMapper.UploadVideo(video);
        int videoid=video.getId();
        return videoMapper.Keywordinsert(videoid,keywords);
    }
    public void GetVideo(int userid){
        List<String> lokens=redisService.range(userid+":likes",0,4);
        if(redisService.keyExists(userid+":newLikes")){
            Set<String> nlokens=redisService.getTopElements(userid+":newLikes",4);
            lokens=new ArrayList<>(nlokens);
            redisService.recreateList(userid+":likes",lokens);
            redisService.deleteKey(userid+":newLikes");
        }
        List<Integer> video_list= videoMapper.getVideo(lokens);
        for(int i=0;i<video_list.size();++i){
            redisService.insertElementAtHead(String.valueOf(userid),String.valueOf(video_list.get(i)));
        }
    }

    public Video GetVideoById(int index,int userid){
        int videoid= redisService.getElementByIndex(String.valueOf(userid),index);
        if(videoid ==-1){
            GetVideo(userid);
        }
        videoid= redisService.getElementByIndex(String.valueOf(userid),index);
        Video video=videoMapper.getVideoById(videoid);
        List<String> lokens=videoMapper.videoLoken(videoid);
        for(int i=0;i<lokens.size();++i){
            redisService.incrementScore(userid+":newLikes",lokens.get(i));
        }

        if(userid>0){
            video.setIsfavourites(videoMapper.isFavourites(userid,videoid)==1);
        }
        return videoMapper.getVideoById(videoid);
    }

    public int favourites(int userid,int videoid,int action){
        if(action==1){//点赞
            return videoMapper.dianzan(userid,videoid);
        }else{
            return videoMapper.quxiaodianzan(userid,videoid);
        }
    }

    public List<String> CommentList(int videoid){
        return videoMapper.comment(videoid);
    }

    public int makeComment(int videoid,int userid,String comment){
        return videoMapper.makeComment(videoid,userid,comment);
    }
}
