package com.company;
/**
 * @import the required classes.
 */
import com.company.search.FindThis;
import com.company.search.SearchAll;
import com.company.search.SearchInterface;
/**
 * @import the required libraries
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

/** 
 * @PFrame class inherits from JFrame. 
 */
public class PFrame extends JFrame {

/**
 * @OnButtonPress implements ActionListener. This is needed to identify button clicks
 */
    private class OnButtonPress implements ActionListener {

/**
    * makes a private SearchState called search
     */
        private SearchInterface search;

        //When the searchall button is pressed a new search is made.
        //- This is the constructor - This is only ran once to register the action listener
        /**
         * [OnButtonPress When the searchall button is pressed a new search is made.]
         * @param  search [takes the search SearchInterface]
         */
        public OnButtonPress(SearchInterface search){
            this.search = search;
        }

        //- This is the code that will run when the button is clicked
        /**
         * [actionPerformed When the Find button is clicked]
         * @param actionEvent [When the Find button is clicked]
         */
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            /**
             * [Thread      The actual searching is done on a new Thread, this is needed to improve performance.]
             * @param  ( [The actual searching is done on a new Thread, this is needed to improve performance]
             * @return   [If no text is in the input filed then an error message is shown and return to runtime]
             */
            new Thread(() -> {
                /**
                 * @this.search == SearchInterface.FIND_BY_FILE_NAME then continue
                 */
                if (this.search == SearchInterface.FIND_BY_FILE_NAME) {
                    //if the Input text box is empty then show error message.
                    /**
                     * @inputField replace spaces with no space, then if the lengh of the text in the imput filed = 0 then show error message and don't preform the search.
                     */
                    if (PFrame.this.inputField.getText().replace(" ", "").length() == 0) {
                        JOptionPane.showMessageDialog(PFrame.this, "Field must be filled in", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
                //While the search is ongoing show the progress bar.
                //- This line doesn't show the process bar, it is shown anyway. This line shows the bar on the progress bar going back and fourth
                /**
                 * @PFrame.this.bar.setIndeterminate(true) shows the prosgress bar going back and forth. 
                 */
                PFrame.this.bar.setIndeterminate(true);
                /**
                 * @check returns true or false depending on if the findFileButton is enabled
                 */
                boolean check = PFrame.this.findFileButton.isEnabled();
                /**
                 * Disables the findFileButton
                 */
                findFileButton.setEnabled(false);
                /**
                 * Disables the searchButton
                 */
                searchButton.setEnabled(false);
                /**
                 * Disables the copy to USB button.
                 */
                PFrame.this.copyToUSB.setEnabled(false);


               /**
                * //Declares the new hashset called DefaultListModel
                */
                DefaultListModel<File> model = new DefaultListModel<>();
                //
                /**
                 * Sets the String called input to the value of the input field.
                 */
                String input = PFrame.this.inputField.getText();
                
                /**
                 * Starts the searching with the String gained from the input field.
                 */
                Set<File> files = this.search.search(input);
                //
                /**
                 * Filters the found files then loops over any that are found in the Recycle Bin.
                 */
                files.stream().filter(f -> !f.getAbsolutePath().contains("$Recycle.Bin")).forEach(f -> {
                    
                    /**
                     * Prints out the path of found files.
                     */
                    System.out.println(f.getAbsolutePath());
                    
                    /**
                     * Adds f to the data model. Variable f represents the file, f.getAbsolutePath represents the current files location/path
                     */
                    model.addElement(f);
                });
                //
                /**
                 * Resets the data model to outputList so it matches the list stored in the Model
                 */
                PFrame.this.outputList.setModel(model);
                
                /**
                 *                Stops the bar within the progress bar from being shown

                 */
                PFrame.this.bar.setIndeterminate(false);
                
                
                /**
                 * This enables/disables the 'findFileButton' based on if it was enabled/disabled before
                 */
                findFileButton.setEnabled(check);
                
                /**
                 *                 This enables/disabled the 'searchButton' based on the opposite of it the 'findFileButton' was enabled/disabled

                 */
                searchButton.setEnabled(!check);
                /**
                 *                Providing the files set is not empty then enable the copyToUSB button.

                 */
                if (!files.isEmpty()) {
                    PFrame.this.copyToUSB.setEnabled(true);
                }
                /**
                 * @JOptionPane displays When the search is complete show the amount of files that have been found.

                 */
                JOptionPane.showMessageDialog(PFrame.this, "Found " + files.size() + " files");
            /**
             * Starts the thread
             */
            }).start();
        }
    }

//Declares all the elements used by the UI. Also gives them default text.
/**
 * @outputList - This is the main output for the end user. It will show the results from either of the searches. It uses the collection returned from either state after the search has been completed
 * @inputField - this is text box designated for user input
 * @textToggle @docToggle @picToggle @videoToggle @musicToggle - These are simple JCheckBoxes that define what file types will be found when the Search all button is  clicked. After the Search all button is click multiple if statements are executed that check which extension toggle is clicked
 * @findFileButton - When the findFileButton is clicked it takes the user input text from @inputField then searches the whole C drive for files that match or contain that of the user input text. Once a file is found with the given name its location gets added to the output list and the search continues, it does not stop if the file is found.
 * @searchButton - when the searchButton is clicked it checks wich tick boxes are ticked, these checkboxes represent  file types. Then a search is preformed looking for files that are the correct type
 * @copyToUSB -  - After either search has complete the user will then be given the option to copy all or some of the files to another location, note that this new location is not exclusive to USB drives.  When the location is defined by the user a new folder is made, inside that folder new folders are made with specific file types that the search has found, Documents, Images, Videos ECT
 *  @bar - this is a progress bar, this is needed to stop FindMe looking like it has crashed. 
 *  @defineStart - this button once clicked allows the user to spesifiy a search starting point.
 *    */
    private JList<File> outputList = new JList<>(new DefaultListModel<>());
    private JLabel findFileLabel = new JLabel("Find File");
    private JTextField inputField = new JTextField();
    private JCheckBox textToggle = new JCheckBox("Text");
    private JCheckBox docToggle = new JCheckBox("Document");
    private JCheckBox picToggle = new JCheckBox("Picture");
    private JCheckBox videoToggle = new JCheckBox("Video");
    private JCheckBox musicToggle = new JCheckBox("Music");
    private JButton findFileButton = new JButton("Find");
    private JButton searchButton = new JButton("Search all");
    private JButton copyToUSB = new JButton("Copy To USB");
    private JProgressBar bar = new JProgressBar();
    private JButton defineStart = new JButton("Define start");
/**
 * [PFrame GUI allong with sorting and copying functions]
 * @return [start PFrame.init]
 */
    public PFrame(){
        init();
    }
    /**
     * Declares the public static String jumpingOffPoint and sets its value to null
     */
    public static String jumpingOffPoint = null;
/**
 * [init On initilazation]
 */
    private void init(){
        //Wait for the copyToUsb button to be clicked.
        /**
         *  Registers the click  that occour on'copyToUSB' button

         */
        this.copyToUSB.addActionListener(e -> {
            /**
            populate the list with files that are selected within the outputList.
             * 
             */
            List<File> files = PFrame.this.outputList.getSelectedValuesList();
            /**
             * If no files are selected

             */
            if (files.isEmpty()){
                /**
                Populate the list with all the entries on the outputList.
                 * 
                 */
                files = new ArrayList<>();
                DefaultListModel<File> model = ((DefaultListModel<File>)PFrame.this.outputList.getModel());
                for (int A = 0; A < model.size(); A++){
                    files.add(model.getElementAt(A));
                }
            }
            //Start a new instance of JFileChooser.
            JFileChooser chooser = new JFileChooser();
            //Only make Directory's visable in the JFileChooser.
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            //Intising a verable but not giving it a value as the value will be given at a later time.
            //- Initializing the variable 'folder' for the while loop below
            File folder;

            while(true) {
                //Opens SaveDialog, if there is an expected error the program will be able to determine what the error is from the integer error.
                int error = chooser.showSaveDialog(PFrame.this);
                //If error = 0 then approve the selection.
                if (error == JFileChooser.APPROVE_OPTION){
                    //the varable folder has the same value as chooser.getselected (user selection)
                    //- variable - chooser.getSelected
                    folder = chooser.getSelectedFile();
                    //If the selection is a directory then continue
                    //- If the selected 'File' is not a directory, then try again (continue)
                    if (!folder.isDirectory()){
                        continue;
                    }
                    //If the selection is writable then continue
                    //- If the selected 'File' is not writable, then try again (continue)
                    if (!folder.canWrite()){
                        continue;
                    }
                    break;
                    //Else cancel the JFileChooser and show a error.
                    //- Checks if the error was the cancel button being clicked
                }else if(error == JFileChooser.CANCEL_OPTION){
                   //show the error message.  JOptionPane.showMessageDialog(null, "ERROR: Please select a folder.");
                    return;

                }
            }
            //Set the maximum value of the progress bar to the size of files in the collection. 
            PFrame.this.bar.setMaximum(files.size());
            //converts the value of File to final verable. While it is final it can be used in a nested class.
            //- variable
            final File finalFolder = folder;
            //converts the value of List<File> to final verable. While it is final it can be used in a nested class.
            //- variable
            final List<File> finalFiles = files;
            //Start new thread that is used by the progress bar. Also having multiple threads improves performance.
            new Thread(new Runnable() {
                //Starts the function run in runnable on the thread.
                @Override
                public void run() {
                    //Gives the percentage to the progress bar.
                    PFrame.this.bar.setStringPainted(true);
                    //start a for loop with the size of the finalFiles set
                    for (int A = 0; A < finalFiles.size(); A++) {
                        //Starts the progress bar at 0. This is equal to the integer A.
                        
                        PFrame.this.bar.setValue(A);
                        
                      //- This gets the File at the index of the interation progress
                        File file = finalFiles.get(A);
                        //Initiation of the variable folder2 set its value to the same as finalFolder
                        
                        File folder2 = finalFolder;
                        //Checks if the file ends .docx or .doc or odt or ppsx or pps or ppt or pptx or xlsr or xlr or xlsm or xls or xlth ETC
                        if (file.getName().endsWith(".docx") || file.getName().endsWith(".doc") || file.getName().endsWith(".odt") || file.getName().endsWith(".ppsx") || file.getName().endsWith(".pps") || file.getName().endsWith(".ppt") || file.getName().endsWith(".pptx") || file.getName().endsWith(".xlsx") || file.getName().endsWith(".xlr") || file.getName().endsWith(".xlsm") || file.getName().endsWith(".xls") || file.getName().endsWith(".xltx") || file.getName().endsWith(".xltm") || file.getName().endsWith(".accdb") || file.getName().endsWith(".db") || file.getName().endsWith(".dbf") || file.getName().endsWith(".mdb") || file.getName().endsWith(".pdb") || file.getName().endsWith(".sql")) {
                            //Adds the files to a new folder called Documents.
                            folder2 = new File(finalFolder, "Documents");
                        }
                        //Checks if the file ends .jpeg or .jgp or gif or png or tiff or psd or ai or raw or indd ECT
                        if (file.getName().endsWith(".jpeg") || file.getName().endsWith(".jgp") || file.getName().endsWith(".gif") || file.getName().endsWith(".png") || file.getName().endsWith(".tiff") || file.getName().endsWith(".psd") || file.getName().endsWith(".ai") || file.getName().endsWith(".raw") || file.getName().endsWith(".indd")) {
                            //Adds the files to a new folder called Picture.
                            folder2 = new File(finalFolder, "Picture");
                        }
                        //Checks if the file ends .mp3 or .wav or aac or wma or flac or alac ECT
                        if (file.getName().endsWith(".mp3") || file.getName().endsWith(".wav") || file.getName().endsWith(".aac") || file.getName().endsWith(".wma") || file.getName().endsWith(".flac") || file.getName().endsWith(".alac")) {
                            //Adds the files to a new folder called Music.
                            folder2 = new File(finalFolder, "Music");
                        }
                        //Checks if the file ends .mp4 or .avi or mkv or m4v or mpg or wmv or 3gp ECT
                        if (file.getName().endsWith(".mp4") || file.getName().endsWith(".avi") || file.getName().endsWith(".mkv") || file.getName().endsWith(".m4v") || file.getName().endsWith(".mpg") || file.getName().endsWith(".wmv") || file.getName().endsWith(".3pg")) {
                            //Adds the files to a new folder called Video.
                            folder2 = new File(finalFolder, "Video");
                        }
                        //Checks if the file ends with txt
                        if (file.getName().endsWith(".txt")) {
                            //Adds the files to the folder called text.
                            folder2 = new File(finalFolder, "Text");
                        }
                        //makes the perent directory.
                        //- Parent
                        folder2.mkdirs();
                        //Integer that is set to 0.
                        //- I think your tutor can read ...
                        int attempt = 0;
                        while (true) {
                            //Sets the string filename to the results file.getName
                            String fileName = file.getName();
                            //If integer called attempt is not  0
                            if (attempt != 0) {
                                //splitArray is equal to the fileName with the extension name removed. Note: QUOTE converts the '.' to the computers version of '.' so the splitting works
                                String[] splitArray = fileName.split(Pattern.quote("."));
                               
                                //- This gets the last element from the split, that last element being the file extention.
                                String extension = splitArray[splitArray.length - 1];
                                //Names the new file name with a number if needed. This number is gained from the integer called attempt.
                                fileName = fileName.substring(0, fileName.length() - extension.length() - 1) + "(" + attempt + ")." + extension;
                            }
                            //Creating a new file element at the folder2 posision and with the name of the String fileName
                            //- Position
                            File newFile = new File(folder2, fileName);
                            //attempt the following, may encounter errors.
                            try {
                                //copy the files in the given path to the newly allocated path.
                                Files.copy(file.toPath(), newFile.toPath());
                                //break out of the loop, providing there is no errors 
                                
                                break;
                                //Catch the error NoSuchFileException
                            } catch (NoSuchFileException ex) {
                                //Break out of the loop.
                                
                                break;
                                //Catch the error FileAlreadyExistsException.
                            } catch (FileAlreadyExistsException ex) {
                                //Increment integer attempt by 1. This should change the file name to something different.
                                attempt++;
                                //try the loop again.
                                continue;
                                //Catch AccessDeniedException error.
                            } catch (AccessDeniedException ex) {
                                //Break out of the loop.
                                

                                break;
                                //Catch FileNotFoundException/
                            } catch (FileNotFoundException ex) {
                            //Prints out the error to the console.
                                ex.printStackTrace();
                                //Break out of the loop.
                                
                                break;
                                //Catch IOException
                            } catch (IOException ex) {
                                //Prints out the error to the console.
                                ex.printStackTrace();
                                //Break out of the loop.
                                

                                break;
                            }
                        }
                    }
                    //Sets the progress bar to position 0.
                    PFrame.this.bar.setValue(0);
                    //Turns of the progress bar percentage
                    PFrame.this.bar.setStringPainted(false);

                }
                //Start the thread.
            }).start();
        });
//Waits for key input in the inputField
        this.inputField.addKeyListener(new KeyListener() {

//overrides the keyType keyevent
            @Override
            public void keyTyped(KeyEvent keyEvent) {

            }
//Waits for the enter key to be pressed then starts the function findFileButton.doclick
            @Override
            public void keyPressed(KeyEvent keyEvent) {
//if the enter ey is pressed then treet it as the findFileButton been clicked 
                if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER){
                    findFileButton.doClick();
                }
            }
//Waits for the key to be released.
            @Override
            public void keyReleased(KeyEvent keyEvent) {
                //Replace spaces with no space
                
                String input = PFrame.this.inputField.getText().replace(" ", "");
                //sets the boolean check to true.
                boolean check = true;
                //If there is no text in the input filed then the boolean check is false
                if (input.length() == 0){
                    check = false;
                }
                //Makes the findFileButton enabled
                //- This does not enable the button. It can do, however if the 'input' has a length of 0 then it will disable it
                findFileButton.setEnabled(check);
                //Disables the searchButton.
                //- Same as my comment above
                searchButton.setEnabled(!check);
            }
        });
this.defineStart.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
//       new DefinedStartState();
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle("Define starting point");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);
//FindThisState.getStartingPoint(true);

        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            jumpingOffPoint = chooser.getCurrentDirectory().toString();
//            new definedDrive(defineStart);
            FindThis.getStartingPoint();
            SearchAll.getStartingPoint();
//            System.out.println("PFrame_jumpingOffPoint==" + jumpingOffPoint);

        } else {
            JOptionPane.showMessageDialog(null,"No directory specified will use default: C:\\");
            System.out.println("No Selection ");
            jumpingOffPoint = "C:\\";

//            new UndefinedDrive(defineStart);
    }
}});
        //Sets the SearchState to FIND_BY_FILE_NAME_NAME when the findFileButton is clicked
        //- This registers an actionlistener of OnButtonPress with the search algorithm being the FIND_BY_FILE_NAME
        this.findFileButton.addActionListener(new PFrame.OnButtonPress(SearchInterface.getSearch(SearchInterface.FIND_BY_FILE_NAME_NAME).get()));
        ////Sets the SearchState to FIND_BY_FILE_EXTENSION_NAME when the findFileButton is clicked
        //- Same as my above comment
        this.searchButton.addActionListener(new PFrame.OnButtonPress(SearchInterface.getSearch(SearchInterface.FIND_BY_FILE_EXTENSION_NAME).get()));
