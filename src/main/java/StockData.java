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
}
