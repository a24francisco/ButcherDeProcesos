/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Planification;

import Interfaces.Planification;
import Job.Job;
import Manager.JobManager;

/**
 *
 * @author frana
 */
public class RR implements Planification{

    private JobManager jm;
    private int quantum;

    public RR(JobManager jm, int quantum) {
        this.jm = jm;
        this.quantum = quantum;
    }
      @Override
    public void algorithm(){
       finishQuantum();
        }

    public void finishQuantum() {

        if (!jm.getREADY().isEmpty()) {
            Job j=jm.getREADY().get(0);
            jm.getREADY().remove(0);
            jm.getREADY().add(j);
        }
    }

    public JobManager getJm() {
        return jm;
    }

    public void setJm(JobManager jm) {
        this.jm = jm;
    }

    public int getQuantum() {
        return quantum;
    }

    public void setQuantum(int quantum) {
        this.quantum = quantum;
    }
    

}
