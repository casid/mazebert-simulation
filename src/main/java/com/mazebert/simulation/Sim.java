package com.mazebert.simulation;

public strictfp class Sim {
    /**
     * If there are breaking changes, increment this number and the version in pom.xml
     * If there are no breaking changes, there is no need for a new version
     */
    public static final int version = 21;

    public static final int v10 = 10;
    public static final int v11 = 11;
    public static final int v12 = 12;
    public static final int v13 = 13;
    public static final int v14 = 14;
    public static final int v15 = 15;
    public static final int v16 = 16;
    public static final int v17 = 17;
    public static final int vDoL = 18; // Dawn of Light release
    public static final int v19 = 19;
    public static final int vCorona = 20;
    public static final int vDoLEnd = 21; // Dawn of Light season end merge
    @SuppressWarnings("unused")
    public static final int vCoC = 22; // Call of Cthulhu release

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

    public static void setContext(Context context) {
        Sim.context.set(context);
    }

    public static void resetContext() {
        context.remove();
    }

    public static boolean isDoLSeasonContent() {
        int version = context().version;
        return version >= vDoLEnd || (version >= vDoL && context().season);
    }
}
