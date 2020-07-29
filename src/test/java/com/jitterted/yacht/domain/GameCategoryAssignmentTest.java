package com.jitterted.yacht.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class GameCategoryAssignmentTest {

  private static final ScoreCategory ARBITRARY_SCORE_CATEGORY = ScoreCategory.FULLHOUSE;

  @Test
  public void newRollThenLastRollIsNotYetAssignedToCategory() throws Exception {
    Game game = new Game();
    game.rollDice();

    assertThat(game.lastRollAssignedToCategory())
        .isFalse();
  }

  @Test
  public void newRollWhenAssignedThenRollIsAssignedToCategory() throws Exception {
    Game game = new Game();
    game.rollDice();

    game.assignRollTo(ARBITRARY_SCORE_CATEGORY);

    assertThat(game.lastRollAssignedToCategory())
        .isTrue();
  }

  @Test
  public void newRollAfterAssignmentWhenRollAgainThenRollIsNotAssignedToCategory() throws Exception {
    Game game = new Game();
    game.rollDice();
    game.assignRollTo(ARBITRARY_SCORE_CATEGORY);

    game.rollDice();

    assertThat(game.lastRollAssignedToCategory())
        .isFalse();
  }

  @Test
  public void newRollAfterAssignmentThenShouldNotBeAbleToReRoll() throws Exception {
    Game game = new Game();
    game.rollDice();

    game.assignRollTo(ARBITRARY_SCORE_CATEGORY);

    assertThat(game.canReRoll())
        .isFalse();
  }

  @Test
  public void newGameThenAllCategoriesAreNotAssigned() throws Exception {
    Game game = new Game();

    assertThat(game.scoreboard.allCategoriesAssigned())
        .isFalse();
  }

  @Test
  public void assigningToAllCategoriesEndsTheGame() throws Exception {
    Game game = new Game();

    assignRollToAllCategories(game);

    assertThat(game.scoreboard.allCategoriesAssigned())
        .isTrue();
  }

  private void assignRollToAllCategories(Game game) {
    for (ScoreCategory scoreCategory : ScoreCategory.values()) {
      game.rollDice();
      game.assignRollTo(scoreCategory);
    }
  }
}
