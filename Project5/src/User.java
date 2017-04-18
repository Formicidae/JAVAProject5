
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Eddie
 */
public class User {
    private String name;
    private String password;
    public ArrayList orders = new ArrayList();
    
    public User(String n,String p){
        name = n;
        password = p;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList getOrders() {
        return orders;
    }

    public void setOrders(ArrayList orders) {
        this.orders = orders;
    }
    
}
