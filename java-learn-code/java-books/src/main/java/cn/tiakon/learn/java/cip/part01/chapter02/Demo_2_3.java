package cn.tiakon.learn.java.cip.part01.chapter02;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Demo_2_3 {
}

@Slf4j
class LazyInitRace {
    private ExpensiveObject instance = null;

    private ExpensiveObject getInstance() {

        if (instance == null) {
            instance = new ExpensiveObject();
        }
        return instance;
    }

    public static void main(String[] args) {
        log.info("");
        LazyInitRace lazyInitRace = new LazyInitRace();

        ExecutorService executorService = Executors.newCachedThreadPool();

        for (int i = 0; i < 20; i++) {
            executorService.execute(() -> log.info(">> {}", lazyInitRace.getInstance()));
        }
        executorService.shutdown();
    }

}

class ExpensiveObject {
}