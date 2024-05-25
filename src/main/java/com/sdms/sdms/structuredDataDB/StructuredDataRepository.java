package com.sdms.sdms.structuredDataDB;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface StructuredDataRepository extends JpaRepository<StructuredData, Long> {

    @Query(value = "INSERT INTO json_data (data, file_name) VALUES (CAST(:data AS jsonb), :name) RETURNING id", nativeQuery = true)
    Long insertData(@Param("data") String jsonData, @Param("name") String name);

//    Optional<StructuredData> findById(Long Id);

    Optional<StructuredData> findByName(String name);
}
