/**
 * Program Name: Die.java Purpose: Simulates a single die with values 1-6 Coder:
 * Oluwadarasimi Adufe and Tsz Hung Tsui 
 * Date: ...
 */

public class Die
{
	//Declare a private int variable called rollValue
	private int rollValue;

	//Create a constructor that sets rollValue to 0
	public Die(){
		this.rollValue = 0;
	}

	//Create a getter method for rollValue
	public int getRollValue() {return rollValue;}
	
	//Create a rollDie() method that:
	public int rollDie() {
		 int rollValue = (int)(Math.random() * 6) +1;
		 return rollValue;
	}
	
}