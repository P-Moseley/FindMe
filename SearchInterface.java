package com.company.search;

import java.io.File;
import java.util.Optional;
import java.util.Set;

//In PFame there is a nested class that requires a search algorithm. Only way to access this is by using a common class, in this case interface.
public interface SearchInterface {
    Set<File> search(String input);

//These are the search states in a final global variable.


    SearchInterface FIND_BY_FILE_EXTENSION = new SearchAll();
    FindThis FIND_BY_FILE_NAME = new FindThis();

    /**
     *
     * The string for the @Link[Search.getSearch(String alg)] function for @Link[Search.FIND_BY_FILE_EXTENSION]
     */
    //Sets the string FIND_BY_FILE_EXTENSION_NAME to FIND_BY_FILE_EXTENSION
    String FIND_BY_FILE_EXTENSION_NAME = "FIND_BY_FILE_EXTENSION";

    /**
     * The string for the @Link[Search.getSearch(String alg)] function for @Link[Search.FIND_BY_FILE_NAME]
     */
    //Sets the string FIND_BY_FILE_NAME_NAME to FIND_BY_FILE_NAME
    String FIND_BY_FILE_NAME_NAME = "FIND_BY_FILE_NAME";

    /**
     * Searching for what the class demands, the user input is specified for a more accurate result
     * @param input The user input
     * @return A collection of files that met the search requirements
     */
    //The set called search requires a string called input. This must be used in all searchStates.

    /**
     * This is a factory function for getting the search algorithm.
     * @param alg The algorithm to get
     * @return The algorithm in a instance format
     */
   //This is the factory pattern.
    static Optional<SearchInterface> getSearch(String alg){

        if (alg == null){
            return Optional.empty();
        }
        switch (alg.toUpperCase()){
            case FIND_BY_FILE_EXTENSION_NAME: return Optional.of(FIND_BY_FILE_EXTENSION);
            case FIND_BY_FILE_NAME_NAME: return Optional.of(FIND_BY_FILE_NAME);
            default: return Optional.empty();
        }
    }

}
