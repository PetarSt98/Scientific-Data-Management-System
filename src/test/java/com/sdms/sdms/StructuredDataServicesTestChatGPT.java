package com.sdms.sdms;

import com.sdms.sdms.structuredDataDB.StructuredData;
import com.sdms.sdms.structuredDataDB.StructuredDataRepository;
import com.sdms.sdms.structuredDataDB.StructuredDataService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class StructuredDataServicesTestChatGPT {

    @Mock
    private StructuredDataRepository structuredDataRepository;

    @InjectMocks
    private StructuredDataService structuredDataService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetJsonDataById() {
        Long id = 1L;
        StructuredData data = new StructuredData(id, "{\"key\":\"value\"}", "test.json");
        when(structuredDataRepository.findById(id)).thenReturn(Optional.of(data));

        Optional<StructuredData> result = structuredDataService.getJsonDataById(id);

        assertEquals(Optional.of(data), result);
    }
}
