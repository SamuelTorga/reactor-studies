package br.com.samueltorga.reactor.model.r2dbc;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
@Builder
@AllArgsConstructor
public class Product {

    @Id
    private Integer id;
    private String name;
    @ReadOnlyProperty
    @CreatedDate
    private ZonedDateTime created;
    @LastModifiedDate
    @ReadOnlyProperty
    private ZonedDateTime updated;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ReadOnlyProperty
    private Manufacturer manufacturer;

}