//unticks the text checkbox by default.
        //- Why is this needed? by default JCheckBox are not selected
        this.textToggle.setSelected(false);
        //ticks the Document, Images, videos, music tickboxes by default.
        this.docToggle.setSelected(true);
        this.picToggle.setSelected(true);
        this.videoToggle.setSelected(true);
        this.musicToggle.setSelected(true);
        //disables the copyToUSB button by default.
        this.copyToUSB.setEnabled(false);
        //Disables the findFileButton by default.
        findFileButton.setEnabled(false);
//Sets and positions all the parts of the JFrame layout.
        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;
        this.add(this.findFileLabel, c);
        c.gridx = 1;
        c.weightx = 1.0;
        this.add(this.inputField, c);
        c.gridx = 2;
        c.weightx = 0.0;
        this.add(findFileButton, c);
        c.gridx = 3;
        this.add(searchButton, c);
        c.gridx = 4;
        this.add(this.copyToUSB, c);
        c.gridx = 5;
        this.add(this.defineStart,c );
        c.gridx = 6;

        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 1.0;
        c.gridwidth = 5;
        JPanel extensionPanel = new JPanel();
        extensionPanel.setLayout(new FlowLayout());
        extensionPanel.add(this.textToggle);
        extensionPanel.add(this.docToggle);
        extensionPanel.add(this.picToggle);
        extensionPanel.add(this.musicToggle);
        extensionPanel.add(this.videoToggle);
        this.add(extensionPanel, c);

        c.gridy = 2;
        c.weighty = 1.0;
        this.add(new JScrollPane(this.outputList), c);
        c.gridy = 3;
        c.weighty = 0.0;
        this.add(this.bar, c);
    }
//Declares  the interactive parts of the UI with getters.
    public JList<File> getTextArea() {
        return this.outputList;
    }

    public JLabel getFindFileLabel() {
        return findFileLabel;
    }

    public JTextField getInputField() {
        return inputField;
    }

    public JCheckBox getTextToggle() {
        return textToggle;
    }

    public JCheckBox getPicToggle() {
        return picToggle;
    }

    public JCheckBox getVideoToggle() {
        return videoToggle;
    }

    public JCheckBox getMusicToggle() {
        return musicToggle;
    }

    public JButton getCopyToUSB() {
        return copyToUSB;
    }

    public JProgressBar getBar() {
        return bar;
    }

    public JCheckBox getDocToggle(){
        return this.docToggle;
    }
}
