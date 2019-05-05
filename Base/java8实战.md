## 1.JAVA8
1. 核心思想

	- 将方法和Lambda作为一等值(可以作为参数传递)
	- 在没有可变的共享对象时,函数或方法可以有效安全的并行执行

2. `java.util.stream.Collectors`

## 2.通过行为参数化传递代码
1. 筛选苹果	
	1. 筛选绿苹果--->定义一个方法
	2. 新需求筛选红苹果--->把颜色作为参数
	3. 新需求筛选重量--->新增加参数
	4. 抽象筛选条件---> `ApplePredicate`
		
			public interface ApplePredicate() {
				boolean test (Apple apple);
			}
			
			filterApples(List<Apple> inventory, ApplePredicate p);
			
	5. 使用匿名类
	6. 使用lambda表达式
	7. 将List类抽象化,泛型


2. Comparator

		inventory.sort((Apple a1, Apple a2) -> a1.getWeight().compareTo(a2.getWeight()))
		

3. Runnable

	 
	 	Thread T = new Thread(()->System.out.println("Hello world")).start();

## 3.Lambda表达式

1. `(paramters) -> expression` 或 `(paramters) -> {statements;}`

2. 函数式接口: 只定义一个抽象方法的接口。 例如`Runnable`
3. 接口用`@FunctionalInterface`标注
4. `java.util.function.Predicate<T>`
		
	- 定义了test方法
	- 接受泛型T对象,并返回一个boolean, eg:`filter`

5. `java.util.function.Consumer<T>	`
	
	- 定义了accept方法
	- 接口泛型T对象,无返回。eg:`forEach`

6. `java.util.function.Function<T, R>`
	
	- 定义了apply方法
	- 接收泛型T，返回泛型R。 eg: `map`

7. IntPredicate避免了装箱操作,提升性能

8. Lambda可以捕获实例变量和静态变量,但局部变量必须为final才能捕获

		//以下代码编译器会报错
		int portNumber = 1337;
		Runnable r = () -> System.out.println(portnNumber)
		portNumber = 3777;
		

9. 方法引用
	
	1. 指向静态方法
	2. 指向任意类型示例方法
	3. 指向现有对象的实例方法

			(Apple a) -> a.getWeight() // Apple::getWeight
			()-> Thread.currentThread().dumpStack()//Thread.currentThread()::dumpStack
		 	(str, i)-> str.substring(i) //String::substring
			(String s) -> System.out.println(s) //System.out::println
			
10. 谓词复合 顺序从左往右
	
	1. negate

			Predicate<Apple> notRed = redApple.negate()
	
	2. and
			
			Predicate<Apple> redAndHeavy = redApple.and((a)-> a.getWeight() > 150);
	
	3. or

			Predicate<Apple> redAndHeavyOrGreen = redApple.and((a)-> a.getWeight() > 150).or(a-> "green".equals(a.getColor()));
			
11. 函数复合
		
		
		 Function<Integer, Integer> f = x -> x + 1;
        Function<Integer, Integer> g = x -> x * 2;
        
        Function<Integer, Integer> andThen = f.andThen(g);
        Function<Integer, Integer> compose = f.compose(g);
        
        //g(f(x)) 先调用f后g
        andThen.apply(1);
        //f(g(x)) 先调用g后f
        compose.apply(1);

##4. 流
1. 流只能遍历一次，遍历完以后说明被消费调了。流中的元素是按需计算的
2. 中间操作
	1. filter
	2. map
	3. limit、skip 互补
	4. sorted
	5. distinct
	6. flatmap:各个数组不会分别映射成一个流,而是映射成流的内容
3. 终端操作
	1. forEach
	2. count
	3. collect
	4. reduce

4. 查找和匹配
	1. allMatch  -> boolean
	2. anyMatch -> boolean
	3. noneMatch -> boolean
	4. findFirst -> Optional<T>
	5. findAny -> Optional<T>

5. 使用`IntStream,DoubleStream,LongStream`避免装箱成本

		int calories = memu.stream()
							.mapToInt(Dish::getCalories)
							.sum().

6. 创建流`Stream.of`
	
	1. 由值创建
	
			Stream<String> stream = Stream.of("JAVA 8", "in");
	
	2. 	由数组创建

			int[] numbers = {2, 3, 4, 5};
			int sum = Arrays.stream(numbers).sum();
			
	3. 由文件生成

			long uniqueWords = 0;
	        try (Stream<String> lines = Files.lines(Paths.get("data.txt"), Charset.defaultCharset())){
	          uniqueWords =  lines.flatMap(line -> Arrays.stream(line.split(" ")))
	                    .distinct()
	                    .count();
	        } catch (IOException e) {
	            
	        }


	4. 由函数生成:创建无限流: `Stream.iterate`, `Stream.generate` 一般配合`limit`使用
		 	
		 	//斐波那契数列
		 	Stream.iterate(new int[]{0, 1}, t -> new int[]{t[1], t[0] + t[1]})
                .limit(10)
                .map(t-> t[0])
                .forEach(System.out::println);
        

## 5.用流收集数据
1. toList --> List<T>
2. toSet --> Set<T>
3. toCollection --> Collection<T>
4. counting --> Long
5. summingInt --> Integer
6. averagingInt --> Double
7. summarizingInt --> IntSummaryStatistics 统计如最大,最小,平均
8. joining --> String
9. maxBy --> Optional<T>
10. minBy --> Optional<T>
11. reducing --> 归约操作产生的类型
12. collectingAndthen --> 转换函数返回的类型
13. groupingBy --> Map<K, List<T>>
14. partitioningBy --> Map<Boolean, List<T>> 

## 6.并行流
1. 测试顺序流和并行流的速度,再进行选择
2. 注意装箱！使用(IntStream, LongStream, DoubleStream)避免装箱操作
3. limit,findFirst再并行流上代价十分大。注意避免
4. 对于较小的数据量，避免使用并行流
5. `ArrayList,IntStream.range`使用并行流极佳。 `HashSet,TreeSet`好，`LinkedList, Stream.iterate`差

## 7.默认方法
1. 接口中添加`default`修饰符表明此方法是默认方法
2. 类或者父类种声明的方法优先级高于任何接口中的默认方法
		
## 8.Optional
1. 创建
	
	1. Optional.empty()
	2. Optional.of() 如果对象为null,抛出异常
	3. Optional.ofNullable()

2. Optional提供一个map方法,用于对象转换
3. 在返回为Optional<T>的时候可以使用flatmap进行调用
			
		//这里Person::getCar返回的是一个Optional<Car>类型,如果使用map,结果就是一个Optional<Optional<Car>>类型。 所以这里要使用flatmap
		person.flatMap(Person::getCar)
            .flatMap(Car::getInsurance)
            .map(Insurance::getNmae)
            .orElse("Unknown")
 
4. map.get的时候使用Optional.ofNullable包裹
 			
 			
 		Optional.ofNullable(map.get("key"));
 
5. 不推荐使用基础类型的Optional,例如`OptionalInt`, 因为这些没有map，filter等方法

## 9.CompletableFuture
1. `supplyAsync()`
			
		public class CompletableFutureTest {
		    public static void main(String[] args) {
		        Shop shop = new Shop("testShop");
		        CompletableFuture<Double> future = CompletableFuture
		                .supplyAsync(()->shop.calculatePrice("test"))
		                .whenComplete(((r, e) -> System.out.println("result:" + r)));
		
		        System.out.println("等待执行结果");
		        future.join();
		    }
		}
2. 如果是计算密集型任务,使用stream并行流。 如果是IO密集型,使用CompletableFuture并创建线程池。