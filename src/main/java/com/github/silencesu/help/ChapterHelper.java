package com.github.silencesu.help;

import com.github.silencesu.task.DownloadTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * 章节任务助手
 */
public class ChapterHelper {
    private static final Logger logger = LoggerFactory.getLogger(ChapterHelper.class);

    /**
     * 开启线程数
     */
    ExecutorService service = Executors.newFixedThreadPool(5);

    private static Map<Integer, Future<Boolean>> task = new ConcurrentHashMap<>();

    private static Map<Integer, String> chapterUrls = new HashMap<>();


    public void chapterTask(int chapterId, String chapterUrl) {
        Future<Boolean> submit = service.submit(new DownloadTask(chapterId, chapterUrl));
        task.put(chapterId, submit);

        chapterUrls.put(chapterId, chapterUrl);

    }

    /**
     * 开启任务
     */
    public void start() {
        while (true) {

            //没结束继续
            if (!check()) {
                continue;
            }

            return;
        }
    }


    public boolean check() {


        List<Integer> successIds = new ArrayList<>();
        List<Integer> failIds = new ArrayList<>();
        for (Map.Entry<Integer, Future<Boolean>> entry : task.entrySet()) {
            Future<Boolean> value = entry.getValue();
            if (!value.isDone()) {
                continue;
            }

            try {
                Integer key = entry.getKey();
                //成功
                if (value.get()) {
                    successIds.add(key);
                } else {
                    //失败
                    failIds.add(key);
                }
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }

        }


        for (Integer id : successIds) {
            task.remove(id);
        }

        //失败从新添加
        for (Integer failId : failIds) {
            String s = chapterUrls.get(failId);
            chapterTask(failId, s);
        }


        waitAndPrint();


        if (task.size() == 0) {
            logger.info("全部任务消耗完毕，成功");
            service.shutdown();
            return true;
        }


        return false;

    }





    private void waitAndPrint()   {

        //5s检测一次
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        logger.error("-------------------------------------");

        logger.error("当前还有执行中的任务：{}", task.size());

        logger.error("-------------------------------------");
    }






}
