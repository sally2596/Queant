package com.ssafy.queant.model.dto.news;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema
public class UploadDto {

    @Schema(description = "업로드 성공 여부")
    private Boolean uploaded;

    @Schema(description = "이미지 경로")
    private String url;

    public Boolean getUploaded() {
        return uploaded;
    }

    public void setUploaded(Boolean uploaded) {
        this.uploaded = uploaded;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public UploadDto() {
    }

    public UploadDto(Boolean uploadType, String imgPath) {
        this.uploaded = uploadType;
        this.url = imgPath;
    }
}
