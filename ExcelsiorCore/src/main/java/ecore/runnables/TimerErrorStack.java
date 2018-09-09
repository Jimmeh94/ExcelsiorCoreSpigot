package ecore.runnables;

import ecore.ECore;

public class TimerErrorStack extends AbstractTimer {

    public TimerErrorStack() {
        super(20L * 10);
    }

    @Override
    protected void runTask() {
        ECore.INSTANCE.getErrorStack().tick();
    }
}
