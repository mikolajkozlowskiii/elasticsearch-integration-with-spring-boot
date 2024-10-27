package com.example.elasticintegration.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.elasticsearch.annotations.Field;

import static org.springframework.data.elasticsearch.annotations.FieldType.Text;

@Data
@ToString
@AllArgsConstructor
public class Author {

    @Field(type = Text)
    private String name;

}
