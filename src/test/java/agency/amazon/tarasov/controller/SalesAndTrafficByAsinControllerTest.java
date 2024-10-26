package agency.amazon.tarasov.controller;

import agency.amazon.tarasov.model.SalesAndTrafficByAsin;
import agency.amazon.tarasov.service.statistic.SalesAndTrafficByAsinService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "test@example.com", roles = "USER")
class SalesAndTrafficByAsinControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SalesAndTrafficByAsinService service;

    @Test
    void testGetSalesAndTrafficByDate() throws Exception {
        var salesData = new SalesAndTrafficByAsin();
        salesData.setParentAsin("ASIN123");

        when(service.findSalesAndTrafficByAsin(anyString())).thenReturn(salesData);

        mockMvc.perform(get("/statistic/asin")
                        .param("asin", "ASIN123")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.parentAsin").value("ASIN123"));
    }

    @Test
    void testGetSalesAndTrafficByAsinRange() throws Exception {
        var salesData1 = new SalesAndTrafficByAsin();
        salesData1.setParentAsin("ASIN123");
        var salesData2 = new SalesAndTrafficByAsin();
        salesData2.setParentAsin("ASIN456");

        when(service.findSalesAndTrafficByAsinRange(anyList())).thenReturn(List.of(salesData1, salesData2));

        mockMvc.perform(get("/statistic/asin/range")
                        .param("range", "ASIN123", "ASIN456")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].parentAsin").value("ASIN123"))
                .andExpect(jsonPath("$[1].parentAsin").value("ASIN456"));
    }

    @Test
    void testGetAllSalesAndTrafficByAsin() throws Exception {
        var salesData1 = new SalesAndTrafficByAsin();
        salesData1.setParentAsin("ASIN123");
        var salesData2 = new SalesAndTrafficByAsin();
        salesData2.setParentAsin("ASIN456");


        when(service.findAllSalesAndTrafficByAsin()).thenReturn(List.of(salesData1, salesData2));

        mockMvc.perform(get("/statistic/asin/all")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].parentAsin").value("ASIN123"))
                .andExpect(jsonPath("$[1].parentAsin").value("ASIN456"));
    }
}