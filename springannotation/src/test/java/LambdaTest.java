import org.junit.Test;

import java.util.Comparator;
import java.util.function.Consumer;

/**
 * lambda表达式本质：作为接口的实例（依赖于函数式接口）
 */
public class LambdaTest {


    //lambda语法格式一：无参数，无返回值
    @Test
    public void test01() {

        Runnable r1 = new Runnable() {
            public void run() {
                System.out.println("我爱北京天安门");
            }
        };
        r1.run();
        System.out.println("*****************************************************");

        Runnable r2 = () -> System.out.println("天安门上红旗升");
        r2.run();
    }

    //lambda语法格式二：有参数，无返回值
    @Test
    public void test02() {
        Consumer<String> consumer1 = new Consumer<String>() {

            @Override
            public void accept(String s) {
                System.out.println(s);
            }
        };
        consumer1.accept("五星红旗迎风飘扬");

        System.out.println("*****************************************************");

        Consumer<String> consumer2 =(String s) -> System.out.println(s);
        consumer2.accept("胜利歌声多么嘹亮");

    }


    //lambda语法格式三：数据类型可以省略，因为编译器能推断出，称为“类型推断”
    //                  如果只需要一个参数，那么参数的小括号也可以省略
    @Test
    public void test03() {
        Consumer<String> consumer1 = new Consumer<String>() {

            @Override
            public void accept(String s) {
                System.out.println(s);
            }
        };
        consumer1.accept("五星红旗迎风飘扬");

        System.out.println("*****************************************************");

        Consumer<String> consumer2 = s -> System.out.println(s);
        consumer2.accept("胜利歌声多么嘹亮");

    }


    //lambda语法格式四：两个或以上的且有返回值
    @Test
    public void test04() {
        Comparator<Integer> comparator1 = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
            }
        };
        System.out.println(comparator1.compare(1,2));

        System.out.println("************************************************");

        Comparator<Integer> comparator2 = (o1,o2) -> {
            System.out.println(o1);
            System.out.println(o2);
            return o1.compareTo(o2);
        };
        System.out.println(comparator2.compare(3,2));
    }


    //lambda语法格式四：当右边的lambda体只有一条执行语句时return和大括号可以省略
    @Test
    public void test05() {
        Comparator<Integer> comparator1 = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
            }
        };
        System.out.println(comparator1.compare(1,2));

        System.out.println("************************************************");

        Comparator<Integer> comparator2 = (o1,o2) -> o1.compareTo(o2);
        System.out.println(comparator2.compare(3,2));
    }
}
