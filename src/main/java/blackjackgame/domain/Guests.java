package blackjackgame.domain;

import java.util.List;
import java.util.stream.Collectors;

public class Guests {
    private static final int MIN_GUESTS_NUMBER = 1;
    private static final int MAX_GUESTS_NUMBER = 10;

    private final List<Guest> guests;

    public Guests(final List<String> playerNames) {
        validateGuestNumbers(playerNames);

        this.guests = generateGuests(playerNames);
    }

    private List<Guest> generateGuests(final List<String> playerNames) {
        validateDuplicate(playerNames);

        return playerNames.stream()
                .map(playerName -> new Guest(new Name(playerName)))
                .collect(Collectors.toUnmodifiableList());
    }

    private void validateGuestNumbers(final List<String> playerNames) {
        if (playerNames.size() < MIN_GUESTS_NUMBER || playerNames.size() > MAX_GUESTS_NUMBER) {
            throw new IllegalArgumentException("참여자는 " + MIN_GUESTS_NUMBER + "명 이상 " + MAX_GUESTS_NUMBER + "명 이하여야 합니다.");
        }
    }

    private void validateDuplicate(final List<String> playerNames) {
        if (playerNames.size() != playerNames.stream().distinct().count()) {
            throw new IllegalArgumentException("참여자의 이름은 중복될 수 없습니다.");
        }
    }

    public List<Guest> getGuests() {
        return List.copyOf(guests);
    }
}