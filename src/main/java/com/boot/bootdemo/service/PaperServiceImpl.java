package com.boot.bootdemo.service;


import com.boot.bootdemo.dao.PaperDao;
import com.boot.bootdemo.dao.PaperRepository;
import com.boot.bootdemo.entity.Paper;
import com.boot.bootdemo.service.base.BaseServiceImpl;
import com.boot.bootdemo.util.UuidUtil;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.index.query.WildcardQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.FacetedPageImpl;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PaperServiceImpl extends BaseServiceImpl<Paper> implements PaperService {
    @Autowired
    private PaperDao paperDao;
    @Autowired
    PaperRepository paperRepository;
    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;


    @Override
    public int addPaper(Paper paper) {
        paper.setId(UuidUtil.get32UUID());
        return paperDao.addPaper(paper);
    }

    @Override
    public int deletePaperById(String id) {
        return paperDao.deletePaperById(id);
    }

    @Override
    public int updatePaper(Paper paper) {
        return paperDao.updatePaper(paper);
    }

    @Override
    public Paper queryById(String id) {
        return paperDao.queryById(id);
    }

    @Override
    @Transactional
    @Cacheable(value = "AllPaper",key = "#pageIndex+#pageSize")
    public List<Paper> queryAllPaper(Integer pageIndex,Integer pageSize) {
        return paperDao.queryAllPaper(pageIndex,pageSize);
    }

    @Override
    public List<Paper> selectAll() {
        return paperDao.selectAll();
    }

    @Override
    public void updateES() {
        paperRepository.deleteAll();
        List<Paper> list = paperDao.selectAll();
        if (list.size()>0){
            for (Paper paper : list){
                paperRepository.save(paper);
            }
        }
    }

    @Override
    public List<Paper> findPaperByEs(Paper paper) {
        PageRequest pageRequest = new PageRequest(0,5);
        List<Paper>  list = new ArrayList<>();
        String[] s = new String[4];
        s[0] = "id";
        s[1] = "paperName";
        s[2] = "paperDetail";
        s[3] = "code";
        NativeSearchQuery searchQuery = getSearchQuery(s,paper.getKeyWord(),pageRequest);
        Page<Paper> page = elasticsearchTemplate.queryForPage(searchQuery, Paper.class, new SearchResultMapper() {
            @Override
            public <T> AggregatedPage<T> mapResults(SearchResponse searchResponse, Class<T> aClass, Pageable pageable) {
                ArrayList<Paper> papers = new ArrayList<Paper>();
                SearchHits hits = searchResponse.getHits();
                int totalHits = (int) hits.getTotalHits();
                for (SearchHit searchHit : hits) {
                    if (hits.getHits().length <= 0) {
                        return null;
                    }
                    Optional  optional = paperRepository.findById(searchHit.getId());

                    Object value = optional.get();
                    Paper paper = (Paper) value;

                    Paper paper1 = paperDao.queryById(paper.getId());
                    List<Paper> list = new ArrayList<>();
                    list.add(paper1);
                    if (list == null || list.size() < 1){
                        totalHits--;
                        continue;
                    }
                    paper = list.get(0);
                    paper = setHighlight(paper, s, searchHit);
                    papers.add(paper);
                }
                if(papers.size()>0){
                    return new AggregatedPageImpl<T>((List<T>) papers);
                }
                return null;
            }
        });
        if (page!=null){
           list = page.getContent();
        }
        return list;
    }



    private NativeSearchQuery getSearchQuery(String[] s, String keyWord, PageRequest pageRequest) {
        BoolQueryBuilder query = new BoolQueryBuilder();
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        //将大写字符转小写
        keyWord = keyWord.toLowerCase();
        //配置查询字段条件
        List<HighlightBuilder.Field> fieldList = new ArrayList<HighlightBuilder.Field>();
        for (String field : s) {
            org.elasticsearch.index.query.QueryBuilder wildcardQuery = new WildcardQueryBuilder(
                    field, "*" + keyWord + "*");
            QueryStringQueryBuilder queryStringQueryBuilder = new QueryStringQueryBuilder(keyWord)
                    .defaultField(field);
            boolQueryBuilder.should(wildcardQuery);
            boolQueryBuilder.should(queryStringQueryBuilder);
            fieldList.add(new HighlightBuilder.Field(field).postTags("</span>").preTags("<span style=\"color:green\">"));
        }
        HighlightBuilder.Field[] fieldArray = fieldList.toArray(new HighlightBuilder.Field[fieldList.size()]);
        query.must(boolQueryBuilder);

        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(query)
                .withHighlightFields(fieldArray)
                .withPageable(pageRequest)
                .build();
        return searchQuery;
    }



}