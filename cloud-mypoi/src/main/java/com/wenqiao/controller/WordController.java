package com.wenqiao.controller;

import com.wenqiao.utils.WordUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/word")
@Slf4j
public class WordController {

    @PostMapping("/fillWord")
    public void fillWord() {
        log.info("");
        WordUtils.wordOperate();
    }

    @PostMapping("/wordtoPDf")
    public void wordtoPDf() {
        log.info("");
        WordUtils.wordToPDF();
    }
}
