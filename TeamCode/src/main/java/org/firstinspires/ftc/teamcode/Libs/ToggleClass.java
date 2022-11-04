package org.firstinspires.ftc.teamcode.Libs;

public class ToggleClass {
    //Global variables to be used throughout the class
    private boolean firstRun = true;
    private boolean buttonPressed = false;
    private boolean flag;

    public boolean buttonReleaseToggle(boolean buttonPress, boolean initialFlagState) {
        //Checks for the first run of the method to initialize the variables
        if(firstRun) {
            flag = initialFlagState;
            firstRun = false;
        }

        //Checks to see if the button is pressed without being pressed before on that push
        if(buttonPress && !buttonPressed) {
            //Button has been pressed
            buttonPressed = true;
        //Waits for button to be released
        } else if(!buttonPress && buttonPressed) {
            //Flips the state of the flag depending on the current value
            flag = !flag;

            //Sets the state to released
            buttonPressed = false;
        }

        return flag;
    }
}