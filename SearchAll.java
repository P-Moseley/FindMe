package com.company.search;

import com.company.Drive;
import com.company.Main;
import com.company.defaultDrive;
import com.company.definedDrive;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.HashSet;
import java.util.Set;
//Concrete class for the SearchAllState, impelments SearchState interface.
public class SearchAll implements SearchInterface {
    static Drive definedDrive = new definedDrive();
    static Drive defaultDrive = new defaultDrive();
    static Drive currentDrive = defaultDrive;




    public static void getStartingPoint() {

        currentDrive = definedDrive;
//System.out.println("From FindThis_currentDrive.getDrive =="+ currentDrive.getDrive().toString());
        System.out.println("From SearchAllState.getDrive =="+ currentDrive.getDrive());

    }
    //makes a public set called search that requires input string. This String will come from the text box on the jframe

    @Override
    public Set<File> search(String input) {
        //

//        String mySt = currentDrive.getDrive();
       File dir = new File(currentDrive.getDrive().toString());
//       System.out.println(getStartingPoint);
       //Java.io.File needs a pathname to start.
         Set<String> extensions = new HashSet<>();
        //Initiates a new string HashSet called extensions
        if (Main.FRAME.getDocToggle().isSelected()){
            //Checks if the Documents tickbox is checked
            extensions.add("doc");
            extensions.add("docx");
            extensions.add("odt");
            //Adds common Microsoft Word file extensions to the HashSet
            extensions.add("ppsx");
            extensions.add("pps");
            extensions.add("ppt");
            extensions.add("pptx");
            //Adds common Microsoft PowerPoint file extensions to the HashSet

            extensions.add("xlsx");
            extensions.add("xlr");
            extensions.add("xlsm");
            extensions.add("xls");
            extensions.add("xltx");
            extensions.add("xltm");
            extensions.add("accdb");
            //Adds common Microsoft Excel file extensions to the HashSet

          /*  extensions.add("db");
            extensions.add("dbf");
            extensions.add("mdb");
            extensions.add("pbd");
            extensions.add("sql");*/
          //Old code to add db and sql files. Windows 10 uses these type of files internally, causing errors when attempting to copy hence the code is removed.
        }
        if (Main.FRAME.getMusicToggle().isSelected()){
            //Checks if the Music tickbox is checked.
            extensions.add("mp3");
            extensions.add("wav");
            extensions.add("aac");
            extensions.add("wma");
            extensions.add("flac");
            extensions.add("alac");
            //Adds common music file extensions to the HashSet
        }
        if (Main.FRAME.getPicToggle().isSelected()){
            //Checks if the pictures tickbox is checked.
            extensions.add("jpeg");
            extensions.add("jpg");
            extensions.add("gif");
            extensions.add("png");
            extensions.add("tiff");
            extensions.add("psd");
            extensions.add("ai");
            extensions.add("raw");
            extensions.add("indd");
            //Adds common image file types to the HashSet. This includes file extension associated with some Adobe products.
        }
        if (Main.FRAME.getVideoToggle().isSelected()){
            //Checks if the video tickbox is checked
            extensions.add("mp4");
            extensions.add("avi");
            extensions.add("mkv");
            extensions.add("m4v");
            extensions.add("mpg");
            extensions.add("wmv");
            extensions.add("3gp");
            //Adds common video file extension's to the HashSet
        }
        if (Main.FRAME.getTextToggle().isSelected()){
            //Checks if the text tickbox is checked.
            extensions.add("txt");
            //Adds .txt to the HashSet.
        }
        /*This line first uses the apache commons FileUtils to get a collection of Files
        * that is located in the directory labelled as "dir". All files will have a
        * file extension from the "extensions" Hashset .
        *
        * As the apache commons FileUtil returns a Collection of Files, this is not what
        * the method needs to return, therefore we convert it to a HashSet by providing
        * the Collection as a parameter within the constructor of HashSet.
        */
        return new HashSet<>(FileUtils.listFiles(dir, extensions.toArray(new String[extensions.size()]), true));
        //Returns a new HashSet populated with the a list of files
    }
}
