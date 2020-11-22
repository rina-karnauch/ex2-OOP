import oop.ex2.SpaceShipPhysics;

import java.awt.*;

/**
 * abstract class SpaceShip which describes an abstract ship, which is a "father" for several more subtypes of ships.
 *
 * @author oop staff and rina karnauch
 */
public abstract class SpaceShip {

    /*
     * health current level of current spacehsip.
     */
    private int healthCurrentLevel;

    /*
     * energy current level of current spaceship.
     */
    private int energyCurrentLevel;

    /*
     * energy maximal level of current ship.
     */
    private int energyMaximalLevel;

    /*
     * shield mode of the current spaceship: on or off.
     */
    private int shieldMode;

    /*
     * counter of the number of current round for the spaceship.
     */
    private int roundCounter;

    /*
     * saver of the last round when fire has been shot
     */
    private int lastFireRound;

    /*
     * physics position of the ship
     */
    private SpaceShipPhysics shipPhysics;

    /**
     * Does the actions of this ship for this round.
     * This is called once per round by the SpaceWars game driver.
     *
     * @param game the game object to which this ship belongs.
     */
    public abstract void doAction(SpaceWars game);

    /**
     * This method is called every time a collision with this ship occurs
     */
    public void collidedWithAnotherShip() {
        if (this.shieldMode == Values.SHIELD_OFF_MODE && !this.isDead()) {
            this.shipEnergyReductionsPerHit(); // down in energy levels
            this.healthCurrentLevel -= Values.HEALTH_REDUCTION_PER_COLLISION; // health
        } else if (this.shieldMode == Values.SHIELD_ON_MODE && !this.isDead()) { // BASHING MODE,
            // shield is up- no damage
            this.energyMaximalLevel += Values.ADDED_VALUE_TO_ENERGY_PER_BASHING;
            this.energyCurrentLevel += Values.ADDED_VALUE_TO_ENERGY_PER_BASHING;
        }
    }

    /**
     * This method is called whenever a ship has died. It resets the ship's
     * attributes, and starts it at a new random position.
     */
    public void reset() {

        this.healthCurrentLevel = Values.HEALTH_BEGINNING_RATE;
        this.energyCurrentLevel = Values.BEGINNING_ENERGY_LEVEL;
        this.energyMaximalLevel = Values.MAXIMAL_ENERGY_LEVEL;
        this.shieldMode = Values.SHIELD_OFF_MODE;
        this.roundCounter = Values.ZERO;
        this.lastFireRound = Values.UNDETERMINED;

        this.shipPhysics = new SpaceShipPhysics();
    }

    /**
     * Checks if this ship is dead.
     *
     * @return true if the ship is dead. false otherwise.
     */
    public boolean isDead() {
        return this.healthCurrentLevel == Values.DEATH_BOUND;
    }

    /**
     * Gets the physics object that controls this ship.
     *
     * @return the physics object that controls the ship.
     */
    public SpaceShipPhysics getPhysics() {
        return this.shipPhysics;
    }

    /**
     * setter function
     *
     * @param newPhysics the new physics value
     */
    protected void setPhysics(SpaceShipPhysics newPhysics) {
        this.shipPhysics = newPhysics;
    }

    /**
     * This method is called by the SpaceWars game object when ever this ship
     * gets hit by a shot.
     */
    public void gotHit() {
        if (this.getShieldMode() == Values.SHIELD_OFF_MODE && !this.isDead()) {
            this.shipEnergyReductionsPerHit(); // energy reduction
            this.healthCurrentLevel -= Values.HEALTH_REDUCTION_PER_SHOT; // health reduction per shot
        } //otherwise shield is up- no damage.
    }

    /**
     * Gets the image of this ship. This method should return the image of the
     * ship with or without the shield. This will be displayed on the GUI at
     * the end of the round.
     *
     * @return the image of this ship.
     */
    public abstract Image getImage();

    /**
     * Attempts to fire a shot.
     *
     * @param game the game object.
     */
    public void fire(SpaceWars game) {
        if (energyCurrentLevel >= Values.SHOT_FIRING_ENERGY_PRICE &&
                this.checkIfAbleToFireAgain()) {
            game.addShot(this.getPhysics());
            this.lastFireRound = this.roundCounter; // we update the current last fired time.
            energyCurrentLevel -= Values.SHOT_FIRING_ENERGY_PRICE; // shot price
        }
    }

    /**
     * Attempts to turn on the shield.
     */
    public void shieldOn() {
        if (energyCurrentLevel >= Values.ENERGY_CONSUMPTION_PER_SHIELDED_MODE
                && this.getShieldMode() == Values.SHIELD_OFF_MODE) {
            energyCurrentLevel -= Values.ENERGY_CONSUMPTION_PER_SHIELDED_MODE;
            this.shieldMode = Values.SHIELD_ON_MODE;
        }
    }

    /**
     * Attempts to turn off the shield.
     */
    protected void shieldOff() {
        this.shieldMode = Values.SHIELD_OFF_MODE;
    }

