/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Job;

/**
 *
 * @author frana
 */
public class Resources {
    private int cpu_cores;
     private String memory;

    public Resources() {
    }

     
    public Resources(int cpu_cores, String memory) {
        this.cpu_cores = cpu_cores;
        this.memory = memory;
    }

    public int getCpu_cores() {
        return cpu_cores;
    }

    public void setCpu_cores(int cpu_cores) {
        this.cpu_cores = cpu_cores;
    }

    public String getMemory() {
        return memory;
    }

    public void setMemory(String memory) {
        this.memory = memory;
    }
    
   
}
