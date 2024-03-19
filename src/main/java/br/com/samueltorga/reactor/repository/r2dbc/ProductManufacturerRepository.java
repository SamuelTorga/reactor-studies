package br.com.samueltorga.reactor.repository.r2dbc;

import br.com.samueltorga.reactor.model.r2dbc.Manufacturer;
import br.com.samueltorga.reactor.model.r2dbc.Product;
import io.r2dbc.spi.Row;
import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.time.ZonedDateTime;

@Repository
@RequiredArgsConstructor
public class ProductManufacturerRepository {

    private final DatabaseClient databaseClient;

    public Flux<Product> listAllProductsWithManufacturers() {
        String sql = """
        SELECT p.id as p_id,
               p.name as p_name,
               p.manufacturer_id as p_manufacturer_id,
               p.created as p_created,
               p.updated as p_updated,
               m.id as m_id,
               m.name as m_name,
               m.country_iso_code as m_country_iso_code,
               m.created as m_created,
               m.updated as m_updated
        FROM products p
                 INNER JOIN reactive.manufacturers m on p.manufacturer_id = m.id""";
        return databaseClient.sql(sql)
                .map((row, rowMetadata) -> convertToObject(row))
                .all();
    }

    public Flux<Product> listAllProductsWithManufacturers(Integer manufacturerId) {
        String sql = """
        SELECT p.id as p_id,
               p.name as p_name,
               p.manufacturer_id as p_manufacturer_id,
               p.created as p_created,
               p.updated as p_updated,
               m.id as m_id,
               m.name as m_name,
               m.country_iso_code as m_country_iso_code,
               m.created as m_created,
               m.updated as m_updated
        FROM products p
                 INNER JOIN reactive.manufacturers m on p.manufacturer_id = m.id
        WHERE p.manufacturer_id = :manufacturerId""";
        return databaseClient.sql(sql)
                .bind("manufacturerId", manufacturerId)
                .map((row, rowMetadata) -> convertToObject(row))
                .all();
    }

    private Product convertToObject(Row row) {
        return Product.builder()
                .id(row.get("p_id", Integer.class))
                .name(row.get("p_name", String.class))
                .created(row.get("p_created", ZonedDateTime.class))
                .updated(row.get("p_updated", ZonedDateTime.class))
                .manufacturer(Manufacturer.builder()
                        .id(row.get("m_id", Integer.class))
                        .name(row.get("m_name", String.class))
                        .isoCountryCode(row.get("m_country_iso_code", String.class))
                        .created(row.get("m_created", ZonedDateTime.class))
                        .updated(row.get("m_updated", ZonedDateTime.class))
                        .build())
                .build();
    }
}
