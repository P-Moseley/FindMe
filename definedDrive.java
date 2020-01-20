package com.company;

public class definedDrive implements Drive {
    definedDrive definedStartState;
   public static String startingPoint;
        @Override
        
    public String getDrive() {
    	
        startingPoint = PFrame.jumpingOffPoint.toString();
        

        return startingPoint;
 }
}
