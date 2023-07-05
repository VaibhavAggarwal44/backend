package com.example.VaibhavProject.utils;

import co.elastic.clients.elasticsearch._types.query_dsl.*;
import lombok.val;

import java.util.function.Supplier;

public class ArticleSearchUtil {
    public static Supplier<Query> supplier(String word){
        Supplier<Query> supplier=()->Query.of(q->q.match(matchAllQuery(word)));
        return supplier;
    }

    public static MatchQuery matchAllQuery(String word){
        val matchAllQuery=new MatchQuery.Builder();
        return matchAllQuery.field("articleBody").query(word).build();
    }

    public static Supplier<Query> supplierQueryWithWord(String word){
        Supplier<Query> supplier=()->Query.of(q->q.fuzzy(matchAllQueryWithWord(word)));
        return supplier;
    }

//    MultiMatchQuery
    public static FuzzyQuery matchAllQueryWithWord(String word){
        val matchQuery=new FuzzyQuery.Builder();
        return matchQuery.field("articleBody").value(word).fuzziness("2").build();
    }
}
