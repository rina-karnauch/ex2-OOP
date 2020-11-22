import oop.ex2.SpaceShipPhysics;
import javax.swing.*;
import java.awt.*;

/**
 * Represents a human space ship.
 * The player is controlling the ship.
 * @author rina karnauch
 */
public class HumanShip extends SpaceShip {

    /*
     * image for no shield human ship
     */
    private static final String HUMAN_SHIP_NO_SHIELD = "humanShipNoShield.png";

    /*
     * image for shield human ship
     */
    private static final String HUMAN_SHIP_SHIELDED = "humanShipShield.png";

    /**
     * constructor
     */
    HumanShip() {
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

        shieldOff(); // off shield from last session, because otherwise it won't be visible.

        SpaceShipPhysics thisPhysics = this.getPhysics();

        int turnValue = Values.ZERO;
        boolean accel = false;
        boolean bothRightAndLeft = game.getGUI().isLeftPressed() && game.getGUI().isRightPressed();

        if (game.getGUI().isTeleportPressed()) {
            this.teleport();
        }
        if (game.getGUI().isRightPressed() && !bothRightAndLeft) { // we want only one of them to be pressed
            turnValue--;
        }
        if (game.getGUI().isLeftPressed() && !bothRightAndLeft) { // we want only one of them to be pressed
            turnValue++;
        }
        if (game.getGUI().isUpPressed()) { // acceleration
            accel = true;
        }

        thisPhysics.move(accel, turnValue);

        boolean check = game.getGUI().isShieldsPressed();
        if (check) {
            this.shieldOn();
        }
        if (game.getGUI().isShotPressed() && this.checkIfAbleToFireAgain()) {
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
    a function to read the image for th ship, no shield
     */
    private Image readImageNoShield() {

        ImageIcon icon = new ImageIcon(HUMAN_SHIP_NO_SHIELD);
        return icon.getImage();
    }

    /*
    a function to read the image for the ship, with shield
     */
    private Image readImageShield() {

        ImageIcon icon = new ImageIcon(HUMAN_SHIP_SHIELDED);
        return icon.getImage();
    }

}
