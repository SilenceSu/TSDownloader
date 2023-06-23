package com.github.silencesu.parser;

import com.github.silencesu.bean.ChapterInfo;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * 章节内容解析器
 */
public class ChapterParser {

    private WebDriver webDriver;

    public ChapterParser(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public ChapterInfo get() {

        ChapterInfo chapterInfo = new ChapterInfo();

        chapterInfo.setName(findNameByEle());

        chapterInfo.setMp3Url(findUrlByEle());
        return chapterInfo;
    }


    private String findNameByEle() {
        WebElement element = new WebDriverWait(webDriver, Duration.ofSeconds(60))
                .until(driver -> driver.findElement(By.className("p-h")));

        return element.getText().replace("正在播放：", "");
    }

    private String findUrlByEle() {

        //等待
        WebElement element = new WebDriverWait(webDriver, Duration.ofSeconds(60))
                .until(driver -> driver.findElement(By.id("jp_audio_0")));


        String mp4url = element.getAttribute("src");

        return mp4url;
    }

}
