public class Card{
    int suit; // Масть карты. 1 - ♠, 2 - ♣, 3 - ♥, 4 - ♦
    int dignity; // Достоинство 2-10 - 2-10, J - 11, Q - 12, K - 13, A - 14
    String path;
    public String setPath(Card card){
        path = "pics/" + String.valueOf(card.suit) + "_"+ String.valueOf(card.dignity) + ".png";
        return path;
    }

}