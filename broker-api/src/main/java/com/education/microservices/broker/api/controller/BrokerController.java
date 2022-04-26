package com.education.microservices.broker.api.controller;

import com.education.microservices.broker.api.dto.ShareDto;
import com.education.microservices.broker.api.service.BrokerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Locale;

@RestController
@RequiredArgsConstructor
@RequestMapping("/broker/api/shares")
public class BrokerController {

    private final BrokerService brokerService;

    @PostMapping("/{ticker}")
    public ResponseEntity<ShareDto> addNewShare(@PathVariable String ticker) {
        return ResponseEntity.ok(brokerService.findNeedFigiAndAddToMongo(ticker));
    }

    @GetMapping("/name/{ticker}")
    public ResponseEntity<List<ShareDto>> findByTicker(@PathVariable String ticker) {
        return ResponseEntity.ok(brokerService.findShareByName(ticker.toUpperCase(Locale.ROOT)));
    }

    @GetMapping("/time/{date}")
    public ResponseEntity<List<ShareDto>> findByTime(@PathVariable String date) {
        return ResponseEntity.ok(brokerService.findShareByTime(date));
    }

    @GetMapping("")
    public ResponseEntity<List<ShareDto>> findByTimeAndTicker(@RequestParam String time,
                                                              @RequestParam String ticker) {
        return ResponseEntity.ok(brokerService.findShareByNameAndDateQuery(
                ticker.toUpperCase(Locale.ROOT),time));
    }

    @GetMapping("/risk")
    public ResponseEntity<List<ShareDto>> findNeesShareForUser() {
        return ResponseEntity.ok(brokerService.findNeedFigiForUser());
    }

    @GetMapping("/one")
    public ResponseEntity<String> sendOneFigi() {
        brokerService.findOneFigiForUser();
        return ResponseEntity.ok("Send");
    }
}