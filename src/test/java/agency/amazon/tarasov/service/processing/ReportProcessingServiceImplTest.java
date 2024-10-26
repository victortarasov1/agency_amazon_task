package agency.amazon.tarasov.service.processing;

import agency.amazon.tarasov.dto.Report;
import agency.amazon.tarasov.model.SalesAndTrafficByAsin;
import agency.amazon.tarasov.model.SalesAndTrafficByDate;
import agency.amazon.tarasov.reader.Reader;
import agency.amazon.tarasov.repository.SalesAndTrafficByAsinRepository;
import agency.amazon.tarasov.repository.SalesAndTrafficByDateRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class ReportProcessingServiceImplTest {
    private Reader reader;
    private CacheManager cacheManager;
    private SalesAndTrafficByAsinRepository salesAndTrafficByAsinRepository;
    private SalesAndTrafficByDateRepository salesAndTrafficByDateRepository;
    private ReportProcessingService service;

    @BeforeEach
    public void setUp() {
        reader = mock(Reader.class);
        cacheManager = mock(CacheManager.class);
        salesAndTrafficByDateRepository = mock(SalesAndTrafficByDateRepository.class);
        salesAndTrafficByAsinRepository = mock(SalesAndTrafficByAsinRepository.class);
        service = new ReportProcessingServiceImpl(reader, cacheManager, salesAndTrafficByAsinRepository, salesAndTrafficByDateRepository);
    }

    @Test
    public void testProcessFile() {
        var report = new Report();
        var cacheNames = List.of("name1", "name2");
        var byDate = List.of(new SalesAndTrafficByDate());
        var byAsin = List.of(new SalesAndTrafficByAsin());
        report.setSalesAndTrafficByAsin(byAsin);
        report.setSalesAndTrafficByDate(byDate);

        when(reader.readData(any(), eq(Report.class))).thenReturn(report);
        when(cacheManager.getCacheNames()).thenReturn(cacheNames);
        when(cacheManager.getCache(anyString())).thenReturn(mock(Cache.class));

        service.processFile();

        verify(salesAndTrafficByAsinRepository, times(1)).saveAll(any());
        verify(salesAndTrafficByDateRepository, times(1)).saveAll(any());
    }
}