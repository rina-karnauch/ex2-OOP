import oop.ex2.SpaceShipPhysics;

import javax.swing.*;
import java.awt.*;

/**
 * Represents a basher space ship.
 * The computer is controlling the ship and is trying to clash at other ships.
 *
 * @author rina karnauch
 */
public class BasherShip extends SpaceShip {

    /*
    attacking distance unit
     */
    private static final double ATTACK_DISTANCE_UNITS = 0.19;

    /*
    image for no shield path
     */
    private static final String BASHER_SHIP_NO_SHIELD = "basherAssadNoShield.png";

    /*
    image for shield path
     */
    private static final String BASHER_SHIP_SHIELD = "basherAssadShield.png";

    /**
     * constructor
     */
    BasherShip() {
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

        shieldOff();
        SpaceShip close = game.getClosestShipTo(this);
        this.AttackingShips(game);

        boolean distCheck = this.getDistance(close) <= ATTACK_DISTANCE_UNITS;
        if (distCheck) {
            this.shieldOn();
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
     a function to read the image for the ship, with shield
     */
    private Image readImageNoShield() {

        ImageIcon icon = new ImageIcon(BASHER_SHIP_NO_SHIELD);
        return icon.getImage();
    }

    /*
    a function to read the image for the ship, with shield
     */
    private Image readImageShield() {

        ImageIcon icon = new ImageIcon(BASHER_SHIP_SHIELD);
        return icon.getImage();
    }
}
