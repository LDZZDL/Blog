# Java面试题全集（上）

参考该博主的[Java面试题全集（上）](https://blog.csdn.net/jackfrued/article/details/44921941),[Java面试题全集（中）](https://blog.csdn.net/jackfrued/article/details/44931137)，[Java面试题全集（下）](https://blog.csdn.net/jackfrued/article/details/44931161)加上自己的一些自己的理解。


## 1.面向对象有哪些特性？
1. 抽象性。抽象是对包含相同特征的对象，提取出相同属性和行为的过程。
2. 封装性。封装是隐藏类的属性和行为，对外屏蔽所有具体细节，只对外提供简单的编程接口（比如将类的属性全部设为private，只提供get方法进行访问）。
3. 继承性。继承是利用现有类（别名：基类、超类、子类）创建新类（别名：子类、派生类）的过程。
4. 多态性。多态性是允许不同子类型的对象对同一消息作出不同的响应。方法重载（overload）实现的是编译时的多态性（也称为前绑定），而方法重写（override）实现的是运行时的多态性（也称为后绑定）。
方法重载（overload）是指在同一个类中，一个名字相同的方法，参数列表不同。
方法重写（override）是指在父类和子类中，子类和父类有相同名字、相同参数列表、相同返回值的方法
```java
class Man extends Person{
	//重写
	public void eat(){
		System.out.println("Man is eating food");
	}
}
public class Person {
	//重载
	public void eat(){}
    //重载
	public void eat(String food){}
	//重载
	public void eat(String food, String drink){}
	
	public static void main(String[] args) {
		Person person = new Man();
		person.eat();
	}
}
```
PS：Java的三大特性为封装、继承、多态

## 2.访问修饰符public,private,protected,以及不写（默认）时的区别？
| 修饰符 | 当前类 | 同包 | 子类 | 其他包 |
| :------: | :------: | :------: | :------: | :------: |
| public | √ | √ | √ | √ |
| protected | √ | √ | √ | × |
| default | √ | √ | × | × |
| private | √ | × | × | × |

PS:形状像是一个倒三角形

## 3.String（Integer） 是最基本的数据类型吗？
很明显不是，基本数据类型就8种，int,long,float,double,boolean,char,byte,short。基本数据类型哪里有像String包装类这么多的现成方法可以直接使用

## 4.float f=3.4;是否正确？ 
Java默认是double，所以系统会进行下转型（down-casting）。可以写成float f =3.4F避免系统的转换

## 5.short s1 = 1; s1 = s1 + 1;有错吗?short s1 = 1; s1 += 1;有错吗？
没有，在Java中数字默认是int类型，但是Java会自动进行类型转换


## 6.Java有没有goto？ 
goto是Java的保留字，但是没有使用

## 7.int和Integer有什么区别？ 
简单说，int是基本数据类型，Integer是类。

| 基本数据类型 | short | int | long | float | double | char | byte | boolean |
| :------: | :------: | :------: | :------: | :------: | :------: | :------: | :------: | :------: |
| 包装类型 | Short | Integer | Long | Float | Double | Character | Byte | Boolean |

下面几个相关的题目：
```java
class AutoUnboxingTest {

    public static void main(String[] args) {
        Integer a = new Integer(3);
        Integer b = 3;                  // 将3自动装箱成Integer类型
        int c = 3;
        System.out.println(a == b);     //false,两个引用没有引用同一对象
        System.out.println(a == c);     //true,a自动拆箱成int类型再和c比较
    }
}
```

## 8.&和&&的区别？ 
&的作用：1.按位与 2.逻辑与
&&的作用： 1.逻辑与

&和&&的逻辑与的区别：&&的左边的表达式的值是false，右边的表达式会被直接短路掉
```java
//假设str == null,只会进行str != null的比较，不会进行str.equals("xxx")的比较操作
if(str != null && str.equals("xxx"){
    
}

//假设str == null,进行str != null和str.equals("xxx")的比较操作，最终导致NullPointerException异常
if(str != null & str.equals("xxx"){
    
}
```

## 9.Java的栈和堆
栈：基本数据类型、局部变量、函数调用的现场保存
堆：new关键字和构造器创建的对象

在下面的例子中，栈中存放arr变量,堆中存放int[3]数组
```java
int [] arr = new int[3]
```

## 10.Math.round(11.5)等于多少？Math.round(-11.5)等于多少？
Math.round(11.5)的返回值是12，Math.round(-11.5)的返回值是-11。四舍五入的原理是在参数上加0.5然后进行下取整。

## 11.switch 是否能作用在byte 上，是否能作用在long上，是否能作用在String上？
java5之前：byte、short、char、int
Java之后：enum
java7之后：String
所以，你如果使用的是Java7之上的版本，是可以作用到String上的
```java
	public static void main(String[] args) {
		String result = "90";
		switch (result) {
		case "90":
			System.out.println(90);
			break;
		case "80":
			System.out.println(80);
			break;
		default:
			break;
		}
	}
```

## 12.用最有效率的方法计算2乘以8？ 
左移三位

## 13.数组有没有length()方法？String有没有length()方法？
```java
	public static void main(String[] args) {
		int [] arr = new int[3];
		System.out.println(arr.length);
		
		String str = "Hello World";
		System.out.println(str.length());
	}
```

## 14.在Java中，如何跳出当前的多重嵌套循环？
使用`break;`

## 15.构造器（constructor）是否可被重写（override）？
构造器不能被继承，因此不能被重写，但可以被重载。

## 16.两个对象值相同(x.equals(y) == true)，但却可有不同的hash code，这句话对不对？
不对

## 17.是否可以继承String类？ 
不行，String是final类

## 18.当一个对象被当作参数传递到一个方法后，此方法可改变这个对象的属性，并可返回变化后的结果，那么这里到底是值传递还是引用传递？ 
1.如果是传递的参数是基本数据类型（int,long,float,double,boolean,char,byte,short），是值传递，因为根本没有改变原来的传入参数的值
```java
	public static void main(String[] args) {
		int a = 1, b = 5;
		System.out.println("a="+a+", b="+b); //a=1, b=5
		swap(a,b);
		System.out.println("a="+a+", b="+b); //a=1, b=5
	}
	//交换a和b的值
	public static void swap(int a, int b){
		int temp = a;
		b = a;
		a = temp;
	}
```
2.先看下面的代码
```java
	public static void main(String[] args) {
		StringBuffer sb = new StringBuffer("Hello ");
        System.out.println("before change:"+sb.toString());
        change(sb);
        System.out.println("after change:"+sb.toString());
	}
	public static void change(StringBuffer stringBuffer){
		stringBuffer.append("World");
	}
```
最终结果：
`before change:Hello `
`after change:Hello World`

如果是参数是对象，实际上传的是引用

## 19.String和StringBuilder、StringBuffer的区别？ 
String是只读字符串（字符串常量），内容无法被修改，里面并没有存在像append()的方法
StringBuilder、StringBuffer是可以修改的字符串，两者的区别是
StringBuffer 是线程安全的；StringBuilder 是非线程安全的。

参考链接：[深入理解Java中的String](https://www.cnblogs.com/xiaoxi/p/6036701.html)

## 20.重载的方法能否根据返回类型进行区分？
不能，重载是根据参数列表的类型和数量，根据返回值无法判定调用哪个方法,就比如下面的例子
```java
long f(){}

int f() {}
```

## 21.描述一下JVM加载class文件的原理机制？ 
略

## 22.char 型变量中能不能存贮一个中文汉字，为什么？ 
可以

## 23.抽象类（abstract class）和接口（interface）有什么异同？
同：抽象类和接口都不能实例化，需要对其抽象方法进行实现
异：接口只能定义public的抽象方法，抽象类可以定义属性、抽象方法（修饰符不限）、具体方法（修饰符不限）。


## 24.静态嵌套类(Static Nested Class)和内部类（Inner Class）的不同？ 
略

## 25.Java 中会存在内存泄漏吗，请简单描述
理论上来说存在GC，不存在内存泄漏，但是......

## 26.抽象的（abstract）方法是否可同时是静态的（static）,是否可同时是本地方法（native），是否可同时被synchronized修饰？ 
抽象方法需要子类重写，而静态的方法是无法被重写的，因此二者是矛盾的。

## 27.阐述静态变量和实例变量的区别
静态类变量属于类，全部类的实例均能够访问得到，只存在一份内存拷贝；实例变量属于实例，只有通过实例才能访问到

## 28.是否可以从一个静态（static）方法内部发出对非静态（non-static）方法的调用？
不行，静态方法只能访问静态树形，因为非静态变量可能需要实例来进行初始化

## 29、如何实现对象克隆？ 
1). 实现Cloneable接口并重写Object类中的clone()方法； 
2). 实现Serializable接口，通过对象的序列化和反序列化实现克隆，可以实现真正的深度克隆

