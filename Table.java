import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.FileWriter;
import java.io.IOException;  // Import the IOException class to handle errors
import java.util.*;
import java.util.Random;
/**
*	This GUI assumes that you are using a 52 card deck and that you have 13 sets in the deck.
*	The GUI is simulating a playing table
	@author Patti Ordonez
*/
public class Table extends JFrame implements ActionListener 
{
	final static int numDealtCards = 9;
	JPanel player1;
	JPanel player2;
	JPanel deckPiles;
	JLabel deck;
	JLabel stack;
	JList p1HandPile;
	JList p2HandPile;
	Deck cardDeck;
	MyStack stackDeck;

	SetPanel [] setPanels = new SetPanel[13];
	JLabel topOfStack;
	JLabel deckPile;
	JButton p1Stack;
	JButton p2Stack;

	JButton p1Deck;
	JButton p2Deck;

	JButton p1Lay;
	JButton p2Lay;

	JButton p1LayOnStack;
	JButton p2LayOnStack;

	JButton turnIsOverp1;
	JButton turnIsOverp2;

	JButton AutoPlay;

	DefaultListModel p1Hand;
	DefaultListModel p2Hand;

	private void deal(Card [] cards)
	{
		for(int i = 0; i < cards.length; i ++)
			cards[i] = (Card)cardDeck.dealCard();
	}

	public Table()
	{
		super("The Card Game of the Century");

		setLayout(new BorderLayout());
		setSize(1200,700);


		cardDeck = new Deck();

		for(int i = 0; i < Card.suit.length; i++){
			for(int j = 0; j < Card.rank.length; j++){
				Card card = new Card(Card.suit[i],Card.rank[j]);
				cardDeck.addCard(card);
			}
		}
		cardDeck.shuffle();
		stackDeck = new MyStack();
		

		JPanel top = new JPanel();

		for (int i = 0; i < Card.rank.length;i++)
			setPanels[i] = new SetPanel(Card.getRankIndex(Card.rank[i]));


		top.add(setPanels[0]);
		top.add(setPanels[1]);
		top.add(setPanels[2]);
		top.add(setPanels[3]);

		player1 = new JPanel();

		player1.add(top);

		add(player1, BorderLayout.NORTH);
		JPanel bottom = new JPanel();


		bottom.add(setPanels[4]);
		bottom.add(setPanels[5]);
		bottom.add(setPanels[6]);
		bottom.add(setPanels[7]);
		bottom.add(setPanels[8]);

		player2 = new JPanel();




		player2.add(bottom);
		add(player2, BorderLayout.SOUTH);


		JPanel middle = new JPanel(new GridLayout(1,3));

		p1Stack = new JButton("Stack");
		p1Stack.addActionListener(this);
		p1Deck = new JButton("Deck ");
		p1Deck.addActionListener(this);
		p1Lay = new JButton("Lay  ");
		p1Lay.addActionListener(this);
		p1LayOnStack = new JButton("LayOnStack");
		p1LayOnStack.addActionListener(this);
		turnIsOverp1 = new JButton("Player 1 Turn Is Over");
		turnIsOverp1.addActionListener(this);


		Card [] cardsPlayer1 = new Card[numDealtCards];

		deal(cardsPlayer1);

		/*for(int i = 0; i < cardsPlayer1.length; i++){
			setPhotos(cardsPlayer1[i]);
		}*/

		p1Hand = new DefaultListModel();
		for(int i = 0; i < cardsPlayer1.length; i++)
			p1Hand.addElement(cardsPlayer1[i]);
		p1HandPile = new JList(p1Hand);
		

		middle.add(new HandPanel("Player 1", p1HandPile, p1Stack, p1Deck, p1Lay, p1LayOnStack, turnIsOverp1));
	

		deckPiles = new JPanel();
		deckPiles.setLayout(new BoxLayout(deckPiles, BoxLayout.Y_AXIS));
		deckPiles.add(Box.createGlue());
		JPanel left = new JPanel();
		left.setAlignmentY(Component.CENTER_ALIGNMENT);


		stack = new JLabel("Stack");
		stack.setAlignmentY(Component.CENTER_ALIGNMENT);

		left.add(stack);
		topOfStack = new JLabel();
		topOfStack.setIcon(new ImageIcon(Card.directory + "blank.gif"));
		topOfStack.setAlignmentY(Component.CENTER_ALIGNMENT);
		left.add(topOfStack);
		deckPiles.add(left);
		deckPiles.add(Box.createGlue());

		JPanel right = new JPanel();
		right.setAlignmentY(Component.CENTER_ALIGNMENT);

		deck = new JLabel("Deck");

		deck.setAlignmentY(Component.CENTER_ALIGNMENT);
		right.add(deck);
		deckPile = new JLabel();
		deckPile.setIcon(new ImageIcon(Card.directory + "b.gif"));
		deckPile.setAlignmentY(Component.CENTER_ALIGNMENT);
		right.add(deckPile);
		deckPiles.add(right);
		deckPiles.add(Box.createGlue());
		middle.add(deckPiles);


		p2Stack = new JButton("Stack");
		p2Stack.addActionListener(this);
		p2Deck = new JButton("Deck ");
		p2Deck.addActionListener(this);
		p2Lay = new JButton("Lay  ");
		p2Lay.addActionListener(this);
		p2LayOnStack = new JButton("LayOnStack");
		p2LayOnStack.addActionListener(this);
		turnIsOverp2 = new JButton("Player 2 Turn Is Over");
		turnIsOverp2.addActionListener(this);
		AutoPlay = new JButton("Auto Play");
		AutoPlay.addActionListener(this);

		Card [] cardsPlayer2 = new Card[numDealtCards];
		deal(cardsPlayer2);
		p2Hand = new DefaultListModel();

		for(int i = 0; i < cardsPlayer2.length; i++)
			p2Hand.addElement(cardsPlayer2[i]);

		p2HandPile = new JList(p2Hand);

		middle.add(new HandPanel("Player 2", p2HandPile, p2Stack, p2Deck, p2Lay, p2LayOnStack, turnIsOverp2, AutoPlay));

		add(middle, BorderLayout.CENTER);

		JPanel leftBorder = new JPanel(new GridLayout(2,1));


		setPanels[9].setLayout(new BoxLayout(setPanels[9], BoxLayout.Y_AXIS));
		setPanels[10].setLayout(new BoxLayout(setPanels[10], BoxLayout.Y_AXIS));
		leftBorder.add(setPanels[9]);
		leftBorder.add(setPanels[10]);
		add(leftBorder, BorderLayout.WEST);

		JPanel rightBorder = new JPanel(new GridLayout(2,1));

		setPanels[11].setLayout(new BoxLayout(setPanels[11], BoxLayout.Y_AXIS));
		setPanels[12].setLayout(new BoxLayout(setPanels[12], BoxLayout.Y_AXIS));
		rightBorder.add(setPanels[11]);
		rightBorder.add(setPanels[12]);
		add(rightBorder, BorderLayout.EAST);

		// Adds a first card in the stack at the start of every game
		Object firstCard = cardDeck.dealCard();
		Card firstInStack = (Card)firstCard;
		stackDeck.push(firstInStack);
		topOfStack.setIcon(firstInStack.getCardImage());

		

	}