    /**
     * Attempts to teleport the ship.
     */
    public void teleport() {
        if (energyCurrentLevel >= Values.TELEPORTING_ENERGY_PRICE) {
            this.shipPhysics = new SpaceShipPhysics();
            this.energyCurrentLevel -= Values.TELEPORTING_ENERGY_PRICE;
        }
    }

    /*
     * a function to handle Reductions to Energy levels after a hit(collsion nor shot).
     */
    private void shipEnergyReductionsPerHit() {
        // the function is being called only when shields are down, by an outer function.
        this.energyMaximalLevel -= Values.REDUCTION_VALUE_TO_MAXIMAL_ENERGY_PER_BEING_HIT; // maximal energy reduction
        if (energyCurrentLevel > this.energyMaximalLevel) {
            energyMaximalLevel = this.energyCurrentLevel; // current energy
        }
    }

    /**
     * a setter function for health value.
     */
    protected void setHealthCurrentLevel() {
        this.healthCurrentLevel = Values.HEALTH_BEGINNING_RATE;
    }

    /**
     * setter function for maximal energy value.
     */
    protected void setMaximalEnergy() {
        this.energyMaximalLevel = Values.MAXIMAL_ENERGY_LEVEL;
    }

    /**
     * getter function for current energy level.
     *
     * @return current energy level.
     */
    protected int getEnergyCurrentLevel() {
        return this.energyCurrentLevel;
    }

    /**
     * setter function for current energy level.
     *
     * @param value value for new current energy level
     */
    protected void setCurrentEnergyLevel(int value) {
        this.energyCurrentLevel = value;
    }

    /**
     * getter function for current shield mode.
     *
     * @return current shield mode- on or off.
     */
    protected int getShieldMode() {
        return this.shieldMode;
    }

    /**
     * setter for shield mode.
     *
     * @param wantedMode the wanted shield mode.
     */
    protected void setShieldMode(int wantedMode) {
        this.shieldMode = wantedMode;
    }

    /**
     * getter function for current round counter.
     *
     * @return current round count.
     */
    protected int getRoundCounter() {
        return this.roundCounter;
    }

    /**
     * setter function for current round counter.
     */
    protected void setRoundCounter() {
        this.roundCounter = Values.ZERO;
    }

    /*
    a function to get last fire round
     */
    private int getLastFire() {
        return this.lastFireRound;
    }

    /**
     * setter function for last fire round count.
     *
     * @param value new value for last fire round
     */
    protected void setLastFire(int value) {
        this.lastFireRound = value;
    }

    /*
     * getter functions
     *
     * @param beginRound beginning round count.
     * @param endRound   ending round count.
     * @return how many rounds have passed
     */
    private int getRoundDifference(int beginRound, int endRound) {
        return (endRound - beginRound);
    }

    /**
     * getter of gun fire current availability
     *
     * @return true for avaliable to fire, false otherwise.
     */
    protected boolean checkIfAbleToFireAgain() {
        return (this.lastFireRound == Values.UNDETERMINED) ||
                (getRoundDifference(getLastFire(), getRoundCounter()) > Values.FIRE_CYCLE);
    }

    /**
     * a function to recharge the current ship with energy after a round.
     */
    protected void recharging() {
        this.roundCounter += Values.ONE; // one more round to count
        this.energyCurrentLevel += Values.CHARGING_ENERGY_VALUE_PER_ROUND;
        if (energyCurrentLevel > energyMaximalLevel) {
            energyMaximalLevel = energyCurrentLevel;
        }
    }

    /**
     * a function to get distatnce between close and current ship.
     *
     * @param close spaceship closest object.
     * @return distance from current ship to closest ship.
     */
    protected double getDistance(SpaceShip close) { // added to get distance and avoid duplicated functions
        SpaceShipPhysics closePhysics = close.getPhysics();
        SpaceShipPhysics thisPhysics = this.getPhysics();

        double distanceX = Math.pow(closePhysics.getX() - thisPhysics.getX(), Values.TWO);
        double distanceY = Math.pow(closePhysics.getY() - thisPhysics.getY(), Values.TWO);

        return Math.sqrt(distanceX + distanceY);
    }

    /**
     * a function to get angle between close and current ship.
     *
     * @param close spacehip closest object
     * @return angle from current ship to closest ship.
     */
    protected double getAngle(SpaceShip close) { // added to get angle and avoid duplicated functions
        SpaceShipPhysics closePhysics = close.getPhysics();
        SpaceShipPhysics thisPhysics = this.getPhysics();
        return closePhysics.angleTo(thisPhysics);
    }

    /**
     * a function to act as an attacking ship (basher or aggressive) and move towards the current close ship.
     *
     * @param game the game of spacewars we now play.
     */
    protected void AttackingShips(SpaceWars game) { // added so we have don't have duplicated code.
        int turnValue = Values.ONE;
        final boolean accel = true;

        SpaceShip close = game.getClosestShipTo(this);
        SpaceShipPhysics thisPhysics = this.getPhysics();

        double angle = this.getAngle(close); // if we get angle<0 we should go right(+) for the ship, otherwise left(-)

        if (angle >= Values.ZERO) {
            turnValue *= Values.UNDETERMINED;
        }

        thisPhysics.move(accel, turnValue);
    }
}
