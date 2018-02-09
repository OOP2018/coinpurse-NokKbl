package coinpurse;

import java.util.Comparator;

/**
 * Compare two objects that implement Valuable by currency.
 * If both objects have the same currency, order them by value.
 * @author Kunyaruk Katebunlu
 */
public class ValueComparator implements Comparator<Valuable>{

	/**
	 * Compare two objects that implement Valuable.
	 * First compare them by currency, so that "Baht" < "Dollar".
	 * If both objects have the same currency, order them by value.
	 * @return comparison's result
	 */
	@Override
	public int compare(Valuable a, Valuable b) {
		if(a.getCurrency().equalsIgnoreCase(b.getCurrency())) {
			double compare = a.getValue() - b.getValue();
			
			if (compare < 0) return -1;
			else if (compare > 0) return 1;
			else return 0;
		}
		return (a.getCurrency()).compareToIgnoreCase(b.getCurrency());
	}

}
