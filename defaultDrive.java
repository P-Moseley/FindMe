package com.company;

public class defaultDrive implements Drive {
	
	
    defaultDrive undefinedStartState;
    @Override
    
    public String getDrive() {
    String defaultStart = "C:\\";

        return defaultStart;
    }
}

