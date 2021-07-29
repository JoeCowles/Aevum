package tracking;

public class TrackedObj {
    

    public static enum EnforceStage{
		RUNNING,
		WARN,
		ENFORCE,
		SHUTDOWN
	}


    protected boolean stageEnforced;
    protected EnforceStage stage;

    public TrackedObj(){
        stage = EnforceStage.RUNNING;
    }
    public void advanceStage() {
		setStageEnforced(false);
		switch(stage) {
			case RUNNING:
				stage = EnforceStage.WARN;
				break;
			case WARN:
				stage = EnforceStage.ENFORCE;
				break;
			case ENFORCE:
				stage = EnforceStage.SHUTDOWN;
				break;
		}
		
	}
    public boolean stageEnforced() {
		return stageEnforced;
	}
	public void setStageEnforced(boolean stageEnforced) {
		this.stageEnforced = stageEnforced;
	}
    public void setEnforcementStage(EnforceStage stage) {
		
		this.stage = stage;
		
	}
	
	
	public EnforceStage getStage() {
		
		return stage;
		
	}
}
