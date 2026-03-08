package Manager;


import Computer.Computer;
import Job.Job;
import Job.State;
import java.util.ArrayList;
import java.util.List;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author frana
 */
public class JobManager {

    private List<Job> ready;
    private List<Job> waiting;
    private List<Job> running;
    private List<Job> New;
    private Computer C;

    public JobManager(Computer C) {
        this.ready = new ArrayList<>();
        this.waiting = new ArrayList<>();
        this.running = new ArrayList<>();
        this.New = new ArrayList<>();
        this.C = C;
    }

    public void newJobList(Job j) {
        New.add(j);
    }

    public void moveNewToReadyWaiting() {
        if (!New.isEmpty()) {
            Job j = New.get(0);

            if (C.acceptJobs(j.getCpu(), j.getMemory())) {
                C.reserveResources(j.getCpu(), j.getMemory());
                ready.add(j);

                j.setState(State.READY);
            } else {
                waiting.add(j);

                j.setState(State.WAITING);
            }
            New.remove(j);
        }
    }

    public void moveWaitingToReady() {
        for (int i = 0; i < waiting.size(); i++) {
            Job j = waiting.get(i);
            if (C.acceptJobs(j.getCpu(), j.getMemory())) {
                C.reserveResources(j.getCpu(), j.getMemory());
                ready.add(j);
                waiting.remove(j);
                j.setState(State.READY);
                i--;//encoger la lista  y todos se mueven una posicion
            }

        }
    }

    public void moveReadyToRunning() {
        if (!ready.isEmpty()) {
            Job j = ready.get(0);
            ready.remove(j);
            running.add(j);
            j.setState(State.RUNNING);
        }
    }

    public void moveRunningToDone() {
        if (!running.isEmpty()) {
            Job j = running.get(0);
            running.remove(j);
            C.freeResources(j.getCpu(), j.getMemory());
            j.setState(State.DONE);
            moveWaitingToReady();
        }
    }

    public List<Job> getREADY() {
        return ready;
    }

    public void setREADY(List<Job> READY) {
        this.ready = READY;
    }

    public List<Job> getWAITING() {
        return waiting;
    }

    public void setWAITING(List<Job> WAITING) {
        this.waiting = WAITING;
    }

    public List<Job> getRUNNING() {
        return running;
    }

    public void setRUNNING(List<Job> RUNNING) {
        this.running = RUNNING;
    }

    public List<Job> getNEW() {
        return New;
    }

    public void setNEW(List<Job> NEW) {
        this.New = NEW;
    }

    public Computer getC() {
        return C;
    }

    public void setC(Computer C) {
        this.C = C;
    }

}
