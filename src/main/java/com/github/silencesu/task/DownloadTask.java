package com.github.silencesu.task;

import com.github.silencesu.Option;
import com.github.silencesu.bean.ChapterInfo;
import com.github.silencesu.driver.ChromeDriverFactory;
import com.github.silencesu.parser.ChapterParser;
import org.apache.logging.log4j.util.Strings;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.Callable;

/**
 * @author SuSu
 * @Email 381963558@qq.com
 * Created by SuSu on 2023/6/21.
 */
public class DownloadTask implements Callable<Boolean> {

    private static final Logger logger = LoggerFactory.getLogger(DownloadTask.class);

    private int taskid;
    private ChromeDriver webDriver;
    private String url;





    public DownloadTask(int taskid, String url) {
        this.taskid = taskid;
        this.url = url;
    }

    @Override
    public Boolean call() throws Exception {
        return startJob();
    }

    public boolean startJob() {
        try {

            webDriver = ChromeDriverFactory.builder().build().instance();

            Thread.sleep(new Random().nextInt(1000, 5000));


            /**
             * 打开页面
             */

            logger.info("任务：{},准备打开页面：{}", taskid, url);



            webDriver.get(url);

            logger.info("任务：{},已经页面：{}", taskid, url);


            /**
             * 解析参数
             */
            ChapterParser chapterParser = new ChapterParser(webDriver);

            ChapterInfo info = chapterParser.get();

            logger.info("任务:{},准备获取页面元素：name:{},url:{}", taskid, info.getName(), info.getMp3Url());


            /**
             * 准备下载
             */
            if (info.isFull()) {
                String output = Option.downloadPath + info.getName();

                return downMp3(output, info);

            }

            logger.error("任务:{},获取页面元素失败：name:{},url:{}", taskid, info.getName(), info.getMp3Url());
            return false;


        } catch (Exception e) {

            logger.error("任务:{},任务异常", taskid);
            logger.error("任务错误栈", e);
            return false;

        } finally {
            webDriver.quit();
            logger.info("关闭窗口:{}", taskid);
        }


    }


    private boolean downMp3(String output, ChapterInfo info) {
        logger.info("任务:{},准备执行下载命令", taskid);


        ProcessBuilder pb;
        //是否配置代理
        if (Strings.isBlank(Option.proxyAdd)) {
            pb = new ProcessBuilder("curl", "--proxy", "127.0.0.1:7890", "-o", output, info.getMp3Url());
        } else {
            pb = new ProcessBuilder("curl", "-o", output, info.getMp3Url());
        }


        try {
            Process p = pb.start();
            int exitCode = p.waitFor();
            if (exitCode == 0) {
                logger.info("success downloaded:,File:{} downloaded successfully.", info.getName());
                return true;
            } else {
                logger.info("failed download: file:{}", info.getName());
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return false;
    }


}
