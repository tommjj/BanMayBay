package utiliz;

public class Constants {


    public static class WindowConstants {      
        //dont change these values
        public static final int DEFAULT_WIDTH_SIZE = 1280;
        public static final int DEFAULT_HEIGHT_SIZE = 768;
        public static final float SCALE = 1.1f; 
        public static final int WIDTH_SIZE = (int) (DEFAULT_WIDTH_SIZE * SCALE);
        public static final int HEIGHT_SIZE = (int) (DEFAULT_HEIGHT_SIZE * SCALE);
    }
    
    public static class GameConstants {
        public static final int DEFAULT_WIDTH_SIZE = 1280 + 200;
        public static final int DEFAULT_HEIGHT_SIZE = 768 + 200;
        public static final int WIDTH_SIZE = (int) (DEFAULT_WIDTH_SIZE * WindowConstants.SCALE);
        public static final int HEIGHT_SIZE = (int) (DEFAULT_HEIGHT_SIZE * WindowConstants.SCALE);
    }
}
