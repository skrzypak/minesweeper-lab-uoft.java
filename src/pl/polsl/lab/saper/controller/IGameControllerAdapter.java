package pl.polsl.lab.saper.controller;
import pl.polsl.lab.saper.model.Index;

/**
 * Interface that used to communicate from GameView to GameController
 *
 * @author Konrad Skrzypczyk
 * @version 1.0
 */
public interface IGameControllerAdapter {

    /**
     * Method do action when user click primary mouse button on field
     *
     * @param inx field index object
     */
    void onMouseButtonPrimaryFieldClick(Index inx);

    /**
     * Method do action when user click secondary mouse button on field
     *
     * @param inx field index object
     */
    void onMouseButtonSecondaryFieldClick(Index inx);

}
