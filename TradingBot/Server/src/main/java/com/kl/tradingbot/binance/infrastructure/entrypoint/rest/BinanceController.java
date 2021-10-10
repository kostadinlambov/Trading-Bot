package com.kl.tradingbot.binance.infrastructure.entrypoint.rest;

import com.binance.api.client.domain.account.AssetBalance;
import com.kl.tradingbot.binance.core.services.BinanceService;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/binance")
public class BinanceController {

  private final BinanceService binanceService;

  @Autowired
  public BinanceController(BinanceService binanceService) {
    this.binanceService = binanceService;
  }

  @GetMapping(value = "/getBinanceDataForUser/{email}")
  public List<AssetBalance> getBinanceDataForUser(@PathVariable String email) throws IOException {

    List<AssetBalance> binanceDataForUser = binanceService.getBinanceDataForUser(email);

    return binanceDataForUser;
  }
}