## 30、GC是什么？为什么要有GC？
GC（Garbage Collections）垃圾回收，程序员自己手动管理内存申请和释放经常会出错。

## 31.String s = new String("xyz");创建了几个字符串对象？ 
两个对象，一个是方法区中String常量池"xyz"，一个是在堆中new的对象

## 32.接口是否可继承（extends）接口？抽象类是否可实现（implements）接口？抽象类是否可继承具体类（concrete class）？
接口可以继承接口，而且支持多重继承。抽象类可以实现(implements)接口，抽象类可继承具体类也可以继承抽象类。

## 33.一个".java"源文件中是否可以包含多个类（不是内部类）？有什么限制？
可以，但是只能拥有一个修饰符为public的类

## 34.Anonymous Inner Class(匿名内部类)是否可以继承其它类？是否可以实现接口？
可以继承其他类或实现其他接口，在Swing编程和Android开发中常用此方式来实现事件监听和回调。

## 35.内部类可以引用它的包含类（外部类）的成员吗？有没有什么限制？
一个内部类可以访问他的创建他的外部类

## 36.Java 中的final关键字有哪些用法？ 
(1)修饰类：表示该类不能被继承；(2)修饰方法：表示方法不能被重写；(3)修饰变量：表示变量只能一次赋值以后值不能被修改（常量）。


