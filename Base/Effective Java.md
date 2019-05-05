## 1.创建和销毁对象

#### 1. 用静态工厂方法代替构造器
#### 2. 遇到多个搞糟参数时考虑使用构造器

>  构建build类

#### 3.用私有构造器或枚举类强化Sigleton

> 使用枚举实现单例

	public enum Elvis {
    INSTANCE;
    
    public void  leaveTheBuilding(){}
	}
	
#### 4.通过私有构造器强化不可实例化的能力

> 工具类只包含静态属性方法，不应被实例化
	
	public class UtilityClass {
	    /**
	     * Suppress default constructor for noninstantiability
	     */
	    private UtilityClass() {
	        throw new AssertionError();
	    }
	}	
	
#### 5. 优先考虑依赖注入来引用资源

#### 6. 避免创建不必要的对象

> 循环中避免使用包装类型

#### 7.消除过期对象的引用

#### 8.避免使用finalizer和cleaner方法

#### 9.try-with-resources 优先于 try-finally
		
	try (BufferedReader br = new BufferedReader(new FileReader("path"))){
        br.readLine();
    } catch (IOException e) {
        e.printStackTrace();
    }

## 2.通用方法

#### 10. 覆盖equals时请遵守通用约定
1. 使用 == 检测引用是否相同
2. 使用 instanceof检查 类型是否正确
3. 把参数转为正确类型
4. 检测是否匹配

* 覆盖equals时总是覆盖hashCode
* 不要将equals声明中的Object对象替换为其他的类型

#### 11. 覆盖euqals时总要覆盖hashCode

#### 12. 始终要覆盖 toString

#### 13. 谨慎地覆盖 clone

- 不可变的类永远都不应该提供 clone 方法
- 这条规则的例外是数组 最好利用clone方法赋值数组

#### 14. 考虑实现Comparable接口

- 实现了`comparable`接口 可以调用`Arrays.sort(a)`进行排序

## 3. 类和接口

#### 15. 使类和成员的可访问性最小化
#### 16. 在公有类而非公有域中使用访问方法
#### 17. 使可变性最小化
#### 18. 复合优先于继承
#### 19. 要么设计继承并提供接口文档， 要么禁止继承
#### 20. 接口优于抽象类
#### 21. 为后代设计接口
1. java8 可添加 default implementation

#### 22. 接口只用于定义类型
1. 接口中不要添加常量

#### 23. 类层次优于标签类
#### 24. 静态成员类优于非静态成员类
1. 4种嵌套类,只为外围类提供服务
	1. 静态成员类 (若无外围引用,最好使用静态)
	2. 非静态成员类
	3. 匿名类
	4. 局部类
#### 25. 限制源文件为单个顶级类
1. 一个源文件种只定义一个类

## 4.泛型

#### 26. 不要使用原生态类型
1. 使用List一定要加上类型参数`List<Object> a = new ArrayList();`
2. Set<Objcet>是个参数化类型, Set<?>则是一个通配符类型，表示智能包含某种未知对象类型的一个集合

#### 27. 消除非受检警告
1. 先消除警告,不能消除的只有在确保类型安全的情况下最小范围内使用`SuppressWarnings("unchecked")`

#### 28. 列表优于数组
1. 使用泛型的时候不要使用数组`int[]`， 使用列表`List<integer>`

#### 29. 优先考虑泛型
#### 30. 优先考虑泛型方法
#### 31. 利用有限制通配符来提升API灵活性
#### 32. 谨慎并用泛型和可变参数
#### 33. 优先考虑类型安全的异构容器

## 5.枚举和注解
> Java支持两种特殊用途的引用类型:一种是类`enum type`，一种是接口`annotation type`

#### 34. 用enum代替int常量
> 枚举如果只是被用在一个特定的顶层类种,它应该成为一个顶层类的成员类

#### 35. 用实例域代替序数
 	
 	public enum Ensemble {
 		SOLO(1), DUTE(11)
 	}

#### 36. 用EnumSet代替位域		
	//do not use it
	public static  final  int STYLE_BOLD = 1 << 0;
	
	public class Text {
	
	
	    public enum Style {BOLD, ITALIC, UNDERLINE, STRIKETHROUGH}
	    
	    public void applyStyles(Set<Style> styleSet) {
	        
	    }
	
	    public static void main(String[] args) {
	        new Text().applyStyles(EnumSet.of(Style.BOLD, Style.ITALIC));
	    }
	}

#### 37.用EnumMap代替序数索引
#### 38.用接口模拟可扩展的枚举
#### 39.使用注解,不要使用命名模式
#### 40.坚持使用Override注解
#### 41.用标记接口定义类型
1. 标记接口不包含方法声明,例如`Serializable`

## 6.Lambda和Stream
#### 42.Lambda优于匿名类

#### 43.优先使用方法引用而不是Lambda表达式
> 方法引用和Lambda哪个简洁用哪个

1. 静态
		
		//方法引用
		Integer::parseInt 
		//Lambad表达式
		str->Integer.parseInt(str)

2. 有限制
		
		//方法引用
		Instant.now()::isAfter
		//Lambad表达式
		Instant then = Instant.now();
		t -> then.isAfter(t)

3. 无限制
		
		//方法引用
		String::toLowerCase
		//Lambad表达式
		str->str.toLowerCase()
		

4. 类构造器
		
		//方法引用
		TreeMap<K,V>::new
		//Lambad表达式
		()-> new TreeMap<K,V>
		

3. 数组构造器
		
		//方法引用
		int[]::new
		//Lambad表达式
		len-> new int[len]
		
#### 44.坚持使用标准的函数接口

