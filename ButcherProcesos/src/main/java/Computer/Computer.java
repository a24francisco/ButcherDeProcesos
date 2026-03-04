/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Computer;

/**
 *
 * @author frana
 */
public class Computer {

    private int cpuCores;
    private int memoryMB;
    private int usedMemory = 0;
    private int usedCPU = 0;

    public Computer(int cpuCores, int memoryMB) {
        this.cpuCores = cpuCores;
        this.memoryMB = memoryMB;
    }

    public int getCpuCores() {
        return cpuCores;
    }

    public void setCpuCores(int cpuCores) {
        this.cpuCores = cpuCores;
    }

    public int getMemoryMB() {
        return memoryMB;
    }

    public void setMemoryMB(int memoryMB) {
        this.memoryMB = memoryMB;
    }

    public int getUsedMemory() {
        return usedMemory;
    }

    public void setUsedMemory(int usedMemory) {
        this.usedMemory = usedMemory;
    }

    public int getUsedCPU() {
        return usedCPU;
    }

    public void setUsedCPU(int usedCPU) {
        this.usedCPU = usedCPU;
    }

    public boolean acceptJobs(int SCores, int SMemory) {
        return cpuCores - usedCPU >= SCores && memoryMB - usedMemory >= SMemory;
    }
    
    public void reserveResources(int SCores,int SMemory){
        if (!acceptJobs(SCores, SMemory)) {
            System.out.println("ERROR");
        }else{
        usedCPU += SCores;
        usedMemory += SMemory;}
    }
    public void freeResources(int SCores,int SMemory){
        usedCPU -= SCores;
        usedMemory -= SMemory;
        if (usedCPU<0) {
            usedCPU=0;
        }
        if (usedMemory<0) {
            usedMemory=0;
        }
    }

}
