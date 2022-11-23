package com.jitterted.yacht.adapter.web;

import com.jitterted.yacht.adapter.out.dieroller.RandomDieRoller;
import com.jitterted.yacht.application.DiceRoller;
import com.jitterted.yacht.application.GameService;
import com.jitterted.yacht.domain.ScoreCategory;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;

import static org.assertj.core.api.Assertions.*;

public class YachtControllerRuleTest {

    @Test
    public void newGameDoesNotRollDiceSoNoRollToAssign() throws Exception {
        GameService gameService = new GameService(new DiceRoller(RandomDieRoller.createNull()));
        YachtController yachtController = new YachtController(gameService);

        yachtController.startGame();

        assertThat(gameService.roundCompleted())
                .isTrue();
    }

    @Test
    public void givenRollHasNotBeenAssignedThenRollAssignedToCategoryIsFalse() throws Exception {
        GameService gameService = new GameService(new DiceRoller(RandomDieRoller.createNull()));
        YachtController yachtController = new YachtController(gameService);
        yachtController.startGame();
        yachtController.rollDice();

        Model model = new ConcurrentModel();
        yachtController.rollResult(model);

        assertThat(model.getAttribute("roundCompleted"))
                .isEqualTo(Boolean.FALSE);
    }

    @Test
    public void givenRollWhenAssignedThenRollAssignedToCategoryIsTrue() throws Exception {
        GameService gameService = new GameService(new DiceRoller(RandomDieRoller.createNull()));
        YachtController yachtController = new YachtController(gameService);
        yachtController.startGame();
        yachtController.rollDice();

        yachtController.assignRollToCategory(ScoreCategory.FULLHOUSE.toString());

        Model model = new ConcurrentModel();
        yachtController.rollResult(model);

        assertThat(model.getAttribute("roundCompleted"))
                .isEqualTo(Boolean.TRUE);
    }
}
