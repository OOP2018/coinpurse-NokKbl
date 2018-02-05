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
		
		coins.add(new Coin(10.0, "Baht")); // index 0
		coins.add(new Coin(0.5, "Baht")); // index 1
		coins.add(new Coin(2.0, "Baht")); // index 2
		coins.add(new Coin(0.25, "Baht")); // index 3
		
		// same value but difference currency
		coins.add(new Coin(1.0, "Baht")); // index 4
		coins.add(new Coin(1.0, "Rupie")); // index 5
		
		// negative value
		coins.add(new Coin(-1.0, "Baht")); // index 6
		
		// Test compareTo() method (unsorted value and ignore currency)
		// result < 0 : index 1 (0.5) has order before index 2 (2.0) --> yes
		System.out.println(coins.get(1).compareTo(coins.get(2)));
		// result > 0 : index 0 (10.0) has order after index 3 (0.25) --> yes
		System.out.println(coins.get(0).compareTo(coins.get(3)));
		// result = 0 : 1.0 (index 4) has the same order with 1.0 (index 5) --> yes
		System.out.println(coins.get(4).compareTo(coins.get(5)));
		System.out.println("-----------");
		
		// unsorted a list of coins
		printCoins(coins);
		System.out.println("-----------");
		
		// Filter only coin that has same currency
		List<Valuable> money = new ArrayList<Valuable>();
		money.addAll(coins);
		sortMoney(money);
		filterByCurrency(money, "Baht");
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
			if(!value.getCurrency().equals(currency)) money.remove(value);
		}
		return null;
	}
	
	/**
	 * Sort money by use the value of the valuable and print.
	 * @param val is the list of money that want to sort
	 */
	public static void sortMoney(List<Valuable> val) {
		Comparator<Valuable> cmp = new ValueComparator();
		Collections.sort(val, cmp);
	}
	
	public static void main(String[] args) {
		sortAndCompareCoin();
	}
}
