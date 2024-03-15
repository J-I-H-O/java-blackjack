package blackjack.domain.player;

import blackjack.domain.card.Card;

public class Dealer extends Player {
    private static final String NAME = "딜러";
    private static final int HIT_THRESHOLD = 16;

    public Dealer() {
        super(NAME);
    }

    public boolean isMoreCardNeeded() {
        return this.hand.calculateScore() <= HIT_THRESHOLD;
    }

    public Card getFirstCard() {
        try {
            return hand.getAllCards().get(0);
        } catch (IndexOutOfBoundsException e) {
            throw new RuntimeException("[ERROR] 딜러가 카드를 갖고 있지 않습니다.");
        }
    }
}
