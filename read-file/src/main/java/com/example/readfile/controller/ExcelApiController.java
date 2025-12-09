package com.example.readfile.controller;

import com.example.readfile.dto.BookDto;
import com.example.readfile.service.ExcelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/excel")
public class ExcelApiController {

    private final ExcelService excelService;

    @PostMapping("/upload")
    public List<BookDto> handleFileUpload(@RequestParam("file") MultipartFile file) {
        return excelService.uploadExcel(file);
    }
}
