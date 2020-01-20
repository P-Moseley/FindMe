package com.company;

 
 
import javax.swing.

 

 
class PFrameSingleton extends PFrame {
    
    
     

    private static PFrame FRAME = null;
    
     


    private PFrameSingleton() {
        PFrame FRAME = new PFrame();
    }
    
     

     
    public static PFrame getInstance() {
        
        If the object FRAME is null then It makes a new instance of PFrame called FRAME
         
         
        if (FRAME == null) {
          FRAME = new PFrame();
        }
        
         
         
        //Returns FRAME.
        return FRAME;
    }
}

 
 
    public class Main {
   
         
         
        public static final PFrame FRAME = PFrameSingleton.getInstance();


    
        public static final void main(String[] args) {
           
            
            
            FRAME.setSize(500, 500);
            FRAME.setTitle("FindMe");
            FRAME.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            FRAME.setVisible(true);
        }

    }



