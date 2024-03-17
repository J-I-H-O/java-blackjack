package blackjack.controller;

import blackjack.domain.cardgame.BlackjackGame;
import blackjack.domain.player.Dealer;
import blackjack.domain.player.Player;
import blackjack.view.InputView;
import blackjack.view.OutputView;
import java.util.List;

public class BlackjackController {
    public void run() {
        final List<String> names = InputView.askPlayerNames();
        final List<Player> players = createPlayersByNames(names);
        final Dealer dealer = new Dealer();
        final BlackjackGame blackjackGame = new BlackjackGame();

        blackjackGame.initializeHand(dealer, players);
        OutputView.printInitialHandOfEachPlayer(dealer, players);

        bet(dealer, players);

        givePlayersMoreCardsIfWanted(blackjackGame, players);
        giveDealerMoreCardsIfNeeded(blackjackGame, dealer);

        printHandStatusOfEachPlayer(dealer, players);
        printPlayerProfits(dealer);
    }

    private static List<Player> createPlayersByNames(final List<String> names) {
        return names.stream()
                .map(Player::new)
                .toList();
    }

    private void givePlayersMoreCardsIfWanted(final BlackjackGame blackjackGame, final List<Player> players) {
        for (final Player player : players) {
            givePlayerMoreCardsIfWanted(blackjackGame, player);
        }
    }

    private void givePlayerMoreCardsIfWanted(final BlackjackGame blackjackGame, final Player player) {
        final String playerName = player.getName();
        while (!player.isBust() && InputView.askForMoreCard(playerName)) {
            blackjackGame.giveCard(player);
            OutputView.printPlayerCard(player);
        }
    }

    private void giveDealerMoreCardsIfNeeded(final BlackjackGame blackjackGame, final Dealer dealer) {
        while (dealer.isMoreCardNeeded()) {
            blackjackGame.giveCard(dealer);
            OutputView.printDealerHitMessage(dealer);
        }
    }

    private void printHandStatusOfEachPlayer(final Dealer dealer, final List<Player> players) {
        OutputView.printPlayerCardWithScore(dealer);
        for (final Player player : players) {
            OutputView.printPlayerCardWithScore(player);
        }
    }

    private static void bet(final Dealer dealer, final List<Player> players) {
        for (Player player : players) {
            final int bettingAmount = InputView.askBettingAmount(player.getName());
            dealer.bet(player, bettingAmount);
        }
    }

    private void printPlayerProfits(final Dealer dealer) {
        OutputView.printProfits(dealer.findPlayerProfits());
    }
}
