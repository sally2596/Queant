package com.ssafy.queant.model.dto.news;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema
public class NewsDto {

    @Schema(description = "뉴스 제목")
    private String title;
    @Schema(description = "뉴스 썸네일 링크")
    private String imgLink;
    @Schema(description = "뉴스 프리뷰")
    private String preview;
    @Schema(description = "뉴스 링크")
    private String articleLink;
    @Schema(description = "언론사")
    private String writer;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgLink() {
        return imgLink;
    }

    public void setImgLink(String imgLink) {
        this.imgLink = imgLink;
    }

    public String getPreview() {
        return preview;
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }

    public String getArticleLink() {
        return articleLink;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public void setArticleLink(String articleLink) {
        this.articleLink = articleLink;
    }

    public NewsDto() {
    }

    public NewsDto(String title, String imgLink, String preview, String articleLink, String writer) {
        this.title = title;
        this.imgLink = imgLink;
        this.preview = preview;
        this.articleLink = articleLink;
        this.writer = writer;
    }
}
