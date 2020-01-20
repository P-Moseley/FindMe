package com.company.search;

import java.io.File;
import java.util.Optional;
import java.util.Set;


public interface SearchInterface {
    Set<File> search(String input);


    SearchInterface FIND_BY_FILE_EXTENSION = new SearchAll();
    FindThis FIND_BY_FILE_NAME = new FindThis();

    
    
    
    
  
    String FIND_BY_FILE_EXTENSION_NAME = "FIND_BY_FILE_EXTENSION";

    
    
    
  
    String FIND_BY_FILE_NAME_NAME = "FIND_BY_FILE_NAME";

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
