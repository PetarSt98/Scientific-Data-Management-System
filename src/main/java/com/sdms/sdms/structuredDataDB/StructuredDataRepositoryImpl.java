package com.sdms.sdms.structuredDataDB;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.StringJoiner;

public class StructuredDataRepositoryImpl implements StructuredDataRepositoryCustom {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public String findValueByIdAndPath(Long id, String[] path) {
        StringJoiner joiner = new StringJoiner(", ", "jsonb_extract_path_text(data::jsonb, ", ")");
        for (String key : path) {
            joiner.add("'" + key + "'");
        }
        String sql = "SELECT " + joiner.toString() + " FROM json_data WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, String.class);
    }

    @Override
    public void setValueByIdAndPath(Long id, List<String> path, String newValue) {
        StringJoiner pathJoiner = new StringJoiner(", ", "ARRAY[", "]::text[]");
        for (String key : path) {
            pathJoiner.add("'" + key + "'");
        }
        String sql = "UPDATE json_data SET data = jsonb_set(data::jsonb, " + pathJoiner.toString() + ", ?::jsonb) WHERE id = ?";

        jdbcTemplate.update(sql, newValue, id);
    }

    @Override
    public void addValueByIdAndPath(Long id, List<String> path, String newValue) {
        StringJoiner pathJoiner = new StringJoiner(", ", "ARRAY[", "]::text[]");
        for (String key : path.subList(0, path.size() - 1)) {
            pathJoiner.add("'" + key + "'");
        }

        String newKey = path.get(path.size() - 1);


        String sql = "UPDATE json_data " +
                "SET data = jsonb_set(data::jsonb, " + pathJoiner.toString() + " || '[" + newKey + "]'::text[], ?::jsonb, true) " +
                "WHERE id = ? AND NOT (data::jsonb ? '" + newKey + "')";

        jdbcTemplate.update(sql, newValue, id);
    }

    private String formatAsJson(String value) {
        // Simple JSON formatting: wrap the value in double quotes if it's not already
        return "\"" + value.replace("\"", "\\\"") + "\"";
    }
}
