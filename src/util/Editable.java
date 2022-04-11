/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

/**
 *
 * @author LEGION
 */
public interface Editable extends FillDetails {

    /**
     * Edit mode when state is true, enable text field editing
     * @param state
     */
    public void enableDisablePanel(boolean state);

    /**
     * Edit mode when state is false, disable all button clicking outside panel
     * @param state
     */
    public void enableDisableButton(boolean state);

    /**
     * Function to write data
     */
    public void write();
}
