import oop.ex2.SpaceShipPhysics;

import javax.swing.*;
import java.awt.*;

/**
 * Represents an aggressive space ship.
 * The ship is trying to attack close ships, controlled by the computer.
 *
 * @author rina karnauch
 */
public class AggressiveShip extends SpaceShip {

    /*
    minimal distance to start attacking
     */
    private static final double ATTACK_DISTANCE_UNITS = 0.21;
    /*
    image for no shield aggressive
     */
    private static final String AGGRESSIVE_SHIP_NO_SHIELD = "AggressiveNoShield.png";
    /*
    image in case we would want aggressive
     */
    private static final String AGGRESSIVE_SHIP_SHIELD = "AggressiveShield.png";

    /**
     * constructor
     */
    AggressiveShip() {
        this.setCurrentEnergyLevel(Values.BEGINNING_ENERGY_LEVEL);
        this.setMaximalEnergy();
        this.setRoundCounter();
        this.setShieldMode(Values.SHIELD_OFF_MODE);
        this.setHealthCurrentLevel();
        this.setLastFire(Values.UNDETERMINED);
        this.setPhysics(new SpaceShipPhysics());
    }

    /**
     * Does the actions of this ship for this round.
     * This is called once per round by the SpaceWars game driver.
     *
     * @param game the game object to which this ship belongs.
     */
    @Override
    public void doAction(SpaceWars game) {

        AttackingShips(game);
        SpaceShip close = game.getClosestShipTo(this);
        boolean angleCheck = Math.abs(this.getAngle(close)) < ATTACK_DISTANCE_UNITS;
        if (angleCheck) {
            this.fire(game);
        }

        this.recharging();
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
        } else {
            return readImageShield();
        }
    }

    /*
    a function to read the image for the ship, with no shield
     */
    private Image readImageNoShield() {

        ImageIcon icon = new ImageIcon(AGGRESSIVE_SHIP_NO_SHIELD);
        return icon.getImage();
    }

    /*
    a function to read the image for the ship, with shield
     */
    private Image readImageShield() {

        ImageIcon icon = new ImageIcon(AGGRESSIVE_SHIP_SHIELD);
        return icon.getImage();
    }
}
