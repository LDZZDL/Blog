# 实战Java高并发程序设计（第二版）

## 第一章
- 同步与异步。同步就是操作A必须等待B操作完成，操作A才能执行。异步就是B操作完成之前，A便可以执行。
- 并发与并行。并行就是操作A和操作B之间同时进行，并发只是宏观意义上的并行，它将操作划分为多个很小时间片，在时间片之间操作交替进行，这样在我们所能感知的基础上就造成了并行的效果。为什么需要这样呢？因为线程或者操作执行需要获得CPU控制权，早期计算机通常只有一个CPU。后来，尽管有了多核CPU，但是线程的数量也远超CPU的数量。所以，所以就需要并发操作。
- 临界区用来代表共有资源或者共享数据
- 阻塞和非阻塞。
- 死锁（Deadlock）、饥饿（Starvation）、活锁（Livelock）。死锁就是所有线程获得不到所需要资源，导致所有线程无法前行推进。饥饿就是一个线程或者多个线程长期没有获得所需要的资源，导致线程无法推进。

并发级别：阻塞、无饥饿、无障碍、无锁、无等待
- 阻塞：线程是阻塞的，在其他线程释放资源之前，它是无法执行的
- 无饥饿：线程调调度室公平的，不会导致线程长期无法或者所需要的临界资源
- 无障碍：无障碍是一种最弱的非阻塞调度。多个线程可以同时进入临界区，这就是无障碍。如果如果同时对临界资源进行修改，会触发回滚。从这个意义上来说，无障碍是乐观的，因为它假设线程冲突的概率不大。从反面讲，阻塞就是悲观的。乐观锁和悲观锁也大概就是这个意思。
- 无锁：在无障碍的基础上，无锁能够保证一个线程在有限的步内离开临界区。
- 无等待：在无锁的基础上要求所有的线程都必须在有限的步内完成。

并行的两个定律：
Amdahl:加速比 = 优化前系统耗时 / 优化后系统耗时
Gustafson:加速比 = （串行时间 + 并发时间）/ （串行时间 + n*并发时间) ,n代表处理器的个数
总而言之，就是加速比不仅取决于处理器的个数，还取决于并行和串行操作的比例。如果n趋近于无穷大，加速比就区域1/n

JMM内存模型（JMM）
- 原子性，指的是一个操作是不可中断的
- 可见性，值的是一个操作对共享变量进行修改，其他线程是否能够立即感知。可能造成的原因：缓存优化、硬件优化、指令重排
- 有序性，指的是编写的代码顺序和执行的代码顺序不一样，因为可能发生指令重排。指令排重的具体细节参看计算机组成原理。

## 第二章
- 线程和进程的区别，面试最常问的问题之一。
- 在Java中创建进程的两种方式：extend Thread、implements Runnable。

---
——在Java中，如何终止线程？
——使用stop()方法。stop()是一个废弃的方法，stop方法在结束线程的时候，会直接终止所有线程，并立即释放线程所有的锁，这样很容易导致数据不一致问题的出现。

---
线程中断：给线程发送一个通知，告知目标线程推出的消息。
```
public void Thread.interrupt()        //中断线程
public void Thread.isInterrupt()      //判断是否被中断
public static boolean Thread.interrupted()  //判断是否被中断，并清除当前中断状态
```

---
等待（wait)和通知（notify）
```
public final void wait() throws InterruptedException
public final native void notify()
```
一个线程调用wait()方法，该线程就会进入等待队列，这个等待队列可能会有多个线程。如果调用notify方法，就会从等待队列中随机选择一个线程唤醒。如果调用notifyAll()方法则会唤醒所有等待队列中线程。
PS：wait()方法会释放对象的锁，notify不会释放掉对象的锁。一个在Java面试中经常被问到的问题——wait()和sleep()的异同。两个方法都会让线程等待若干时间，sleep方法能够控制时间，而wait()方法无法控制时间，需要其他线程调用notify方法才唤醒。最后就是sleep不会释放掉对象的锁，而wait会释放掉对象的锁。

---

挂起（suspend）和继续执行（resume）
两者均被定义为废弃方法。线程会让线程挂起，但是并不会释放掉对象的锁，这就有可能导致死锁的出现。sleep你必须指定休眠时间，wait方法会释放掉对象的锁。所以从这个角度来看，这两个方法被废弃也不是没有道理。

