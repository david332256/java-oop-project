
/**
 * Program Name: CrapsGUI.java
 * Purpose: Main GUI for the Craps game
 * Coder: Oluwadarasimi Adufe and Tsz Hung Tsui 
 * Date: ...
 */

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class CrapsGUI extends JFrame
{
	// ===== CLASS-WIDE VARIABLES =====
	private Game game;
	private Pass currentPass;
	private Die die1, die2;
	private int currentPlayerIndex;

	// Declare menu components (JMenuBar, JMenu, JMenuItem)
	private JMenuBar menuBar = new JMenuBar();
	private JMenu instructionsMenu = new JMenu("Instructions");
	private JMenu aboutMenu = new JMenu("About");
	private JMenuItem rulesItem = new JMenuItem("Rules");
	private JMenuItem aboutItem = new JMenuItem("About");

	// Declare GUI components (JPanel, JLabel, JButton, JTextArea)
	private JPanel mainPanel;
	private JPanel playerPanel;
	private JPanel dicePanel;
	private JPanel controlPanel;
	private JPanel statusPanel;

	private JButton rollButton;
	private JButton betButton;
	private JButton passButton;

	private JLabel die1Label;
	private JLabel die2Label;
	private JLabel rollTotoLabel;
	private JLabel statusLabel;
	private JButton crapsButton;
	private JTextArea crapsInputField;

	private ArrayList<JPanel> playerPanels;
	private int currentPoint;
	private int actionCovered;

	// ===== CONSTRUCTOR =====
	public CrapsGUI()
	{
		super("Craps Game - Roll the Bones!");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(500, 400);
		this.setLocationRelativeTo(null);
		this.setLayout(new BorderLayout(10, 10));

		game = new Game();
		game.populatePlayerList();
		ArrayList<Player> players = game.getPlayerList();
		players.get(0).setIsShooter(true);
		currentPlayerIndex = 0;
		

		// Build the menu
		instructionsMenu.add(rulesItem);
		aboutMenu.add(aboutItem);
		menuBar.add(instructionsMenu);
		menuBar.add(aboutMenu);
		setJMenuBar(menuBar);

		// Build the GUI layout
		mainPanel = new JPanel(new BorderLayout());
		playerPanel = new JPanel(new GridLayout(0, 3));
		dicePanel = new JPanel(new FlowLayout());
		controlPanel = new JPanel(new FlowLayout());
		statusPanel = new JPanel(new FlowLayout());

		// CREATE buttons FIRST
		rollButton = new JButton("Roll Dice");
		betButton = new JButton("Place Bet");
		passButton = new JButton("Pass Dice");

		// CREATE labels FIRST
		die1Label = new JLabel("Die 1: 0");
		die2Label = new JLabel("Die 2: 0");
		rollTotoLabel = new JLabel("Total: 0");
		statusLabel = new JLabel("Welcome to Craps!");

		// THEN add them to panels
		dicePanel.add(die1Label);
		dicePanel.add(die2Label);
		dicePanel.add(rollTotoLabel);

		controlPanel.add(rollButton);
		controlPanel.add(betButton);
		controlPanel.add(passButton);

		// Initialize text area
		crapsInputField = new JTextArea(5, 30);
		crapsInputField.setEditable(false);

		// Create listener and register
		GameHandler nanny = new GameHandler();
		rulesItem.addActionListener(nanny);
		aboutItem.addActionListener(nanny);

		rollButton.addActionListener(nanny);
		betButton.addActionListener(nanny);
		passButton.addActionListener(nanny);

		statusPanel.add(statusLabel);

		// Create player panels DYNAMICALLY
		playerPanels = new ArrayList<>();
		ArrayList<Player> playerss = game.getPlayerList();
		for (int i = 0; i < playerss.size(); i++)
		{
			JPanel pPanel = new JPanel(new GridLayout(4, 1));
			pPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			pPanel.add(new JLabel("Player " + (i + 1) + ": " + playerss.get(i).getName()));
			pPanel.add(new JLabel("Balance: $" + playerss.get(i).getBankBalance()));
			pPanel.add(new JLabel("Bet: $0"));
			pPanel.add(new JLabel("Status: Waiting"));
			playerPanels.add(pPanel);
			playerPanel.add(pPanel);
		}
		
		updatePlayerPanels();

		// Add everything to mainPanel cleanly
		mainPanel.add(dicePanel, BorderLayout.NORTH);
		mainPanel.add(playerPanel, BorderLayout.CENTER);

		// Combine control and status at the bottom so they don't overwrite each other
		JPanel bottomContainer = new JPanel(new BorderLayout());
		bottomContainer.add(controlPanel, BorderLayout.CENTER);
		bottomContainer.add(statusPanel, BorderLayout.SOUTH);
		mainPanel.add(bottomContainer, BorderLayout.SOUTH);

		// Add to frame
		add(mainPanel);

		// Set visible
		this.setVisible(true);
	}

	private void updatePlayerPanels()
	{
		playerPanel.removeAll();
		ArrayList<Player> player = game.getPlayerList();

		for (int i = 0; i < player.size(); i++)
		{
			JPanel pPanel = new JPanel(new GridLayout(4, 1));
			pPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

			String status = "Waiting";
			if (player.get(i).getIsShooter())
			{
				status = "SHOOTER";
			}
			if (player.get(i).getBankBalance() <= 0)
			{
				pPanel.setBackground(Color.RED);
				status = "OUT!";
			}

			pPanel.add(new JLabel("Player " + (i + 1) + ": " + player.get(i).getName()));
			pPanel.add(new JLabel("Balance: $" + player.get(i).getBankBalance()));
			pPanel.add(new JLabel("Bet: $" + player.get(i).getBetAmount()));
			pPanel.add(new JLabel("Status: " + status));

			playerPanel.add(pPanel);
		}

		playerPanel.revalidate();
		playerPanel.repaint();
	}

	private void resetAllBets()
	{
		ArrayList<Player> players = game.getPlayerList();
		for (Player p : players)
		{
			p.setBetAmount(0);
		}
		actionCovered = 0;
	}

	// ===== INNER CLASS LISTENER =====
	private class GameHandler implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent ev)
		{
			String command = ev.getActionCommand();

			// ===== HANDLE MENU ITEMS =====
			if (command.equals("Rules"))
			{
				// Show rules in JOptionPane
				JOptionPane.showMessageDialog(null,
						"CRAPS RULES:\n" + "1. Roll 7 or 11 = WIN!\n" + "2. Roll 2, 3, or 12 = LOSE!\n"
								+ "3. Roll 4,5,6,8,9,10 = POINT!\n" + "4. Roll point again before 7 = WIN!\n"
								+ "5. Roll 7 before point = LOSE!");
			} 
			
			else if (command.equals("About"))
			{
				// Show coder names
				JOptionPane.showMessageDialog(null, "Craps Game\n" + "Coded by: Oluwadarasimi Adufe and Tsz Hung Tsui");
			}

			else if (command.equals("Roll Dice"))
			{
				// Create dice if they don't exist
				if (die1 == null)
				{
					die1 = new Die();
				}
				if (die2 == null)
				{
					die2 = new Die();
				}

				// Roll both dice
				int roll1 = die1.rollDie();
				int roll2 = die2.rollDie();

				// Update labels
				die1Label.setText("Die 1: " + roll1);
				die2Label.setText("Die 2: " + roll2);

				// Calculate and display total
				int total = roll1 + roll2;
				rollTotoLabel.setText("Total: " + total);

				// Check if we have a point
				if (currentPoint == 0)
				{
					// First roll or point was reset - check for instant win/lose
					if (total == 7 || total == 11)
					{
						statusLabel.setText("You WIN! Total: " + total);
						if (currentPass != null)
						{
							currentPass.settleBets(true, game.getPlayerList());
							resetAllBets();
						}
						updatePlayerPanels();
						
						if (game.checkForGameWinner())
						{
							ArrayList<Player> players = game.getPlayerList();
							String winnerName = "";
							for (Player p : players)
							{
								if (p.getBankBalance() == game.getTotalPotAmount())
								{
									winnerName = p.getName();
									break;
								}
							}
							JOptionPane.showMessageDialog(null, "GAME OVER!\n" + winnerName + " wins all the money!\n"
									+ "Total winnings: $" + game.getTotalPotAmount());
							System.exit(0);
						}
						
						int choice = JOptionPane.showConfirmDialog(null, "You won! Do you want to shoot again?", "Shoot Again?",
								JOptionPane.YES_NO_OPTION);

						if (choice == JOptionPane.NO_OPTION)
						{
							// Pass the dice
							ArrayList<Player> players = game.getPlayerList();
							currentPlayerIndex++;
							if (currentPlayerIndex >= players.size())
							{
								currentPlayerIndex = 0;
							}
							for (Player p : players)
							{
								p.setIsShooter(false);
							}
							players.get(currentPlayerIndex).setIsShooter(true);
							currentPoint = 0;
							statusLabel.setText(players.get(currentPlayerIndex).getName() + " is now the shooter!");
							updatePlayerPanels();
						}
					} 
					else if (total == 2 || total == 3 || total == 12)
					{
						statusLabel.setText("You LOSE! Total: " + total);
						if (currentPass != null)
						{
							currentPass.settleBets(false, game.getPlayerList());
							resetAllBets();
						}
						updatePlayerPanels();
						if (game.checkForGameWinner())
						{
							ArrayList<Player> players = game.getPlayerList();
							String winnerName = "";
							for (Player p : players)
							{
								if (p.getBankBalance() == game.getTotalPotAmount())
								{
									winnerName = p.getName();
									break;
								}
							}
							JOptionPane.showMessageDialog(null, "GAME OVER!\n" + winnerName + " wins all the money!\n"
									+ "Total winnings: $" + game.getTotalPotAmount());
							System.exit(0);
						}
					} 
					else
					{
						currentPoint = total;
						statusLabel.setText("POINT: " + currentPoint + "! Roll again!");
					}
				} 
				else
				{
					// We have a point - check if player wins or loses
					if (total == currentPoint)
					{
						statusLabel.setText("Made POINT! You WIN!");
						currentPoint = 0;
						if (currentPass != null)
						{
							currentPass.settleBets(true, game.getPlayerList());
							resetAllBets();
						}
						updatePlayerPanels();
						
						if (game.checkForGameWinner())
						{
							ArrayList<Player> players = game.getPlayerList();
							String winnerName = "";
							for (Player p : players)
							{
								if (p.getBankBalance() == game.getTotalPotAmount())
								{
									winnerName = p.getName();
									break;
								}
							}
							JOptionPane.showMessageDialog(null, "GAME OVER!\n" + winnerName + " wins all the money!\n"
									+ "Total winnings: $" + game.getTotalPotAmount());
							System.exit(0);
						}
						
						int choice = JOptionPane.showConfirmDialog(null, "You won! Do you want to shoot again?", "Shoot Again?",
								JOptionPane.YES_NO_OPTION);

						if (choice == JOptionPane.NO_OPTION)
						{
							ArrayList<Player> players = game.getPlayerList();
							currentPlayerIndex++;
							if (currentPlayerIndex >= players.size())
							{
								currentPlayerIndex = 0;
							}
							for (Player p : players)
							{
								p.setIsShooter(false);
							}
							players.get(currentPlayerIndex).setIsShooter(true);
							currentPoint = 0;
							statusLabel.setText(players.get(currentPlayerIndex).getName() + " is now the shooter!");
							updatePlayerPanels();
						}
					} 
					else if (total == 7)
					{
						statusLabel.setText("Sevens out! You LOSE!");
						currentPoint = 0;
						if (currentPass != null)
						{
							currentPass.settleBets(false, game.getPlayerList());
							resetAllBets();
						}
						updatePlayerPanels();
						
						if (game.checkForGameWinner())
						{
							ArrayList<Player> players = game.getPlayerList();
							String winnerName = "";
							for (Player p : players)
							{
								if (p.getBankBalance() == game.getTotalPotAmount())
								{
									winnerName = p.getName();
									break;
								}
							}
							JOptionPane.showMessageDialog(null, "GAME OVER!\n" + winnerName + " wins all the money!\n"
									+ "Total winnings: $" + game.getTotalPotAmount());
							System.exit(0);
						}
					} 
					else
					{
						statusLabel.setText("Roll again! Point is " + currentPoint);
					}
				}
			} 
			
			else if (command.equals("Place Bet"))
			{
				ArrayList<Player> players = game.getPlayerList();
				Player currentPlayer = players.get(currentPlayerIndex);

				String input = JOptionPane.showInputDialog("How much do you want to bet? (Multiples of 10 only)");
				int bet = Integer.parseInt(input);

				if (bet % 10 != 0)
				{
					JOptionPane.showMessageDialog(null, "Bet must be a multiple of 10!");
				}
				else if (bet > currentPlayer.getBankBalance())
				{
					JOptionPane.showMessageDialog(null,
							"You don't have enough money! You have: $" + currentPlayer.getBankBalance());
				} 
				else if (bet < 10)
				{
					JOptionPane.showMessageDialog(null, "Minimum bet is $10!");
				}
				else
				{
					currentPlayer.setBetAmount(bet);
					statusLabel.setText(currentPlayer.getName() + " bet $" + bet);
					actionCovered = 0;
					currentPass = new Pass(currentPlayerIndex, bet, 0);

					// ===== OPPONENT BETTING =====
					for (int i = 0; i < players.size(); i++)
					{
						if (i != currentPlayerIndex && players.get(i).getBankBalance() > 0)
						{
							int opponentBet = 0;
							boolean validBet = false;

							while (!validBet)
							{
								String oppInput = JOptionPane.showInputDialog(
										players.get(i).getName() + ", how much to bet against the shooter? (0 to skip, multiples of 10)");
								opponentBet = Integer.parseInt(oppInput);

								if (opponentBet == 0)
								{
									validBet = true;
								} 
								else if (opponentBet % 10 != 0)
								{
									JOptionPane.showMessageDialog(null, "Bet must be a multiple of 10!");
								} 
								else if (opponentBet < 10)
								{
									JOptionPane.showMessageDialog(null, "Minimum bet is $10!");
								} 
								else if (opponentBet > players.get(i).getBankBalance())
								{
									JOptionPane.showMessageDialog(null, "You don't have enough money!");
								} 
								else if (actionCovered + opponentBet > bet)
								{
									JOptionPane.showMessageDialog(null,
											"Not enough action left! Only $" + (bet - actionCovered) + " available.");
								} 
								else
								{
									validBet = true;
								}
							}

							if (opponentBet > 0)
							{
								players.get(i).setBetAmount(opponentBet);
								actionCovered += opponentBet;
								statusLabel.setText(players.get(i).getName() + " bet $" + opponentBet + " against shooter");
							}
						}
					}

					// Update Pass object with final actionCovered
					currentPass = new Pass(currentPlayerIndex, bet, actionCovered);

					if (actionCovered >= bet)
					{
						statusLabel.setText("ACTION COVERED! No more bets!");
					} else
					{
						statusLabel.setText("Action available: $" + (bet - actionCovered));
					}

					updatePlayerPanels();
				}
			}
			
			else if (command.equals("Pass Dice"))
			{
				// Move to next player
				ArrayList<Player> players = game.getPlayerList();
				currentPlayerIndex++;
				if (currentPlayerIndex >= players.size())
				{
					currentPlayerIndex = 0;
				}

				// Reset shooter status
				for (Player p : players)
				{
					p.setIsShooter(false);
				}

				// Set new shooter
				players.get(currentPlayerIndex).setIsShooter(true);
				currentPoint = 0;

				statusLabel.setText(players.get(currentPlayerIndex).getName() + " is now the shooter!");
				updatePlayerPanels();
			}
		}
	}

	// ===== MAIN METHOD =====
	public static void main(String[] args)
	{
		// Create CrapsGUI object
		new CrapsGUI();
	}
}