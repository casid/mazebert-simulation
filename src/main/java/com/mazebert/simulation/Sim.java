package com.mazebert.simulation;

public strictfp class Sim {
    @SuppressWarnings("AnonymousHasLambdaAlternative") // For android 19 compatibility
    private static ThreadLocal<Context> context = new ThreadLocal<Context>() {
        @Override
        protected Context initialValue() {
            return new Context();
        }
    };

    public static Context context() {
        return context.get();
    }

    static void setContext(Context testContext) {
        context.set(testContext);
    }
}
