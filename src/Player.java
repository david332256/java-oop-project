/**
 * Program Name: Player.java Purpose: Represents a player with name, balance,
 * and bet status Coder: Oluwadarasimi Adufe and Tsz Hung Tsui 
 * Date: ...
 */

public class Player
{
	// Declare private variables:
	private String name;
	private int bankBalance;
	private boolean isShooter;
	private int betAmount;
	private boolean passCompleted;

	//Constructor that takes a String name parameter
	public Player(String name) {
		this.name  = name;
		this.bankBalance = 100;
		this.isShooter = false;
		this.betAmount = 0;
		this.passCompleted = false;
	}
	
	//Create ALL getters and setters from the UML diagram:
	public String getName() {return name;}
	public int getBankBalance() {return bankBalance;}
	public boolean getIsShooter() {return isShooter;}
	public int getBetAmount() {return betAmount;}
	public boolean getPassCompleted() {return passCompleted;}
	
	//setters
	public void setBankBalance(int newBalance) {
		this.bankBalance = newBalance;
	}
	public void setIsShooter(boolean value) {
		this.isShooter = value;
	}
	public void setBetAmount(int betAmount) {
		this.betAmount = betAmount;
	}
	public void setPassCompleted(boolean value) {
		this.passCompleted = value;
	}
}