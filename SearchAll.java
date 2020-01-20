package com.company.search;

import com.company.Drive;
import com.company.Main;
import com.company.defaultDrive;
import com.company.definedDrive;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class SearchAll implements SearchInterface {
    static Drive definedDrive = new definedDrive();
    static Drive defaultDrive = new defaultDrive();
    static Drive currentDrive = defaultDrive;




    public static void getStartingPoint() {

        currentDrive = definedDrive;

        System.out.println("From SearchAllState.getDrive =="+ currentDrive.getDrive());

    }
    

    @Override
    public Set<File> search(String input) {
        


       File dir = new File(currentDrive.getDrive().toString());

       
         Set<String> extensions = new HashSet<>();
        
        if (Main.FRAME.getDocToggle().isSelected()){
            
            extensions.add("doc");
            extensions.add("docx");
            extensions.add("odt");
            
            extensions.add("ppsx");
            extensions.add("pps");
            extensions.add("ppt");
            extensions.add("pptx");
            

            extensions.add("xlsx");
            extensions.add("xlr");
            extensions.add("xlsm");
            extensions.add("xls");
            extensions.add("xltx");
            extensions.add("xltm");
            extensions.add("accdb");
            

          /*  extensions.add("db");
            extensions.add("dbf");
            extensions.add("mdb");
            extensions.add("pbd");
            extensions.add("sql");*/
          
        }
        if (Main.FRAME.getMusicToggle().isSelected()){
            
            extensions.add("mp3");
            extensions.add("wav");
            extensions.add("aac");
            extensions.add("wma");
            extensions.add("flac");
            extensions.add("alac");
            
        }
        if (Main.FRAME.getPicToggle().isSelected()){
            
            extensions.add("jpeg");
            extensions.add("jpg");
            extensions.add("gif");
            extensions.add("png");
            extensions.add("tiff");
            extensions.add("psd");
            extensions.add("ai");
            extensions.add("raw");
            extensions.add("indd");
            
        }
        if (Main.FRAME.getVideoToggle().isSelected()){
            
            extensions.add("mp4");
            extensions.add("avi");
            extensions.add("mkv");
            extensions.add("m4v");
            extensions.add("mpg");
            extensions.add("wmv");
            extensions.add("3gp");
            
        }
        if (Main.FRAME.getTextToggle().isSelected()){
            
            extensions.add("txt");
            
        }
        
        return new HashSet<>(FileUtils.listFiles(dir, extensions.toArray(new String[extensions.size()]), true));
        
    }
}
