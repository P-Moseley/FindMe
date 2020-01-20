package com.company;
/**
 * Drive state defined drive.
 */
public class definedDrive implements Drive {
    definedDrive definedStartState;
   public static String startingPoint;
        @Override
        /**
         * @return When getDrive is called the string startingPoint is returned.
         */
    public String getDrive() {
    	/**
         * @startingPoint comes from jumpingOffPoint in Pframe. jumpingOffPoint comes from a new instance of JFileChoser
         */
        startingPoint = PFrame.jumpingOffPoint.toString();
        //Old code used for testing. 
        // System.out.println(" From Defined Start: startingPoint=="+ startingPoint);

        return startingPoint;
 }
}
