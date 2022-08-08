package com.ssafy.queant.controller;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.ssafy.queant.model.dto.news.NewsDetailDto;
import com.ssafy.queant.model.dto.news.NewsDto;
import com.ssafy.queant.model.dto.news.UploadDto;
import com.ssafy.queant.model.entity.content.Content;
import com.ssafy.queant.model.repository.ContentRepository;
import com.ssafy.queant.model.service.content.ContentService;
import com.ssafy.queant.model.service.content.S3Uploader;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@Slf4j
@CrossOrigin("*")
@RequiredArgsConstructor
public class ContentController { ;
    @Autowired
    private ContentService contentService;
    @Autowired
    private ContentRepository contentRepository;

    private S3Uploader s3Uploader;

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    public String bucket;

    @ApiResponses({
            @ApiResponse(code = 200, message="기사 리스트가 성공적으로 전송되었습니다."),
            @ApiResponse(code = 403, message="접속이 거부되었습니다.")
    })
    @ApiOperation(value="뉴스 기사 목록 조회", notes="기사 리스트 정보 반환.")
    @GetMapping("/")
    public ResponseEntity<List<NewsDto>> NewsList() throws Exception {
        log.info("[NewsList] is running");
        List<NewsDto> newsList = contentService.getNewsList();

        if(newsList == null) {
            log.info("[NewsList] run failed");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else {
            log.info("[NewsList] run finished");
            return new ResponseEntity<List<NewsDto>>(newsList, HttpStatus.OK);
        }
    }

    @ApiResponses({
            @ApiResponse(code = 200, message="컨텐츠 리스트가 성공적으로 전송되었습니다."),
            @ApiResponse(code = 403, message="접속이 거부되었습니다.")
    })
    @ApiOperation(value="컨텐츠 리스트 조회", notes="컨텐츠 리스트를 받아옴")
    @GetMapping("/contents")
    public ResponseEntity<List<Content>> ContentsList() throws Exception {
        log.info("[ContentsList] is running");

        List<Content> articles = contentRepository.findAll();

        if(articles == null) {
            log.info("[ContentsList] run failed");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else {
            log.info("[ContentsList] run finished");
            return new ResponseEntity<List<Content>>(articles, HttpStatus.OK);
        }
    }

    @ApiResponses({
            @ApiResponse(code = 200, message="컨텐츠 상세 조회가 성공적으로 처리되었습니다."),
            @ApiResponse(code = 403, message="접속이 거부되었습니다.")
    })
    @ApiOperation(value="컨텐츠 상세 조회", notes="컨텐츠 상세 정보 페이지로 이동")
    @GetMapping("/contents/{contentId}")
    public ResponseEntity<Content> ContentDetail(@PathVariable("contentId") Long contentId) throws Exception {
        log.info("[ContentDetail] is running");

        Content article = contentRepository.findByContentId(contentId);

        if(article == null) {
            log.info("[ContentDetail] run failed");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else {
            log.info("[ContentDetail] run finished");
            return new ResponseEntity<Content>(article, HttpStatus.OK);
        }
    }

    @ApiResponses({
            @ApiResponse(code = 200, message="컨텐츠가 작성되었습니다."),
            @ApiResponse(code = 403, message="접속이 거부되었습니다.")
    })
    @ApiOperation(value="컨텐츠 작성", notes="컨텐츠 작성완료 버튼을 누르면 작동")
    @PostMapping("/contents/edit")
    public ResponseEntity<Content> ContentCreate(@RequestBody Content content) throws Exception {
        log.info("[ContentCreate] is running");
        log.info(content.getMemberId());
        log.info(content.getTitle());
        log.info(content.getContent());

        //contentId는 자동부여되니까 넣지 않음
        Content article = Content.builder().memberId(content.getMemberId()).title(content.getTitle()).content(content.getContent()).build();

        if(article == null) {
            log.info("[ContentCreate] run failed");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else {
            //컨텐츠 저장
            contentRepository.save(article);
            log.info("[ContentCreate] run finished");
            return new ResponseEntity<Content>(article, HttpStatus.OK);
        }
    }

//    @ApiResponses({
//            @ApiResponse(code = 200, message="컨텐츠가 수정되었습니다."),
//            @ApiResponse(code = 403, message="접속이 거부되었습니다.")
//    })
//    @ApiOperation(value="컨텐츠 수정", notes="컨텐츠 수정완료 버튼을 누르면 작동 > 근데 좀 이상하게 작성한거같아서 테스트 필요")
//    @PatchMapping("/contents/edit")
//    public ResponseEntity<Content> ContentUpdate(@RequestBody Content entity) throws Exception {
//        log.info("[ContentUpdate] is running");
//
//        //contentId는 자동부여되니까 넣지 않음
//        Content article = Content.builder().contentId(entity.getContentId()).memberId(entity.getMemberId()).title(entity.getTitle()).content(entity.getContent()).build();
//
//        if(article == null) {
//            log.info("[ContentUpdate] run failed");
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//        else {
//            //컨텐츠 수정
//            contentRepository.save(article);
//            log.info("[ContentUpdate] run finished");
//            return new ResponseEntity<Content>(article, HttpStatus.OK);
//        }
//    }

    @ApiResponses({
            @ApiResponse(code = 200, message="컨텐츠가 삭제되었습니다."),
            @ApiResponse(code = 403, message="접속이 거부되었습니다.")
    })
    @ApiOperation(value="컨텐츠 삭제", notes="컨텐츠 삭제 버튼을 누르면 작동")
    @DeleteMapping("/contents/delete")
    public ResponseEntity<String> ContentDelete(@RequestBody Content article) throws Exception {
        log.info("[ContentDelete] is running");

        if(contentRepository.findByContentId(article.getContentId()) == null) {
            log.info("[ContentDelete] run failed");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else {
            //컨텐츠 삭제
            contentRepository.delete(Content.builder().contentId(article.getContentId()).build());
            log.info("[ContentDelete] run finished");
            return new ResponseEntity<String>("컨텐츠가 삭제되었습니다.", HttpStatus.OK);
        }
    }

    @ApiResponses({
            @ApiResponse(code = 200, message="컨텐츠가 삭제되었습니다."),
            @ApiResponse(code = 403, message="접속이 거부되었습니다.")
    })
    @ApiOperation(value="이미지 업로드", notes="이미지 업로드 버튼을 누르면 작동")
    @ResponseBody
    @PostMapping("/contents/upload")
    public ResponseEntity<UploadDto> UploadImage(@RequestParam("upload") MultipartFile multipartFile) throws IOException {
        log.info("[UploadImage] is running");

        //s3Uploader.upload(multipartFile, "static");

        UploadDto ud = new UploadDto();

        File convertFile = new File(System.getProperty("user.dir") + "/" + multipartFile.getOriginalFilename());
        convertFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(convertFile);
        fos.write(multipartFile.getBytes());

        File uploadFile = convertFile;

        String fileName = "static" + "/" + UUID.randomUUID() + uploadFile.getName();   // S3에 저장된 파일 이름

        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));

        String uploadImageUrl = amazonS3Client.getUrl(bucket, fileName).toString(); // s3로 업로드

        log.info(uploadImageUrl);

        //uploaded와 url을 반환해야 업로드가 정상작동
        ud.setUploaded(true);
        ud.setUrl(uploadImageUrl);

        if(uploadImageUrl == null) {
            log.info("[UploadImage] run failed");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else {

            log.info("[UploadImage] run finished");
            return new ResponseEntity<UploadDto>(ud, HttpStatus.OK);
        }

    }
}
