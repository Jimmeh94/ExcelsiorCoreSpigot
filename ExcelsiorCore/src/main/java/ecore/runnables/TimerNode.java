package ecore.runnables;

import ecore.ECore;

public class TimerNode extends AbstractTimer {

    public TimerNode() {
        super(10L);
    }

    @Override
    protected void runTask() {
        ECore.INSTANCE.getNodes().tick();
    }
}
