
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Eddie
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Hashtable table = new Hashtable();
        
        try{
            File usersF = new File("userdb.dat");
            Scanner users = new Scanner(usersF);
            while(users.hasNextLine()){
                User user = new User(users.next(),users.next());
                table.put(user.getName(),user);
            }
            users.close();
            
        } catch (IOException e) {
             // complain to user
             System.out.println("File not found" + e);
        }
        
        System.out.println(table.get("user1").getPassword());
    }
    
}
