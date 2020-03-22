package blackjack.view;

import blackjack.domain.card.Card;
import blackjack.domain.player.Dealer;
import blackjack.domain.player.Gambler;
import blackjack.domain.player.Player;
import blackjack.domain.player.Players;
import blackjack.view.dto.GameStatisticsDTO;

import java.util.List;
import java.util.stream.Collectors;

public class OutputView {
    public static void showCards(Players players) {
        List<Gambler> gamblers = players.findGamblers();
        Dealer dealer = players.findDealer();

        System.out.println(String.format("딜러와 %s에게 %d장을 나누었습니다.", collectGamblersNames(gamblers), Players.STARTING_CARD_SIZE));

        System.out.println(String.format("%s : %s", dealer.getName(), makeCardInfoWithHiddenCard(dealer)));
        gamblers.forEach(gambler -> System.out.println(getCardInfo(gambler)));
    }

    private static String collectGamblersNames(List<Gambler> gamblers) {
        return gamblers.stream()
                .map(Gambler::getName)
                .collect(Collectors.joining(", "));
    }

    public static void showCardInfo(Player player) {
        System.out.println(getCardInfo(player));
    }

    private static String getCardInfo(Player player) {
        return String.format("%s: %s", player.getName(), makeCardInfo(player));
    }

    private static String makeCardInfo(Player player) {
        return player.getCardBundle().stream()
                .map(card -> String.format("%s%s", card.getMessage(), card.getSymbol()))
                .collect(Collectors.joining(", "));
    }

    private static String makeCardInfoWithHiddenCard(Player dealer) {
        List<Card> cards = dealer.getCardBundle();
        return cards.get(0).getMessage() + cards.get(0).getSymbol();
    }

    private static String getCardList(Players players) {
        List<Player> gamePlayers = players.getPlayers();
        return gamePlayers.stream()
                .map(player -> String.format("%s 결과 - %d", getCardInfo(player), player.getScoreValue()))
                .collect(Collectors.joining(System.lineSeparator()));
    }

    public static void showDealerDrawMessage() {
        System.out.println("딜러는 16이하라 한장의 카드를 더 받았습니다.\n");
    }

    public static void showReports(Players players) {
        showFinalPlayersCards(players);
        GameStatisticsDTO gameStatisticsDTO = new GameStatisticsDTO(players.getReports());
        System.out.println("\n## 최종 수익");
        System.out.println(gameStatisticsDTO.getDealerResult());
        System.out.println(gameStatisticsDTO.getGamblerResult());
    }

    private static void showFinalPlayersCards(Players players) {
        System.out.println(getCardList(players));
    }
}