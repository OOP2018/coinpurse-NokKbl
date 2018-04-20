package coinpurse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * MoneyUtil use to demonstrate compareTo() method, sort the coins by value of any stuff that implements Valuable
 *  or filter any stuff that implements Valuable by currency.
 * @author Kunyaruk Katebunlu
 */
public class MoneyUtil {
	private static Comparator<Valuable> cmp = new ValueComparator();
	
	/**
	 * Filter any stuff that implements Valuable by use the specified currency value.
	 * @param valuable is list of money with many currency value
	 * @param currency is currency value that want to use
	 * @return list of any stuff that implements Valuable with the same currency value
	 */
	public static <E extends Valuable> List<E> filterByCurrency(List<E> valuable, String currency) {
		List<E> moneyCopy = new ArrayList<E>();
		moneyCopy.addAll(valuable);
		for (E val : new ArrayList<>(moneyCopy)) {
			if(!val.getCurrency().equalsIgnoreCase(currency)) moneyCopy.remove(val);
		}
		return moneyCopy;
	}
	
	/**
	 * Sort list of any stuff that implements Valuable by the value of the valuable.
	 * @param valuable is the list of money that want to sort
	 */
	public static void sortMoney(List<? extends Valuable> valuable) {
		Collections.sort(valuable, cmp);
	}
	
	/**
	 * Return the larger argument, based on sort order, using
	 * the objects' own compareTo method for comparing.
	 * @param args one or more Comparable objects to compare.
	 * @return the argument that would be last if sorted the elements.
	 * @throws IllegalArgumentException if no arguments given.
	 */
	public static <E extends Comparable<? super E>> E max(E ... args) {
		if(args == null || args.length == 0) throw new IllegalArgumentException();
		E max = args[0];
		for(int n = 0;n<args.length;n++) {
			max = (args[n].compareTo(max) > 0) ? args[n] : max;
		}
		return max;
	}
	
	public static void main(String[] args) {
		String max = MoneyUtil.max("dog", "zebra", "cat");
		System.out.println(max);
		
		Money m1 = new BankNote(100, "Baht", 123);
		Money m2 = new BankNote(500, "Baht", 1234);
		Money m3 = new Coin(20, "Baht");
		Money maxx = MoneyUtil.max(m1,m2,m3);
		System.out.println(maxx);
		
		List<BankNote> list = new ArrayList<BankNote>();
		list.add(new BankNote(10.0, "USD", 10101));
		list.add(new BankNote(500.0, "Baht", 1010000));
		MoneyUtil.sortMoney(list);
		System.out.println(Arrays.toString(list.toArray()));
		
		List<Coin> coins = Arrays.asList(new Coin(5, "Baht"), new Coin(0.1, "Ringgit"), new Coin(5, "Cent"));
		List<Coin> result = MoneyUtil.filterByCurrency(coins, "Baht");
		System.out.println(Arrays.toString(result.toArray()));
	}
}
