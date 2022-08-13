package com.ssafy.queant.model.service.content;



import com.ssafy.queant.model.dto.news.NewsDetailDto;
import com.ssafy.queant.model.dto.news.NewsDto;
import com.ssafy.queant.model.entity.content.Content;

import java.util.List;

public interface ContentService {

    public List<NewsDto> getNewsList() throws Exception;
    //public NewsDetailDto getNewsDetail(String url) throws Exception;

    public List<Content> getPreview(List<Content> article) throws Exception;

}
