package com.github.silencesu.help;

import lombok.Getter;
import com.github.silencesu.driver.ChromeDriverFactory;
import com.github.silencesu.parser.CatalogByPageParser;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

@Getter
public class BookCatalogueHtml {
    public static final Logger logger = LoggerFactory.getLogger(BookCatalogueHtml.class);

    /**
     * id 与 url
     */
    private Map<Integer, String> pagesUrl = new HashMap<>();


    /**
     * 加载目录
     *
     * @param bookUrlByPage
     */
    public void load(String bookUrlByPage) {


        ChromeDriver driver = ChromeDriverFactory.builder().openWindows(false).build().instance();

        driver.get(bookUrlByPage);


        CatalogByPageParser parser = new CatalogByPageParser(driver);
        pagesUrl = parser.parser();


        logger.info("目录总共获取数据:{}", pagesUrl.size());
    }


}
