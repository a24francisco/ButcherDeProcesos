/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Job;

/**
 *
 * @author frana
 */
public class Job {
    private String id;
    private String name;
    private int priority;
    private Resources resources;
    private Workload workload;
    private long startTime;
    private long durationTime;
    private long pid;
    private State state=State.NEW;
    private double progress;

    public Job() {
    }
    
    

    public Job(String id, String name, int priority, Resources resources, Workload workload, long startTime, long durationTime, long pid) {
        this.id = id;
        this.name = name;
        this.priority = priority;
        this.resources = resources;
        this.workload = workload;
        this.startTime = startTime;
        this.durationTime = durationTime;
        this.pid = pid;
        this.progress=0.0;
    }
    
    public int getCpu(){
        return this.resources.getCpu_cores();
    }
    public int getMemory(){
        String [] memoryValue=resources.getMemory().split(" ");
        String type= memoryValue[1];
        int memory=Integer.parseInt(memoryValue[0]);
        switch (type.toUpperCase()) {
            case "MB":
                return memory;
            case "GB":
                return memory*1000;
            default:
                return 0;
        }
        
            
        }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public Resources getResources() {
        return resources;
    }

    public void setResources(Resources resources) {
        this.resources = resources;
    }

    public Workload getWorkload() {
        return workload;
    }

    public void setWorkload(Workload workload) {
        this.workload = workload;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getDurationTime() {
        return durationTime;
    }

    public void setDurationTime(long durationTime) {
        this.durationTime = durationTime;
    }

    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public double getProgress() {
        return progress;
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }

    @Override
    public String toString() {
        return "Job{" + "id=" + id + ", name=" + name + ", priority=" + priority + ", resources=" + resources + ", workload=" + workload + ", startTime=" + startTime + ", durationTime=" + durationTime + ", pid=" + pid + ", state=" + state + ", progress=" + progress + '}';
    }
    
    
    public boolean isValid(){
        if (id==null||id.isEmpty()) {
            return false;
        }
        if (name==null||name.isEmpty()) {
            return false;
        }
        if (priority<0||priority>4) {
            return false;
        }
        if (resources==null) {
            return false;
        }
        if (resources.getCpu_cores()<1) {
            return false;
        }
        if (getMemory()<=0) {
            return false;
        }
        if (workload==null) {
            return false;
        }
        if (workload.getDuration_ms()<=0) {
            return false;
        }
        return true;
    }
    
    }
    
    
    
    

