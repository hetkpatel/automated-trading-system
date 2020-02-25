import yahoofinance.*;
import java.io.*;
import java.util.*;

public class Main {
	
	// Enter stock tickers to keep track of here
	static String[] symbols = new String[] {"TMUS", "TSLA", "S"};
	static Map<String, StockData> stocks = new HashMap<String, StockData>();
	
	public static void main(String[] args) throws IOException {
		for (int i = 0;i < symbols.length;i++)
			stocks.put(symbols[i], new StockData(YahooFinance.get(symbols[i])));
		
		System.out.println("Note: RSI values will show after 3.5 min");
		System.out.println("Watchlist: " + Arrays.toString(symbols));
		System.out.println("Ticker\tRSI");
		
		Thread thread = new Thread(new Runnable() {
			public void run() {
				int counter = 0;
				while (true) {
					try {
						for (String ticker : symbols)
							stocks.get(ticker).tick(counter);
						if (counter >= 14) {
							for (String ticker : symbols)
								System.out.println(ticker + "\t" + stocks.get(ticker).rsi());
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