/**
 * Represents consts for the classes in our projects
 * @author rina karnauch
 */
public final class Values {

    // USED VALUES FOR GAME:
    // HEALTH
    /**
     * health beginning rate
     */
    static final int HEALTH_BEGINNING_RATE = 22;
    /**
     * death lower bound
     */
    static final int DEATH_BOUND = 0;
    /**
     * health reduction amount per shot
     */
    static final int HEALTH_REDUCTION_PER_SHOT = 1;
    /**
     * health reduction amount per collision
     */
    static final int HEALTH_REDUCTION_PER_COLLISION = 1;

    //ENERGY
    /**
     * maximal starting energy level
     */
    static final int MAXIMAL_ENERGY_LEVEL = 210;
    /**
     * beginning current energy level
     */
    static final int BEGINNING_ENERGY_LEVEL = 190;
    /**
     * added value to energy level with shield and bashing
     */
    static final int ADDED_VALUE_TO_ENERGY_PER_BASHING = 18;
    /**
     * reduction to maximal value per shot or collision
     */
    static final int REDUCTION_VALUE_TO_MAXIMAL_ENERGY_PER_BEING_HIT = 10;
    /**
     * charging value per round
     */
    static final int CHARGING_ENERGY_VALUE_PER_ROUND = 1;
    /**
     * shot firing cost
     */
    static final int SHOT_FIRING_ENERGY_PRICE = 19;
    /**
     * teleporting cost
     */
    static final int TELEPORTING_ENERGY_PRICE = 140;
    /**
     * energy cost per shield
     */
    static final int ENERGY_CONSUMPTION_PER_SHIELDED_MODE = 3;

    //SHIELD
    /**
     * represents on shield
     */
    static final int SHIELD_ON_MODE = 1;
    /**
     * represents of shield
     */
    static final int SHIELD_OFF_MODE = 0;

    //GUNS
    /**
     * cycle of ungunable mode
     */
    static final int FIRE_CYCLE = 7;


    // MAGIC NUMBERS:
    /**
     * magic number 1
     */
    static final int ONE = 1;
    /**
     * magic number 0
     */
    static final int ZERO = 0;
    /**
     * magic number 2
     */
    static final int TWO = 2;
    /**
     * magic number 3
     */
    static final int THREE = 3;
    /**
     * magic number 4
     */
    static final int FOUR = 4;
    /**
     * magic number 7
     */
    static final int SEVEN = 7;
    /**
     * magic number 10
     */
    static final int TEN = 10;
    /**
     * magic number -1
     */
    static final int UNDETERMINED = -1;
}