---
等待线程结束（join）和谦让（yeild）
join的作用就是：t1.join()方法在哪里线程调用，哪个线程就必须等待t1线程执行完毕之后才能执行。
```
public class JoinMain {

	public volatile static int i = 0;
	
	public static class AddThread extends Thread{
		
		@Override
		public void run(){
			for(i=0;i<100000;i++);
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		AddThread at = new AddThread();
		at.start();
		at.join();
		System.out.println(i);
	}

}
```
看下面的例子，在主线程中调用join会让at线程先执行完毕，之后再打印i的值100000，如果不适用join方法最终结果很明显会是一个非常小的结果。
yield：就是当调用线程让出对CPU的占用，但不能保证CPU再次调度到该线程。

---
volatile与Java的内存模型
Java的内存模型围绕着原子性、有序性、可见性等方面展开。volatile的英文意思是易变的、不稳定的。你如果使用了volatile变量，会提示虚拟机，当某个变量容易被修改。为确保这个变量在修改之后可以对所有线程可见，你就需要用到volatile。

---
线程组就是将线程集中管理。
驻守后台：守护线程（Daemon）,一种特殊的线程，是系统的维护者，完成系统工作，如垃圾回收线程、JIT机制线程。当只存在守护线程，就代表应用程序结束了。

---
线程优先级。线程可以设置优先级，但这不一味者高优先级线程总能被提前调用，这是一个概率问题。这就好比你扔一个骰子，得到<=5的概率非常大，但是也是有可能最终结果是6.下面是设置线程优先级的范围。
```
public final static int MIN_PRIORITY = 1;
public final static int NORM_PRIORITY = 5;
public final static int MAX_PRIORITY = 10;
```

---
volatile能保证数据的可见性，但是并不能保证真正的线程安全。
```
public class AccountingVol implements Runnable{

	static AccountingVol instance = new AccountingVol();
	
	static volatile int i = 0;
	
	public static void increase(){
		i++;
	}
	
	@Override
	public void run() {
		for(int j = 0; j < 10000000; j++){
			increase();
		}
	}
	
	public static void main(String[] args) throws InterruptedException {		
		Thread t1 = new Thread(instance);
		Thread t2 = new Thread(instance);
		t1.start();
		t2.start();
		t1.join();
		t2.join();
		System.out.println(i);
	}
}
```
其实最终结果绝对不是我们想要`20000000`。因此执行次数增多，会出现我们同时读取值，并加1的结算。比如i的值为10,两个线程同时读取i=10，之后同时加1，最终结果为11,但是我们想要的结果是12。如果我们想到最终完成线程安全的设定，需要使用`synchronized`的关键字。
`synchronized`用法：
> - 为指定对象加锁，进入同步代码前，需要获得对象的锁
> - 直接作用于实例方法：相当于对当前实例加锁，进入同步代码前需要获得当前实例的锁
> - 直接作用于静态方法：对当前类加锁，进入同步代码要获得当前类的锁

对上面代码进行修改，加入`synchronized`
```
public class AccountingVol implements Runnable{

	static AccountingVol instance = new AccountingVol();
	
	static volatile int i = 0;
	
	public static void increase(){
		synchronized (instance) {
			i++;
		}
		//不加入sunchronized关键字
		//i++
	}
	
	@Override
	public void run() {
		for(int j = 0; j < 10000000; j++){
			increase();
		}
	}
	
	public static void main(String[] args) throws InterruptedException {		
		Thread t1 = new Thread(instance);
		Thread t2 = new Thread(instance);
		t1.start();
		t2.start();
		t1.join();
		t2.join();
		System.out.println(i);
	}
}
```
这样我们就能得到我们想要的结果。但是不要使用`synchronized`方法。
```
	public synchronized void increase(){
		i++
	}
```
因为在方法上使用`synchronized`是对实例对象进行加锁，但是在我们上面写的main函数中，我们是new出了两个对象，所以，这样并不能达到我们想要的结果。

---
程序中的隐藏BUG：
- 无提示的错误
- 并发下的Arraylist，可以改用线程安全的Vector
- 并发下的MapHash,在JDK8中可以使用ConcurrentHashMap代替HashMap。
- 错误的加锁。
```
public class BadLockOnInteger implements Runnable{

	public static Integer i = 0;
	
	static BadLockOnInteger instance = new BadLockOnInteger();
	
	@Override
	public void run() {
		for(int j = 0; j < 10000000;j++){
			synchronized (i) {
				i++;
			}
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		Thread t1 = new Thread(instance);
		Thread t2 = new Thread(instance);
		t1.start();
		t2.start();
		t1.join();
		t2.join();
		System.out.println(i);
	}

}
```
在上面的例子中，我么所能看到的结果并不是`20000000`，而是`11744170`。为什么?不是都加锁了吗？因为Integer是不可变对象，我们对他进行++操作，它其实会重新产生新的对象——`valueOf(i.intValue()+1)`。所以i所指向的对象会一直发生改变，所以加锁对象都发生了变回，自然谈不上线程安全了。

