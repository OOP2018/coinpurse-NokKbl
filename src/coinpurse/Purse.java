package coinpurse;

import java.util.List;

import coinpurse.strategy.GreedyWithdraw;
import coinpurse.strategy.RecursiveWithdraw;
import coinpurse.strategy.WithdrawStrategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * A valuable purse contains money. You can insert money, withdraw money, check the
 * balance, and check if the purse is full.
 * @author Kunyaruk Katebunlu
 */
public class Purse {
	/** Collection of objects in the purse. */
	private List<Valuable> money;
	/**
	 * Capacity is maximum number of items the purse can hold. Capacity is set when
	 * the purse is created and cannot be changed.
	 */
	private final int capacity;
	/** default currency */
	private static String currency = "Baht";
	private Comparator<Valuable> cmp = new ValueComparator();
	private WithdrawStrategy strategy = null;
	
	/**
	 * Create a purse with a specified capacity.
	 * @param capacity is maximum number of money you can put in purse.
	 */
	public Purse(int capacity) {
		this.capacity = capacity;
		money = new ArrayList<Valuable>();
		strategy = new GreedyWithdraw();
	}
	
	public void setWithdrawStrategy(WithdrawStrategy strategy) {
		this.strategy = strategy;
	}

	/**
	 * Count and return the amount of money in the purse.
	 *  This is the amount of money, not their value.
	 * @return the amount of money in the purse
	 */
	public int count() {
		return money.size();
	}

	/**
	 * Get the total value of all items in the purse.
	 * @return the total value of items in the purse.
	 */
	public double getBalance() {
		double balance = 0;
		for (Valuable a_money : money) balance += a_money.getValue();
		return balance;
	}

	/**
	 * Return the capacity of the valuable purse.
	 * @return the capacity of purse
	 */
	public int getCapacity() {
		return this.capacity;
	}

	/**
	 * Test whether the purse is full. The purse is full if amount of items in purse
	 * equals or greater than the purse capacity.
	 * @return true if purse is full.
	 */
	public boolean isFull() {
		return money.size() >= this.getCapacity();
	}

	/**
	 * Insert money into the purse. The money is only inserted if the purse has
	 * space for it and the money has positive value. No worthless money!
	 * @param insertAmount is a Valuable object to insert into purse
	 * @return true if money inserted, false if can't insert
	 */
	public boolean insert(Valuable insertAmount) {
		boolean purseFull = isFull();

		if (purseFull || insertAmount.getValue() <= 0) return false;
		else {
			money.add(insertAmount);
			return true;
		}
	}
	
	/**
	 * Withdraw the requested amount of money that have same currency as the parameter.
	 * Return an array of Valuables withdrawn from purse, or return null if cannot
	 * withdraw the amount requested.
	 * @param amount is the amount to withdraw
	 * @return array of Valuable objects for money withdrawn, or null if cannot withdraw
	 *         requested amount.
	 */
	public Valuable[] withdraw(Valuable amount) {
		if(amount.getValue() <= 0 || amount == null || money.isEmpty() || amount.getValue() > this.getBalance()) return null;
		List<Valuable> tempList = new ArrayList<Valuable>();
		
		if(strategy.withdraw(amount, money) == null) return null;
		tempList.addAll(strategy.withdraw(amount, money));
		for (Valuable val : tempList) money.remove(val);
		Valuable[] withdrawCoin = new Valuable[tempList.size()];
		return tempList.toArray(withdrawCoin);
	}
	
	/**
	 * Withdraw the requested amount of money with default currency (Baht).
	 * Return an array of Valuables withdrawn from purse, or return null 
	 * if cannot withdraw the amount requested.
	 * @param amount is the amount to withdraw
	 * @return array of Valuable objects for money withdrawn, or null if cannot withdraw
	 *         requested amount.
	 */
	public Valuable[] withdraw(double amount) {
		return withdraw(new Money(amount, Main.init()));
	}

	/**
	 * toString returns a string description of the purse contents.
	 * @return description of the money
	 */
	public String toString() {
		return String.format("Amount of money = %d with value %.1f", this.count(), this.getBalance());
	}
}
