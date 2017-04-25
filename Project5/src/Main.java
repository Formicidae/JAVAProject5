
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Eddie
 */
public class Main {

    static char[][][] auds;
    
    public static void main(String[] args) {

        
        char[][] aud1 = null;
        char[][] aud2 = null;
        char[][] aud3 = null;
        char[][][] auds = {aud1,aud2,aud3};

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

        char[][] testaud = auds[2];
        
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
                                                    newOrder.aud = 1;
                                                    reserve(input, aud1, newOrder);
                                                    ((User) table.get(username)).orders.add(newOrder);
                                                }
                                                if (num == 2) {
                                                    Order newOrder = new Order();
                                                    newOrder.aud = 2;
                                                    reserve(input, aud2, newOrder);
                                                    ((User) table.get(username)).orders.add(newOrder);
                                                }
                                                if (num == 3) {
                                                    Order newOrder = new Order();
                                                    newOrder.aud = 3;
                                                    reserve(input, aud3, newOrder);
                                                    ((User) table.get(username)).orders.add(newOrder);
                                                }

                                                break;
                                            } else {
                                                System.out.println("Invalid input");
                                            }
                                        }
                                        break;
                                    case 2:
                                        viewOrders((User) table.get(username));
                                        break;
                                    case 3:
                                        updateOrder(input,(User) table.get(username));
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
        try{
            writeF(aud1,new File("A1.txt"));
            writeF(aud2,new File("A2.txt"));
            writeF(aud3,new File("A3.txt"));
        }
        catch(Exception ex){
            
        }
        
    }

    public static void MainMenu() {

    }

    public static void reserve(Scanner input, char[][] aud, Order order) {
        display(aud);
        boolean looping = true;
        while (looping) {
            System.out.println("How many adult tickets");
            if (input.hasNextInt()) {
                order.adults = input.nextInt();
                break;
            } else {
                System.out.println("Invalid input");
            }
        }

        looping = true;
        while (looping) {
            System.out.println("How many senior tickets");
            if (input.hasNextInt()) {
                order.senior = input.nextInt();
                break;
            } else {
                System.out.println("Invalid input");
            }
        }

        looping = true;
        while (looping) {
            System.out.println("How many child tickets");
            if (input.hasNextInt()) {
                order.child = input.nextInt();
                break;
            } else {
                System.out.println("Invalid input");
            }
        }

        order.sum = order.child + order.senior + order.adults;
        order.rows = new int[order.sum];
        order.seats = new int[order.sum];
        order.types = new char[order.sum];

        for (int i = 0; i < order.adults; i++) {
            System.out.println("Enter row number");
            while (looping) {
                if (input.hasNextInt()) {
                    order.rows[i] = input.nextInt() - 1;
                    break;
                } else {
                    System.out.println("Invalid input");
                }
            }
            System.out.println("Enter seat number");
            while (looping) {
                if (input.hasNextInt()) {
                    order.seats[i] = input.nextInt() - 1;
                    break;
                } else {
                    System.out.println("Invalid input");
                }
            }
            order.types[i] = 'A';
        }
        for (int i = 0; i < order.senior; i++) {
            System.out.println("Enter row number");
            while (looping) {
                if (input.hasNextInt()) {
                    order.rows[i + order.adults] = input.nextInt() - 1;
                    break;
                } else {
                    System.out.println("Invalid input");
                }
            }
            System.out.println("Enter seat number");
            while (looping) {
                if (input.hasNextInt()) {
                    order.seats[i + order.senior] = input.nextInt() - 1;
                    break;
                } else {
                    System.out.println("Invalid input");
                }
            }
            order.types[i + order.senior] = 'S';
        }

        for (int i = 0; i < order.child; i++) {
            System.out.println("Enter row number");
            while (looping) {
                if (input.hasNextInt()) {
                    order.rows[i + order.adults + order.senior] = input.nextInt() - 1;
                    break;
                } else {
                    System.out.println("Invalid input");
                }
            }
            System.out.println("Enter seat number");
            while (looping) {
                if (input.hasNextInt()) {
                    order.seats[i + order.senior + order.senior] = input.nextInt() - 1;
                    break;
                } else {
                    System.out.println("Invalid input");
                }
            }
            order.types[i + order.senior + order.senior] = 'C';
        }
        boolean g2r = true; // good to reserve
        for (int i = 0; i < order.sum; i++) {
            if (!available(aud, order.rows[i], order.seats[i], 1)) {
                g2r = false;
                //best avalible

                bestAvalible(aud, order.sum, input);
                break;

            }
        }
        if (g2r) {
            //reserves seats
            for (int i = 0; i < order.sum; i++) {
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

        //out of bounds
        if (seat < 0 || row < 0) {
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

    public static double distance(int row, int seat, int mr, int ms) {
        //uses standard distance formula to find distance fron point (usaully middle seat)
        double dis = Math.sqrt(Math.pow(Math.abs((double) row - (double) mr), 2) + Math.pow(Math.abs((double) seat - (double) ms), 2));
        if (row == mr) {
            //Tie breaker
            dis -= .1;
        }
        return dis;
    }

    public static boolean bestAvalible(char[][] aud, int quan, Scanner input) {
        int middleR = aud.length / 2;
        int middleC = aud[0].length / 2;
        int shortestDistance = 2147483647;
        int bestR = -1;
        int bestC = -1;

        for (int i = 0; i < aud.length; i++) {
            for (int j = 0; j < aud[0].length; j++) {
                if (available(aud, i, j, quan)) {
                    if (shortestDistance > distance(i, j + (quan / 2), middleR, middleC)) {
                        bestR = i;
                        bestC = j;
                    }
                }
            }
        }
        System.out.println("Seat Requested could not be reserved");
        if (bestR != -1) {
            System.out.println("Would you like row:" + bestR + " Seat: " + bestC + " instead ? (y/n)");
            char yn = input.next().charAt(0);
            if (yn == 'Y' || yn == 'y') {
                for (int i = 0; i < quan; i++) {
                    aud[bestR][bestC + i] = '.';
                }
                System.out.println("Seats reserved");
            }
        } else {
            System.out.println("No seats can accommodate your party size");
        }
        return true;
    }
    
        public static void writeF(char[][] aud, File file) throws IOException {
        OutputStream f = new FileOutputStream(file);
        for (int i = 0; i < aud.length; i++) {
            for (int j = 0; j < aud[0].length; j++) {
                f.write(aud[i][j]);
            }
            f.write('\r');
            f.write('\n');

        }
    }
        
    public static void viewOrders(User user){
        for(int i = 0;i < user.orders.size();i++){
            System.out.print("Order: " + (i+1));
            System.out.print("\tAuditorium: " + ((Order)user.orders.get(i)).aud);
            System.out.print("\tAdult tickets: " + ((Order)user.orders.get(i)).adults);
            System.out.print("\tSenior tickets: " + ((Order)user.orders.get(i)).senior);
            System.out.print("\tSenior tickets: " + ((Order)user.orders.get(i)).child);
            System.out.print("\tSeats: ");
            for(int j = 0; j < ((Order)user.orders.get(i)).sum;j++){
                System.out.print( "(" + ((Order)user.orders.get(i)).rows[j] + "," + ((Order)user.orders.get(i)).seats[j] + ")");         
            }
            System.out.println();
        }
    }
    
    public static void updateOrder(Scanner input, User user){
        viewOrders(user);
        System.out.println("Choose an order to update: ");
        int order = input.nextInt() - 1;
        boolean menu = true;
        while(menu){
            System.out.println("1. Add tickets to order\n" + "2. Delete tickets from order\n" + "3. Cancel Order");
            int choice = input.nextInt();
            switch(choice){
                case 1:
                    reserve(input, auds[((Order)user.orders.get(order)).aud - 1], ((Order)user.orders.get(order)));
                    menu = false;
                    break;
                case 2:
                    //delete individual seat
                    boolean submenu = true;
                    while(submenu){
                        System.out.println("Which seat would you like to remove?");
                        for(int j = 0; j < ((Order)user.orders.get(order)).sum;j++){
                            System.out.print( (j+1) + ": " + ((Order)user.orders.get(order)).types[j] + "(" + ((Order)user.orders.get(order)).rows[j] + "," + ((Order)user.orders.get(order)).seats[j] + ")");         
                        }
                        System.out.println();
                        int seat = input.nextInt() - 1;
                        if(seat >= ((Order)user.orders.get(order)).sum){
                            System.out.println("Invalid seat");
                        }
                        else{
                            char[][] test = auds[((Order)user.orders.get(order)).aud - 1];
                            auds[((Order)user.orders.get(order)).aud - 1][((Order)user.orders.get(order)).rows[seat]][((Order)user.orders.get(order)).seats[seat]] = '#';
                            
                            if(((Order)user.orders.get(order)).types[seat] == 'A'){
                                ((Order)user.orders.get(order)).adults--;
                            }
                            else if(((Order)user.orders.get(order)).types[seat] == 'S'){
                                ((Order)user.orders.get(order)).senior--;
                            }
                            else if(((Order)user.orders.get(order)).types[seat] == 'C'){
                                ((Order)user.orders.get(order)).child--;
                            }
                            ((Order)user.orders.get(order)).sum--;
                            
                            if(((Order)user.orders.get(order)).sum == 0){
                                user.orders.remove(order);
                            }
                            else{
                                ((Order)user.orders.get(order)).rows = removeElement(((Order)user.orders.get(order)).rows, seat);
                                ((Order)user.orders.get(order)).seats  = removeElement(((Order)user.orders.get(order)).seats, seat);
                                ((Order)user.orders.get(order)).types = removeElementC(((Order)user.orders.get(order)).types, seat);
                            }

                            
                        }
                    }
                    
                    menu = false;
                    break;
                case 3:
                    
                    for(int i = 0; i < ((Order)user.orders.get(i)).sum;i++){
                        auds[((Order)user.orders.get(order)).aud - 1][((Order)user.orders.get(order)).rows[i]][((Order)user.orders.get(order)).seats[i]] = '#';
                    }
                    user.orders.remove(order);
                    menu = false;
                    break;
                default:
                    System.out.println("Invalid input");
                
            }
        }
        
    }
    
    public static int[] removeElement(int[] original, int element){
        int[] n = new int[original.length - 1];
        System.arraycopy(original, 0, n, 0, element );
        System.arraycopy(original, element+1, n, element, original.length - element-1);
        return n;
    }
    
    public static char[] removeElementC(char[] original, int element){
        char[] n = new char[original.length - 1];
        System.arraycopy(original, 0, n, 0, element );
        System.arraycopy(original, element+1, n, element, original.length - element-1);
        return n;
    }

}