		// Boolean variable for turns and variable for writing output on a txt file
		public boolean turnPlayer1Pick = false;
		public boolean turnPlayer2Pick = false;
		public boolean turnPlayer1Lay = false;
		public boolean turnPlayer2Lay = false;
		public boolean turnPlayer1Set = false;
		public boolean turnPlayer2Set = false;
		public boolean turnPlayer1 = false;
		public boolean turnPlayer2 = false;
		public int writeDoc = 0;
		public FileWriter myWriter;
		public Hand setCards = new Hand();
		public int [] arr = new int[13];
	  	public boolean isInAutoPlay = false;
		public boolean notInAutoPlay = false;

	public void actionPerformed(ActionEvent e)
	{
		Object src = e.getSource();
		writeDoc++;
		
		// At the start of the game it calls the function WriteToFileBeginning to write the hand of each player
		if(writeDoc == 1){
			try {
				WriteToFileBeginning();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	}

		// When player 1 clicks pick from deck
		if(p1Deck == src){
			//Checks if its really players 1 turn
			if(!turnPlayer2){
				// checks if player 1 already picked a card
				if(!turnPlayer1Pick){
					Card card = cardDeck.dealCard();
					
					if (card != null){
							
							p1Hand.addElement(card);
							
							// Output of card picked
							deck(card);
							
							// Writes on txt file the card picked
							WriteToFileAdd(card, 1);
						

					}
					// Changes this value so that the player 1 does not picks again and player 2 is not allowed to make a move
					turnPlayer1Pick = true;
					turnPlayer1 = true;
				}
				// Shows this message when the player already picked a card
				else System.out.println("You already picked a card try other moves like set or discard");
			}
			// Shows this message when its not players 2 turn
			else{
				System.out.println("Its Player 2's turn not yours");
			}
		}

		// When player 2 clicks pick from deck
		if(p2Deck == src){
			if(!isInAutoPlay){
				//Checks if its really players 2 turn
				if(!turnPlayer1){
					// checks if player 2 already picked a card
					if(!turnPlayer2Pick){
							Card card = cardDeck.dealCard();

							if (card != null){
								
									p2Hand.addElement(card);

									// Output of card picked
									deck(card);

									// Writes on txt file the card picked
									WriteToFileAdd(card, 2);
								
								
							}
							// Changes this value so that the player 2 does not picks again and player 1 is not allowed to make a move
								turnPlayer2Pick = true;
								turnPlayer2 = true;
								notInAutoPlay = true;
						}

						// Shows this message when the player already picked a card
						else System.out.println("You already picked a card try other moves like set or discard");
				}

				// Shows this message when its not players 1 turn
				else{
					System.out.println("Its Player 1's turn not yours");
				}
			}
			else if (isInAutoPlay) System.out.println("Its in auto play mode to play with a real player close the game and start again");
		}
		


		// Player 1 picks from stack
		if(p1Stack == src){
			//Checks that is players 1 turn
			if(!turnPlayer2){
				// Checks if player 1 already didnt picked a card
				if(!turnPlayer1Pick){
					Card card = stackDeck.pop();

					if(card != null){
						Card topCard = stackDeck.top();
						if (topCard != null)
							topOfStack.setIcon(topCard.getCardImage());
						else
							topOfStack.setIcon(new ImageIcon(Card.directory + "blank.gif"));
						
							p1Hand.addElement(card);

							// Outputs the card picked
							stack(card);

							// Writes on txt the card picked
							WriteToFileAdd(card, 1);
							
							// Changes values so that player 1 cannot pick again and player 2 cant go
							turnPlayer1Pick = true;
							turnPlayer1 = true;
					}
				}
				// Outputs that the player already picked a card
				else System.out.println("You already picked a card try other moves like set or discard");
			}
			// Output that its not players 2 turn
			else{
				System.out.println("Its Player 2's turn not yours");
			}
		}

		// Player 2 picks from stack
		if(p2Stack == src ){
		if(!isInAutoPlay){
			//Checks that is players 2 turn
			if(!turnPlayer1){
				// Checks if player 2 already didnt picked a card
				if(!turnPlayer2Pick){
					Card card = stackDeck.pop();

					if(card != null){
						Card topCard = stackDeck.top();
						if (topCard != null)
							topOfStack.setIcon(topCard.getCardImage());
						else
							topOfStack.setIcon(new ImageIcon(Card.directory + "blank.gif"));

							p2Hand.addElement(card);

							// Outputs the card picked
							stack(card);
							
							// Writes on txt the card picked
						    WriteToFileAdd(card, 2);
							
							// Changes values so that player 2 cannot pick again and player 1 cant go
							turnPlayer2Pick = true;
							turnPlayer2 = true;
							notInAutoPlay = true;
					}
				}
				// Outputs that the player already picked a card
					else System.out.println("You already picked a card try other moves like set or discard");
				}
				// Output that its not players 1 turn
				else{
					System.out.println("Its Player 1's turn not yours");
				}
			}
			else if (isInAutoPlay) System.out.println("Its in auto play mode to play with a real player close the game and start again");
		} 


		// Player 1 lays cards
		if(p1Lay == src){
			// Checks that it is player 1 turn
			if(!turnPlayer2){
				// Checks if player 1 already didnt lay
				if(!turnPlayer1Lay){
					Object [] cards = p1HandPile.getSelectedValues();

					Card [] bunchCards = new Card[cards.length];

					Boolean checkIfItsThree = true;

					if(cards.length == 1){
						Card singleCard = (Card)cards[0];

						for(int i = 0; i < arr.length; i++){
							if(arr[Card.getRankIndex(singleCard.getRank())] < 3) checkIfItsThree = false;
						}

						if(checkIfItsThree){
									
							//outouts the cards layed
							layCard(singleCard);
							p1Hand.removeElement(singleCard);

							// Writes on txt file the cards layed and changes the variables so player 2 cant go and player 1 does not lay again
							WriteToFileLay(cards, 1);
							turnPlayer1Lay = true;
							turnPlayer1 = true;

						}
					}

					if(cards.length!=1){
						// adds to array of card the selected cards 
						for(int i = 0; i < cards.length; i++){
								bunchCards[i] = (Card)cards[i];
								//System.out.println(bunchCards[i].getRank());
						}
						
						Hand checkHandLay = new Hand();

						// adds to varible type hand the selected cards 
						for(int i = 0; i < bunchCards.length; i++){
							checkHandLay.addCard(bunchCards[i]);
						}
						//checkHandLay.sort();
						
						// cheks if cards are of the same rank
						if(checkHandLay.findSet(bunchCards) != null){
							if (cards != null){
								for(int i = 0; i < cards.length; i++)
								{
									Card card = (Card)cards[i];
									arr[Card.getRankIndex(card.getRank())]++;
									//outouts the cards layed
									layCard(card);
									p1Hand.removeElement(card);
								}
								// Writes on txt file the cards layed and changes the variables so player 2 cant go and player 1 does not lay again
								WriteToFileLay(cards, 1);
								turnPlayer1Lay = true;
								turnPlayer1 = true;
							}
						}
						//outputs that the cards are not of the same rank or they are less than 3 or more than 4
						else System.out.println("Try again, selected cards not of same rank or you select more than 4 or less than 3");
					}
				}
				// outputs that player already layed cards
				else System.out.println("You already layed cards try other moves like pick or discard");
			}
			// outputs that its not players 2 turn
			else{
				System.out.println("Its Player 2's turn not yours");
			}
		}

		// Player 2 lays cards
		if(p2Lay == src){
			if(!isInAutoPlay){
			// checks that it is players 2 turn
			if(!turnPlayer1){
				// checks if player 2 didnt alreary layed cards
				if(!turnPlayer2Lay){
					Object [] cards = p2HandPile.getSelectedValues();
					
					Card [] bunchCards = new Card[cards.length];

					Boolean checkIfItsThree = true;

					if(cards.length == 1){
						Card singleCard = (Card)cards[0];

						for(int i = 0; i < arr.length; i++){
							if(arr[Card.getRankIndex(singleCard.getRank())] < 3) checkIfItsThree = false;
						}

						if(checkIfItsThree){
									
							//outouts the cards layed
							layCard(singleCard);
							p2Hand.removeElement(singleCard);

							// Writes on txt file the cards layed and changes the variables so player 2 cant go and player 1 does not lay again
							WriteToFileLay(cards, 2);
							turnPlayer1Lay = true;
							turnPlayer1 = true;
							notInAutoPlay = true;

						}
					}

					if(cards.length!=1){
							// adds to array of card the selected cards 
							for(int i = 0; i < cards.length; i++){
									bunchCards[i] = (Card)cards[i];
									//System.out.println(bunchCards[i].getRank());
							}
							
							Hand checkHandLay = new Hand();

							// adds to varible type hand the selected cards 
							for(int i = 0; i < bunchCards.length; i++){
								checkHandLay.addCard(bunchCards[i]);
							}
							//checkHandLay.sort();
						
						// checks if cards are of the same rank
						if(checkHandLay.findSet(bunchCards) != null){
							if (cards != null){
								for(int i = 0; i < cards.length; i++)
								{
									
									Card card = (Card)cards[i];
									arr[Card.getRankIndex(card.getRank())]++;
									//cardsSet.addCard(card);
									layCard(card);
									p2Hand.removeElement(card);
									
								}
								// writes in txt cards layed
								WriteToFileLay(cards, 2);
								// Writes on txt file the cards layed and changes the variables so player 1 cant go and player 2 does not lay again
								turnPlayer2 = true;
								turnPlayer2Lay = true;
							}
						}
						//outputs that the cards are not of the same rank or they are less than 3 or more than 4
						else System.out.println("Try again, selected cards not of same rank or you select more than 4 or less than 3");
					}
				}
				// outputs that player already layed cards
				else System.out.println("You already layed cards try other moves like pick or discard");
			}
				// outputs that its not players 1 turn
			else{
				System.out.println("Its Player 1's turn not yours");
			}
		}  else if (isInAutoPlay) System.out.println("Its in auto play mode to play with a real player close the game and start again");
		} 


		// player 1 discards card
		if(p1LayOnStack == src){
			// checks that it is players 1 turn
				if(!turnPlayer2){
					// checks that player 1 only discarted one time
				   if(!turnPlayer1Set){
						int [] num  = p1HandPile.getSelectedIndices();
						if (num.length == 1)
						{
							Object obj = p1HandPile.getSelectedValue();
							if (obj != null)
							{
								p1Hand.removeElement(obj);
								Card card = (Card)obj;
								stackDeck.push(card);
								topOfStack.setIcon(card.getCardImage());
								// prints discarted card
								layOnStack(card);

								// writes on txt card discarted
							    WriteToFileDiscard(card, 1);
								

								// makes sure player 1 does not discards again and that player 2 cannot make a move
								turnPlayer1 = true;
								turnPlayer1Set = true;
							}
						}
				}
				// prints if player 1 already discarted a card
				else System.out.println("You already discarted a card try other moves like pick or lay sets");
			}
			else{
				// prints that its player 2 turn
				System.out.println("Its Player 2's turn not yours");
			}
		}

		// player 2 discards card
		if(p2LayOnStack == src){
			if(!isInAutoPlay){
			// checks that it is players 2 turn
				if(!turnPlayer1){
					// checks that player 2 only discarted one time
				   if(!turnPlayer2Set){
						int [] num  = p2HandPile.getSelectedIndices();
						if (num.length == 1)
						{
							Object obj = p2HandPile.getSelectedValue();
							if (obj != null)
							{
								p2Hand.removeElement(obj);
								Card card = (Card)obj;
								stackDeck.push(card);
								topOfStack.setIcon(card.getCardImage());
								// prints discarted card
								layOnStack(card);

								// writes on txt card discarted
								WriteToFileDiscard(card, 2);
								
								// makes sure player 1 does not discards again and that player 2 cannot make a move
								turnPlayer2 = true;
								turnPlayer2Set = true;
								notInAutoPlay = true;
							}
						}
				    }
					// prints if player 2 already discarted a card
				else System.out.println("You already discarted a card try other moves like pick or lay sets");
			}
			// prints that its player 1 turn
			else{
				System.out.println("Its Player 1's turn not yours");
			}
		}else if (isInAutoPlay) System.out.println("Its in auto play mode to play with a real player close the game and start again");
		} 


		// when players 1 turn is over
		if(turnIsOverp1 == src){
			// makes sure that its the players turn
			if(turnPlayer2) System.out.println("Its not your turn");
			// makes sure the player had picked a card
			else if(!turnPlayer1Pick) System.out.println("You need to pick a card before your turn is over");
			//makes sure the player discarted a card
			else if(!turnPlayer1Set) System.out.println("You need to discard at least one card before your turn is over");
			// changes the turn to player 2
			else{
				turnPlayer1 = false;
				turnPlayer1Set = false;
				turnPlayer1Pick = false;
				turnPlayer1Lay = false;
				turnPlayer2 = true;
				System.out.println("Its Player 2 turn");
				// Writes the hand the player 1 on txt file
				WriteWhenFinishedTurn(p1Hand, 1);
			}

			// If the deck is empty it checks which player won
			if(cardDeck.getSizeOfDeck() == 0){
				deckPile.setIcon(new ImageIcon(Card.directory + "blank.gif"));
				WriteToFileWinner();
			}
			// if the player 1 hand is empty player 1 wins
			else if(p1Hand.isEmpty()) {
				//System.out.println("Player 1 wins");
				WriteToFileWinner();
			}
		}

		// when players 2 turn is over
		if(turnIsOverp2 == src){
			if(!isInAutoPlay){
			// makes sure that its the players turn
			if(turnPlayer1) System.out.println("Its not your turn");
			// makes sure the player had picked a card
			else if(!turnPlayer2Pick) System.out.println("You need to pick a card before your turn is over");
			//makes sure the player discarted a card
			else if(!turnPlayer2Set) System.out.println("You need to discard at least one card before your turn is over");
			// changes the turn to player 1
			else {
				turnPlayer2 = false;
				turnPlayer2Set = false;
				turnPlayer2Pick = false;
				turnPlayer2Lay = false;
				turnPlayer1 = true;
				System.out.println("Its Player 1 turn");
				// Writes the hand the player 2 on txt file
				WriteWhenFinishedTurn(p2Hand, 2);
			}

			// If the deck is empty it checks which player won
			if(cardDeck.getSizeOfDeck() == 0){
				deckPile.setIcon(new ImageIcon(Card.directory + "blank.gif"));
				WriteToFileWinner();
			}
			// if the player 2 hand is empty player 2 wins
			else if(p2Hand.isEmpty()) {
				//System.out.println("Player 2 wins");
				WriteToFileWinner();
			}
		} else if (isInAutoPlay) System.out.println("Its in auto play mode to play with a real player close the game and start again");
	}

		// automated player
		if(AutoPlay == src ){
			
		if(!notInAutoPlay){
			if(!turnPlayer1){
			Random rand = new Random();
			boolean turn = rand.nextBoolean();

			// get card from stack or deck
			if(turn && (!stackDeck.isEmpty())){
				Card card = stackDeck.pop();
				Card topCard = stackDeck.top();
				if(topCard != null) topOfStack.setIcon(topCard.getCardImage());
				else topOfStack.setIcon(new ImageIcon(Card.directory + "blank.gif"));
				p2Hand.addElement(card);
				stack(card);
				WriteToFileAdd(card, 2);
			}
			else{
				Card card = cardDeck.dealCard();
				p2Hand.addElement(card);
				deck(card);
				WriteToFileAdd(card, 2);

			}

			
			Hand playersHand = new Hand();
			Card [] cardInHand = new Card[p2Hand.size()];

			for(int i = 0; i < p2Hand.getSize(); i++){
					cardInHand[i] = (Card) p2Hand.get(i);
			}

			for(int i = 0; i < p2Hand.size(); i++){
				playersHand.addCard(cardInHand[i]);
			}

			//Discard a card
			int random = rand.nextInt(p2Hand.getSize());
			Card discardCard = playersHand.play(random, null);
			p2Hand.removeElement(discardCard);
			stackDeck.push(discardCard);
			topOfStack.setIcon(discardCard.getCardImage());
			// prints discarted card
			layOnStack(discardCard);
			// writes on txt card discarted
			WriteToFileDiscard(discardCard, 2);
			
			//platersHand.play(3);

			int [] a = new int[13];
			Card cardS;

			for(int i = 0; i < p2Hand.size(); i++){
				cardS = (Card) p2Hand.getElementAt(i);
				a[Card.getRankIndex(cardS.getRank())]++;
				
			}

			int index = -1;
			int numberOfTimes = 0;

			// Checks if there are sets available
			for(int i = 0; i < a.length; i++){
					if(a[i] >= 3){
						index = i;
						numberOfTimes = a[i];
					}
			}

			// Lays sets of cards
			if(index != -1){
				Card [] cards = new Card[numberOfTimes]; 

				for(int i = 0, j = 0; i < p2Hand.getSize(); i++){
					if(Card.getRankIndex(((Card)p2Hand.getElementAt(i)).getRank()) == index){
						cards[j] = (Card)p2Hand.getElementAt(i);
						j++;
					}
				}

				for(int i = 0; i < cards.length; i++){
									
					Card card = (Card)cards[i];
					arr[Card.getRankIndex(card.getRank())]++;
					//cardsSet.addCard(card);
					layCard(card);
					p2Hand.removeElement(card);
					//WriteToFileLay(cards, 1);
									
				}
					// writes in txt cards layed
					WriteToFileLay(cards, 2);
					// Writes on txt file the cards layed and changes the variables so player 1 cant go and player 2 does not lay again
					// turnPlayer2 = true;
					// turnPlayer2Lay = true;
			}

			int index2 = 0;
			for(int i = 0; i < arr.length; i++){
				if(arr[i] >= 3){
					index2 = i;
				}
			}

			// Check if a single part is part of a set already placed.
			if(index != -1){

				Object [] cards2 = new Object[1];
				boolean yesOrNo = false;

				for(int i = 0; i < p2Hand.getSize(); i++){
					if(Card.getRankIndex(((Card)p2Hand.getElementAt(i)).getRank()) == index2){
						cards2[0] = p2Hand.getElementAt(i);
						yesOrNo = true;
					}
				}

				if(yesOrNo){
					Card card = (Card)cards2[0];
					arr[Card.getRankIndex(card.getRank())]++;
					layCard(card);
					p2Hand.removeElement(card);
					
					
				}

			}
			
			WriteWhenFinishedTurn(p2Hand, 2);

			// If the deck is empty it checks which player won
			if(cardDeck.getSizeOfDeck() == 0){
				deckPile.setIcon(new ImageIcon(Card.directory + "blank.gif"));
				WriteToFileWinner();
			}
			// if the player 2 hand is empty player 2 wins
			else if(p2Hand.isEmpty()) {
				//System.out.println("Player 2 wins");
				WriteToFileWinner();
			}

			turnPlayer1 = true;
			turnPlayer2 = false;
			isInAutoPlay = true;
			System.out.println("Players 1 turn");
		} else System.out.println("Its not the auto play turn");
		}
		else if (notInAutoPlay) {
			System.out.println("Its not in auto play mode to play with a generated player close the game and start again then press autoplay.");
			System.out.print("Beware that everytime your turn is over you have to press auto play button in order for the generated player to go.");
		}
	} 
}

	// Sets the image of the layed cards and also outputs them on the terminal
	void layCard(Card card)
	{
		char rank = card.getRank();
		char suit = card.getSuit();
		int suitIndex =  Card.getSuitIndex(suit);
		int rankIndex =  Card.getRankIndex(rank);
		//setPanels[rankIndex].array[suitIndex].setText(card.toString());
		System.out.println("laying " + card);
		setPanels[rankIndex].array[suitIndex].setIcon(card.getCardImage());
	}

	// Outputs the card that was picked from deck
	void deck(Card card){	
		System.out.println("picking from deck " + card);
	}

    // Outputs the card that was picked from stack
	void stack(Card card){
		System.out.println("picking from stack " + card);
	}

	// Outputs the card that was layed from stack
	void layOnStack(Card card){	
		System.out.println("laying on stack " + card);
	}

	/**
	 * Converts the DefaultListModel of the hand in order to evaluate the hand
	 * @param p1Hand
	 * @return total points in the hand of the player
	 */
	int countHands(DefaultListModel p1Hand){

		Hand cardsInHand = new Hand();
		Card [] cardsToCount = new Card[p1Hand.getSize()];
		p1Hand.copyInto(cardsToCount);
		for(int i = 0; i < p1Hand.size(); i++){
			cardsInHand.addCard(cardsToCount[i]);
		}
		
		return cardsInHand.evaluateHand();
		
	}

	/**
	 * This method writes on a txt file, in the beginning of the game, the hand of each player
	 * @throws IOException
	 */
	public void WriteToFileBeginning() throws IOException{
		Card [] cards = new Card[p1Hand.getSize()];
		p1Hand.copyInto(cards);
		
	    myWriter = new FileWriter("p-output.txt");

		myWriter.write("Initial Player 1: ");

		for(int i = 0; i < p1Hand.size(); i++){
			myWriter.write(cards[i].toString() + ", ");
		}

		p2Hand.copyInto(cards);

		myWriter.write("\nInitial Player 2: ");

		for(int i = 0; i < p2Hand.size(); i++){
			myWriter.write(cards[i].toString() + ", ");
		}

		myWriter.close();
		
	}

	/**
	 *  This function writes on a txt file the cards that each player picks eather from stack or deck
	 * @param card
	 * @param n
	 */
	public void WriteToFileAdd(Card card, int n){
		try{
		myWriter = new FileWriter("p-output.txt", true);
		myWriter.write("\nPlayer " + n);
		myWriter.write("\n\tAdded: " + card);
		myWriter.close();
	} catch(IOException e1){
		e1.printStackTrace();
	}
	
	}

	/**
	 * This method writes on a txt file the card that each player discards
	 * @param card
	 * @param n
	 */
	public void WriteToFileDiscard(Object card, int n){
		try{
		myWriter = new FileWriter("p-output.txt", true);
		myWriter.write("\nPlayer " + n);
		myWriter.write("\n\tDiscarted: " + card);
		myWriter.close();
	} catch(IOException e1){
		e1.printStackTrace();
	}
	}

	/**
	 *  This method writes on a txt file that cards that each players lays (set of cards)
	 * @param cards
	 * @param n
	 */
	public void WriteToFileLay(Object [] cards, int n){
		try{
		myWriter = new FileWriter("p-output.txt", true);
		myWriter.write("\nPlayer " + n);
		myWriter.write("\n\tLayed: ");
		for(int i = 0; i < cards.length; i++){
			myWriter.write(cards[i] + ", ");
		}
		
		myWriter.close();
	} catch(IOException e1){
		e1.printStackTrace();
	}
	}

	/**
	 * This method writes on a text file the hand of the player when their turn is over
	 * @param pHand
	 * @param n
	 */
	public void WriteWhenFinishedTurn(DefaultListModel pHand, int n){
		try{
			Card [] cards = new Card[pHand.getSize()];
			pHand.copyInto(cards);
		
			myWriter = new FileWriter("p-output.txt", true);

			myWriter.write("\n\tPlayer " + n + " Hand Now: ");
			for(int i = 0; i < pHand.size(); i++){
				myWriter.write(cards[i].toString() + ", ");
			}
			myWriter.close();

		} catch(IOException e1){
			e1.printStackTrace();
		}
	}

	/**
	 * This method writes on a txt file who won in the game 
	 */
	public void WriteToFileWinner(){
		try{
			myWriter = new FileWriter("p-output.txt", true);

			// If the player 1 hand is empty he won
			if(p1Hand.isEmpty()) {
				myWriter.write("\nPlayer 1 wins");
				System.out.println("Player 1 wins");
			}

			// if the player 2 hand is empty he won
			else if(p2Hand.isEmpty()) {
				myWriter.write("\nPlayer 2 wins");
				System.out.println("Player 2 wins");
			}

			// When the deck is empty it checks the total of each hand of the players
			else if (cardDeck.getSizeOfDeck() == 0){

				// If both hands have the same total it is a tie
				if(countHands(p1Hand) == countHands(p2Hand)) {
					myWriter.write("\nPoints: " + countHands(p1Hand) + " to " + countHands(p2Hand));
					myWriter.write("\nIts a tie");
					System.out.println("Its a tie");
				}

				// If player 1 has less cards than player 2, player 1 wins
				else if(countHands(p1Hand) < countHands(p2Hand)) {
					myWriter.write("\nPoints: " + countHands(p1Hand) + " to " + countHands(p2Hand));
					myWriter.write("\nPlayer 1 wins");
					System.out.println("Player 1 wins");
					
				}

				// If player 2 has less cards than player 1, player 2 wins
				else {
					myWriter.write("\nPoints: " + countHands(p1Hand) + " to " + countHands(p2Hand));
					myWriter.write("\nPlayer 2 wins");
					System.out.println("Player 2 wins");
				}
			}
			myWriter.close();

		} catch(IOException e1){
			e1.printStackTrace();
		}
	}

}

class HandPanel extends JPanel
{

