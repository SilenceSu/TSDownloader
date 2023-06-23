package com.github.silencesu;

import com.github.silencesu.help.PageChapterHelp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine;

import java.util.Map;
import java.util.concurrent.*;

/**
 * @author SuSu
 * @Email 381963558@qq.com
 * Created by SuSu on 2023/6/21.
 */
public class Start {
    private static final Logger logger = LoggerFactory.getLogger(Start.class);

    private static final int BOOK_ID_MIN = 100997;
    private static final int BOOK_ID_MAX = 147879;

    private static final int BOOK_ID = 19508;

    private static Map<Integer, Future<Boolean>> task = new ConcurrentHashMap<>();


    public static void main(String[] args) {




        CommandLine commandLine = new CommandLine(Option.class);

        try {
            commandLine.parseArgs(args);
        } catch (CommandLine.MissingParameterException e) {

            logger.info("参数输入错误，请核对：");

            commandLine.usage(System.out);
            return;        }


        logger.info("准备开始执行任务");





        PageChapterHelp pageChapterHelp = new PageChapterHelp();
        pageChapterHelp.pageTask(Option.pageUrl);
        pageChapterHelp.start();



    }





}
