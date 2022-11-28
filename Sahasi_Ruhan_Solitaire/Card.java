/**
 * The card class enables one to create card objects that can be used to play card games like Solitaire
 * @author Ruhan Sahasi
 * @version 11/15/22
 */
public class Card
{
    private int rank;
    private String suit;
    private boolean isFaceUp;

    /**
     * This method is the constructor that creates the card objects.
     * @param r The number of the card
     * @param s The suit of the card
     * @param isFaceUp Determines what side the card is on
     * @postcondition rank, suit, and isFaceUp are all properly initialized
     */
    public Card(int r, String  s)
    {
        rank = r;
        suit = s;
        isFaceUp = false;
    }
    /**
     * This method returns the rank of a card.
     * @return rank The card's number
     */
    public int getRank()
    {
        return rank;
    }
    /**
     * This method returns the suit of a card.
     * @return suit The card's suit
     */
    public String getSuit()
    {
        return suit;
    }
    /**
     * This method determines whether a card is red or black.
     * @return true if the card is of red color, otherwhise it's black and false is returned
     */
    public boolean isRed()
    {
        return (suit  == "d" || suit  == "h");
    }
    /**
     * This method determines whether a card is face up or not
     * @return the value stored in the boolean variable isFaceUp
     */
    public boolean isFaceUp()
    {
        return this.isFaceUp;
    }
    /**
     * This method makes a card face up.
     * @postcondition the card is turned face up.
     */
    public void turnUp()
    {
        isFaceUp = true;
    }
    /**
     * This method makes a card face down.
     * @postcondition the card is turned face down.
     */
    public void turnDown()
    {
        isFaceUp = false;
    }
    /**
     * This method retrieves and returns the according file name of a card.
     * @return the file name of the card object.
     */
    public String getFileName()
    {
        if(isFaceUp == true)
        {
            if(rank<=9 && rank>=2)
            {
                return "cards/"+rank+suit+".gif";
            }
            
            else if(rank==10)
            {
                return "cards/t"+suit+".gif";
            }
            
            else if(rank==11)
            {
                return "cards/j"+suit+".gif";
            }
            
            else if(rank==12)
            {
                return "cards/q"+suit+".gif";
            }
            
            else if(rank==13)
            {
                return "cards/k"+suit+".gif";
            }
            
            else if(rank==1)
            {
                return "cards/a"+suit+".gif";
            }
        }
        
        return "cards/backapcsds.gif";
    }
}

