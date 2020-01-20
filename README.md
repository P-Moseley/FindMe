# FindMe
FindMe is a small Java application I made for a University project. 
The idea is to streamline the process of transferring personal files from a Windows PC to a Linux PC via USB stick.


Can also find files that contain or match a user given name. 
User can define the search starting point. By default it is C:\\

Built with Java 13 SDK.
Compiled with Java 8


PREREQUISITES
To run FindMe you will require Java installed on your machine. 
To install Java follow this link: https://java.com/en/


HOW TO USE FindMe
When opened the GUI contains the following buttons: 

-Find-- 
What is this?- the Find button is used to search for file names that match or contain a supplied term.


Once text is entered, the Find button is enabled. 
Click Find to start the search.
A progress bar displays at the bottom of the GUI.
The search will return all files that contain or match the given text.
When the search is complete a popup dialog displays the total number of matched files found. 
The locations of found files are shown in the output list on the GUI and the Copy to USB button is enabled.
Note: The tickboxes within the GUI do not apply to Find.
Note:Find is disabled by default. Enter the search term required in the text box to the left of the Find button. 


-Search-- 
What is this?- the Search button is used to find all files of a specific type - or to find all personal files on the machine.

A set of tickboxes allow you to select files of a specified type. Tick the options required for the file type to search for.
Click Search to start the search.
A progress bar displays at the bottom of the GUI.
The search will return all files of the type(s) selected.
When the search is complete a popup dialog displays the total number of files found. 
The locations of found files are shown in the output list on the GUI and the Copy to USB button is enabled. 

-Copy To USB-- 
What is this?- The Copy to USB enables you to select where you want to copy the files to. This button is only available after a search is complete, as it requires a list with file locations upon which to act.

Following your search, click Copy to USB.
A JFileChooser dialog is opened allowing you to select the copy location. It is recommended that you create a new folder for your copied files.
Once copy location is selected click Open.
A progress bar displays percentage complete for the copying action.
All files in the output list are copied to the copy location and placed in folders according to file type. 

-Define Search Start-- 
What is this?- this button allows to define the starting point for your search, for example a specific directory or attached storage location.

Click Define Start.
A JFileChooser dialog is opened allowing you to browse and select the search start point. With a starting point defined the Find or Search buttons will use this location as the basis for their search. 
If no start point is defined or JFileChooser is cancelled, then the default 'C:\\' will be used for the search start. 

Note: Non-Windows machines must define a starting point. 

Thanks for taking the time to read this and I hope you enjoy my program

P.Moseley
