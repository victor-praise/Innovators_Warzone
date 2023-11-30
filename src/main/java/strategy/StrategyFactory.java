package main.java.strategy;

/**
 * @author kevin on 2023-11-24
 */
public class StrategyFactory {
    public static PlayerStrategy generateStrategyObject(Strategy strategy) {
        PlayerStrategy playerStrategy = null;
        switch (strategy) {
            case Human -> playerStrategy = new HumanPlayerStrategy(strategy);
            case Aggressive -> playerStrategy = new AggressivePlayerStrategy(strategy);
            case Benevolent -> playerStrategy = new BenevolantPlayerStrategy(strategy);
            case Cheater -> playerStrategy = new CheaterPlayerStrategy(strategy);
            case Random -> playerStrategy = new RandomPlayerStrategy(strategy);
        }

        return playerStrategy;
    }
}