## 第三章

重入锁，英文`ReentrantLock`，为什么叫做重入锁？一个线程可以多次获得对象的锁，在释放的过程中我们也需要多次释放掉对象的锁。
```
lock.lock();
lock.lock();
try{
    i++;
}finally{
    lock.unlock();
    lock.unlock();
}
```
冲入锁的优势：
- 上说所提到的灵活性
- 重入锁可以提供中断处理能力。使用`lockInterruptibly`方法，这是一个可以响应中断的申请锁操作。如果线程产生中断，会放弃对锁的申请。
- 可以设定申请锁的时间。使用`lock.tryLock`方法。

---
公平锁：通常而言，我们所使用的锁都是非公平的，但是我们可以在冲入锁中设置公平性。
```
public ReentrantLock(boolean fair)
```
公平锁的优点不会产生饥饿现象，但是公平锁需要维护有序公共队列，成本就高，性能会下降。

ReentrantLock的几个重要方法：
lock()：获得锁，如果所被占用，等待
lockInterruptibly()：获得锁，但优先响应中断
tryLock()：尝试获得或，如果成功，则返回true，失败返回false。该方法不等待，立即返回。
tryLock(long time,TimeUnit unit)：在给定时间内尝试获得锁
unlock()：释放锁

---
`Object.wait()`、`Object.notify()`、和`Synchronized`关键字一起使用。而`condition`和冲入锁搭配使用。
condition接口：
```
void await() throws InterruptedException
void awaitUninterruptibly();
void awaitNanos(long nanoTimeout) throws InteruptedException;
void await(long time, TimeUnit unit) throws InterruptedException;
void await(Date deadline) throws InterruptedException;
void signal();
void signalAll();
```

---
允许多个线程同时访问：信号量（Semaphore）
信号量是对内部锁`synchronized`和重入锁`ReentrantLock`的延伸，信号量可以指定多个信号量，同时访问临界资源。
信号量的构造函数：
```
public Semaphore(int permits);
public Semaphore(int permits,boolean fair);
```
信号量的逻辑方法：
```
public void acquire(); //获取对临界资源的访问权限，获取不到则等待
public void acquireUninterrupted(); //和acquire，但是不响应中断
public boolean tryAcquire(); //尝试获取对临界资源的访问，不进行等待
public boolean tryAcquire(long timeout,TimeUnit unit);
public void release(); //释放对临界资源的占用
```

---
ReadWriteLock读写锁
`ReadWriteLock`是JDK5中提供的读写分离锁。
| 类型 | 读 | 写|
|:----:|:----:|:----:|
|读| 非阻塞| 阻塞|
|写| 阻塞 | 阻塞|
就是，我们同时在读取临界资源或者共享变量的同时，并不需要加锁。

---
倒计时：CountDownLatch。这个可以让某个线程等待直到倒计时结束，再开始执行。构造函数接受一个整数作为参数，这个参数当做计时器的计时个数。
`public CountDownLatch(int count)`

--- 
循环栅栏：CyclieBarrier。循环栅栏可以实现线程之间的计数等待，功能比CountDownLatch更加复杂强大。

---
LockSupport是一个线程阻塞工具。里面有 `pork`和`unpork`方法。

---
Guava是Google出品的Java库，里面提供了很多实用的工具类。`RateLimiter`是限流工具库。限流算法：漏桶算法和令牌桶算法。

线程池：一次性创建多个线程，将线程连接统一进行管理，需要线程去线程池中获得，线程实用完毕之后将线程重新放回到线程池中。
JDK对线程池提供了支持，即`Executor`框架，来帮助开发人员进行管理线程。
核心线程池的内部实现：`ThreadExecutor`
```
public ThreadPoolExecutor(int corePoolSize,
                              int maximumPoolSize,
                              long keepAliveTime,
                              TimeUnit unit,
                              BlockingQueue<Runnable> workQueue);
            
```
`corePoolSize`:指定了线程池中的线程数量
`maximumPoolSize`:指定了线程池中的最大线程数量
`keepAliveTime`:当当前线程数据超过`corePoolSize`时，多余的空闲线程的存活时间，即超过`corePoolSize`的空闲线程，在多长时间内会被销毁
`unit`:KeepAliveTime的时间单位
`workQueue`:任务队列，被提交尚未执行的任务
`ThreadFactory`:线程工厂，用来穿件线程，一般默认即可
`handler`:拒接策略，当任务太多来不及处理的时候，如何拒绝任务。

