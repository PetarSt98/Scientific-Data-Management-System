package com.sdms.sdms.structuredDataDB;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StructuredDataRepository extends JpaRepository<StructuredData, Long>, StructuredDataRepositoryCustom {

    @Query(value = "INSERT INTO json_data (data, file_name) VALUES (CAST(:data AS jsonb), :name) RETURNING id", nativeQuery = true)
    Long insertData(@Param("data") String jsonData, @Param("name") String name);

//    Optional<StructuredData> findById(Long Id);

    Optional<StructuredData> findByName(String name);

//    @Query(value = "SELECT jsonb_extract_path_text(data::jsonb, :keys) FROM json_data WHERE id = :id", nativeQuery = true)
//    String findValueByIdAndKeys(@Param("id") Long id, @Param("keys") String keys);
//    @Query(value = "SELECT jsonb_extract_path_text(data::jsonb, :path) FROM json_data WHERE id = :id", nativeQuery = true)
//    String findValueByIdAndPath(@Param("id") Long id, @Param("path") String[] path);
}
