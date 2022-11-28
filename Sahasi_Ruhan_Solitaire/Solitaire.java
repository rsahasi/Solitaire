import java.util.*;

/**
 * This class enables one to use the card class and actually play the game.Solitaire is played as follows:
 * There's a stock pile which one can take cards from, which are placed in an area known as the waste pile.
 * Under that, there are six spots for cards to be placed, and the user moves individual cards from 
 * the waste pile onto a game pile. One can move cards from pile to pile only if they are of one less rank, and the 
 * opposite color as well. The goal of the game is to move all the cards in a deck onto the foundations (located in the
 * top right), in ascending order of rank, all from the same exact suit. This program enables users to have a great, 
 * no-money-losing game experience!
 * @author Ruhan Sahasi
 * @version 11/15/22
 */

public class Solitaire
{
    /**
     * This is the main method, and when it runs, it creates a new game of Solitaire.
     */
    public static void main(String[] args)
    {
        new Solitaire();
    }

    /**
     * These are the class's instance fields which store all the game attributes.
     */
    private Stack<Card> stock;
    private Stack<Card> waste;
    private Stack<Card>[] foundations;
    private Stack<Card>[] piles;
    private SolitaireDisplay display;
    private boolean canMoveFound;
    private int currentFound;
    
    /**
     * This is the Solitaire constructor, and it creates 4 spots for the foundations, all the piles, and the display.
     */
    public Solitaire()
    {
        foundations = new Stack[4];
        piles = new Stack[7];
        for(int i=0;i<4; i++)
        {
            foundations[i] = new Stack<Card>();
        }
        for(int i=0; i<7;i++)
        {
            piles[i] = new Stack<Card>();
        }
        stock = new Stack<Card>();
        waste = new Stack<Card>();
        display = new SolitaireDisplay(this);
        canMoveFound = false;
        currentFound = 0;
        createStock();
        deal();
        
    }

    /**
     * This method enables users to retrieve a card from the stock pile.
     * @return the top stock card, or null in the case when stock pile is empty
     */
    public Card getStockCard()
    {
        if(stock.isEmpty())
        {
            return null;
        }
        
        return stock.peek();
    }

    /**
     * This method enables users to retrieve a card from the waste pile.
     * @return the waste pile card, or null in the case when waste pile is empty
     */
    public Card getWasteCard()
    {
        if(waste.isEmpty())
        {
            return null;
        }
        
        return waste.peek();
    }

    /**
     * This method enables users to retrieve a card from the foundation pile.
     * @param index The index of the foundation we want
     * @precondition index is a valid pile location from 0<=index<4
     * @postcondition returns the top foundation card
     * @return the top foundation card, or null in the case when foundation pile is empty at index position
     */
    public Card getFoundationCard(int index)
    {
        if(foundations[index].isEmpty())
        {
            return null;
        }
        
        return foundations[index].peek();
    }
    /**
     * This method enables users to retrieve a card from the normal piles.
     * @param index the index of the pile we want
     * @precondition index is a valid pile location from 0<=index<7
     * @return the actual pile at index (referenced)
     */
    public Stack<Card> getPile(int index)
    {
        return piles[index];
    }
    /**
     * This method creates the stock pile.
     * @postcondition A proper, shuffled stock pile has been created
     */
    private void createStock()
    {
        ArrayList<Card> currCards = new ArrayList<Card>();
        for(int i=1;i<14; i++)
        {
            currCards.add(new Card(i, "c"));
            currCards.add(new Card(i, "d"));
            currCards.add(new Card(i, "h"));
            currCards.add(new Card(i, "s"));
        }
        int size = currCards.size();
        for(int i=0; i<size; i++)
        {
            int index = (int) (Math.random() * currCards.size());
            Card cap = currCards.remove(index);
            stock.push(cap);
        }
    }
    /**
     * This method enables users to utilize the stock pile in order to deal cards and advance the game, and it also
     * turns over the top pile cards.
     * @postcondition the stock pile provides cards to the normal pile, and top pile cards are turned over
     */
    private void deal()
    {
        for(int i=0; i<7; i++) 
        {
            for(int j = i; j < 7; j++)
            {
                piles[j].push(stock.pop());
            }
        }
        for(int i=0; i<7; i++)
        {
            piles[i].peek().turnUp();
        }
    }
    /**
     * This method is similar to the previous one, but it uses the waste pile to transfer cards onto three separate 
     * piles.
     * @postcondition three cards are dealed onto individual and different piles
     */
    private void dealThreeCards()
    {
        for(int i=0; i<3; i++)
        {
            if(!stock.isEmpty())
            {
                waste.push(stock.pop());
                waste.peek().turnUp();
            }
        }
    }
    /**
     * This method resets the stock pile by getting cards from the waste pile. 
     * @postcondition the stock is reset with cards
     */
    private void resetStock()
    {
        while(!waste.isEmpty())
        {
            stock.push(waste.pop());
            stock.peek().turnDown();
        }
    }

