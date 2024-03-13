package com.ssy.Entity;
import lombok.Data;

import java.util.List;

@Data
public class Video {
    private Integer id;
    private String title;
    private int author;
    private boolean isfavourites;
    private List<String> lokens;
    private String keywords;
}
