package agency.amazon.tarasov.controller;

import agency.amazon.tarasov.model.SalesAndTrafficByDate;
import agency.amazon.tarasov.service.statistic.SalesAndTrafficByDateService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "test@example.com", roles = "USER")
class SalesAndTrafficByDateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SalesAndTrafficByDateService service;

    @Test
    void testGetSalesAndTrafficByDate() throws Exception {
        var date = LocalDate.of(2023, 10, 1);
        var salesData = new SalesAndTrafficByDate();
        salesData.setDate(date);

        when(service.findSalesAndTrafficByDate(any(LocalDate.class))).thenReturn(salesData);

        mockMvc.perform(get("/statistic/date")
                        .param("date", date.toString())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.date").value(date.toString()));
    }

    @Test
    void testGetSalesAndTrafficByDateRange() throws Exception {
        var firstDate = LocalDate.of(2023, 10, 1);
        var lastDate = LocalDate.of(2023, 10, 31);
        var salesData1 = new SalesAndTrafficByDate();
        salesData1.setDate(firstDate);
        var salesData2 = new SalesAndTrafficByDate();
        salesData2.setDate(lastDate);

        when(service.findSalesAndTrafficByDateRange(any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(List.of(salesData1, salesData2));

        mockMvc.perform(get("/statistic/date/range")
                        .param("firstDate", firstDate.toString())
                        .param("lastDate", lastDate.toString())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].date").value(firstDate.toString()))
                .andExpect(jsonPath("$[1].date").value(lastDate.toString()));
    }

    @Test
    void testGetAllSalesAndTrafficByDate() throws Exception {
        var firstDate = LocalDate.of(2023, 10, 1);
        var lastDate = LocalDate.of(2023, 10, 31);
        var salesData1 = new SalesAndTrafficByDate();
        salesData1.setDate(firstDate);
        var salesData2 = new SalesAndTrafficByDate();
        salesData2.setDate(lastDate);

        when(service.findAllSalesAndTrafficByDate()).thenReturn(List.of(salesData1, salesData2));

        mockMvc.perform(get("/statistic/date/all")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].date").value(firstDate.toString()))
                .andExpect(jsonPath("$[1].date").value(lastDate.toString()));
    }
}