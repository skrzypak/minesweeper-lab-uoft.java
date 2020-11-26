package pl.polsl.lab.saper.view;

/**
 * Interface that render error to screen
 *
 * @author Konrad Skrzypczyk
 * @version 1.0
 */
public interface IErrorView {

    /**
     * The constant ANSI_RED, define red color
     */
    String ANSI_RED = "\u001B[31m";
    /**
     * The constant ANSI_RESET, define neutral color
     */
    String ANSI_RESET = "\u001B[0m";

    /**
     * Render message error
     * @param msg message in String format
     */
    default void err(String msg) {
        System.out.println(ANSI_RED + msg + ANSI_RESET);
    }

}
