package com.sdms.sdms.structuredDataDB;

import java.util.List;

public interface StructuredDataRepositoryCustom {
    String findValueByIdAndPath(Long id, String[] path);
    void setValueByIdAndPath(Long id, List<String> path, String value);
    void addValueByIdAndPath(Long id, List<String> path, String value);
}
