package com.company.search;

import com.company.Drive;
import com.company.defaultDrive;
import com.company.definedDrive;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.Collection;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
//Concrete class for the SearchAllState, impelments SearchState interface.




public class FindThis implements SearchInterface {



   static Drive definedDrive = new definedDrive();
   static Drive defaultDrive = new defaultDrive();
    static Drive currentDrive = defaultDrive;




    public static void getStartingPoint() {

        currentDrive = definedDrive;
        System.out.println("From FindThis_currentDrive.getDrive =="+ currentDrive.getDrive());
//        if(currentDrive.getDrive() == "This PC"){
//            this.currentDrive.getDrive() = "C:\\";

        }



    //makes a public set called search that requires input string. This String will come from the text box on the jframe

    @Override
    public Set<File> search(String input) {



        //Java.io.File needs a pathname to start.

        File raw = new File(currentDrive.getDrive().toString());
        System.out.println("From FindThisState raw==" + raw.toString());
        try {
            //Collection of found files that includes all the files that FileUtils has found/
            Collection<File> files = FileUtils.listFiles(raw, null, true);
            //Returns a stream of files after a filter has been applied.
            return files.stream().filter(new Predicate<File>() {
                @Override
                //Checks if any of the collected files contain the input String to lowercase.
                public boolean test(File f) {
                    return f.getName().toLowerCase().contains(input.toLowerCase());
                }
            }).collect(Collectors.toSet());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
