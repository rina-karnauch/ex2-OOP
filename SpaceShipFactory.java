import java.lang.String;

public class SpaceShipFactory {

    private static final char HUMAN = 'h';
    private static final char RUNNER = 'r';
    private static final char DRUNK = 'd';
    private static final char SPECIAL = 's';
    private static final char AGGRESSIVE = 'a';
    private static final char BASHER = 'b';
    private static final int hLimit = Values.ONE;


    /**
     * a function to create all given spaceships
     * @param args the arguments from the command line
     * @return array of ships
     */
    public static SpaceShip[] createSpaceShips(String[] args) {
        int hCounter = Values.ZERO;
        if (args.length == Values.ZERO) {
            return null;
        }
        SpaceShip[] ships = new SpaceShip[args.length];
        int length = args.length;
        for (int i = 0; i < length; i++) {
            char c = args[i].charAt(Values.ZERO);
            switch (c) {
                case HUMAN: {
                    if (hCounter == hLimit) {
                        ships[i] = null;
                    } else {
                        ships[i] = new HumanShip();
                        hCounter++;
                    }
                    break;
                }
                case RUNNER: {
                    ships[i] = new RunnerShip();
                    break;
                }
                case BASHER: {
                    ships[i] = new BasherShip();
                    break;
                }
                case AGGRESSIVE: {
                    ships[i] = new AggressiveShip();
                    break;
                }
                case DRUNK: {
                    ships[i] = new DrunkardShip();
                    break;
                }
                case SPECIAL: {
                    ships[i] = new SpecialShip();
                    break;
                }
                default:
                    ships[i] = null;
                    break;
            }
        }
        return ships;
    }

}
