import yahoofinance.*;
import java.io.*;
import java.math.BigDecimal;
import java.util.*;

public class Main {

	// Enter stock tickers to keep track of here
	static String[] symbols = new String[] {"BABA", "TSLA", "S", "TMUS"};
	static Map<String, StockData> stocks = new HashMap<String, StockData>();
	
	static ArrayList<BigDecimal> lastPrice = new ArrayList<BigDecimal>();
	
	public static void main(String[] args) throws IOException {
		for (int i = 0;i < symbols.length;i++)
			stocks.put(symbols[i], new StockData(YahooFinance.get(symbols[i])));
		
		Thread thread = new Thread(new Runnable() {
			public void run() {
				int counter = 0;
				while (true) {
					try {
						if (lastPrice.size() == 0)
							for (int i = 0;i < symbols.length;i++)
								lastPrice.add(i, stocks.get(symbols[i]).price());
						else {
							System.out.println(counter);
							for (int i = 0;i < symbols.length;i++) {
								BigDecimal price = stocks.get(symbols[i]).price();
								BigDecimal last = lastPrice.get(Arrays.asList(symbols).indexOf(symbols[i]));
								System.out.println(symbols[i] + "\t" + price + "\t-->\t" + last);
								if (price.compareTo(last) == -1) {
									stocks.get(symbols[i]).appendLoss(price.subtract(last).abs());
								} else if (price.compareTo(last) == 1) {
									stocks.get(symbols[i]).appendGain(price.subtract(last).abs());
								}
								lastPrice.set(Arrays.asList(symbols).indexOf(symbols[i]), price);
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

/**
 * RSI = 100 - (100 / 1 + (avgUp/avgDnw))
 */