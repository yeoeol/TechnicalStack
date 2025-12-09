package com.example.readfile.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/excel")
public class ExcelController {

    @GetMapping("/upload")
    public String uploadForm() {
        return "/pages/excelUpload";
    }
}
