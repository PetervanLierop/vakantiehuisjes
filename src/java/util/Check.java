/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

/**
 *
 * @author Peter
 */
public class Check {
        public static boolean isInteger( String input )  
    {  
       try  
       {  
          Integer.parseInt( input );  
          return true;  
       }  
       catch(Exception e)  
       {  
          return false;  
       }  
    }  

    
}
