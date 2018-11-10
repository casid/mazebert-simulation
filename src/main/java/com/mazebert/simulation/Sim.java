package com.mazebert.simulation;

public strictfp class Sim {
    private static ThreadLocal<Context> context = ThreadLocal.withInitial(Context::new);

    public static Context context() {
        return context.get();
    }

    static void setTestContext(Context testContext) {
        context.set(testContext);
    }
}
