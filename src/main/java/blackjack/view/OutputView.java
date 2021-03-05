package blackjack.view;

import blackjack.domain.participant.Dealer;
import blackjack.domain.participant.GameResult;
import blackjack.domain.participant.Participant;
import blackjack.domain.participant.Player;

import java.util.List;
import java.util.stream.Collectors;

public class OutputView {
    public static final String LINE_SEPARATOR = System.lineSeparator();
    public static final String GAME_RESULT_MESSAGE = "%d승 %d무 %d패" + LINE_SEPARATOR;
    public static final String CARD_INFO_MESSAGE = "%s카드: %s";
    public static final String SET_UP_MESSAGE = "%s와 %s에게 2장의 나누었습니다." + LINE_SEPARATOR;
    public static final String DEALER_DRAW_MESSAGE = "딜러는 16이하라 한장의 카드를 더 받았습니다.";
    public static final String RESULT_PREFIX = " - 결과 : %d" + LINE_SEPARATOR;
    public static final String NAME_PREFIX = "%s: ";

    private OutputView() {
    }

    public static void printSetup(Dealer dealer, List<Player> players) {
        String playerNames = players.stream()
                                    .map(Player::getName)
                                    .collect(Collectors.joining(", "));
        System.out.printf(SET_UP_MESSAGE, dealer.getName(), playerNames);

        printCardInfo(dealer);
        printMessage("");
        for (Player player : players) {
            printCardInfo(player);
            printMessage("");
        }
    }

    public static void printCardInfo(Participant participant) {
        String cardInfo = participant.getCards()
                                     .stream()
                                     .map(card -> card.getNumber() + card.getShape())
                                     .collect(Collectors.joining(", "));

        System.out.printf(CARD_INFO_MESSAGE, participant.getName(), cardInfo);
    }

    public static void printDealerDrawMessage(int dealerDrawCount) {
        for (int i = 0; i < dealerDrawCount; i++) {
            System.out.println(DEALER_DRAW_MESSAGE);
        }
    }

    public static void printFinalCardInfo(Dealer dealer, List<Player> players) {
        printFinalCardInfo(dealer);
        for (Player player : players) {
            printFinalCardInfo(player);
        }
    }

    private static void printFinalCardInfo(Participant participant) {
        printCardInfo(participant);
        System.out.printf(RESULT_PREFIX, participant.calculateResult());
    }

    public static void printWinOrLoseResult(Participant participant, GameResult gameResult) {
        String result = gameResultToString(gameResult);
        System.out.printf(NAME_PREFIX + result, participant.getName());
    }

    private static String gameResultToString(GameResult gameResult) {
        int win = gameResult.getWinCount();
        int draw = gameResult.getDrawCount();
        int lose = gameResult.getLoseCount();

        return String.format(GAME_RESULT_MESSAGE, win, draw, lose);
    }

    public static void printMessage(String s) {
        System.out.println(s);
    }

}
