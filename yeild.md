# Java中yield

yield英文含义为：投降、屈服。按照JDK中注释：
> A hint to the scheduler that the current thread is willing to yield its current use of a processor. The scheduler is free to ignore this hint.

中文意思：提示调度者，当前线程想放弃对CPU的占用，但是调度者可能会忽略。