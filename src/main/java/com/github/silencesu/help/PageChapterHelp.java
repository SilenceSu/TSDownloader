package com.github.silencesu.help;

import java.util.Map;

public class PageChapterHelp extends ChapterHelper {


    /**
     *
     * 开启页码任务
     */
    public void pageTask(String pageUrl) {


        BookCatalogueHtml catalogueHtml = new BookCatalogueHtml();
        catalogueHtml.load(pageUrl);



        Map<Integer, String> pagesUrl = catalogueHtml.getPagesUrl();

        for (Map.Entry<Integer, String> entry : pagesUrl.entrySet()) {
            int i = entry.getKey();


            String chapterUrl = entry.getValue();


            chapterTask(i, chapterUrl);

        }
    }

}
