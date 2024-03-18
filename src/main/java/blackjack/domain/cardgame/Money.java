package blackjack.domain.cardgame;

public class Money {
    private final double value;

    public Money(final double value) {
        this.value = value;
    }

    public Money multiply(final double multiplier) {
        return new Money(value * multiplier);
    }

    public int getValue() {
        return (int) value;
    }
}
