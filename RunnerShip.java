import oop.ex2.SpaceShipPhysics;

import javax.swing.*;
import java.awt.*;

/**
 * Represents a runner space ship.
 * The ship is trying to get away from other ships, controlled by the computer.
 *
 * @author rina karnauch
 */
public class RunnerShip extends SpaceShip {


    /*
    minimal danger alert distance units
     */
    private static final double DANGER_DISTANCE_UNITS = 0.25;

    /*
    minimal danger alert angle units
     */
    private static final double DANGER_RADIAN_UNITS = 0.19;

    /*
    image for no shield for runner ship
     */
    private static final String RUNNER_SHIP_ICON = "RunnerNoShield.png";

    /**
     * constructor
     */
    RunnerShip() {
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

        final boolean accel = true;
        SpaceShip close = game.getClosestShipTo(this);
        SpaceShipPhysics thisPhysics = this.getPhysics();

        double dist = this.getDistance(close);
        double angle = this.getAngle(close); // return is the angle this should turn to face close-
        // if its <0 we should turn left to get away and if its >= 0 we should turn right to get away.

        int turnValue = Values.ONE; // we point to the right now.
        if (angle < Values.ZERO) { // we should go opposite to the angle
            turnValue *= Values.UNDETERMINED; // we make it point left, as said before.
        }

        boolean distCheck = (dist < DANGER_DISTANCE_UNITS);
        boolean radianCheck = (angle < DANGER_RADIAN_UNITS);
        int currentEnergy = this.getEnergyCurrentLevel();
        boolean ableToTeleport = (currentEnergy >= Values.TELEPORTING_ENERGY_PRICE);

        if (distCheck && radianCheck && ableToTeleport) {
            this.teleport();
        }

        thisPhysics.move(accel, turnValue);
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
        return readImageNoShield();
    }

    /*
    a function to read the image for the ship, with no shield
     */
    private Image readImageNoShield() {

        ImageIcon icon = new ImageIcon(RUNNER_SHIP_ICON);
        return icon.getImage();
    }

}