	public HandPanel(String name,JList hand, JButton stack, JButton deck, JButton lay, JButton layOnStack, JButton turnOver)
	{
		//model = hand.createSelectionModel();

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
//		add(Box.createGlue());
		JLabel label = new JLabel(name);
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(label);
		stack.setAlignmentX(Component.CENTER_ALIGNMENT);
//		add(Box.createGlue());
		add(stack);
		deck.setAlignmentX(Component.CENTER_ALIGNMENT);
//		add(Box.createGlue());
		add(deck);
		lay.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(lay);
		layOnStack.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(layOnStack);
		turnOver.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(turnOver);
		add(Box.createGlue());
		add(hand);
		add(Box.createGlue());
	}

	public HandPanel(String name,JList hand, JButton stack, JButton deck, JButton lay, JButton layOnStack, JButton turnOver, JButton autoplay)
	{
		//model = hand.createSelectionModel();

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
//		add(Box.createGlue());
		JLabel label = new JLabel(name);
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(label);
		stack.setAlignmentX(Component.CENTER_ALIGNMENT);
//		add(Box.createGlue());
		add(stack);
		deck.setAlignmentX(Component.CENTER_ALIGNMENT);
//		add(Box.createGlue());
		add(deck);
		lay.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(lay);
		layOnStack.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(layOnStack);
		turnOver.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(turnOver);
		autoplay.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(autoplay);
		add(Box.createGlue());
		add(hand);
		add(Box.createGlue());
	}


}
class SetPanel extends JPanel
{
	private Set data;
	JButton [] array = new JButton[4];

	public SetPanel(int index)
	{
		super();
		data = new Set(Card.rank[index]);

		for(int i = 0; i < array.length; i++){
			array[i] = new JButton("   ");
			add(array[i]);
		}
	}

}