## 37.构造器，构造代码块，静态代码块的执行顺序
无继承：静态代码块--->普通代码块--->构造器
有继承: 父类静态代码块--->子类静态代码块--->父类普通代码块--->子类普通代码块--->子类构造器

## 38.数据类型之间的转换： 
略

## 39.如何实现字符串的反转及替换？
```java
    public static String reverse(String originStr) {
        if(originStr == null || originStr.length() <= 1) 
            return originStr;
        return reverse(originStr.substring(1)) + originStr.charAt(0);
    }
```

## 40.怎样将GB2312编码的字符串转换为ISO-8859-1编码的字符串？ 
略

## 41.日期和时间： 
- 如何取得年月日、小时分钟秒？ 
- 如何取得从1970年1月1日0时0分0秒到现在的毫秒数？ 
- 如何取得某月的最后一天？ 
- 如何格式化日期？
略

## 42.打印昨天的当前时刻。 
略

## 43.比较一下Java和JavaSciprt
略
## 44.什么时候用断言（assert）？ 
断言是一种软件开发中的调试方式，如果assert中出现error,会抛出AssertionError

## 45.Error和Exception有什么区别？ 
- Error表示系统级的错误和程序不必处理的异常
- 需要程序处理的错误，通常是设计或者实现问题引起的

## 46.try{}里有一个return语句，那么紧跟在这个try后的finally{}里的代码会不会被执行，什么时候被执行，在return前还是后? 
答：会执行，在方法返回调用者前执行。

## 47.阐述final、finally、finalize的区别
- final是修饰符，可以用在类、方法、属性上
- finally：通常放在try…catch…的后面构造总是执行代码块
- finalize，可以通过它，在GC之前，做一些必要的清理操作

