package agency.amazon.tarasov.service.statistic;

import agency.amazon.tarasov.exception.SalesAndTrafficNotFoundException;
import agency.amazon.tarasov.model.SalesAndTrafficByAsin;
import agency.amazon.tarasov.repository.SalesAndTrafficByAsinRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.ExpectedCount.times;

class SalesAndTrafficByAsinServiceImplTest {

    private SalesAndTrafficByAsinRepository repository;
    private SalesAndTrafficByAsinService service;

    @BeforeEach
    public void setUp() {
        repository = mock(SalesAndTrafficByAsinRepository.class);
        service = new SalesAndTrafficByAsinServiceImpl(repository);
    }

    @Test
    void testFindSalesAndTrafficByAsin_shouldReturnData() {
        var asin = "some asin";
        var data = new SalesAndTrafficByAsin();
        data.setParentAsin(asin);

        when(repository.findById(anyString())).thenReturn(Optional.of(data));

        var result = service.findSalesAndTrafficByAsin(asin);

        assertThat(result).isEqualTo(data);
    }

    @Test
    void testFindSalesAndTrafficByAsin_shouldThrowSalesAndTrafficNotFound() {
        var asin = "some asin";

        when(repository.findById(anyString())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.findSalesAndTrafficByAsin(asin)).isInstanceOf(SalesAndTrafficNotFoundException.class);
    }

    @Test
    void testFindSalesAndTrafficByAsinRange_shouldReturnData() {
        var asins = List.of("ASIN123", "ASIN456");
        var dataList = List.of(new SalesAndTrafficByAsin(), new SalesAndTrafficByAsin());

        when(repository.findAllById(asins)).thenReturn(dataList);

        var result = service.findSalesAndTrafficByAsinRange(asins);

        assertThat(result).isEqualTo(dataList);
    }

    @Test
    void testFindAllSalesAndTrafficByAsin_shouldReturnAllData() {

        var allData = List.of(new SalesAndTrafficByAsin(), new SalesAndTrafficByAsin());
        when(repository.findAll()).thenReturn(allData);

        var result = service.findAllSalesAndTrafficByAsin();

        assertThat(result).isEqualTo(allData);

    }
}