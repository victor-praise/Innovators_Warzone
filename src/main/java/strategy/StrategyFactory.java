package main.java.strategy;

/**
 * @author kevin on 2023-11-24
 */
public class StrategyFactory {
    public static PlayerStrategy generateStrategyObject(Strategy strategy) {
        PlayerStrategy playerStrategy = null;
        switch (strategy) {
            case Human -> playerStrategy = new HumanPlayerStrategy();
            case Aggressive -> playerStrategy = new AggressivePlayerStrategy();
            case Benevolent -> playerStrategy = new BenevolantPlayerStrategy();
            case Cheater -> playerStrategy = new CheaterPlayerStrategy();
            case Random -> playerStrategy = new RandomPlayerStrategy();
        }

        return playerStrategy;
    }
}
