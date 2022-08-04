package com.ssafy.queant.controller;

import com.ssafy.queant.model.dto.news.NewsDetailDto;
import com.ssafy.queant.model.dto.news.NewsDto;
import com.ssafy.queant.model.entity.content.Content;
import com.ssafy.queant.model.repository.ContentRepository;
import com.ssafy.queant.model.service.content.ContentService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@CrossOrigin("*")
@RequiredArgsConstructor
public class ContentController { ;
    @Autowired
    private ContentService contentService;
    @Autowired
    private ContentRepository contentRepository;

    @ApiResponses({
            @ApiResponse(code = 200, message="기사 리스트가 성공적으로 전송되었습니다."),
            @ApiResponse(code = 403, message="접속이 거부되었습니다.")
    })
    @ApiOperation(value="뉴스 기사 목록 조회", notes="기사 리스트 정보 반환.")
    @PostMapping("/")
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

//    @ApiResponses({
//            @ApiResponse(code = 200, message="기사 내용이 성공적으로 전송되었습니다."),
//            @ApiResponse(code = 403, message="접속이 거부되었습니다.")
//    })
//    @ApiOperation(value="뉴스 기사 상세 조회", notes="기사 링크를 받아옴")
//    @PostMapping("/detail")
//    public ResponseEntity<NewsDetailDto> NewsDetail(@RequestBody String url) throws Exception {
//        log.info("[NewsDetail] is running");
//
//        NewsDetailDto news = contentService.getNewsDetail(url);
//
//        if(news == null) {
//            log.info("[NewsDetail] run failed");
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//        else {
//            log.info("[NewsDetail] run finished");
//            return new ResponseEntity<NewsDetailDto>(news, HttpStatus.OK);
//        }
//    }

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
    public ResponseEntity<Content> ContentCreate(@RequestBody Content entity) throws Exception {
        log.info("[ContentCreate] is running");

        //contentId는 자동부여되니까 넣지 않음
        Content article = Content.builder().memberId(entity.getMemberId()).title(entity.getTitle()).content(entity.getContent()).build();

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

    @ApiResponses({
            @ApiResponse(code = 200, message="컨텐츠가 수정되었습니다."),
            @ApiResponse(code = 403, message="접속이 거부되었습니다.")
    })
    @ApiOperation(value="컨텐츠 수정", notes="컨텐츠 수정완료 버튼을 누르면 작동 > 근데 좀 이상하게 작성한거같아서 테스트 필요")
    @PatchMapping("/contents/edit")
    public ResponseEntity<Content> ContentUpdate(@RequestBody Content entity) throws Exception {
        log.info("[ContentUpdate] is running");

        //contentId는 자동부여되니까 넣지 않음
        Content article = Content.builder().contentId(entity.getContentId()).memberId(entity.getMemberId()).title(entity.getTitle()).content(entity.getContent()).build();

        if(article == null) {
            log.info("[ContentUpdate] run failed");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else {
            //컨텐츠 수정
            contentRepository.save(article);
            log.info("[ContentUpdate] run finished");
            return new ResponseEntity<Content>(article, HttpStatus.OK);
        }
    }

    @ApiResponses({
            @ApiResponse(code = 200, message="컨텐츠가 삭제되었습니다."),
            @ApiResponse(code = 403, message="접속이 거부되었습니다.")
    })
    @ApiOperation(value="컨텐츠 삭제", notes="컨텐츠 삭제 버튼을 누르면 작동")
    @DeleteMapping("/contents/delete")
    public ResponseEntity<String> ContentDelete(@RequestBody Long contentId) throws Exception {
        log.info("[ContentDelete] is running");

        if(contentRepository.findByContentId(contentId) == null) {
            log.info("[ContentDelete] run failed");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else {
            //컨텐츠 삭제
            contentRepository.delete(Content.builder().contentId(contentId).build());
            log.info("[ContentDelete] run finished");
            return new ResponseEntity<String>("컨텐츠가 삭제되었습니다.", HttpStatus.OK);
        }
    }

}
