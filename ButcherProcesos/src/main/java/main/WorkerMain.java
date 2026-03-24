/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author frana
 */
public class WorkerMain {

    public static void main(String[] args) {
        if (args.length < 4) {
            System.out.println("No se han pasado los argumentos requeridos");
            System.exit(1);
        } else {
            long start = System.currentTimeMillis();
            String id = args[0];
            long duration = Long.parseLong(args[1]);
            int cpuCores = Integer.parseInt(args[2]);
            String memMb = args[3];
            long elapsed = 0;
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
            while (elapsed < duration) {
                try {
                    elapsed = System.currentTimeMillis() - start;
                    double progress = Double.valueOf(elapsed)/ Double.valueOf(duration);
                    progress = Math.min(progress, 1.0);
                    bw.write( "progress: " + progress);
                    bw.newLine();
                    bw.flush();
                    Thread.sleep(1000);
                    
                } catch (IOException ex) {
                    Logger.getLogger(WorkerMain.class.getName()).log(Level.SEVERE, null, ex);
                    System.exit(1);
                } catch (InterruptedException ex) {
                    Logger.getLogger(WorkerMain.class.getName()).log(Level.SEVERE, null, ex);
                    System.exit(1);
                }
                
            }
            System.exit(0);
            
        }
        
    }
    
}
