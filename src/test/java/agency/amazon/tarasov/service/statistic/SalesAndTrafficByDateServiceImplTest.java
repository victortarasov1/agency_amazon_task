package agency.amazon.tarasov.service.statistic;

import agency.amazon.tarasov.exception.SalesAndTrafficNotFoundException;
import agency.amazon.tarasov.model.SalesAndTrafficByDate;
import agency.amazon.tarasov.repository.SalesAndTrafficByDateRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SalesAndTrafficByDateServiceImplTest {
    private SalesAndTrafficByDateRepository repository;
    private SalesAndTrafficByDateService service;

    @BeforeEach
    public void setUp() {
        repository = mock(SalesAndTrafficByDateRepository.class);
        service = new SalesAndTrafficByDateServiceImpl(repository);
    }

    @Test
    void testFindSalesAndTrafficByDate_shouldReturnData() {
        var date = LocalDate.of(2024, 10, 26);
        var data = new SalesAndTrafficByDate();
        data.setDate(date);

        when(repository.findById(date)).thenReturn(Optional.of(data));

        var result = service.findSalesAndTrafficByDate(date);

        assertThat(result).isEqualTo(data);
    }

    @Test
    void testFindSalesAndTrafficByDate_shouldThrowSalesAndTrafficNotFound() {
        var date = LocalDate.of(2024, 10, 26);

        when(repository.findById(date)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.findSalesAndTrafficByDate(date))
                .isInstanceOf(SalesAndTrafficNotFoundException.class);
    }

    @Test
    void testFindSalesAndTrafficByDateRange_shouldReturnData() {
        var startDate = LocalDate.of(2024, 10, 20);
        var endDate = LocalDate.of(2024, 10, 25);
        var dataList = List.of(new SalesAndTrafficByDate(), new SalesAndTrafficByDate());

        when(repository.findAllById(anyList())).thenReturn(dataList);

        var result = service.findSalesAndTrafficByDateRange(startDate, endDate);

        assertThat(result).isEqualTo(dataList);
    }

    @Test
    void testFindAllSalesAndTrafficByDate_shouldReturnAllData() {
        var allData = List.of(new SalesAndTrafficByDate(), new SalesAndTrafficByDate());

        when(repository.findAll()).thenReturn(allData);

        var result = service.findAllSalesAndTrafficByDate();

        assertThat(result).isEqualTo(allData);
    }
}