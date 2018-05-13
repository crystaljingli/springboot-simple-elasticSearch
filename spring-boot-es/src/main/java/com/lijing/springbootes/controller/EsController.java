package com.lijing.springbootes.controller;

import org.elasticsearch.action.delete.DeleteRequestBuilder;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequestBuilder;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.NumberUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * @Description
 * @Author crystal
 * @CreatedDate 2018年05月13日 星期日 10时59分.
 */
@RestController
@RequestMapping("/es")
public class EsController {
    @Autowired
    private TransportClient transportClient;

    @GetMapping("/get/book/novel")
    public ResponseEntity get(String id){
        if(StringUtils.isEmpty(id)){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        //发送请求
        GetRequestBuilder builder = transportClient.prepareGet("book", "nover", id);
        //获取结果
        GetResponse response = builder.get();
        if(!response.isExists()){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(response.getSource(),HttpStatus.OK);
    }

    @PostMapping("/add/book/novel")
    public ResponseEntity add(@RequestParam(name = "title") String title,
                              @RequestParam(name = "author") String author,
                              @RequestParam(name = "word_count") String wordCount,
                              @RequestParam(name = "publish_date")
                                          @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                          Date publishDate){
        try {
            //组装参数
           XContentBuilder content = XContentFactory.jsonBuilder()
                    .startObject()
                    .field("title", title)
                    .field("author", author)
                    .field("word_count", wordCount)
                    .field("publish_date", publishDate)
                    .endObject();
            //发送请求
            IndexRequestBuilder builder = transportClient.prepareIndex("book", "novel");
            //获取结果
            IndexResponse response = builder.setSource(content).get();
            return new ResponseEntity(response.getId(),HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/delete/bool/novel")
    public ResponseEntity delete(String id){
        DeleteRequestBuilder builder = transportClient.prepareDelete("booke", "novel", id);
        DeleteResponse response = builder.get();
        return new ResponseEntity(response.getResult(),HttpStatus.OK);
    }

    @PutMapping("/update/book/novel")
    public ResponseEntity update(@RequestParam(name="id") String id,
                                 @RequestParam(name="title", required = false)String title,
                                 @RequestParam(name="author", required = false)String author){
        UpdateRequest updateRequest = new UpdateRequest("book", "novel", id);
        try {
            //组装参数
            XContentBuilder content = XContentFactory.jsonBuilder().startObject();
            if(!StringUtils.isEmpty(title)){
                content.field("title",title);
            }
            if(!StringUtils.isEmpty(author)){
                content.field("author",author);
            }
            content.endObject();
            updateRequest.doc(updateRequest);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        //发送请求&获取结果
        try {
            UpdateResponse response = transportClient.update(updateRequest).get();
            return new ResponseEntity(response.getResult(),HttpStatus.OK);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return  null;
    }

    @PostMapping("/query/book/novel")
    public ResponseEntity query(@RequestParam(name = "author", required = false) String author,
                                @RequestParam(name = "title", required = false) String title,
                                @RequestParam(name = "gt_word_count", defaultValue = "0") String gtWordCount,
                                @RequestParam(name = "lt_word_count", required = false) String ltWordCount){
        //组装参数
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        if(author != null){
            boolQuery.must(QueryBuilders.matchQuery("author",author));
        }
        if(title != null){
            boolQuery.must(QueryBuilders.matchQuery("title", title));
        }
        RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery("word_count");
        if(ltWordCount != null){
            rangeQuery.to(ltWordCount);
        }
        boolQuery.filter(rangeQuery);
        //发送请求
        SearchRequestBuilder searchRequest = transportClient
                .prepareSearch("book")
                .setTypes("novel")
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(boolQuery).setFrom(0).setSize(10);
        //获取结果
       SearchResponse searchResponse = searchRequest.get();
       List<Map<String, Object>> result = new ArrayList<>();
       for(SearchHit hit : searchResponse.getHits()){
           result.add(hit.getSource());


       }
        return new ResponseEntity(result,HttpStatus.OK);
    }


}
