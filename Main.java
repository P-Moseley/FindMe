package com.company;
/**
 * Import the swing library
 */
import javax.swing.*;
/**
 * PFrameSingleton class. This class ensures that there is only one instance of PFrame running at any given time. 

 */
class PFrameSingleton extends PFrame {
    
    /**
     *   PFrame FRAME  needs to be set to null. The determination of object instance relies on this null value
*/
    private static PFrame FRAME = null;
    /**
     *  private constructor for making the PFrame. PFrame inherits from jframe.

*/
    private PFrameSingleton() {
        PFrame FRAME = new PFrame();
    }
    /**
     * @getInstance is public method for checking for an instance of PFrame.

     */
    public static PFrame getInstance() {
        /**
        If the object FRAME is null then It makes a new instance of PFrame called FRAME
         * 
         */
        if (FRAME == null) {
          FRAME = new PFrame();
        }
        /**
         * @return FRAME is needed to start the Jframe called PFrame
         */
        //Returns FRAME.
        return FRAME;
    }
}
/**
 * Main class
 */
    public class Main {
    //gets the allready made instance of FRAME from the PFrameSingleton class.
//calls the singleton methord that creates a new instance PFrame. 
        /**
         * @PFrameSingleton.getInstance calls the singleton pattern that can be seen above. 
         */
        public static final PFrame FRAME = PFrameSingleton.getInstance();


    
        public static final void main(String[] args) {
            //This sets the frames width and height
           /**
            * @FRAME -- the size and title of FRAME is set.
            */
            FRAME.setSize(500, 500);
            //Sets the title to "FindMe"
            FRAME.setTitle("FindMe");
            //Makes the frame close when the X in the top right is clicked.
            FRAME.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            //Makes the frame visible.
            FRAME.setVisible(true);
        }

    }



