import yahoofinance.*;
import java.io.*;
import java.math.BigDecimal;
import java.util.*;

public class Main {

	// Enter stock tickers to keep track of here
//	static String[] symbols = new String[] {"TMUS"};
//	static Map<String, StockData> stocks = new HashMap<String, StockData>();
	static String symbol = "TMUS";
	static StockData stock;
	
//	static ArrayList<BigDecimal> lastPrice = new ArrayList<BigDecimal>();
	static BigDecimal lastPrice;
	
	public static void main(String[] args) throws IOException {
//		for (int i = 0;i < symbols.length;i++)
//			stocks.put(symbols[i], new StockData(YahooFinance.get(symbols[i])));
		stock = new StockData(YahooFinance.get(symbol));
		
		System.out.println("Count\tPrice\tChange\tGain\tLoss\tAvg Gain\tAvg Loss\tRS\tRSI");
		
		Thread thread = new Thread(new Runnable() {
			public void run() {
				int counter = 0;
				while (true) {
					try {
						if (lastPrice == null) {
							lastPrice = stock.price();
							System.out.println(counter + "\t" + lastPrice + "\t");
						} else {
							BigDecimal price = stock.price();
							BigDecimal change = price.subtract(lastPrice);
							if (change.compareTo(new BigDecimal(0)) == 1) {
								stock.appendGain(change);
								System.out.print(counter + "\t" + price + "\t" + change + "\t" + change + "\t\t");
							} else if (change.compareTo(new BigDecimal(0)) == -1) {
								stock.appendLoss(change.abs());
								System.out.print(counter + "\t" + price + "\t" + change + "\t\t" + change.abs() + "\t");
							}
							lastPrice = price;
							if (counter >= 14) {
								System.out.print(stock.avgGain() + "\t" + stock.avgLoss() + "\t" + stock.rs() + "\t" + stock.rsi());
							}
							System.out.println();
						}
						counter++;
						Thread.sleep(15000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});
		thread.start();
	}
}