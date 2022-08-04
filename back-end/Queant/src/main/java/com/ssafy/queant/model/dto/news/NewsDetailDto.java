package com.ssafy.queant.model.dto.news;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema
public class NewsDetailDto extends NewsDto{

    @Schema(description = "입력날짜")
    private String inputDate;
    @Schema(description = "수정날짜")
    private String modifyDate;
    @Schema(description = "본문")
    private String content;

    public String getInputDate() {
        return inputDate;
    }

    public void setInputDate(String inputDate) {
        this.inputDate = inputDate;
    }

    public String getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(String modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public NewsDetailDto() {
    }

    public NewsDetailDto(String title, String imgLink, String preview, String articleLink, String writer, String inputDate, String modifyDate, String content) {
        super(title, imgLink, preview, articleLink, writer);
        this.inputDate = inputDate;
        this.modifyDate = modifyDate;
        this.content = content;
    }
}
