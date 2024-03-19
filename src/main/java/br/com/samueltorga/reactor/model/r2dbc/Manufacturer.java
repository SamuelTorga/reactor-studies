package br.com.samueltorga.reactor.model.r2dbc;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.ZonedDateTime;


@Table(name = "manufacturers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Manufacturer {

    @Id
    private Integer id;
    private String name;
    @Column("country_code_iso")
    private String isoCountryCode;
    @ReadOnlyProperty
    @CreatedDate
    private ZonedDateTime created;
    @LastModifiedDate
    @ReadOnlyProperty
    private ZonedDateTime updated;

}
