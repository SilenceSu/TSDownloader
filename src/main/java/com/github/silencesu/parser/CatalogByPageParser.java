package com.github.silencesu.parser;

import com.github.silencesu.task.DownloadTask;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 目录解析
 */
public class CatalogByPageParser {
    private static final Logger logger = LoggerFactory.getLogger(CatalogByPageParser.class);
    private WebDriver webDriver;


    public CatalogByPageParser(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public Map<Integer, String> parser() {
        //生成目录树
        return  generateCatalog();
    }




    private Map<Integer, String> generateCatalog() {

        Map<Integer, String> pageCatalog = new HashMap<>();


        List<WebElement> elementTagLi = parerTagLI();
        for (WebElement webElement : elementTagLi) {
            WebElement ElementByTagA = parserTagA(webElement);

            String href = ElementByTagA.getAttribute("href");
            String title = ElementByTagA.getAttribute("title");

            int firstIndex = title.indexOf("第")+1;

            String chapterId = title.substring(firstIndex, firstIndex + 4);

            try {
                pageCatalog.put(Integer.parseInt(chapterId), href);
            } catch (NumberFormatException e) {
                logger.error("解析错误:title:{},chapterId{}", title, chapterId);
            }
        }

        return pageCatalog;
    }

    private WebElement parserTagA(WebElement element) {
        return element.findElement(By.tagName("a"));
    }

    private List<WebElement> parerTagLI() {

        return webDriver.findElement(By.className("playlist")).findElement(By.tagName("ul")).findElements(By.tagName("Li"));

    }
}
