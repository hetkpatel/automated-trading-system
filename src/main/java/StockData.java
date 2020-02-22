import yahoofinance.*;
import yahoofinance.histquotes.*;
import java.math.*;
import java.io.*;
import java.util.*;

class StockData {
  private Stock stock;
  private ArrayList<BigDecimal> gains = new ArrayList<BigDecimal>(),
		  loss = new ArrayList<BigDecimal>();
  
  StockData(Stock stockData) {
    stock = stockData;
  }

  void print() {
    stock.print();
  }

  BigDecimal price() throws IOException {
    return stock.getQuote(true).getPrice();
  }
  
  void appendGain(BigDecimal amount) {
	  gains.add(amount);
  }
  
  void appendLoss(BigDecimal amount) {
	  loss.add(amount);
  }
  
  BigDecimal avgGain() {
	  BigDecimal sum = new BigDecimal(0);
	  for (int i = 0;i < gains.size();i++) {
		  sum.add(gains.get(i));
	  }
	  return sum.divide(new BigDecimal(gains.size()));
  }
  
  BigDecimal avgLoss() {
	  BigDecimal sum = new BigDecimal(0);
	  for (int i = 0;i < loss.size();i++) {
		  sum.add(loss.get(i));
	  }
	  return sum.divide(new BigDecimal(loss.size()));
  }
  
  BigDecimal rs() {
	  return avgGain().divide(avgLoss());
  }
  
  BigDecimal rsi() {
	  return avgLoss().equals(new BigDecimal(0)) ? new BigDecimal(100) :
		  new BigDecimal(100).subtract(new BigDecimal(100).divide(rs().add(new BigDecimal(1))));
  }
}

/**
 * RSI = 100 - (100 / 1 + (avgUp/avgDnw))
 */