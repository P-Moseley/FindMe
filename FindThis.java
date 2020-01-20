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





public class FindThis implements SearchInterface {



   static Drive definedDrive = new definedDrive();
   static Drive defaultDrive = new defaultDrive();
    static Drive currentDrive = defaultDrive;




    public static void getStartingPoint() {

        currentDrive = definedDrive;
        System.out.println("From FindThis_currentDrive.getDrive =="+ currentDrive.getDrive());



        }



    

    @Override
    public Set<File> search(String input) {



        

        File raw = new File(currentDrive.getDrive().toString());
        System.out.println("From FindThisState raw==" + raw.toString());
        try {
            
            Collection<File> files = FileUtils.listFiles(raw, null, true);
            
            return files.stream().filter(new Predicate<File>() {
                @Override
                
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
