package coinpurse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * MoneyUtil use to demonstrate compareTo() method,
 *  sort the coins by value of the coins
 *  or currency value and print list of coins.
 * @author Kunyaruk Katebunlu
 */
public class MoneyUtil {
	private static Comparator<Valuable> cmp = new ValueComparator();
	
	/**
	 * Print a list of the coins.
	 * @param coins is list of coins that need to print
	 */
	public static void printCoins(List<Coin> coins) {
		for (Coin coin : coins) System.out.print(coin+ " ");
		System.out.println();
	}
	
	/**
	 * Print a list of the money.
	 * @param val is list of money that need to print
	 */
	public static void printVal(List<Valuable> val) {
		for (Valuable valuable : val) {
			if(valuable.getValue() == 0) System.out.print("");
			else System.out.print(valuable+" ");
		}
		System.out.println();
	}
	
	/**
	 * To demonstrate that compareTo() method is correct and
	 * sort the coins by value.
	 */
	public static void sortAndCompareCoin() {
		List<Coin> coins = new ArrayList<Coin>();
		
		coins.add(new Coin(10.0, "Baht"));
		coins.add(new Coin(0.5, "Baht"));
		coins.add(new Coin(2.0, "Baht"));
		coins.add(new Coin(0.25, "Baht"));
		
		// same value but difference currency
		coins.add(new Coin(1.0, "Baht"));
		coins.add(new Coin(1.0, "Rupie"));
		coins.add(new Coin(1.0, "baht"));
		
		// unsorted a list of coins
		System.out.println("Unsorted money");
		printCoins(coins);
				
		System.out.println("-----------");
		// sorted
		Collections.sort(coins, cmp);
		System.out.println("Sorted money");
		printCoins(coins);
		
		System.out.println("-----------");
		// Test compareTo() method
		System.out.printf("%.2f compare to %.2f\n", coins.get(0).getValue(), coins.get(2).getValue());
		System.out.println("Result: " + coins.get(0).compareTo(coins.get(2)));
		System.out.printf("%.2f compare to %.2f\n", coins.get(3).getValue(), coins.get(1).getValue());
		System.out.println("Result: " + coins.get(3).compareTo(coins.get(1)));
		System.out.printf("%.2f compare to %.2f\n", coins.get(4).getValue(), coins.get(4).getValue());
		System.out.println("Result: " + coins.get(4).compareTo(coins.get(4)));
		
		System.out.println("-----------");
		
		// Filter only coin that has same currency
		List<Valuable> money = new ArrayList<Valuable>();
		money.addAll(coins);
		sortMoney(money);
		filterByCurrency(money, "Baht");
		System.out.println("Filter money");
		printVal(money);
	}
	
	/**
	 * Filter coins by use the specified currency value of the coins.
	 * @param coins is list of coins with many currency value
	 * @param currency is currency value that want to use
	 * @return list of coins with the same currency value
	 */
	public static List<Valuable> filterByCurrency(List<Valuable> money, String currency) {
		for (Valuable value : new ArrayList<>(money)) {
			if(!value.getCurrency().equalsIgnoreCase(currency)) money.remove(value);
		}
		return null;
	}
	
	/**
	 * Sort money by use the value of the valuable and print.
	 * @param val is the list of money that want to sort
	 */
	public static void sortMoney(List<Valuable> val) {
		Collections.sort(val, cmp);
	}
	
	public static void main(String[] args) {
		sortAndCompareCoin();
	}
}
