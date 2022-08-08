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
        int page = 1;

        for (int i=1; i<=page; i++) {
            final String url = "https://news.naver.com/main/list.naver?mode=LS2D&mid=sec&sid1=101&sid2=259" + "&page=" + i;
            Document doc = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 6.2; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1667.0 Safari/537.36")
                    .get();

            //기사 리스트
            Elements elements = doc.getElementsByAttributeValue("class","list_body newsflash_body");
            Element element = elements.get(0);
            Elements articleList = element.getElementsByTag("dl");

            for (int j=0; j<articleList.size(); j++) {
                NewsDto news = new NewsDto();

                //하나의 목록씩 가져옴
                Element article = articleList.get(j);

                //사진 없는 기사는 제외시킴
                if(article.getElementsByAttributeValue("class", "photo").size()==0) continue;

                Element head = article.getElementsByTag("dt").get(0);
                Element body = article.getElementsByTag("dt").get(1);
                Element foot = article.getElementsByTag("dd").get(0);

                //기사 링크
                news.setArticleLink(head.select("a").get(0).attr("href"));
                //썸네일 링크
                String thumbUrl = head.select("img").get(0).attr("src");
                //썸네일 후반 ?type=nf106_72 제거용
                news.setImgLink(thumbUrl.substring(0,thumbUrl.length()-14));
                //기사 제목
                news.setTitle(body.select("a").get(0).text());
                //기사 프리뷰
                news.setPreview(foot.getElementsByAttributeValue("class", "lede").get(0).text());
                //언론사
                news.setWriter(foot.getElementsByAttributeValue("class", "writing").get(0).text());

                newsList.add(news);

                //5개까지만 수집
                if(newsList.size()==5) break;
            }

        }
        return newsList;
    }

    @Override
    public List<Content> getPreview(List<Content> article) throws Exception {

        for (int i=0; i<article.size(); i++) {
            String sub = article.get(i).getContent()
                    .replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "")
                    .replaceAll("&nbsp;", "");


            if (sub.length()>45) {
                sub = sub.substring(0,45);
                sub += "...";
            }

            article.get(i).setContent(sub);
        }

        return article;
    }

//    @Override
//    public NewsDetailDto getNewsDetail(String articleLink) throws Exception {
//
//        NewsDetailDto news = new NewsDetailDto();
//
//        Document doc = Jsoup.connect(articleLink).userAgent("Mozilla/5.0 (Windows NT 6.2; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1667.0 Safari/537.36")
//                .get();
//
//        //기사 제목
//        news.setTitle(doc.getElementsByAttributeValue("class", "media_end_head_title").text());
//        //기사 작성일
//        news.setInputDate(doc.getElementsByAttributeValue("class", "media_end_head_info_datestamp_time _ARTICLE_DATE_TIME").text());
//        //기사 수정일
//        news.setModifyDate(doc.getElementsByAttributeValue("class", "media_end_head_info_datestamp_time _ARTICLE_MODIFY_DATE_TIME").text());
//        //기사 본문
//        news.setContent(doc.getElementsByAttributeValue("class", "go_trans _article_content").text());
//
//        return news;
//    }

}
