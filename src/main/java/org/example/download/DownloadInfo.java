package org.example.download;

import com.google.common.base.Strings;

/**
 * @author SuSu
 * @Email 381963558@qq.com
 * Created by SuSu on 2023/6/21.
 */
public class DownloadInfo {
    private String name;
    private String mp3Url;

    public DownloadInfo() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMp3Url() {
        return mp3Url;
    }

    public void setMp3Url(String mp3Url) {
        this.mp3Url = mp3Url;
    }

    public boolean isFull(){
        return !Strings.isNullOrEmpty(name) && !Strings.isNullOrEmpty(mp3Url);
    }
}
