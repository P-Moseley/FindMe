package com.company;

public class defaultDrive implements Drive {
	
	
    defaultDrive undefinedStartState;
    @Override
    /**
     * @return When getDrive is called the string 'defaultStart' will be returned. This String value will always be 'C://'			
     */
    public String getDrive() {
    String defaultStart = "C:\\";
//Old code needed for testing.
        // System.out.println("From defaultDrive.getDrive =="+ defaultStart);
        return defaultStart;
    }
}

