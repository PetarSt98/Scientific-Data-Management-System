package com.sdms.sdms;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.sdms.sdms.structuredDataDB.StructuredData;
import com.sdms.sdms.structuredDataDB.StructuredDataController;
import com.sdms.sdms.structuredDataDB.StructuredDataRepository;
import com.sdms.sdms.structuredDataDB.StructuredDataService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class StructuredDataControllerTest  {

    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writer();

    @Mock
    private StructuredDataService structuredDataService;

    @InjectMocks
    private StructuredDataController structuredDataController;

    StructuredData RECORD_1 = new StructuredData(1L,
            "{\n" +
            "  \"shot_id\": 1,\n" +
            "  \"timestamp\": \"2024-06-27T14:32:10Z\",\n" +
            "  \"success\": true\n" +
            "}",
            "knjiga2.json");
    StructuredData RECORD_2 = new StructuredData(1L,
            "{\n" +
                    "  \"shot_id\": 2,\n" +
                    "  \"timestamp\": \"2024-05-27T14:32:10Z\",\n" +
                    "  \"success\": false\n" +
                    "}",
            "bukvar2.json");

    StructuredData RECORD_3 = new StructuredData(1L,
            "{\n" +
                    "  \"shot_id\": 3,\n" +
                    "  \"timestamp\": \"2023-05-27T14:32:10Z\",\n" +
                    "  \"success\": false\n" +
                    "}",
            "sveska.json");

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(structuredDataController).build();
    }

    @Test
    public void getAllRecords_success() throws Exception{
        List<StructuredData> records = new ArrayList<>(Arrays.asList(RECORD_1, RECORD_2, RECORD_3));

        Mockito.when(structuredDataService.getJsonDataById(1L)).thenReturn(Optional.of(records.getFirst()));

        mockMvc.perform(MockMvcRequestBuilders
                .get("/book")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(3)));


    }
}
