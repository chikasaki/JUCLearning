并发控制工具:
1. `CountDownLatch`: 
    - 两个方法:
        - await
        - countdown
    - 计数器为0时，所有await的线程会一起开始执行
    - 两个常用场景:
        - 一等多: 服务器启动时，等待所有服务启动完成
        - 多等一: 压力测试时，多个线程同时发出请求
        
2. `Semaphore`: 
    - 方法:
        - acquire: 
            - 可相应中断
            - 传入对应的参数，代表一次性取得多少个许可证
        - acquireUninterruptibly:
            - 不可响应中断
        - tryAcquire:
            - 无参数时，会返回获取成功失败的标志，不会阻塞
            - 有参数时，会阻塞x秒
        - release:
            - 归还许可证
            - 有参数时，归还对应数量的许可证
    - 可指定公平与否，本质是对是否使用公平锁的权衡
    - 一个线程获取许可证之后，可由其他线程归还
    
3. `Condition`:
    - 通过锁来获取
    - Lock对应于synchronized，而Condition对应于`wait/notify/notifyAll`等一系列方法
    - 同理与`wait`，当调用`condition`的`await`方法时，会释放锁
    - `Condition`效率高于`wait/notify`，因为一个锁对象可以获取多个Condition对象

4. `CyclicBarrier`与`CountDownLatch`的比较
    - `CountDownLatch`针对`countDown()`事件，而`CyclicBarrier`针对线程
    - `CyclicBarrier`可在满足条件之后，设定要完成的任务
    - `CyclicBarrier`可重用，而`CountDownLatch`不可重用