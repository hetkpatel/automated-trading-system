import yahoofinance.*;
import java.math.*;
import java.io.*;
import java.util.*;

class StockData {
  private Stock stock;
  private ArrayList<BigDecimal> gains = new ArrayList<BigDecimal>(),
		  loss = new ArrayList<BigDecimal>();
  private BigDecimal lastPrice;
  
  StockData(Stock stockData) {
	  stock = stockData;
	  lastPrice = stock.getQuote().getPrice();
  }
  
  void init() {
	  System.out.print("0\t" + lastPrice.round(MathContext.DECIMAL32));
  }
  
  void tick(int counter) throws IOException {
	  if (counter != 0) {
		BigDecimal price = price();
		BigDecimal change = price.subtract(lastPrice);
		if (change.compareTo(new BigDecimal(0)) == 1) {
			appendGain(change);
			System.out.print(counter + "\t" + price.round(MathContext.DECIMAL64) + "\t" + change + "\t" + change + "\t\t");
		} else if (change.compareTo(new BigDecimal(0)) == -1) {
			appendLoss(change.abs());
			System.out.print(counter + "\t" + price.round(MathContext.DECIMAL64) + "\t" + change + "\t\t" + change.abs() + "\t");
		} else {
			System.out.print(counter + "\t" + price.round(MathContext.DECIMAL64) + "\t" + change + "\t\t\t\t\t");
		}
		lastPrice = price;
	  }
  }
  
  void calculateRsi() {
	  System.out.print(avgGain() + "\t" + avgLoss() + "\t" + rs() + "\t" + rsi());
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
	  BigDecimal sum = BigDecimal.ZERO;
	  for (int i = 0;i < gains.size();i++) {
		  sum = sum.add(gains.get(i));
	  }
	  return sum.divide(new BigDecimal(14), 4, RoundingMode.HALF_UP);
  }
  
  BigDecimal avgLoss() {
	  BigDecimal sum = BigDecimal.ZERO;
	  for (int i = 0;i < loss.size();i++) {
		  sum = sum.add(loss.get(i));
	  }
	  return sum.divide(new BigDecimal(14), 4, RoundingMode.HALF_UP);
  }
  
  BigDecimal rs() {
	  return avgGain().divide(avgLoss(), 4, RoundingMode.HALF_UP);
  }
  
  BigDecimal rsi() {
	  return avgLoss().equals(BigDecimal.ZERO) ? new BigDecimal(100) :
		  new BigDecimal(100).subtract(new BigDecimal(100).divide(rs().add(new BigDecimal(1)), 4, RoundingMode.HALF_UP));
  }
  
  @Override
  public String toString() {
	  stock.print();
	  return "";
  }
}

/**
 * RSI = 100 - (100 / 1 + (avgUp/avgDnw))
 */