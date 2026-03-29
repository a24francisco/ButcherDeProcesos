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
import java.util.InputMismatchException;
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
        List<Job> ready = new ArrayList<>(jm.getREADY());
        int option = selectOption();
        if (option == 1) {
            FCFS FCFS = new FCFS(jm);
            FCFS.algorithm();
            Thread show = new Thread(() -> {
                while (!jm.getRUNNING().isEmpty()) {
                    print(jm, "FCFS");
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(ButcherProcesos.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            show.start();
            launchJobFCFS(jm, ready.get(0));
        } else {

            RR RR = new RR(jm, 20);
            for (Job j : ready) {
                RR.algorithm();
                Thread t = new Thread(() -> launchJobRR(jm, j));
                t.start();
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
        while (!jm.getNEW().isEmpty()) {
            jm.moveNewToReadyWaiting();
        }
    }

    public static int selectOption() {
        Scanner sc = new Scanner(System.in);
        try {
            System.out.println("Elige planificación:(por defecto sera FCFS)");
            System.out.println("1-FCFS");
            System.out.println("2-RR");
            int option = sc.nextInt();
            if (option != 1 && option != 2) {
                System.out.println("Error en la seleccion de la opcion");
                return 1;
            }

            return option;
        } catch (InputMismatchException ex) {
            System.out.println("Error en la seleccion de la opcion");
        }
        return 1;

    }

    public static Process launchJobRR(JobManager jm, Job j) {
        try {
            String cp = System.getProperty("java.class.path");
            ProcessBuilder pb = new ProcessBuilder(
                    "java", "-cp", cp, "main.WorkerMain", j.getId(), String.valueOf(j.getWorkload().getDuration_ms()), String.valueOf(j.getCpu()), String.valueOf(j.getMemory())
            );
            Process p = pb.start();
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
            int exitCode = p.waitFor();
            if (exitCode == 0) {
                jm.moveRunningToDone(j);
            } else {
                jm.moveRunningToFailed(j);
            }

            jm.getC().freeResources(j.getCpu(), j.getMemory());
            jm.moveWaitingToReady();
            List<Job> news = new ArrayList<>(jm.getREADY());
            for (Job aNew : news) {
                if (aNew.getState() == State.READY) {
                    jm.moveReadyToRunning();
                    Thread t = new Thread(() -> launchJobRR(jm, aNew));
                    t.start();
                }
            }
            return p;

        } catch (IOException ex) {

            Logger.getLogger(ButcherProcesos.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (InterruptedException ex) {
            Logger.getLogger(ButcherProcesos.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

    public static void launchJobFCFS(JobManager jm, Job j) {
        try {
            String cp = System.getProperty("java.class.path");
            ProcessBuilder pb = new ProcessBuilder(
                    "java", "-cp", cp, "main.WorkerMain", j.getId(), String.valueOf(j.getWorkload().getDuration_ms()), String.valueOf(j.getCpu()), String.valueOf(j.getMemory())
            );
            Process p = pb.start();
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("progress: ");
                String SProgress=parts[1].replace("," , ".");
                double progress = Double.parseDouble(SProgress);
                j.setProgress(progress);
            }
            int exitCode = p.waitFor();
            if (exitCode == 0) {
                jm.moveRunningToDone(j);
            } else {
                jm.moveRunningToFailed(j);
            }
            jm.getRUNNING().remove(j);
            jm.getC().freeResources(j.getCpu(), j.getMemory());
            jm.moveWaitingToReady();
            List<Job> news = new ArrayList<>(jm.getREADY());
            for (Job aNew : news) {
                if (aNew.getState() == State.READY) {
                    jm.moveReadyToRunning();
                    launchJobFCFS(jm, news.get(0));
                }

            }

        } catch (IOException ex) {

            Logger.getLogger(ButcherProcesos.class.getName()).log(Level.SEVERE, null, ex);

        } catch (InterruptedException ex) {
            Logger.getLogger(ButcherProcesos.class.getName()).log(Level.SEVERE, null, ex);

        }

    }

    public static void print(JobManager jm, String algorithm) {
       
        System.out.flush();
        System.out.println("----------------------------------------------");
        System.out.println("--BATCHER MONITOR · Política: " + algorithm + " --");
        System.out.println("----------------------------------------------");
        System.out.println("CPU " + jm.getC().getUsedCPU() + "/" + jm.getC().getCpuCores());
        System.out.println("RAM " + jm.getC().getUsedMemory() + "/" + jm.getC().getMemoryMB());
        System.out.println("----------------------------------------------");
        System.out.println("READY: " + jm.getREADY().size());
        for (Job j : jm.getREADY()) {
            System.out.print(j.getId() + "(p" + j.getPriority() + "," + j.getCpu() + ",CPU: " + j.getMemory() + " MB),");
        }
        System.out.println("");
        System.out.println("WAITING: " + jm.getWAITING().size());
        for (Job j : jm.getWAITING()) {
            System.out.print(j.getId() + "(p" + j.getPriority() + "," + j.getCpu() + ",CPU: " + j.getMemory() + " MB),");
        }
        System.out.println("");
        System.out.println("DONE: " + jm.getDone().size());
        for (Job j : jm.getDone()) {
            System.out.print(j.getId() + "(p" + j.getPriority() + "," + j.getCpu() + ",CPU: " + j.getMemory() + " MB),");
        }
        System.out.println("");
        System.out.println("FAILED: " + jm.getFailed().size());
        for (Job j : jm.getFailed()) {
            System.out.print(j.getId() + "(p" + j.getPriority() + "," + j.getCpu() + ",CPU: " + j.getMemory() + " MB),");
        }
        System.out.println("----------------------------------------------------------------");
        System.out.println("RUNNING: " + jm.getRUNNING().size());
        System.out.println("ID|PID|PRIO|CORES|MEM|PROGRESO|ESTADO");
        for (Job j : jm.getRUNNING()) {
            System.out.println(j.getId() + "|" + j.getPid() + "|" + j.getPriority() + "|" + j.getCpu() + "|" + j.getMemory() + "MB" + "|" +String.valueOf(j.getProgress()) + "|" + j.getState());
        }

    }

}
