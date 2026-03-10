/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package main;

import Computer.Computer;
import Job.Job;
import Manager.JobManager;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import org.yaml.snakeyaml.Yaml;

/**
 *
 * @author frana
 */
public class ButcherProcesos {
    private static final int CPU=4;
    private static final int MEMORY=2048;

    public static void main(String[] args) {
        Computer c = new Computer(CPU,MEMORY);
        JobManager jm = new JobManager(c);

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
}
