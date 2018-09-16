package com.bgo.authdemo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Slf4j
public class PerfController {

    /**
     * See https://gist.github.com/benjchristensen/1566272
     */
    @GetMapping("/perf/random")
    @ResponseBody
    public String random() {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100000000; i++) {
            Math.random();
        }
        String s1 = "Math.random() Time: " + (System.currentTimeMillis() - start) + "ms";
        System.out.println(s1);
        start = System.currentTimeMillis();
        for (long i = 0; i < 8000000000L; i++) {
            // perform random math functions to use CPU
            int v = 4 * 67 + 87 / 45 * 2345;
        }
        String s2 = "Simple Math Time: " + (System.currentTimeMillis() - start) + "ms";
        System.out.println(s2);
        return s1 + " - " + s2;
    }

    @GetMapping("/perf/busythreads")
    @ResponseBody
    public String busyThreads(@RequestParam(value="cores", defaultValue="2") int cores,
            @RequestParam(value="threads", defaultValue="2") int threads,
            @RequestParam(value="duration", defaultValue="100000") int duration) {
        int numCore = cores;
        int numThreadsPerCore = threads;
        double load = 0.8;
        final long d = duration;
        for (int thread = 0; thread < numCore * numThreadsPerCore; thread++) {
            new BusyThread("Thread" + thread, load, d).start();
        }
        return "See logs";
    }

    /**
     * Thread that actually generates the given load
     * @author Sriram
     */
    private static class BusyThread extends Thread {
        private double load;
        private long duration;

        /**
         * Constructor which creates the thread
         * @param name Name of this thread
         * @param load Load % that this thread should generate
         * @param duration Duration that this thread should generate the load for
         */
        public BusyThread(String name, double load, long duration) {
            super(name);
            this.load = load;
            this.duration = duration;
        }

        /**
         * Generates the load when run
         */
        @Override
        public void run() {
            long startTime = System.currentTimeMillis();
            try {
                // Loop for the given duration
                while (System.currentTimeMillis() - startTime < duration) {
                    // Every 100ms, sleep for the percentage of unladen time
                    if (System.currentTimeMillis() % 100 == 0) {
                        Thread.sleep((long) Math.floor((1 - load) * 100));
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @GetMapping("/perf/replaceall")
    @ResponseBody
    public String replaceAll() {
        long start = System.currentTimeMillis();
        int iterations = 10000000 / 2;
        for (int ii = 0 ; ii < iterations ; ii++)
        {
            String s = "this XXX a test".replaceAll("XXX", " is ");
        }
        long elapsed = System.currentTimeMillis() - start;
        String s = "elapsed time = " + elapsed + "ms, " + (elapsed * 1000.0) / iterations + " microseconds per execution";
        System.out.println(s);
        return s;
    }

}