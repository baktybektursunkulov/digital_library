package kz.satbayev.university.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "books")
@Data
public class Books{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "author")
    private String author;

    @Column(name = "url_minio")
    private String url_minio;

    @Column(name = "direction")
    private Long direction;

    @Column(name = "year")
    private Integer year;

    @Column(name = "is_deleted")
    private Boolean is_deleted;
}
