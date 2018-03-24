package coinpurse.strategy;

import java.util.ArrayList;
import java.util.List;

import coinpurse.Money;
import coinpurse.Valuable;

/**
 * RecursiveWithdraw class is a class that use recursive strategy for choose money that should withdraw.
 * @author Kunyaruk Katebunlu
 */
public class RecursiveWithdraw implements WithdrawStrategy {
	private static List<Valuable> result = new ArrayList<Valuable>();
	
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
		if(amount.getValue() == 0) return new ArrayList<Valuable>();
		if(amount.getValue() < 0 || money.size() == 0) return null;
		
		Valuable first = money.get(0);
		if(amount.getCurrency().equals(money.get(0).getCurrency())) {
			Valuable remaining = new Money(amount.getValue() - money.get(0).getValue(), amount.getCurrency());
			result = withdraw(remaining, money.subList(1, money.size()));
			if(result != null) {
				result.add(first);
				return result;
			}
			return withdraw(amount, money.subList(1, money.size()));
		}
		return new ArrayList<Valuable>();
	}

}
