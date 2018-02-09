package coinpurse;

/**
 * Coin represents coinage (money) with a fixed value and currency.
 * @author Kunyaruk Katebunlu
 */
public class Coin extends Money {
	
	/**
	 * Make sure that the value must not be negative,
	 * if it's negative change that to be 0.
	 * @param value is value of a coin
	 * @param currency is coin's currency
	 */
	public Coin (double value, String currency) {
		super(value, currency);
	}
	
	/**
	 * toString show the coin's value and coin's currency.
	 * @return value with currency of the coin
	 */
	@Override
	public String toString () {
		if ((int) value - this.getValue() == 0) return String.format("%.0f-%s", this.getValue(), this.getCurrency());
		else return String.format("%.2f-%s", this.getValue(), this.getCurrency());
	}

}
