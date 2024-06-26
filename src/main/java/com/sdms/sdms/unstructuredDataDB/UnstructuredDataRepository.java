package com.sdms.sdms.unstructuredDataDB;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface UnstructuredDataRepository extends MongoRepository<UnstructuredData, String> {
//    Optional<UnstructuredData> findById(String id); // ovo nije potrebno al radi i sa ovim
    Optional<UnstructuredData> findByTitle(String title);
}
