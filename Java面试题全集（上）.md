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

















