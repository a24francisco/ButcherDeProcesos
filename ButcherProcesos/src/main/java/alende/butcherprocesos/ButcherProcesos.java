/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package alende.butcherprocesos;

import Job.Job;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import org.yaml.snakeyaml.Yaml;

/**
 *
 * @author frana
 */
public class ButcherProcesos {

    public static void main(String[] args) throws FileNotFoundException {
       
       InputStream is= new FileInputStream("Jobs/job1.yaml");
        Yaml yaml = new Yaml();
            Job job = yaml.loadAs(is, Job.class);

            System.out.println("ID: " + job.getId());
            System.out.println("Name: " + job.getName());
            System.out.println("Priority: " + job.getPriority());

            System.out.println("CPU: " + job.getCpu());
            System.out.println("Memory(MB): " + job.getMemory());

            System.out.println("Duration ms: " + job.getWorkload().getDuration_ms());

       
    }
}
