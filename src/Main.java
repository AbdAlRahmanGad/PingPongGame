    public class Main {
        public static gameFrame gameF= new gameFrame();

        public static void main(String[] args) {
            makeFrame(-1);
        }
        public static void makeFrame(int winner){
            gameF.makeFrame(winner);
        }
    }