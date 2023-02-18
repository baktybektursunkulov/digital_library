package kz.satbayev.university.rest;


import kz.satbayev.university.dto.FileDto;
import kz.satbayev.university.model.Books;
import kz.satbayev.university.repository.BooksRepository;
import kz.satbayev.university.service.MinioService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static org.springframework.web.servlet.HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE;

@Slf4j
@RestController
@RequestMapping(value = "/file")
public class FileController {

    @Autowired
    private MinioService minioService;
    @Autowired
    private BooksRepository booksRepository;

    @GetMapping
    public ResponseEntity<Object> getFiles() {
        return ResponseEntity.ok(minioService.getListObjects());
    }

    @GetMapping(value = "/**")
    public ResponseEntity<Object> getFile(HttpServletRequest request) throws IOException {
        String pattern = (String) request.getAttribute(BEST_MATCHING_PATTERN_ATTRIBUTE);
        String filename = new AntPathMatcher().extractPathWithinPattern(pattern, request.getServletPath());
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(IOUtils.toByteArray(minioService.getObject(filename)));
    }

    @PostMapping(value = "/upload")
    public ResponseEntity<Object> upload(@ModelAttribute FileDto request) {
        Books books=new Books();
        books.setName(request.getTitle());
        books.setAuthor(request.getAuthor());
        books.setUrl_minio(minioService.getPreSignedUrl(request.getFile().getOriginalFilename()));
        books.setDirection(3L);
        books.setYear(request.getYear());
        books.setIs_deleted(Boolean.FALSE);
        booksRepository.save(books);
        return ResponseEntity.ok().body(minioService.uploadFile(request));
    }

}
