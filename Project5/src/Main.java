
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    public static void main(String[] args) {

        char[][] aud1 = null;
        char[][] aud2 = null;
        char[][] aud3 = null;

        try {
            aud1 = readAud(new File("A1.txt"));
        } catch (IOException ex) {
            System.out.println("A1.txt not found");
        }

        try {
            aud2 = readAud(new File("A2.txt"));
        } catch (IOException ex) {
            System.out.println("A2.txt not found");
        }

        try {
            aud3 = readAud(new File("A3.txt"));
        } catch (IOException ex) {
            System.out.println("A3.txt not found");
        }

        // TODO code application logic here
        Hashtable table = new Hashtable();

        try {
            File usersF = new File("userdb.dat");
            Scanner users = new Scanner(usersF);
            while (users.hasNextLine()) {
                User user = new User(users.next(), users.next());
                table.put(user.getName(), user);
            }
            users.close();

        } catch (IOException e) {
            // complain to user
            System.out.println("File not found" + e);
        }

        System.out.println(((User) table.get("user1")).getPassword());

        Scanner input = new Scanner(System.in);

        boolean login = true;
        while (login) {
            System.out.println("Enter username:");
            String username = input.next();
            if (table.containsKey(username)) {

                int tries = 3;

                while (tries > 0) {
                    System.out.println("Enter password:");
                    String password = input.next();

                    if (password.equals(((User) table.get(username)).getPassword())) {
                        if (username.equals("admin")) {

                        } else {
                            boolean mainmenu = true;
                            while (mainmenu) {
                                System.out.println("1. Reserve Seats\n" + "2. View Orders\n" + "3. Update Order\n" + "4. Display Receipt\n" + "5. Log Out");
                                int in = input.nextInt();
                                switch (in) {
                                    case 1:
                                        boolean aud = true;
                                        while (aud) {
                                            System.out.println("Which auditorium\n" + "1. Auditorium 1\n" + "2. Auditorium 2\n" + "3. Auditorium 3");
                                            int num = input.nextInt();
                                            if (num == 1 || num == 2 || num == 3) {
                                                if (num == 1) {
                                                    Order newOrder = new Order();
                                                    reserve(input, aud1,newOrder);
                                                    ((User) table.get(username)).orders.add(newOrder);
                                                }
                                                if (num == 2) {
                                                    Order newOrder = new Order();
                                                    reserve(input, aud2,newOrder);
                                                    ((User) table.get(username)).orders.add(newOrder);
                                                }
                                                if (num == 3) {
                                                    Order newOrder = new Order();
                                                    reserve(input, aud3,newOrder);
                                                    ((User) table.get(username)).orders.add(newOrder);
                                                }

                                                break;
                                            } else {
                                                System.out.println("Invalid input");
                                            }
                                        }
                                        break;
                                    case 2:
                                        //view()
                                        break;
                                    case 3:
                                        //update()
                                        break;
                                    case 4:
                                        //receipt()
                                        break;
                                    case 5:
                                        mainmenu = false;
                                        break;
                                    default:
                                        System.out.println("Invalid input");
                                        break;
                                }

                            }
                        }
                        break;
                    }
                    tries++;
                }
            }
        }
    }

    public static void MainMenu() {

    }

    public static void reserve(Scanner input, char[][] aud, Order order) {
        display(aud);
        boolean looping = true;
        while(looping){
            System.out.println("How many adult tickets");
            if(input.hasNextInt()){
                order.adults = input.nextInt();
                break;
            }
            else{
                System.out.println("Invalid input");
            }
        }
        
        looping = true;
        while(looping){
            System.out.println("How many senior tickets");
            if(input.hasNextInt()){
                order.senior = input.nextInt();
                break;
            }
            else{
                System.out.println("Invalid input");
            }
        }
        
        looping = true;
        while(looping){
            System.out.println("How many child tickets");
            if(input.hasNextInt()){
                order.child = input.nextInt();
                break;
            }
            else{
                System.out.println("Invalid input");
            }
        }
        
        order.sum = order.child + order.senior + order.adults;
        order.rows = new int[order.sum];
        order.seats = new int[order.sum];
        order.types = new char[order.sum];
        
        
        for(int i = 0; i < order.adults;i++){
            System.out.println("Enter row number");
            while(looping){
                if(input.hasNextInt()){
                    order.rows[i] = input.nextInt() - 1;
                    break;
                }
                else{
                    System.out.println("Invalid input");
                }
            }
            System.out.println("Enter seat number");
            while(looping){
                if(input.hasNextInt()){
                    order.seats[i] = input.nextInt() - 1;
                    break;
                }
                else{
                    System.out.println("Invalid input");
                }
            }
            order.types[i] = 'A';
        }
        for(int i = 0; i < order.senior;i++){
            System.out.println("Enter row number");
            while(looping){
                if(input.hasNextInt()){
                    order.rows[i + order.adults] = input.nextInt() - 1;
                    break;
                }
                else{
                    System.out.println("Invalid input");
                }
            }
            System.out.println("Enter seat number");
            while(looping){
                if(input.hasNextInt()){
                    order.seats[i + order.senior] = input.nextInt() - 1;
                    break;
                }
                else{
                    System.out.println("Invalid input");
                }
            }
            order.types[i + order.senior] = 'S';
        }
        
        for(int i = 0; i < order.child;i++){
            System.out.println("Enter row number");
            while(looping){
                if(input.hasNextInt()){
                    order.rows[i + order.adults + order.senior] = input.nextInt() - 1;
                    break;
                }
                else{
                    System.out.println("Invalid input");
                }
            }
            System.out.println("Enter seat number");
            while(looping){
                if(input.hasNextInt()){
                    order.seats[i + order.senior + order.senior] = input.nextInt() - 1;
                    break;
                }
                else{
                    System.out.println("Invalid input");
                }
            }
            order.types[i + order.senior + order.senior] = 'C';
        }
        boolean g2r = true; // good to reserve
        for(int i = 0; i < order.sum;i++){
            if(!available(aud, order.rows[i], order.seats[i],1)){
                g2r = false;
                //best avalible
                int middleR = aud.length / 2;
                int middleC = aud[0].length / 2;
                bestAvalible(aud,order.sum);
                
                
            }
        }
        if(g2r){
            //reserves seats
            for(int i = 0; i < order.sum;i++){
                aud[order.rows[i]][order.seats[i]] = '.';
            }
            System.out.println("Seats reserved");
        }
        

    }

    public static char[][] readAud(File fileraw) throws IOException {
        Scanner tmp = new Scanner(fileraw);
        String line = "";
        //Scanner tmp = new Scanner(null);
        int j = 0;
        while (tmp.hasNextLine()) {
            line = tmp.nextLine();
            j++;
        }
        char[][] aud = new char[j][line.length()];
        Scanner file = new Scanner(fileraw);
        j = 0;
        while (file.hasNextLine()) {
            line = file.nextLine();
            for (int i = 0; i < line.length(); i++) {
                aud[j][i] = line.charAt(i);
            }
            j++;
        }
        return aud;
    }

    //Prints formated auditorium
    public static void display(char[][] aud) {
        //Prints colum headers as 1 digit
        System.out.print(" ");
        for (int i = 1; i <= aud[0].length; i++) {
            System.out.print(i % 10);
        }
        //Loops through 2d array and prints seats
        System.out.print("\n");
        for (int i = 0; i < aud.length; i++) {
            System.out.print(i + 1);
            for (int j = 0; j < aud[0].length; j++) {
                System.out.print("" + aud[i][j]);
            }
            System.out.print("\n");
        }

    }
    
        //Check is the requested seats are avalible
    public static boolean available(char[][] aud, int row, int seat, int quantity) {
        //Checks if best seat didn't find a seat
        if (seat == 999) {
            return false;
        }
        //Checks if requested seats would go out of bounds
        if (seat + quantity > aud[0].length) {
            return false;
        }
        //Checks if seats are avalible
        for (int i = 0; i < quantity; i++) {
            if (aud[row][seat + i] == '.') {
                return false;
            }
        }
        return true;
    }
    
    public static double distance(int row, int seat, int mr, int ms){
        //uses standard distance formula to find distance fron point (usaully middle seat)
        double dis = Math.sqrt(Math.pow(Math.abs((double)row - (double)mr),2) + Math.pow(Math.abs((double)seat - (double)ms),2));
        if(row == mr){
            //Tie breaker
            dis -= .1;
        }
        return dis;
    }
    
    public static boolean bestAvalible(char[][] aud,int quan){
        
        return true;
    }
}
