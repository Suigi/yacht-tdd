package com.jitterted.yacht.domain;

import com.jitterted.yacht.application.DiceRoller;

import java.util.List;

public class Game {

    @Deprecated  // Domain should not reference this Application Service class
    private final DiceRoller diceRoller;

    private final Scoreboard scoreboard = new Scoreboard();

    private DiceRoll lastRoll;

    private Rolls rolls = Rolls.start();

    private boolean roundCompleted;

    public Game(DiceRoller diceRoller) {
        this.diceRoller = diceRoller;
        lastRoll = DiceRoll.empty();
        roundCompleted = true;
    }

    public void rollDice() {
        roundCompleted = false;
        rolls = Rolls.start();
        lastRoll = diceRoller.roll();
    }

    public void reRoll(List<Integer> keptDice) {
        requireRerollsRemaining();
        rolls.increment();
        lastRoll = diceRoller.reRoll(keptDice);
    }

    private void requireRerollsRemaining() {
        if (!canReRoll()) {
            throw new TooManyRollsException();
        }
    }

    public DiceRoll lastRoll() {
        return lastRoll;
    }

    public int score() {
        return scoreboard.score();
    }

    public void assignRollTo(ScoreCategory scoreCategory) {
        requireRollNotYetAssigned();
        scoreboard.scoreAs(scoreCategory, lastRoll);
        roundCompleted = true;
    }

    private void requireRollNotYetAssigned() {
        if (roundCompleted) {
            throw new IllegalStateException();
        }
    }

    public List<ScoredCategory> scoredCategories() {
        return scoreboard.scoredCategories();
    }

    public boolean canReRoll() {
        if (roundCompleted()) {
            return false;
        }
        return rolls.canReRoll();
    }

    public boolean roundCompleted() {
        return roundCompleted;
    }

    public boolean isOver() {
        return scoreboard.isComplete();
    }

}
