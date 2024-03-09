package br.com.samueltorga.reactor.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.relational.core.mapping.Table;

import java.time.ZonedDateTime;

@Table(name = "products")
@Data
@NoArgsConstructor
public class Product {

    @Id
    private Integer id;
    private String name;
    private String manufacturer;
    @ReadOnlyProperty
    @CreatedDate
    private ZonedDateTime created;
    @LastModifiedDate
    @ReadOnlyProperty
    private ZonedDateTime updated;

}