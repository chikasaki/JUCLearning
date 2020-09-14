`Future`与`Callable`:
1. `Callable`是什么？
    - 为了补足`Runnable`的两点不足:
        - 无法在方法处抛出异常
        - 没有返回值
    - `Callable`本质其实是对`Runnable`的封装:
        - 一个常见的实现`Callable`接口的类是`RunnableAdapter`
            - 内含`Runnable`属性和一个`result`属性
            - `call`方法其实是对`Runnable`的`run`方法的调用，并返回`result`
            
2. `Future`与`Callable`的关系:
    - `Future`一般是线程池调用`submit`的产物，用来存储
    `Callable`执行完成之后的结果，并且可以控制并检测
    `Callable`的执行情况，并且可以停止`Callable`的执行
    - `submit`是如何产生`Future`的？
        - `submit`方法会在内部创建一个`FutureTask`对象，
        使用这个`FutureTask`去执行，并将结果赋值给`FutureTask`的属性，
        会马上返回
    - `Future`的常用方法:
        - 不带参数的`get`: 返回`Callable`任务执行完成的结果
            - 除了`InterruptedException`，其它所有的异常都会被转为
            `ExecutionException`
        - 带参数的`get`: 可设定超时的`get`方法，可能会抛出`TimeoutException`
        - `isDone`: 判断任务是否执行完毕，正常执行完毕和发生异常都算是执行完毕
        - `isCancelled`: 判断任务是否被取消
        - `cancel`: 取消任务执行
            - 参数若传入true，则代表可强制结束任务(当任务内部可相应InterruptedException时)
            - 参数若传入false，则代表不强制结束执行中的任务
            - 以上两者的共同之处:
                - 对于还没开始的任务，取消之后任务都不再执行
                - 对于一个已经运行结束的任务，取消之后会返回false代表取消失败
                
3. `FutureTask`:
    - 是一个实现了`RunnableFuture`的类，
    而`RunnableFuture`同时实现了`Runnable`和`Future`接口
    - `FutureTask`, `Callable`, `Runnable`三者的关系:
        - 套娃，`FutureTask`内套了个`Callable`，`Callable`
        套了个`Runnable`，而`FutureTask`本身又实现了`Runnable`接口
    - `FutureTask`的使用:
        - 使用时传入`Callable`任务或`Runnable`任务，传入`Runnable`时，
        也会在内部将`Runnable`包装成`Callable`
        - 传入线程池作为任务时，会在线程池的`execute`方法中调用
        `FutureTask`的`run`方法，而`run`方法内会将`Callable`
        的返回值赋给`FutureTask`用来存储结果的属性(因为`FutureTask`
        本身也是一个`Future`)，这样就可以在外部获取`Callable`运行
        完成之后的结果了
        - 直接作为任务开启线程时，那就是直接调用`run`方法了，甚至不用
        `execute`，更加直接
        
    