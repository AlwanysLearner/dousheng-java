package com.ssy.controller;

import com.ssy.Entity.User;
import com.ssy.Entity.Video;
import com.ssy.POJO.VideoUpload;
import com.ssy.util.RedisService;
import com.ssy.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequestMapping("/dousheng")
public class VideoController {
    @Autowired
    private RedisService redisService;
    @Autowired
    private VideoService videoService;

    @GetMapping("/youke")
    public int youkeLogin(){
        List<String> keywords = Arrays.asList("keyword1","keyword2","keyword3","keyword4","keyword5");
        Random rand = new Random();// 创建一个Random实例
        int userid=-1*rand.nextInt(Integer.MAX_VALUE);
        while(redisService.isMemberOfSet("游客",String.valueOf(userid))){
            userid=-1*rand.nextInt(Integer.MAX_VALUE);
        }
        redisService.addToSet("游客",String.valueOf(userid));
        for(int i=0;i<keywords.size();++i){
            redisService.insertElementAtHead(userid+":likes",keywords.get(i));
        }
        return userid;
    }
    @GetMapping("/video")
    public void GetVideo(int userid){
        videoService.GetVideo(userid);
    }

    @GetMapping("/changevideo")
    public Video ChangeVideo( int userid, int index){
        return videoService.GetVideoById(index,userid);
    }

    @PostMapping("/auth/video/favourites")
    public int dianzhan(@RequestBody VideoUpload videoUpload){//用户登录了
        return videoService.favourites(videoUpload.getUserid(),videoUpload.getVideoid(),videoUpload.getAction());
    }

    @GetMapping("/video/hot")
    public void HotVideo(int userid){
        videoService.getHotVideo(userid);
    }

    @GetMapping("/video/comment")
    public List<String> chakanComment(int videoid){
        return videoService.CommentList(videoid);
    }

    @PostMapping("/auth/video/comment")
    public int MakeComment(@RequestBody VideoUpload videoUpload){
        return videoService.makeComment(videoUpload.getVideoid(),videoUpload.getUserid(),videoUpload.getComment());
    }

    @PostMapping("/auth/upload")
    public int UploadVideo(@RequestBody VideoUpload videoUpload){
        return videoService.upload(videoUpload.getUserid(),videoUpload.getTitle(),videoUpload.getKeywords());
    }
}
