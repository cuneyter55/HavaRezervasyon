import java.util.Random;

public class MatrixMultiplication {
    
    //burada matrixlerimizi ve boyutlarını tanımlıyoruz
    static final int MAX = 400;
    static final int MAX_THREAD = 400;
    static int[][] matA = new int[MAX][MAX];
    static int[][] matB = new int[MAX][MAX];
    static int[][] matC = new int[MAX][MAX];
    static int step_i = 0;

    static class Worker implements Runnable {
        int i;

        Worker(int i) {
            this.i = i;
        }

        @Override
        public void run() {
            for (int j = 0; j < MAX; j++) {
                for (int k = 0; k < MAX; k++) {
                    matC[i][j] += matA[i][k] * matB[k][j];
                }
            }
        }
    }

    public static void main(String[] args) {
        Random rand = new Random();

        // matA ve matB matrixleri için rastgele değerler oluşturuyoruz
        for (int i = 0; i < MAX; i++) {
            for (int j = 0; j < MAX; j++) {
                matA[i][j] = rand.nextInt(10);
                matB[i][j] = rand.nextInt(10);
            }
        }

        // matA matrixini gösteriyoruz
        System.out.println("Matrix A");
        for (int i = 0; i < MAX; i++) {
            for (int j = 0; j < MAX; j++) {
                System.out.print(matA[i][j] + " ");
            }
            System.out.println();
        }

        // matB matrixini gösteriyoruz
        System.out.println("Matrix B");
        for (int i = 0; i < MAX; i++) {
            for (int j = 0; j < MAX; j++) {
                System.out.print(matB[i][j] + " ");
            }
            System.out.println();
        }

        // Çalışma süresini başlatıyoruz
        long startTime = System.currentTimeMillis();

        //Thredleri oluşturup başlatıyoruz
        Thread[] threads = new Thread[MAX_THREAD];
        for (int i = 0; i < MAX_THREAD; i++) {
            threads[i] = new Thread(new Worker(step_i++));
            threads[i].start();
        }

        // tüm threadlerinin tamamlanmasını bekliyoruz
        for (int i = 0; i < MAX_THREAD; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Çalışma süresini hesaplıyoruz
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        // Sonuç matrixini gösteriyouz
        System.out.println("A ve B matrixinin çarpımı :");
        for (int i = 0; i < MAX; i++) {
            for (int j = 0; j < MAX; j++) {
                System.out.print(matC[i][j] + " ");
            }
            System.out.println();
        }

        // Çalışma süresini yazdırıyoruz
        System.out.println("\nÇalışma süresi: " + duration + " milisaniye");
        
    }
}
