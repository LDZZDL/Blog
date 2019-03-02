# Java面试题全集（上）

参考该博主的[Java面试题全集（上）](https://blog.csdn.net/jackfrued/article/details/44921941),加上自己的一些自己的理解。


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

















