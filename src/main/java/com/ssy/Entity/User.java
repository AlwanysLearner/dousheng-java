package com.ssy.Entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
public class User {
    private int id;
    private String username;
    private String password;
    private String likes;
    private Integer index;//记录当前观看下标，下标前的作为历史记录，后的作为视频列表。
}
