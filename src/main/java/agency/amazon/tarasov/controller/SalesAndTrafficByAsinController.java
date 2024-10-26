package agency.amazon.tarasov.controller;

import agency.amazon.tarasov.model.SalesAndTrafficByAsin;
import agency.amazon.tarasov.service.statistic.SalesAndTrafficByAsinService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/statistic/asin")
@PreAuthorize("hasRole('ROLE_USER')")
@RequiredArgsConstructor
public class SalesAndTrafficByAsinController {
    private final SalesAndTrafficByAsinService service;

    @GetMapping
    public SalesAndTrafficByAsin getSalesAndTrafficByDate(@RequestParam String asin) {
        return service.findSalesAndTrafficByAsin(asin);
    }

    @GetMapping("/range")
    public List<SalesAndTrafficByAsin> getSalesAndTrafficByAsinRange(@RequestParam List<String> range) {
        return service.findSalesAndTrafficByAsinRange(range);
    }

    @GetMapping("/all")
    public List<SalesAndTrafficByAsin> getAllSalesAndTrafficByAsin() {
        return service.findAllSalesAndTrafficByAsin();
    }
}
