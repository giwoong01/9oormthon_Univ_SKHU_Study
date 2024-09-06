package lotto.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public record Lottos(List<Lotto> lottos) {
    public Lottos(List<Lotto> lottos) {
        this.lottos = new ArrayList<>(lottos);
    }

    public static Lottos generateLottos(int count) {
        List<Lotto> lottos = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            lottos.add(new Lotto(RandomNumbers.generateRandomNumbers()));
        }
        return new Lottos(Collections.unmodifiableList(lottos));
    }

    @Override
    public List<Lotto> lottos() {
        return new ArrayList<>(lottos);
    }

    public int size() {
        return lottos.size();
    }
}