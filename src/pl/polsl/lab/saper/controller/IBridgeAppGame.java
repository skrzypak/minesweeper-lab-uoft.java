package pl.polsl.lab.saper.controller;

import pl.polsl.lab.saper.model.IEnumGame;

/**
 * Interface that used to communicate between AppController and GameController
 *
 * @author Konrad Skrzypczyk
 * @version 1.0
 */
public interface IBridgeAppGame {
    /**
     * Method update tree view result
     * @param gameResult game result
     */
    void updateTreeResult(IEnumGame.GameResult gameResult);
}
