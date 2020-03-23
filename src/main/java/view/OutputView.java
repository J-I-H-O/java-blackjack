package view;

import dto.ResponsePlayerDTO;
import dto.ResponseWinningResultDTO;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OutputView {
    private static final String DELIMITER = ", ";
    private static final int DEALER_INDEX = 0;
    private static final int START_USER_INDEX = 1;
    private static final int DEALER_NOT_HIT = 0;

    private OutputView() {
    }

    public static void printErrorMessage(String message) {
        System.out.println(message);
    }

    public static void printInitialResult(List<ResponsePlayerDTO> result) {
        StringBuilder sb = new StringBuilder();
        sb.append(System.lineSeparator());
        for (int i = 0; i < result.size() - 1; i++) {
            sb.append(result.get(i).getName());
            sb.append(DELIMITER);
        }
        sb.append(result.get(result.size() - 1).getName());
        System.out.println(sb.toString() + "에게 2장의 카드를 나누었습니다.");
        printDealerCard(result);
        printUserCard(result);
        System.out.println();
    }

    private static void printDealerCard(List<ResponsePlayerDTO> result) {
        ResponsePlayerDTO dealerDTO = result.get(DEALER_INDEX);
        List<String> dealerCardNumbers = dealerDTO.getCardNumbers();
        String firstDealerCard = dealerCardNumbers.get(DEALER_INDEX);
        System.out.println(result.get(DEALER_INDEX).getName() + "카드: " + firstDealerCard);
    }

    private static void printUserCard(List<ResponsePlayerDTO> result) {
        for (int i = START_USER_INDEX; i < result.size(); i++) {
            printUserCard(result.get(i));
        }
    }

    public static void printUserCard(ResponsePlayerDTO result) {
        System.out.println(result.getName() + "카드: " + String.join(DELIMITER, result.getCardNumbers()));
    }

    public static void printDealerAdditionalCard(int dealerHitCount) {
        if (dealerHitCount == DEALER_NOT_HIT) {
            System.out.println("\n딜러는 17이상이라 카드를 더 받지 않았습니다.");
            return;
        }
        System.out.printf("\n딜러는 16이하라 카드를 %d장 더 받았습니다.\n", dealerHitCount - DEALER_NOT_HIT);
    }

    public static void printFinalResult(List<ResponsePlayerDTO> result) {
        System.out.println();
        for (ResponsePlayerDTO responsePlayerDTO : result) {
            System.out.println(responsePlayerDTO.getName() + "카드: "
                    + String.join(DELIMITER, responsePlayerDTO.getCardNumbers())
                    + " - 결과: " + responsePlayerDTO.getScore());
        }
    }

    public static void printFinalProfit(ResponseWinningResultDTO responseWinningResultDTO) {
        System.out.println("\n## 최종 수익");
        Map<String, Double> winningProfit = responseWinningResultDTO.getWinningProfit();
        for (String result : getWinningProfit(winningProfit)) {
            System.out.println(result);
        }
    }

    public static List<String> getWinningProfit(Map<String, Double> winningProfit) {
        return winningProfit.entrySet().stream()
                .map(entry -> String.format("%s: %.0f", entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }
}