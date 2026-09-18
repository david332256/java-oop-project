/**
 * Program Name: Pass.java
 * Purpose: Represents one round/pass of the game
 * Coder: Oluwadarasimi Adufe and Tsz Hung Tsui 
 * Date: [DATE]
 */

import java.util.ArrayList;

import javax.swing.JOptionPane;

public class Pass
{
	//Declare private variables:
	private int shooterID;
	private int actionAmount;
	private int actionAmountCovered;
	private boolean shooterWin;

	//Constructor with parameters: shooterID, actionAmount,
	public Pass(int shooterID, int actionAmount, int actionAmountCovered) {
		this.shooterID = shooterID;
		this.actionAmount = actionAmount;
		this.actionAmountCovered = actionAmountCovered;
		this.shooterWin = false;
	}
	
	//Create ALL getters and setters from UML:
	public int getShooterID() {return shooterID;}
	public int getActionAmount() {return actionAmount;}
	public int getActionAmountCovered() {return actionAmountCovered;}
	public boolean getShooterWinOrLose() {return shooterWin;}
	
	//setters
	public void setShooterID(int index) {this.shooterID = index;}
	public void setActionAmount(int amount) {this.actionAmount = amount;}
	public void setActionAmountCovered(int amount) {this.actionAmountCovered = amount;}
	public void setShooterWinOrLose(boolean winOrLose) {this.shooterWin = winOrLose;}


	// Create settleBets() method:
	public void settleBets(boolean shooterWin, ArrayList<Player> playerList)
	{
		for(int i = 0; i < playerList.size(); i++)
		{
			Player currentPlayer = playerList.get(i);
			
			if(currentPlayer.getIsShooter())
			{
				if(shooterWin) {
					int newBalance = currentPlayer.getBankBalance() + actionAmountCovered;
					currentPlayer.setBankBalance(newBalance);
				}
				else 
				{
					int nnewBalance = currentPlayer.getBankBalance() - actionAmountCovered;
					currentPlayer.setBankBalance(nnewBalance);
				}
			}
			else {
				if(shooterWin) {
					int newBalance = currentPlayer.getBankBalance() - currentPlayer.getBetAmount();
					currentPlayer.setBankBalance(newBalance);
				}
				else {
					int nnewBalance = currentPlayer.getBankBalance() + currentPlayer.getBetAmount();
					currentPlayer.setBankBalance(nnewBalance);
				}
			}
		}
	}

	//Create shootOrPass() method:
	public boolean shootOrPass() {
		if (shooterWin)
		{
			int choice = JOptionPane.showConfirmDialog(null, "You won! Do you want to shoot again?", "Shoot Again?",
					JOptionPane.YES_NO_OPTION);

			if (choice == JOptionPane.YES_OPTION)
			{
				return true; // Keep shooting
			} else
			{
				return false; // Pass the dice
			}
		}
		else {
			return false;
		}
	}
}