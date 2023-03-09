package blackjack.domain.game;

import blackjack.domain.participant.Participant;
import blackjack.domain.participant.Participants;

import java.util.List;
import java.util.Map;

import static blackjack.domain.game.WinTieLose.*;

public class ResultGame {

    private final Map<Participant, WinTieLose> playersResult;

    public ResultGame(final Map<Participant, WinTieLose> playersResult) {
        this.playersResult = playersResult;
    }

    public void calculateResult(final Participants participants) {
        final Participant dealer = participants.getDealer();
        final List<Participant> players = participants.getPlayers();

        final Score dealerScore = dealer.getScore();
        for (final Participant player : players) {
            playersResult.put(player, getPlayerResult(player.getScore(), dealerScore));
        }
    }

    private WinTieLose getPlayerResult(final Score playerScore, final Score dealerScore) {
        if ((playerScore.isBust() && dealerScore.isBust()) || playerScore.isEqualsTo(dealerScore)) {
            return TIE;
        }
        if (playerScore.isHit() && (dealerScore.isBust() || playerScore.isGreaterThan(dealerScore))) {
            return WIN;
        }
        return LOSE;
    }

    public int getDealerCount(final WinTieLose expected) {
        return (int) playersResult.values()
                .stream()
                .filter(winTieLose -> winTieLose.equals(expected.reverse()))
                .count();
    }

    public WinTieLose getPlayerResult(final Participant player) {
        return playersResult.get(player);
    }
}