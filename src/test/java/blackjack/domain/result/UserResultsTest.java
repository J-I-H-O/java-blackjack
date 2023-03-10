package blackjack.domain.result;

import blackjack.domain.card.Card;
import blackjack.domain.card.CardNumber;
import blackjack.domain.card.CardSymbol;
import blackjack.domain.player.Dealer;
import blackjack.domain.player.Names;
import blackjack.domain.player.Player;
import blackjack.domain.player.Players;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UserResultsTest {

    @DisplayName("게임 결과를 생성할 수 있다.")
    @Test
    void createUserResults() {
        // given
        Dealer dealer = new Dealer();
        Players players = Players.from(new Names(List.of("a", "b", "c")));
        List<Player> singlePlayers = players.getPlayers();
        dealer.updateCardScore(new Card(CardNumber.THREE, CardSymbol.HEART));
        singlePlayers.get(0).updateCardScore(new Card(CardNumber.TWO, CardSymbol.HEART));
        singlePlayers.get(1).updateCardScore(new Card(CardNumber.THREE, CardSymbol.HEART));
        singlePlayers.get(2).updateCardScore(new Card(CardNumber.FOUR, CardSymbol.HEART));
        UserResults userResults = UserResults.of(dealer, players);

        // when & then
        assertThat(userResults.getResults().get(dealer))
                .contains(Result.WIN, Result.TIE, Result.LOSE);
        assertThat(userResults.getResults().get(singlePlayers.get(0)))
                .contains(Result.LOSE);
        assertThat(userResults.getResults().get(singlePlayers.get(1)))
                .contains(Result.TIE);
        assertThat(userResults.getResults().get(singlePlayers.get(2)))
                .contains(Result.WIN);
    }
}