## 48.Thread类的sleep()方法和对象的wait()方法都可以让线程暂停执行，它们有什么区别? 
答：sleep()方法（休眠）是线程类（Thread）的静态方法，调用此方法会让当前线程暂停执行指定的时间，将执行机会（CPU）让给其他线程，但是对象的锁依然保持，因此休眠时间结束后会自动恢复（线程回到就绪状态，请参考第66题中的线程状态转换图）。wait()是Object类的方法，调用对象的wait()方法导致当前线程放弃对象的锁（线程暂停执行），进入对象的等待池（wait pool），只有调用对象的notify()方法（或notifyAll()方法）时才能唤醒等待池中的线程进入等锁池（lock pool），如果线程重新获得对象的锁就可以进入就绪状态。

## 49.当一个线程进入一个对象的synchronized方法A之后，其它线程是否可进入此对象的synchronized方法B？
不能

## 50.JSP和Servlet是什么？
```java
public interface Servlet {
    void init(ServletConfig var1);
    ServletConfig getServletConfig();
    void service(ServletRequest var1, ServletResponse var2)；
    String getServletInfo();
    void destroy();
}
```
简而言之，Servlet就是编程接口，但是和我们通常所说的Servlet并不是这个编程接口，而是如下面所示的情况。
```java
public class Servlet1 extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }
}
```
通过我们所说的Servlet是特殊的Java程序，能够处理来自客户端的Http请求。
JSP本质上也是servlet，只不过JSP更专注于前端页面的，Servlet更专注于业务流程的控制，在经典的MVC结构中，Servlet通常充当控制器C，JSP重当视图V

## 51.Servlet、Listener、Filter优先级
Servlet、Listener、Filter的加载顺序和在web.xml中的顺序不一样，Listener>Filter>Servlet

## 52.ServletContext、Session、request、PageContext
- ServletContext：在应用服务器被加载之后，立刻创建ServletContext的对象，存在于整个Web应用
- Session：第一次调用request.getSession()创建，因为1.超时 2.主动销毁 session.invalidate() 3.意外销毁，比如关闭服务器。Session存在于整个会话范围
- request：开始于request对象的创建,销毁于request的销毁，作用范围整个请求链
- PageContext：开始于JSP页面创建，销毁与JSP页面销毁


## 53.Web.xml的作用
1.可以用来配置首页或者欢迎页面
```
<welcome-file-list>
    <welcome-file>index1.jsp</welcome-file>
    <welcome-file>index2.jsp</welcome-file>
    <welcome-file>index3.jsp</welcome-file>
    <welcome-file>index4.jsp</welcome-file>
</welcome-file-list>
```
2.可以用来配置error-page,根据Http状态码来配置错误页面
```
<error-page>
    <error-code>404</error-code>
    <location>error-404.jsp</location>
</error-page>
```
3.配置Servlet、Filter、Listener，或者可以使用@WebServlet、@@WebFilter、WebListener注解进行配置
4.配置context-param,在，这个键值对
```
<context-param>
    <param-name>code</param-name>
    <param-value>UTF-8</param-value>
</context-param>
```

## 54.什么是Web Service（Web服务）？ 
简而言之，Web Service就是API接口，通过接口，可以获取所需要的信息。比如查快递的Web Service、查天气的Web Service、查汇率的Web Service，网上也有很多网站提供付费的Web Service

## 54.什么是ORM？
从字面上来看，ORM是Object-Relational Mapping，即对象关系映射，解决面向对象模型和数据库模型之间匹配的问题，通过XML或者注解的方式，将关系数据库中的模型转换成Java对象

## 55.持久层设计要考虑的问题有哪些？你用过的持久层框架有哪些？ 
持久就是将数据存储到可掉电式存储设备，需要的考虑的问题：
- 对外提供抽象的数据访问接口，屏蔽实现逻辑
- 对内需要将实现逻辑和业务逻辑分离，能够实现可迁移性，比如动态切换数据库
- 在持久化层实现统一的资源调度
- 数据抽象，提供更面向对象的数据操作
常用框架：Hibernate MyBatis













