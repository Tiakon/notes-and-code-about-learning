package cn.tiakon.learn.java.multithread.chapter06;

/**
 * 演示对象发布与逸出
 * 第三种线程安全问题：注册监听时间
 * 用工厂模式修改该问题。
 * <p>
 * last time   : 2020/10/9 7:35
 *
 * @author tiankai.me@gmail.com on 2020/10/9 7:35.
 */
public class Demo08_MultiThreadObjectRelease3Fixed {

    int count;

    private EventListener listener;

    private Demo08_MultiThreadObjectRelease3Fixed(MySource source) {

        listener = new EventListener() {
            @Override
            public void onEvent(Event e) {
                System.out.println("\n我得到的数字是" + count);
            }
        };

        for (int i = 0; i < 10000; i++) {
            System.out.print(i);
        }

        count = 100;
    }

    public Demo08_MultiThreadObjectRelease3Fixed getInstance(MySource source) {
        Demo08_MultiThreadObjectRelease3Fixed safeListener = new Demo08_MultiThreadObjectRelease3Fixed(source);
        source.registerListener(safeListener.listener);
        return safeListener;
    }

    public static void main(String[] args) {
        MySource mySource = new MySource();
        new Thread(() -> {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            mySource.eventCome(new Event() {
            });
        }).start();
        Demo08_MultiThreadObjectRelease3Fixed objectRelease3 = new Demo08_MultiThreadObjectRelease3Fixed(mySource);
    }

    static class MySource {
        private EventListener listener;

        void registerListener(EventListener eventListener) {
            this.listener = eventListener;
        }

        void eventCome(Event e) {
            if (listener != null) {
                listener.onEvent(e);
            } else {
                System.out.println("还未完成初始化完毕...");
            }

        }
    }

    interface EventListener {
        void onEvent(Event e);
    }

    interface Event {
    }

}
