import oop.ex2.SpaceShipPhysics;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

/**
 * Represents a special space ship.
 * a ship with extra powers than most ships in SpaceShip abstract class.
 *
 * @author rina karnauch
 */
public class SpecialShip extends SpaceShip {

    /*
     * special protection area for special ship where it sets its shield on and fires one shot.
     */
    private static final double SPECIAL_PROTECT_AREA = 0.1;

    /*
    image for no shield
     */
    private static final String SPECIAL_NO_SHIELD = "dogeSpecial.png";

    /*
    image for shield
     */
    private static final String SPECIAL_SHIELD = "DogeShield.png";

    /*
    shield rounds counter
     */
    private int shieldCounter = Values.ZERO;

    /*
     * death counter- each doge can die 3 times before actual game death.
     */
    private int deathCounter;

    /**
     * constructor
     */
    SpecialShip() {
        this.setCurrentEnergyLevel(Values.BEGINNING_ENERGY_LEVEL);
        this.setMaximalEnergy();
        this.setRoundCounter();
        this.setShieldMode(Values.SHIELD_ON_MODE);
        this.setHealthCurrentLevel();
        this.setLastFire(Values.UNDETERMINED);
        this.setPhysics(new SpaceShipPhysics());

        this.deathCounter = Values.ZERO;

    }

    /**
     * Does the actions of this ship for this round.
     * This is called once per round by the SpaceWars game driver.
     *
     * @param game the game object to which this ship belongs.
     */
    @Override
    public void doAction(SpaceWars game) {

        if (shieldCounter % Values.FOUR == Values.ZERO && (this.getShieldMode() == Values.SHIELD_ON_MODE)) {
            // if shield was on, each fourth round we turn it off.
            shieldOff();
        }

        shieldCounter += Values.ONE;

        boolean check = game.getGUI().isShieldsPressed(); // if shield is pressed- we favor on putting it on.
        if (check) {
            this.shieldOn();
        }

        SpaceShipPhysics thisPhysics = this.getPhysics(); // if we asked to teleport.
        if (game.getGUI().isTeleportPressed()) {
            this.teleportSpecial(game);
        }

        SpaceShip close = game.getClosestShipTo(this);

        if (Math.abs(this.getDistance(close)) <= SPECIAL_PROTECT_AREA) { // if we are in danger zone,
            // we shield on and fire, and teleport to get away again- because we arrived at not safe zone.
            if (this.getShieldMode() == Values.SHIELD_OFF_MODE) {
                shieldOn();
            }
            fire(game); // anyway we fire.
            teleport(); // and we teleport if possible.
        }

        boolean accel = false;
        int turnValue = Values.ZERO;

        // now we start handling what we want to do

        if (game.getGUI().isRightPressed()) { // we want only one of them to be pressed
            turnValue--;
        }
        if (game.getGUI().isLeftPressed()) { // we want only one of them to be pressed
            turnValue++;
        }
        if (game.getGUI().isUpPressed()) { // acceleration
            accel = true;
        }

        // and we always move faster
        thisPhysics.move(accel, turnValue);
        thisPhysics.move(accel, turnValue);

        if (Math.abs(this.getDistance(close)) <= SPECIAL_PROTECT_AREA) // we move even faster if we near anything
        {
            thisPhysics.move(accel, turnValue);
        }

        if (game.getGUI().isShotPressed() && this.checkIfAbleToFireAgain()) {
            Random rand = new Random();
            int randomFireAmount = rand.nextInt(Values.TEN) + Values.ONE;
            this.fireSpecial(game, randomFireAmount);
        }

        int deathCounterLimit = Values.THREE;
        if (this.isDead() && deathCounter < deathCounterLimit) { // it has 3 shots of not dying.
            this.reLive();
        }

        this.recharging(); // and recharge again
    }


    /*
     * a function to relive the dead special spaceship
     */
    private void reLive() {
        this.setCurrentEnergyLevel(Values.BEGINNING_ENERGY_LEVEL);
        this.setMaximalEnergy();
        this.setRoundCounter();
        this.setShieldMode(Values.SHIELD_ON_MODE);
        this.setHealthCurrentLevel();
        this.setLastFire(Values.UNDETERMINED);
        this.setPhysics(new SpaceShipPhysics());
        this.deathCounter += this.deathCounter;
    }

    /*
     * special fire function for specials ship
     *
     * @param game the game we play on
     */
    private void fireSpecial(SpaceWars game, int amount) {
        if (getEnergyCurrentLevel() >= Values.SHOT_FIRING_ENERGY_PRICE &&
                this.checkIfAbleToFireAgain()) {
            for (int i = Values.ZERO; i < amount; i++) {
                game.addShot(this.getPhysics());
            }
            this.setLastFire(this.getRoundCounter());
            setCurrentEnergyLevel(getEnergyCurrentLevel() - Values.SHOT_FIRING_ENERGY_PRICE);
        }
    }

    /**
     * Attempts to teleport.
     *
     * @param game the game we play on
     */
    public void teleportSpecial(SpaceWars game) {
        if (getEnergyCurrentLevel() >= Values.TELEPORTING_ENERGY_PRICE) {
            this.setPhysics(new SpaceShipPhysics());
            this.fireSpecial(game, Values.TWO);
            setCurrentEnergyLevel(this.getEnergyCurrentLevel() - Values.TELEPORTING_ENERGY_PRICE);
            this.setPhysics(new SpaceShipPhysics());
            this.shieldOn();
        }
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
     * function to get the image with no shield
     *
     * @return image not shielded
     */
    private Image readImageNoShield() {

        ImageIcon icon = new ImageIcon(SPECIAL_NO_SHIELD);
        return icon.getImage();
    }


    /*
     * function to get the image with shield on
     *
     * @return image shielded
     */
    private Image readImageShield() {

        ImageIcon icon = new ImageIcon(SPECIAL_SHIELD);
        return icon.getImage();
    }
}
