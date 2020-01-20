package com.company;

 
 
import com.company.search.FindThis;
import com.company.search.SearchAll;
import com.company.search.SearchInterface;

 
 
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


 
 
public class PFrame extends JFrame {


 
 
    private class OnButtonPress implements ActionListener {


    
     
        private SearchInterface search;

        //When the searchall button is pressed a new search is made.
        //- This is the constructor - This is only ran once to register the action listener
        
         
         
         
        public OnButtonPress(SearchInterface search){
            this.search = search;
        }

        //- This is the code that will run when the button is clicked
        
         
         
         
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            
             
             
             
             
            new Thread(() -> {
                
                 
                 
                if (this.search == SearchInterface.FIND_BY_FILE_NAME) {
                    //if the Input text box is empty then show error message.
                    
                     
                     
                    if (PFrame.this.inputField.getText().replace(" ", "").length() == 0) {
                        JOptionPane.showMessageDialog(PFrame.this, "Field must be filled in", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
                //While the search is ongoing show the progress bar.
                //- This line doesn't show the process bar, it is shown anyway. This line shows the bar on the progress bar going back and fourth
                
                 
                 
                PFrame.this.bar.setIndeterminate(true);
                
                 
                 
                boolean check = PFrame.this.findFileButton.isEnabled();
                
                 
                 
                findFileButton.setEnabled(false);
                
                 
                 
                searchButton.setEnabled(false);
                
                 
                 
                PFrame.this.copyToUSB.setEnabled(false);


               
                
                
                DefaultListModel<File> model = new DefaultListModel<>();
                //
                
                 
                 
                String input = PFrame.this.inputField.getText();
                
                
                 
                 
                Set<File> files = this.search.search(input);
                //
                
                 
                 
                files.stream().filter(f -> !f.getAbsolutePath().contains("$Recycle.Bin")).forEach(f -> {
                    
                    
                     
                     
                    System.out.println(f.getAbsolutePath());
                    
                    
                     
                     
                    model.addElement(f);
                });
                //
                
                 
                 
                PFrame.this.outputList.setModel(model);
                
                
                 

                 
                PFrame.this.bar.setIndeterminate(false);
                
                
                
                 
                 
                findFileButton.setEnabled(check);
                
                
                 

                 
                searchButton.setEnabled(!check);
                
                 

                 
                if (!files.isEmpty()) {
                    PFrame.this.copyToUSB.setEnabled(true);
                }
                
                 

                 
                JOptionPane.showMessageDialog(PFrame.this, "Found " + files.size() + " files");
            
             
             
            }).start();
        }
    }

//Declares all the elements used by the UI. Also gives them default text.

 
 
 
 
 
 
 
 
 
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

 
 
 
    public PFrame(){
        init();
    }
    
     
     
    public static String jumpingOffPoint = null;

 
 
    private void init(){
        //Wait for the copyToUsb button to be clicked.
        
         

         
        this.copyToUSB.addActionListener(e -> {
            
            populate the list with files that are selected within the outputList.
             
             
            List<File> files = PFrame.this.outputList.getSelectedValuesList();
            
             

             
            if (files.isEmpty()){
                
                Populate the list with all the entries on the outputList.
                 
                 
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
                        
                        
                        folder2.mkdirs();
                        
                        
                        int attempt = 0;
                        while (true) {
                            
                            String fileName = file.getName();
                            
                            if (attempt != 0) {
                                
                                String[] splitArray = fileName.split(Pattern.quote("."));
                               
                                
                                String extension = splitArray[splitArray.length - 1];
                                
                                fileName = fileName.substring(0, fileName.length() - extension.length() - 1) + "(" + attempt + ")." + extension;
                            }
                            
                            
                            File newFile = new File(folder2, fileName);
                            
                            try {
                                
                                Files.copy(file.toPath(), newFile.toPath());
                                
                                
                                break;
                                
                            } catch (NoSuchFileException ex) {
                                
                                
                                break;
                                
                            } catch (FileAlreadyExistsException ex) {
                                
                                attempt++;
                                
                                continue;
                                
                            } catch (AccessDeniedException ex) {
                                
                                

                                break;
                                
                            } catch (FileNotFoundException ex) {
                            
                                ex.printStackTrace();
                                
                                
                                break;
                                
                            } catch (IOException ex) {
                                
                                ex.printStackTrace();
                                
                                

                                break;
                            }
                        }
                    }
                    
                    PFrame.this.bar.setValue(0);
                    
                    PFrame.this.bar.setStringPainted(false);

                }
                
            }).start();
        });

        this.inputField.addKeyListener(new KeyListener() {


            @Override
            public void keyTyped(KeyEvent keyEvent) {

            }

            @Override
            public void keyPressed(KeyEvent keyEvent) {

                if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER){
                    findFileButton.doClick();
                }
            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {
                
                
                String input = PFrame.this.inputField.getText().replace(" ", "");
                
                boolean check = true;
                
                if (input.length() == 0){
                    check = false;
                }
                
                
                findFileButton.setEnabled(check);
                
                
                searchButton.setEnabled(!check);
            }
        });
this.defineStart.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent actionEvent) {

        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle("Define starting point");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);


        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            jumpingOffPoint = chooser.getCurrentDirectory().toString();

            FindThis.getStartingPoint();
            SearchAll.getStartingPoint();


        } else {
            JOptionPane.showMessageDialog(null,"No directory specified will use default: C:\\");
            System.out.println("No Selection ");
            jumpingOffPoint = "C:\\";


    }
}});
        
        
        this.findFileButton.addActionListener(new PFrame.OnButtonPress(SearchInterface.getSearch(SearchInterface.FIND_BY_FILE_NAME_NAME).get()));
        
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
