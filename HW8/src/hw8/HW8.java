/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hw8;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Eddie
 */
public class HW8 {

    /**
     * @param args the command line arguments
     */
    public static boolean[] traveled;
    
    public static void main(String[] args) throws FileNotFoundException {
        // TODO code application logic here
        Scanner in = new Scanner(new File("graph.txt"));
        int size = in.nextInt();
        traveled = new boolean[size];
        int[][] graph = new int[size][size];
        while(in.hasNextLine()){
            Scanner line = new Scanner(in.nextLine());
            int node = 0;
            if(line.hasNextInt()){
                node = line.nextInt();
            }
            int place = 0;
            while(line.hasNextInt()){
                graph[node][place] = line.nextInt();
                place++;
            }
        }

        connected(graph,0);
        boolean nope = false;
        for(boolean b : traveled){
            if(b == false){
                nope = true;
            }
        }
        if(nope){
            System.out.println("Graph is NOT connected");
        }
        else{
            System.out.println("Graph is connected");
        }
    }
    
    public static void connected(int[][] graph, int n){
        traveled[n] = true;
        for(int i : graph[n]){
            if(traveled[i] == false){
                connected(graph,i);
            }
        }
    } 
}
