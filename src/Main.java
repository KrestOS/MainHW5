public class Main {

    static final int SIZE = 10000000;
    static final int H = SIZE/2;
    static float[] arr;


    public static void main(String[] args) {

        arr = createArrFill1(SIZE);
        System.out.println("One thread did this in " + doOneThread(arr) + " ms");

        arr = createArrFill1(SIZE);
        System.out.println("Two threads did this in " + doTwoThreads(arr) + " ms");


    }

    public static float[] createArrFill1(int size){

        float[] arr = new float[size];
        for (int i = 0; i < size; i++) {
            arr[i] = 1;

        }
        return arr;
    }

    public static long doOneThread(float[] arr){
      long time = System.currentTimeMillis();
        for (int i = 0; i < arr.length; i++) {

            arr[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));

        }
        time = System.currentTimeMillis() - time;
        return time;
    }

    public static long doTwoThreads(float[] arr){
        long time = System.currentTimeMillis();

        float[] arrHalf = new float[H];
        float[] arrHalf2 = new float[H];
        Thread t1 = new Thread(()->{
            System.arraycopy(arr,0,arrHalf,0,H);
            for (int i = 0; i < H; i++) {
                arrHalf[i] = (float)(arrHalf[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
            }
            System.arraycopy(arrHalf, 0, arr, 0, H);
        });
        Thread t2 = new Thread(()->{
            System.arraycopy(arr,H,arrHalf2,0,H);
            for (int i = 0; i < H; i++) {

                arrHalf2[i] = (float)(arrHalf2[i] * Math.sin(0.2f + (i+H) / 5) * Math.cos(0.2f + (i+H)/ 5) * Math.cos(0.4f + (i+H) / 2));
            }
            System.arraycopy(arrHalf2, 0, arr, H, H);
        });

      t1.start();
      t2.start();
        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        time = System.currentTimeMillis() - time;
        return time;
    }
}
