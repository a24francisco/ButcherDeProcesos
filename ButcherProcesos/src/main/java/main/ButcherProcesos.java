/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package main;

import Computer.Computer;
import Job.Job;
import Job.State;
import Manager.JobManager;
import Planification.FCFS;
import Planification.RR;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.yaml.snakeyaml.Yaml;

/**
 *
 * @author frana
 */
public class ButcherProcesos {

    private static final int CPU = 4;
    private static final int MEMORY = 2048;

    public static void main(String[] args) {
        Computer c = new Computer(CPU, MEMORY);
        JobManager jm = new JobManager(c);

        load(jm);
        admitJobs(jm);
         List<Job> ready=new ArrayList<>(jm.getREADY());
        int option= selectOption();
        if(option==1){
           List<Process> total= new ArrayList<>();
            FCFS FCFS=new FCFS(jm);
            for(Job j:ready){
                FCFS.algorithm();
                total.add(launchJob(jm, j));
            }
            for(Process p:total){
               try {
                   p.waitFor();
               } catch (InterruptedException ex) {
                   Logger.getLogger(ButcherProcesos.class.getName()).log(Level.SEVERE, null, ex);
               }
            }
        }
        else{
            List<Process> total= new ArrayList<>();
            RR RR= new RR(jm,20);
                for(Job j:ready){
                RR.algorithm();
                total.add(launchJob(jm, j));
            }
            for(Process p:total){
               try {
                   p.waitFor();
               } catch (InterruptedException ex) {
                   Logger.getLogger(ButcherProcesos.class.getName()).log(Level.SEVERE, null, ex);
               }
            }
        }

    }

    private static void load(JobManager jm) {
        Yaml yaml = new Yaml();
        File carpeta = new File("Jobs/");
        File[] archivo = carpeta.listFiles();
        for (int i = 0; i < archivo.length; i++) {
            try {
                InputStream is = new FileInputStream(archivo[i]);
                Job job = yaml.loadAs(is, Job.class);

                if (job.isValid()) {
                    jm.newJobList(job);
                } else {
                    System.out.println("Job invalido" + archivo[i].getName());
                }
            } catch (Exception e) {
                System.out.println("Error de lectura");
            }
        }
    }

    public static void admitJobs(JobManager jm) {
        while(!jm.getNEW().isEmpty()){
        jm.moveNewToReadyWaiting();}
    }
    public static int selectOption(){
    Scanner sc= new Scanner(System.in);
    
        System.out.println("Elige planificación:(por defecto sera FCFS)");
        System.out.println("1-FCFS");
        System.out.println("2-RR");
        int option=sc.nextInt();
        if (option!=1 && option!=2) {
            System.out.println("Error en la seleccion de la opcion");
             return 1;
        }
        return option;
    }
    public static Process launchJob(JobManager jm, Job j){
        try {
            String cp=System.getProperty("java.class.path");
            ProcessBuilder pb = new ProcessBuilder(
                    "java", "-cp", cp, "main.WorkerMain",j.getId(),String.valueOf(j.getWorkload().getDuration_ms()),String.valueOf(j.getCpu()),String.valueOf(j.getMemory())
            );
            Process p=pb.start();
            BufferedReader br= new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while((line=br.readLine())!=null){
                System.out.println(line);
            }
            int exitCode=p.waitFor();
            if (exitCode==0) {
                j.setState(State.DONE);
            }
            else{
                j.setState(State.FAILED);
            }
            jm.getRUNNING().remove(j);
            jm.getC().freeResources(j.getCpu(), j.getMemory());
            jm.moveWaitingToReady();
            return p;
            
        } catch (IOException ex) {
            
            Logger.getLogger(ButcherProcesos.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (InterruptedException ex) {
            Logger.getLogger(ButcherProcesos.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    
    }
    
}
