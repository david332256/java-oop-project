
/**
 * Program Name: Game.java
 * Purpose: Represents the overall game with players and pot
 * Coder: Oluwadarasimi Adufe and Tsz Hung Tsui 
 * Date: ....
 */

import java.util.ArrayList;
import javax.swing.JOptionPane;

public class Game
{
	//Declare private variables:
	private int totalPotAmount;
	private ArrayList<Player> playerList;

	//Constructor with no parameters:
	public Game()
	{
		totalPotAmount = 0;
		playerList = new ArrayList<>();
	}

	//Create getters:
	public int getTotalPotAmount()
	{
		return totalPotAmount;
	}

	public ArrayList<Player> getPlayerList()
	{
		return playerList;
	}

	// Create populatePlayerList() method:
	public void populatePlayerList()
	{
		String input = JOptionPane.showInputDialog("How many players? (2-6):");
		int numPlayers = 0;
		boolean isValid = false;

		// Keep asking until they enter an actual number between 2 and 6
		while (!isValid)
		{
			try
			{
				// If they click 'Cancel' or close the dialog
				if (input == null)
				{
					System.exit(0);
				}

				numPlayers = Integer.parseInt(input);

				if (numPlayers >= 2 && numPlayers <= 6)
				{
					isValid = true; 
				} else
				{
					JOptionPane.showMessageDialog(null, "Error! Enter a number between 2 and 6.");
					input = JOptionPane.showInputDialog("How many players? (2-6):");
				}
			} catch (NumberFormatException e)
			{
				JOptionPane.showMessageDialog(null, "Error! Please enter a valid number, not text.");
				input = JOptionPane.showInputDialog("How many players? (2-6):");
			}
		}

		// loop and ask for their names
		for (int i = 0; i < numPlayers; i++)
		{
			String name = JOptionPane.showInputDialog("Player " + (i + 1) + ", What is your name?");

			// Handle Cancel button on name selection
			if (name == null || name.trim().isEmpty())
			{
				name = "Player " + (i + 1); 
			}

			Player p = new Player(name);
			playerList.add(p);
		}

		totalPotAmount = playerList.size() * 100;
	}

	//Create checkForGameWinner() method:
	public boolean checkForGameWinner()
	{
		for(int i = 0; i < playerList.size(); i++) {
			Player currentPlayer = playerList.get(i);
			if(currentPlayer.getBankBalance() == totalPotAmount) {
				return true;
			}			
				
		}
		return false;	
	}
}