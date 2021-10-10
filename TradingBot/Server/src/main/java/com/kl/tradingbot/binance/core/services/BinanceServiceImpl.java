package com.kl.tradingbot.binance.core.services;

import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.BinanceApiWebSocketClient;
import com.binance.api.client.domain.account.AssetBalance;
import com.binance.api.client.domain.account.DepositHistory;
import com.binance.api.client.domain.market.AggTrade;
import com.binance.api.client.domain.market.Candlestick;
import com.binance.api.client.domain.market.CandlestickInterval;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class BinanceServiceImpl implements BinanceService {

  private final static Logger LOGGER = LoggerFactory.getLogger(BinanceServiceImpl.class);

  private BinanceApiClientFactory factory;
  private BinanceApiWebSocketClient webSocketClient;
  private BinanceApiRestClient binanceApiRestClient;

  //  @Value("${binance.api-key}")
  private final String apiKey;

  //  @Value("${binance.secret-key}")
  private final String secretKey;

  @Autowired
  public BinanceServiceImpl(@Value("${binance.api-key}") String apiKey,
      @Value("${binance.secret-key}") String secretKey) {
    this.apiKey = apiKey;
    this.secretKey = secretKey;

    this.factory = BinanceApiClientFactory.newInstance(apiKey, secretKey);
    this.webSocketClient = factory.newWebSocketClient();
    this.binanceApiRestClient = factory.newRestClient();
  }


  @Override
  public void createBinanceClient(String email, String apiKey, String secretKey) {
//    this.factory = BinanceApiClientFactory.newInstance(this.apiKey, this.secretKey);
    this.factory = BinanceApiClientFactory.newInstance(apiKey, secretKey);
    this.webSocketClient = factory.newWebSocketClient();
    this.binanceApiRestClient = factory.newRestClient();
  }


  @Override
  public List<AssetBalance> getBinanceDataForUser(String email) throws IOException {
    List<AssetBalance> balances = binanceApiRestClient.getAccount().getBalances();
    List<AggTrade> eur = binanceApiRestClient.getAggTrades("LTCBTC");
    DepositHistory eur1 = binanceApiRestClient.getDepositHistory("EURBTC");

//    List<TickerPrice> allPrices = binanceApiRestClient.getAllPrices();
//
//    for (TickerPrice price : allPrices) {
//      LOGGER.info(String.valueOf(price));
//    }
//
    List<Candlestick> eurbtc = binanceApiRestClient
        .getCandlestickBars("BTCEUR", CandlestickInterval.ONE_MINUTE);

    long startTime = LocalDateTime.now().minusDays(2).atZone(ZoneId.systemDefault()).toInstant()
        .toEpochMilli();

    long endTime = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

    List<Candlestick> eurBtcCandles = binanceApiRestClient
        .getCandlestickBars("BTCEUR", CandlestickInterval.FIFTEEN_MINUTES, 10, startTime,
            endTime);

    LOGGER.info("15 min Candles for BTCEUR");

    for (Candlestick candle : eurBtcCandles) {

      LOGGER.info(String.valueOf(candle));
      LOGGER.info(String.valueOf(candle));
    }

//    webSocketClient.onCandlestickEvent("ethbtc", CandlestickInterval.ONE_MINUTE,
//        response -> LOGGER.info(String.valueOf(response)));

//    webSocketClient.onAggTradeEvent("EURBT".toLowerCase(), new BinanceApiCallback<AggTradeEvent>() {
//      @Override
//      public void onResponse(final AggTradeEvent response) {
//        LOGGER.info(String.valueOf(response));
//        System.out.println(response);
//      }
//
//      @Override
//      public void onFailure(final Throwable cause) {
//        System.err.println("Web socket failed");
//        cause.printStackTrace(System.err);
//        LOGGER.error("Error", cause);
//      }
//    });

//    try (Closeable ethbtc = webSocketClient.onDepthEvent("ethbtc", (DepthEvent response) -> {
//      System.out.println(response.getAsks());
//    });) {

//    }

    return balances;
  }
}
