package com.sdms.sdms.structuredDataDB;

import jakarta.persistence.*;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "json_data")
public class StructuredData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "jsonb")
    private String data;

    @Column(name = "file_name")
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
