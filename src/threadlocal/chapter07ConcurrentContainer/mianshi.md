并发容器相关:
1. Java并发容器的历史:
    - `Vector` 与 `HashTable`: 所有并发相关操作都采用synchronized来保证安全，效率低下
    - `Collections.synchronized*`: 与`Vector`和`HashTable`类似
    - `CopyOnWriteArrayList` 与 `ConcurrentHashMap`: 效率更高
    
2. Java1.7的ConcurrentHashMap:
    ![ConcurrentHashMap1.7](../images/ConcurrentHashMap1.7.png)
    - 采用分段锁，默认是16段，每段都使用一个可重入锁保证线程安全
    - 每个Segment可看作一个更小的HashMap，通过锁来保证线程安全
    - 降低了锁的粒度，提高了并发效率
    - 在1.7版本中，底层的HashMap默认都是使用拉链法来解决哈希冲突
    
3. Java1.8的ConcurrentHashMap:
    ![ConcurrentHashMap1.8](../images/ConcurrentHashMap1.8.png)
    - 比起1.7，更进一步减小了锁的粒度，提高效率
    - 将段的概念替换成Node，对每个Node通过`CAS` + `synchronized`
    来保证线程安全
    - 采用拉链法与红黑树来解决哈希冲突，阈值为8时会从链表转为红黑树
    
4. CopyOnWrite*:
    - 传统的集合如ArrayList在迭代时是不能修改的
    - CopyOnWriteArrayList: 采用了读写分离的方式，除了写写互斥之外，
    其他操作都可以并发执行；原理是在写时，会先Copy一份相同的数组进行操作，
    完成操作之后再将指针指向新的内存
    - 缺点:
        - 可能读取不到最新数据
        - 需要双份内存，以及复制，会有一定开销
    - 适合场景:
        - 读多写少，尽量快地读