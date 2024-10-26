package agency.amazon.tarasov.controller;

import agency.amazon.tarasov.model.SalesAndTrafficByDate;
import agency.amazon.tarasov.service.statistic.SalesAndTrafficByDateService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/statistic/date")
@PreAuthorize("hasRole('ROLE_USER')")
@RequiredArgsConstructor
public class SalesAndTrafficByDateController {
    private final SalesAndTrafficByDateService service;
    @GetMapping
    public SalesAndTrafficByDate getSalesAndTrafficByDate(@RequestParam LocalDate date) {
        return service.findSalesAndTrafficByDate(date);
    }

    @GetMapping("/range")
    public List<SalesAndTrafficByDate> getSalesAndTrafficByDateRange(@RequestParam LocalDate firstDate, @RequestParam LocalDate lastDate) {
        return service.findSalesAndTrafficByDateRange(firstDate, lastDate);
    }

    @GetMapping("/all")
    public List<SalesAndTrafficByDate> getAllSalesAndTrafficByDate() {
        return service.findAllSalesAndTrafficByDate();
    }
}
