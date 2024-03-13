package com.ssy.POJO;

import lombok.Data;

import java.util.List;
@Data
public class VideoUpload {
    private int videoid;
    private int userid;
    private String title;
    private List<String> keywords;
    private String comment;
    private int action;
}