package com.sdms.sdms.Library;

import org.apache.tomcat.jni.Library;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibraryRepository extends JpaRepository<Books, Long> {
}
