package agency.amazon.tarasov.service.processing;

import agency.amazon.tarasov.dto.Report;
import agency.amazon.tarasov.reader.Reader;
import agency.amazon.tarasov.repository.SalesAndTrafficByAsinRepository;
import agency.amazon.tarasov.repository.SalesAndTrafficByDateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Log4j2
public class ReportProcessingServiceImpl implements ReportProcessingService {
    private final Reader reader;
    private final CacheManager cacheManager;
    private final SalesAndTrafficByAsinRepository salesAndTrafficByAsinRepository;
    private final SalesAndTrafficByDateRepository salesAndTrafficByDateRepository;
    @Value("${reports.file.path}")
    private String filePath;
    @Override
    @Scheduled(fixedRateString = "${schedule.time}")
    public void processFile() {
        log.info("started processing file {}", filePath);
        var report = reader.readData(filePath, Report.class);
        salesAndTrafficByAsinRepository.saveAll(report.getSalesAndTrafficByAsin());
        salesAndTrafficByDateRepository.saveAll(report.getSalesAndTrafficByDate());
        log.info("ended processing file {}", filePath);
        cacheManager.getCacheNames().forEach(cacheName -> Objects.requireNonNull(cacheManager.getCache(cacheName)).clear());
    }
}
