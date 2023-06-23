package com.github.silencesu.bean;

import com.google.common.base.Strings;
import lombok.Getter;
import lombok.Setter;

/**
 * 章节信息
 */
@Getter
@Setter
public class ChapterInfo {
    private String name;
    private String mp3Url;

    public boolean isFull(){
        return !Strings.isNullOrEmpty(name) && !Strings.isNullOrEmpty(mp3Url);
    }
}
