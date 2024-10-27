package com.example.elasticintegration.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;

@Document(indexName = "blog")
@Data
@AllArgsConstructor
@ToString
public class Article {

    @Id
    private String id;

    private String title;

//    @Field(type = FieldType.Text)
//    private String text;

    @Field(type = FieldType.Nested, includeInParent = true)
    private List<Author> authors;
}