---
Fork和Join框架，分而治之的思想。
Guava中对线程池的扩展：
- 特殊的DirectExector线程池
- Daemon线程池
- 对Future模式的扩展

---
并发集合：
- 线程安全的HashMap。HashMap是线程不安全的，如果要获得线程安全。我们可以通过下面代码`Collections.synchronizedMap(new HashMap())`。
原理
```
private static class SynchronizedMap<K,V> implements Map<K,V>,serializable {
    private static final long serialVersionUID = 19781984382238238L;
    private final Map<K,V> m;
    final Object mutex;
}

//通过负mutex加锁，来达到我们线程安全的目的
public V get(Object key){
    synchronized (mutex){
        return m.get(key);
    }
}
```

#
使用我们可以看到的ConcurrentHashMap更加适合多线程并发的场景。

## 锁的优化及其注意事项
- 减少锁持有的时间
- 减少锁的粒度
- 用读写分离的锁替换独占锁
- 锁分离
- 锁粗化：锁粗化就是将一系列连续地对一个锁不断进行请求和释放的操作时，便会把所有的锁整合成一次请求，从而减少对所的请求同步的次数。
```
for(int i = 0; i < 1000; i++){
    synchronized(this){
    
    }
}
//对其进行优化操作
synchronized(this){
    for(int i = 0; i < 1000; i++){

    }    
}
```

这里介绍JDK内部的“锁”的优化策略。

- 锁偏向。锁偏向是一种针对锁操作的加锁行为，一个线程获得了锁，那么所就进入了偏向模型。当这个线程再次请求锁的时候，它就无需进行同步操作。竞技激烈的条件下，偏向锁会失效。
- 轻量级锁。偏向锁失败之后，虚拟机会使用一种叫做轻量级锁的优化手段。轻量锁失败，线程转入重量级锁。
- 自旋锁。在锁膨胀之后，为了避免线程真实地在操作系统层面挂起，虚拟机最最后的尝试。虚拟机会让当前线程做若干空循环，等待若干时间，如果线程能够获取锁的，那是最好。如果不行的话，线程挂起。
- 锁消除。Java虚拟机在JIT编译的时候，优化那些不可能存在竞争的锁。锁消除的一项关键技术即`逃逸技术`。所谓的逃逸技术就是观察变量是否会逃出一个作用域。

--- 
无锁，乐观机制。
在无锁的情况下，使用CAS，（Compare And Swap）来鉴别线程冲突。
CAS(V,E,N):

- V代表要更新的变量
- E代表预期值
- N代表新值

仅仅当V=E的时候，才会将V的值设置为N，如果V!=E的时候，就代表其他线程进行修改，当前线程不进行操作。大部分现代处理器已经支持了原子化的CAS指令。

在Java中，JDK中有一个名为`atomic`的包，里面直接使用了CAS操作达到了线程安全。

- atomic包
- unsafe类
- AtomicReference
- AtomicStampedReference：带时间戳的对象引用
- 数组的无锁结构
- 让普通变量也享受原子操作 


## 并发模式与算法
- 单例模式
- 不变模式
不变对象的使用场景。当对象创建之后，其内部状态和数据不再发生任何变化。对象需要被共享，被多想城频繁访问。不变模式的例子：String类，Integer类
如何设置不变模型：
1. 去除setter方法以及所有的修改自身属性的方法
2. 将所有属性设置为私有，并且用final标记，确保其不可修改
3. 确保没有子类可以修改他的行为
4. 创建一个可以完整创建对象的构造函数


- Future模式
- 并行
- NIO

## Java8/9/10与并发

函数式编程特点：

- 函数作为一等公民
- 无副作用
- 声明式（Declarative）
- 不变对象
- 更少的代码

函数式编程基础：

- FunctionalInterface注释
- 接口默认方法
- 方法引用

## 使用Akka构建高并发程序

## 并行程序调试

## 多线程优化示例

