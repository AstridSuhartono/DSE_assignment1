package unisa.dse.a1.students;

import java.util.HashMap;
import java.util.Scanner;

import unisa.dse.a1.interfaces.ListGeneric;

public class SecuritiesExchange {

	/**
	 * Exchange name
	 */
	private String name;

	public String getName() {
		return this.name;
	}

	/**
	 * List of brokers on this exchange
	 */
	public ListGeneric<StockBroker> brokers;

	/**
	 * List of announcements of each trade processed
	 */
	public ListGeneric<String> announcements;

	/**
	 * HashMap storing the companies, stored based on their company code as the key
	 */
	public HashMap<String, ListedCompany> companies;

	/**
	 * Initialises the exchange ready to handle brokers, announcements, and
	 * companies
	 * 
	 * @param name
	 */
	public SecuritiesExchange(String name) {
		this.name = name;
		this.brokers = null;
		this.announcements = null;
		this.companies = null;
	}

	/**
	 * Adds the given company to the list of listed companies on the exchange
	 * 
	 * @param company
	 * @return true if the company was added, false if it was not
	 */
	public boolean addCompany(ListedCompany company) {
		if (company == null) {
			return false;
		}
		if (companies == null) {
			companies = new HashMap<>();
			companies.put(company.getCode(), company);
			return true;
		} else if (companies != null && !companies.containsValue(company)) {
			companies.put(company.getCode(), company);
			return true;
		}
		return false;
	}

	/**
	 * Adds the given broke to the list of brokers on the exchange
	 * 
	 * @param company
	 */
	public boolean addBroker(StockBroker broker) {
		if (broker == null) {
			return false;
		}
		if (brokers == null) {
			brokers = new DSEListGeneric<StockBroker>();
			return brokers.add(broker);
		} else if (brokers != null && !brokers.contains(broker)) {
			return brokers.add(broker);
		}
		return false;
	}

	/**
	 * Process the next trade provided by each broker, processing brokers starting
	 * from index 0 through to the end
	 * 
	 * If the exchange has three brokers, each with trades in their queue, then
	 * three trades will processed, one from each broker.
	 * 
	 * If a broker has no pending trades, that broker is skipped
	 * 
	 * Each processed trade should also make a formal announcement of the trade to
	 * the announcements list in the form a string: "Trade: QUANTITY COMPANY_CODE @
	 * PRICE_BEFORE_TRADE via BROKERNAME", e.g. "Trade: 100 DALL @ 99 via Honest
	 * Harry Broking" for a sale of 100 DALL shares if they were valued at $99 Price
	 * shown should be the price of the trade BEFORE it's processed. Each trade
	 * should add its announcement at the end of the announcements list
	 * 
	 * @return The number of successful trades completed across all brokers
	 * @throws UntradedCompanyException when traded company is not listed on this
	 *                                  exchange
	 */
	public int processTradeRound() throws UntradedCompanyException {
		int noOfTrade = 0;
		SecuritiesExchange secExchange;
		if (companies.containsKey(brokers)) {
			String unlistedCompanyCode;
			throw new UntradedCompanyException(unlistedCompanyCode);
		}
		for (int index = 0 ; index < brokers.size(); index++ ) {
			  Trade trade = brokers.get(index).getNextTrade();
			  while(trade != null) {
				  int quantity = trade.getShareQuantity();
				  String companyCode = trade.getCompanyCode();
				  String brokerName = trade.getStockBroker().getName();
				  ListedCompany company = companies.get(companyCode);
				  int price = company.getCurrentPrice();
				  announcements = new DSEListGeneric<String>();
				  announcements.add("Trade: " + quantity + " " + companyCode + " @ " + price + " via " +brokerName);
				  noOfTrade++;
			  }
		}
		  return noOfTrade;
	}

	public int runCommandLineExchange(Scanner sc) {

	}
}
