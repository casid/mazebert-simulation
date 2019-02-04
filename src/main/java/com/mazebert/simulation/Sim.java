package com.mazebert.simulation;

public strictfp class Sim {
    /**
     * If there are breaking changes, increment this number and the version in pom.xml
     * If there are no breaking changes, there is no need for a new version
     */
    public static final int version = 4;

    // For android 19 compatibility
    private static ThreadLocal<Context> context = new ThreadLocal<Context>() {
        @Override
        protected strictfp Context initialValue() {
            return new Context();
        }
    };

    public static Context context() {
        return context.get();
    }

    static void setContext(Context testContext) {
        context.set(testContext);
    }

    static void resetContext() {
        context.remove();
    }
}
