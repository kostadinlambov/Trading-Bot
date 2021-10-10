package com.kl.tradingbot.binance.core.services;

import com.binance.api.client.domain.account.AssetBalance;
import java.io.IOException;
import java.util.List;

public interface BinanceService {

  void createBinanceClient(String email, String apiKey, String secretKey);

  List<AssetBalance> getBinanceDataForUser(String email) throws IOException;
}
