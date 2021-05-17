/*Michael Terrefortes Rosado
Project 2
801-18-9110
CCOM4029-001 Prof. Patricia Ordonez
This program is a Rummy game with GUI*/

public interface PileInterface{

    public void MyStack();

    public void push(Card card);

    public Card pop();

    public Card top();

    public boolean isEmpty();

    public int size();

    public Card getCard(int i);
}
