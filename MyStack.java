/*Michael Terrefortes Rosado
Project 2
801-18-9110
CCOM4029-001 Prof. Patricia Ordonez
This program is a Rummy game with GUI*/

public class MyStack implements PileInterface {

    static final int MAX = 1000;
    int top;
    Card a[] = new Card[MAX]; // Maximum size of Stack

   /**
     * Initializes an empty stack.
     * 
     */
    public void MyStack() {
        top = -1;
    }

    /**
     * Adds the item to this stack.
     *
     * @param  card the item to add
     */
    public void push(Card card) {
       
        if (top >= (MAX - 1)) {
            System.out.println("Stack Overflow");
          
        }
        else {
            top++;
            a[top] = card;
            
           
           
        }
    }

    /**
     * Removes and returns the item most recently added to this stack.
     *
     * @return the item most recently added
     */
    public Card pop() {
      
        if (top < 0) {
           
            return null;
        }
        else {
            Card card = a[top];
            top--;
            return card;
        }
    }


    /**
     * Returns (but does not remove) the item most recently added to this stack.
     *
     * @return the item most recently added to this stack
     */
    public Card top() {
      
        if (top < 0) {
          
            return null;
        }
        else {
            Card card = a[top];
            return card;
        }
    }

        /**
     * Returns true if this stack is empty.
     *
     * @return true if this stack is empty; false otherwise
     */
    public boolean isEmpty() {
        return (top < 0);
    }

    public int size(){
        return top;
    }

    public Card getCard(int i){
        Card card = a[i];
        return card;
    }
}
