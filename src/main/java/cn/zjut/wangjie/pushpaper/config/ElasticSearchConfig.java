package cn.zjut.wangjie.pushpaper.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

/**
 * @program: PushPaper
 * @description:
 * @author: WangJie
 * @create: 2018-05-06 16:07
 **/
/*
@Configuration
@EnableElasticsearchRepositories(basePackages = "org/springframework/data/elasticsearch/repositories")
static class ElasticSearchConfig {

    @Bean
    public ElasticsearchOperations elasticsearchTemplate() {
        return new ElasticsearchTemplate(nodeBuilder().local(true).node().client());
    }
}*/
