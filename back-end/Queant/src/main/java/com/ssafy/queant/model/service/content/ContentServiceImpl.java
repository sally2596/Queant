package com.ssafy.queant.model.service.content;


import com.ssafy.queant.model.dto.news.NewsDetailDto;
import com.ssafy.queant.model.dto.news.NewsDto;
import com.ssafy.queant.model.entity.content.Content;
import com.ssafy.queant.model.repository.ContentRepository;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ContentServiceImpl implements ContentService {

    @Override
    public List<NewsDto> getNewsList() throws Exception {

        List<NewsDto> newsList = new ArrayList<>();
        
        //네이버 뉴스에서 한번에 긁어올 페이지 수
        int page = 5;

        for (int i=1; i<=page; i++) {
            final String url = "https://news.naver.com/main/list.naver?mode=LSD&mid=sec&sid1=101" + "&page=" + i;
            Document doc = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 6.2; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1667.0 Safari/537.36")
                    .get();

            //기사 리스트
            Elements elements = doc.getElementsByAttributeValue("class","list_body newsflash_body");
            Element element = elements.get(0);

            Elements articlesMain = element.getElementsByAttributeValue("class","photo");
            Elements articlesSub = element.getElementsByAttributeValue("class","lede");
            Elements articlesWriter = element.getElementsByAttributeValue("class","writing");

            for (int j=0; j<articlesMain.size(); j++) {
                NewsDto news = new NewsDto();

                Element article = articlesMain.get(j);
                Elements articleElements = article.select("a");
                Element articleElement = articleElements.get(0);

                //기사 제목
                news.setTitle(articleElement.select("img").get(0).attr("alt"));
                //썸네일 링크
                String thumbUrl = articleElement.select("img").get(0).attr("src");
                //썸네일 후반 ?type=nf106_72 제거용
                news.setImgLink(thumbUrl.substring(0,thumbUrl.length()-14));

                //기사 프리뷰
                Element articleSub = articlesSub.get(j);
                news.setPreview(articleSub.text());

                //기사 링크
                news.setArticleLink(articleElement.attr("href"));

                //언론사
                Element articleWriter = articlesWriter.get(j);
                news.setWriter(articleWriter.text());

                newsList.add(news);

            }

        }
        return newsList;
    }

    @Override
    public NewsDetailDto getNewsDetail(String articleLink) throws Exception {

        NewsDetailDto news = new NewsDetailDto();

        Document doc = Jsoup.connect(articleLink).userAgent("Mozilla/5.0 (Windows NT 6.2; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1667.0 Safari/537.36")
                .get();

        //기사 제목
        news.setTitle(doc.getElementsByAttributeValue("class", "media_end_head_title").text());
        //기사 작성일
        news.setInputDate(doc.getElementsByAttributeValue("class", "media_end_head_info_datestamp_time _ARTICLE_DATE_TIME").text());
        //기사 수정일
        news.setModifyDate(doc.getElementsByAttributeValue("class", "media_end_head_info_datestamp_time _ARTICLE_MODIFY_DATE_TIME").text());
        //기사 본문
        news.setContent(doc.getElementsByAttributeValue("class", "go_trans _article_content").text());

        return news;
    }

}
