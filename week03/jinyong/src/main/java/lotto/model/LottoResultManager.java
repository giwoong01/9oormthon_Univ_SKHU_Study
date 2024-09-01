package lotto.model;

public class LottoResultManager {
    //당첨번호 포함 여부 확인
    public boolean containsWinningNumber(Lotto lotto, int number) {
        return lotto.getNumbers().contains(number);
    }

    //당첨번호와 보너스 번호를 비교하여 각 로또의 등수를 반환(개별 로또 번호에 대한 결과 int 반환)
    public int getLottoRankNumber(Lotto winningLotto, Lotto userLotto) {
        return (int) userLotto.getNumbers().stream()
                .filter(number -> containsWinningNumber(winningLotto, number))
                .count();
    }

    //getLottoRank 메소드를 통해 로또의 Rank 반환
    public Rank getLottoRank(Lotto winningLotto, Lotto userLotto, int bonusNumber) {
        int count = getLottoRankNumber(winningLotto, userLotto);
        boolean matchBonus = containsWinningNumber(userLotto, bonusNumber);
        return Rank.provideRank(count, matchBonus);
    }

    //수익률 계산
    public double calculateEarningRate(int totalPurchaseAmount, int totalWinningAmount) {
        return Math.round(((double) totalWinningAmount / totalPurchaseAmount) * 100.0) / 100.0;
    }


}
