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