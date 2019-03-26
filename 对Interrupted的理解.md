# 对Interrupted的理解
Interrupt在JDK中的注释
>    /**
     * Interrupts this thread.
     *
     * <p> Unless the current thread is interrupting itself, which is
     * always permitted, the {@link #checkAccess() checkAccess} method
     * of this thread is invoked, which may cause a {@link
     * SecurityException} to be thrown.
     *
     * <p> If this thread is blocked in an invocation of the {@link
     * Object#wait() wait()}, {@link Object#wait(long) wait(long)}, or {@link
     * Object#wait(long, int) wait(long, int)} methods of the {@link Object}
     * class, or of the {@link #join()}, {@link #join(long)}, {@link
     * #join(long, int)}, {@link #sleep(long)}, or {@link #sleep(long, int)},
     * methods of this class, then its interrupt status will be cleared and it
     * will receive an {@link InterruptedException}.
     *
     * <p> If this thread is blocked in an I/O operation upon an {@link
     * java.nio.channels.InterruptibleChannel InterruptibleChannel}
     * then the channel will be closed, the thread's interrupt
     * status will be set, and the thread will receive a {@link
     * java.nio.channels.ClosedByInterruptException}.
     *
     * <p> If this thread is blocked in a {@link java.nio.channels.Selector}
     * then the thread's interrupt status will be set and it will return
     * immediately from the selection operation, possibly with a non-zero
     * value, just as if the selector's {@link
     * java.nio.channels.Selector#wakeup wakeup} method were invoked.
     *
     * <p> If none of the previous conditions hold then this thread's interrupt
     * status will be set. </p>
     *
     * <p> Interrupting a thread that is not alive need not have any effect.
     *
     * @throws  SecurityException
     *          if the current thread cannot modify this thread
     *
     * @revised 6.0
     * @spec JSR-51
     */
     
     
中文翻译（一部分）：
中断线程，线程可以自己中断自己，在调用`interrupt`方法的时候，可能会抛出`SecurityException`。调用`wait`、`sleep`、`join`会导致线程处于阻塞状态，之后会状态清楚，会出现`InterruptedException`异常。
上面的意思就说：如果我们在调用了`wait`、`sleep`、`join`方法的时候，想终止线程，需要在异常处理方法中进行处理。
```
		while(true){
			try {
				Thread.sleep(100000000);
			} catch (InterruptedException e) {
				break;
			}
		}
```
如果我们想要终止正在执行的线程状态，我们需要使用`Thread.currentThread().isInterrupted()`方法来获得当前线程是中断状态，对他进行处理。
```
public class Test1 implements Runnable{

	@Override
	public void run() {
		while(!Thread.currentThread().isInterrupted()){
			System.out.println("thread is running");
		}
	}
	
	public static void main(String [] args) throws InterruptedException{
		Thread t1 = new Thread(new Test1());
		t1.start();
		Thread.sleep(5000);
		t1.interrupt();
	}
}
```

`interrupted()`：将线程中断之后，并清除中断状态
`isInterrupted()`：仅仅返回线程中断的状态





