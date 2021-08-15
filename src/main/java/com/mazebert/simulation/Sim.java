package com.mazebert.simulation;

public strictfp class Sim {
    /**
     * If there are breaking changes, increment this number and the version in pom.xml
     * If there are no breaking changes, there is no need for a new version
     */
    public static final int version = 26;

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
    public static final int vRoC = 22; // Rise of Cthulhu release
    public static final int v23 = 23; // Small bugfix/qol release
    public static final int v24 = 24; // April fools release
    public static final int vRoCEnd = 25; // Rise of Cthulhu season end merge
    public static final int vRnR = 26; // Rag Nar Roc release
    public static final int vRnREnd = 1000; // TODO Rag Nar Roc season end merge

    // For android 19 compatibility
    private static final ThreadLocal<Context> context = new ThreadLocal<Context>() {
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

    public static boolean isRoCSeasonContent() {
        int version = context().version;
        return version >= vRoCEnd || (version >= vRoC && context().season);
    }

    public static boolean isRnRSeasonContent() {
        int version = context().version;
        return version >= vRnREnd || (version >= vRnR && context().season);
    }
}
