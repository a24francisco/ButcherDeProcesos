/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Planification;

import Job.Job;
import Manager.JobManager;

/**
 *
 * @author frana
 */
public class FCFS {
    private JobManager jm;

    public FCFS(JobManager jm) {
        this.jm = jm;
    }
    public void algorithm(){
        jm.moveReadyToRunning();
        }
    }
    


