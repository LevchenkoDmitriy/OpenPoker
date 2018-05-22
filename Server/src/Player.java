import java.net.Socket;

public class Player {
    private String name;
    private int balance;
    private CardDeck.Card[] Cards = new CardDeck.Card[2];// 2 карты у каждого игрока
    public Socket PlayerSocket;

    public void setName(String str){
        name = str;
    }

    public int getBalance(){ return balance; }
    public String getName(){ return name; }
    public CardDeck.Card[] getCards(){ return Cards; }
    public void setCards(CardDeck.Card[] playerCards){
        Cards[0] = playerCards[0];
        Cards[1] = playerCards[1];
    }

    public Player(){
        name = "Player";
        balance = 1000;
    }
}
