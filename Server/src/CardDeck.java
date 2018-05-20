import java.util.Random;

public class CardDeck {
    public class Card{
        int suit; // Масть карты. 1 - ♠, 2 - ♣, 3 - ♥, 4 - ♦
        int dignity; // Достоинство 1-9 - 2-10, J - 10, Q - 11, K - 12, A - 13
    }

    // Пишем свой стек для карт
    private class CardStack {
        private Card[] Deck = new Card[52];
        private int top = -1;

        private void push(Card Card) {
            Deck[++top] = Card;
        }

        private Card pop() {
            return Deck[top--];
        }

        private boolean isEmpty() {
            return (top == -1);
        }
        //Перемешиваем все карты в колоде
        private void shuffle(){
            final Random random = new Random();
            for (int i = Deck.length - 1; i > 0; i--)
            {
                int index = random.nextInt(i + 1);
                // Замешиваем 2 элемента
                Card a = Deck[index];
                Deck[index] = Deck[i];
                Deck[i] = a;
            }
        }
    }

    private CardStack Deck = new CardStack();

    public CardStack newDeck(){ // Генерация новой колоды
        //Очищаем колоду
        while(!Deck.isEmpty()){
            Deck.pop();
        }

        for (int i = 1; i <= 4; i++){
            for(int j = 1; j <= 13; j++){
                Card card = new Card();
                card.suit = i;
                card.dignity = j;
                Deck.push(card);
                //Надо бы удалить card, но это работа для GC :)
            }
        }
        Deck.shuffle(); // Перемешиваем колоду
        return Deck;
    }

    public Card popCard(){
        return (Deck.pop());
    }

}