    /**
     * This method utilizes the Solitaire display class to perform various actions when the stock is clicked on, 
     * depending on whether or not it is empty. If it is not empty, you deal 3 cards, else you reset your stock.
     * @postcondition three cards are dealed onto individual and different piles, or the stock is reset
     */
    public void stockClicked()
    {
        if(!(display.isWasteSelected() || display.isPileSelected()))
        {
            if(!stock.isEmpty())
                dealThreeCards();
            else
                resetStock();
            
        }
        
    }
    
    /**
     * This method utilizes the Solitaire display class to perform various actions when the waste is clicked on. If the
     * waste isn't empty among other conditions, you select it, otherwise you deselect it.
     * @postcondition the waste pile is either selected (highlighted yellow) or unselected
     */
    public void wasteClicked()
    {
        if(!waste.isEmpty() && !display.isWasteSelected() && !display.isPileSelected())
            display.selectWaste();
        else if(display.isWasteSelected())
            display.unselect();
        
    }
    /**
     * This method utilizes the Solitaire display class to perform various actions when the foundation is clicked on.
     * @param index the index of the foundation pile we are operating on
     * @precondition index is between 0 (inclusive) and 4 (exclusive)
     * @postcondition "YOU WON SOLITAIRE," and/or the foundation with it's corresponding number is printed
     */
    public void foundationClicked(int index)
    {
        if(display.isWasteSelected() && canAddToFoundation(waste.peek(), index))
        {
            foundations[index].push(waste.pop());
            display.unselect();
            if(haveWon())
            {
                System.out.println("YOU WON SOLITAIRE");
            }
        }
        
        if(display.isPileSelected() && canAddToFoundation(piles[display.selectedPile()].peek(), index))
        {
            foundations[index].push(piles[display.selectedPile()].pop());
            display.unselect();
            if(haveWon())
            {
                System.out.println("YOU WON SOLITAIRE");
            }
        }
        
        if(!display.isWasteSelected() && !display.isPileSelected() && !foundations[index].isEmpty())
        {
           canMoveFound = true;
           currentFound = index;
        }
        
        System.out.println("foundation #" + index + " clicked");
    }
    /**
     * This method utilizes the Solitaire display class to perform various actions when the piles are clicked on.
     * @param index the index of the normal pile we are operating on
     * @precondition index is between 0 (inclusive) and 7 (exclusive)
     * @postcondition a variety of actions can occur depending on the circumstances, inclduing the top card being
     * selected, deselected, and the pile # will be printed to the terminal 
     */
    public void pileClicked(int index)
    {
        if(canMoveFound)
        {
            if(canAddToPile(foundations[currentFound].peek(), index))
            {
                piles[index].push(foundations[currentFound].pop());
            }
        }
        else if(display.isWasteSelected() && canAddToPile(waste.peek(), index))
        {
            Card c = waste.pop();
            piles[index].push(c);
            display.unselect();
        }
        
        else if(display.isPileSelected() && canAddToPile(piles[display.selectedPile()].peek(), index))
        {
            Card c = piles[display.selectedPile()].pop();
            piles[index].push(c);
            display.unselect();
        }
        else if(!display.isPileSelected() && !display.isWasteSelected() && !piles[index].isEmpty() && piles[index].peek().isFaceUp())
        {
            display.selectPile(index);
        }
        else if(display.isPileSelected() && display.selectedPile() == index)
        {
            display.unselect();
        }
        
        else if(display.isPileSelected())
        {
            if(!piles[index].isEmpty())
            {
                Stack<Card> c  = removeFaceUpCards(display.selectedPile());
                if(canAddToPile(c.peek(), index))
                {
                    addToPile(c, index);
                }
                else
                {
                    while(!c.isEmpty())
                    {
                        piles[display.selectedPile()].push(c.pop());
                    }
                }
            }
        }
        
        else if(!display.isPileSelected() && !piles[index].isEmpty() && !display.isWasteSelected() && !piles[index].peek().isFaceUp())
        {
            piles[index].peek().turnUp();
        }
        
        canMoveFound = false;
        
        System.out.println("pile #" + index + " clicked");
    }
    /**
     * This method determines when a player has won the game
     * @return true if they have one, false otherwise
     */
    private boolean haveWon()
    {
        for(int i=0; i<piles.length; i++)
        {
            if (!piles[i].isEmpty())
                return false;
            
        }
        if(!stock.isEmpty() && !waste.isEmpty())
        {  
            return false;
        }
        
        for(int i=0; i<foundations.length; i++)
        {
            if(foundations[i].peek().getRank() != 13)
                return false;
            
        }
        return true;
    }
    /**
     * This method determines if one can add a card (under the rules) to a given pile
     * @return true if they can, false otherwise
     */
    private boolean canAddToPile(Card card, int index)
    {
        if (!piles[index].isEmpty())
        {
            Card c = piles[index].peek();
            if((c.isFaceUp()) && (card.isRed() == !c.isRed()) && (card.getRank()  == c.getRank()-1))
                return true;
        }
        
        else if(card.getRank() == 13)
        {
            return true;
        } 
        return false;
    }
    /**
     * This helper method determines whether a card can be added onto a foundation (valid under the rules of Solitaire)
     * @param card the card we are testing
     * @param index the location of the pile we are operating on
     * @precondition 0<=index<4
     * @postcondition It is determined whether the card can be added onto the foundation at index
     * @return true if it can be, else false
     */
    private boolean canAddToFoundation(Card card, int index)
    {
        if(card.getRank() == 1 && foundations[index].isEmpty())
        {
            return true;
        }
        else if(!foundations[index].isEmpty() && foundations[index].peek().getSuit() == card.getSuit() && foundations[index].peek().getRank() + 1 == card.getRank())
        {
            return true;
        }
        return false;
    }
    
    /**
     * This method adds cards to the pile
     * @param cards A stack of cards that is to be inserted onto piles
     * @param index the location of the pile we are operating on
     * @precondition 0<=index<7
     * @postcondition all the cards in the cards stack are pushed onto the pile
     */
    private void addToPile(Stack<Card> cards, int index)
    {
        while(!cards.isEmpty())
        {
            Card cap = cards.pop();
            piles[index].push(cap);
        }
    }
    /**
     * This method removes the face up cards of a pile.
     * @param index the location of the pile we are operating on
     * @precondition 0<=index<7
     * @postcondition leaves the pile without the face up cards
     * @return cards A stack of all the face up cards
     */
    private Stack<Card> removeFaceUpCards(int index)
    {
        Stack<Card> cards = new  Stack<Card>();
        while(!piles[index].isEmpty() && piles[index].peek().isFaceUp())
        {
            Card cap = piles[index].pop();
            cards.push(cap);
        }
        return cards;
    }
    
}