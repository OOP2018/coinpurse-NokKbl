package coinpurse.strategy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import coinpurse.Money;
import coinpurse.Valuable;

/**
 * RecursiveWithdraw class is a class that use recursive strategy for choose money that should withdraw.
 * @author Kunyaruk Katebunlu
 */
public class RecursiveWithdraw implements WithdrawStrategy {
	//private static List<Valuable> list = new ArrayList<Valuable>();
	
	/**
	 * Find and return items from a collection whose total value equals the requested amount.
	 * @param amount is the amount of money to withdraw, with currency
	 * @param money the contents that are available for possible withdraw.
	 * 			Must not be null, but maybe an empty list.
	 * 			This list is not modified.
	 * @return if a solution is found, return a List containing references
	 * 			from the money parameter (List) whose sum equals the amount.
	 * 			If a solution is not found, returns null.
	 */
	@Override
	public List<Valuable> withdraw(Valuable amount, List<Valuable> money) {
		
		if(amount.getValue() <= 0 || money.size() == 0) return null;
		
		Valuable first = money.get(0);
		Valuable remaining;
		List<Valuable> result;
		if(amount.getCurrency().equalsIgnoreCase(first.getCurrency())) {
			if(amount.getValue() - first.getValue() < 0) return withdraw(amount, money.subList(1, money.size()));
			remaining = new Money(amount.getValue() - first.getValue(), amount.getCurrency());
			//list.add(first);
			result = withdraw(remaining, money.subList(1, money.size()));
			if(result != null) {
				result.add(first);
				return leave(result);
			}
		}
		result = withdraw(amount, money.subList(1, money.size()));
		if(result != null) return leave(result);
		return result;
	}
	
	public List<Valuable> leave(List<Valuable> result){
		System.out.println(result.toArray());
		return result;
	}
	
	public static void main(String[] args) {
		WithdrawStrategy w = new RecursiveWithdraw();
		Money m = new Money(10.0, "Baht");
		Money n = new Money(5.0, "Baht");
		Money o = new Money(10.0, "Baht");
		Money p = new Money(10.0, "Baht");
		
		List<Valuable> x = new ArrayList<>();
		x.add(m);
		x.add(n);
		x.add(o);
		x.add(p);
		System.out.println(m.getValue());
		System.out.println(x.size());
		System.out.println(w.withdraw(new Money(20, "Baht"), x));
	}

}
