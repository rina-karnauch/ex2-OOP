import oop.ex2.SpaceShipPhysics;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

/**
 * Represents a drunk space ship.
 * The ship is confused and controlled by the computer.
 *
 * @author rina karnauch
 */
public class DrunkardShip extends SpaceShip {

    /*
    value to stop attacking units in distance
     */
    private final double UNATTACK_DISTANCE_UNITS;

    /*
    value to stop attacking units in angles
     */
    private final double UNATTACK_ANGLE_UNITS;

    /*
    counter for vomit cycles
     */
    private int vomitFlag = Values.ZERO;

    /*
    image for no shield
     */
    private static final String DRUNK_NO_SHIELD = "DrunkNoShield.png";

    /*
    image for shield
     */
    private static final String DRUNK_SHIELD = "DrunkShield.png";

    /*
    image for beign wasted
     */
    private static final String DRUNK_WASTED = "wasted.png";

    /*
    counter for random actions of firing from time to time
     */
    private int crazyCounter = Values.ZERO;

    /**
     * constructor
     */
    DrunkardShip() {
        this.setCurrentEnergyLevel(Values.BEGINNING_ENERGY_LEVEL);
        this.setMaximalEnergy();
        this.setRoundCounter();
        this.setShieldMode(Values.SHIELD_OFF_MODE);
        this.setHealthCurrentLevel();
        this.setLastFire(Values.UNDETERMINED);
        this.setPhysics(new SpaceShipPhysics());

        Random rand = new Random();
        UNATTACK_DISTANCE_UNITS = rand.nextDouble();
        UNATTACK_ANGLE_UNITS = rand.nextDouble();
    }

    /**
     * Does the actions of this ship for this round.
     * This is called once per round by the SpaceWars game driver.
     *
     * @param game the game object to which this ship belongs.
     */
    @Override
    public void doAction(SpaceWars game) {

        int turnValue = Values.ONE;

        SpaceShip close = game.getClosestShipTo(this);
        SpaceShipPhysics thisPhysics = this.getPhysics();

        double dist = this.getDistance(close);
        double angle = this.getAngle(close);

        if (Math.abs(angle) >= UNATTACK_ANGLE_UNITS) { // if we are far far, we want to move far away.
            turnValue *= Values.UNDETERMINED;
            thisPhysics.move(false, turnValue);
        }

        if (dist >= UNATTACK_DISTANCE_UNITS) { // we are far, we will think its danger
            shieldOn();
            thisPhysics.move(true, turnValue); // lets try and run accel
            this.teleport();
            for (int i = Values.ZERO; i < Values.SEVEN; i++) { // we just fire with no reason
                this.fire(game);
            }
        } else if (dist < UNATTACK_DISTANCE_UNITS) { // we are close, we should not get inside
            // , so we turn it off because drunk people get in danger
            shieldOff();
        }

        if (crazyCounter % Values.FOUR == Values.ZERO) { // we only get recharge not every round
            recharging();
        } else if (crazyCounter % Values.SEVEN == Values.ZERO) // from time to time we spit out
        // some fire
        {
            this.fire(game);
            vomitFlag = Values.ONE;
        }

        crazyCounter++;
    }

    /**
     * Gets the image of this ship. This method should return the image of the
     * ship with or without the shield. This will be displayed on the GUI at
     * the end of the round.
     *
     * @return the image of this ship.
     */
    @Override
    public Image getImage() {
        if (this.getShieldMode() == Values.SHIELD_OFF_MODE) {
            return readImageNoShield();
        } else if (vomitFlag == Values.ONE) {
            return vomitImage();
        } else {
            return readImageShield();
        }
    }

    /*
    a function to read the image for the ship, with no shield
     */
    private Image readImageNoShield() {

        ImageIcon icon = new ImageIcon(DRUNK_NO_SHIELD);
        return icon.getImage();
    }

    /*
    a function to read the image for the ship, with  shield
     */
    private Image readImageShield() {

        ImageIcon icon = new ImageIcon(DRUNK_SHIELD);
        return icon.getImage();
    }

    /*
    a function to read the image for the ship, wasted.
     */
    private Image vomitImage() {
        ImageIcon icon = new ImageIcon(DRUNK_WASTED);
        return icon.getImage();
    }

}
