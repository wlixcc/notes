## 1. Core Data stack

1. NSManagedObjectModel -> database schema
2. entity -> table
3. NSManagedObjectContext **
	1. in-memory scratchpad for objects
	2. 几乎所有工作都是在NSManagedObjectContext进行的
	3. 直到调用`save()`方法才存储到硬盘中
	
4. NSPersistentStore
		
	1. NSQLiteStoreType 
	2. NSXMLStoreType `macos only`
	3. NSBinaryStoreType `rarely`
	4. NSInMemoryStoreType
	5. 可以自定义存储数据类型,继承NSIncrementalStore
5. NSPersistentStoreCoordinator -> the bridge between the managed object model and the persistent store

6. NSPersistentContainer (iOS 10) -> 持有上诉所有对象


## 2.Fetching
1. 在`xcdatamodeld`中长按`add entity`按钮选择`add fetch request`
		
		//获取fetch request
		  [self.managedContext.persistentStoreCoordinator.managedObjectModel fetchRequestTemplateForName:@"FetchReuqest"];

2. 可以设置`NSFetchRequest.resultType`决定返回值类型

3. 使用`NSSortDescriptor`进行排序，这个排序是在sqlite level, 效率比较高

4. Asynchronous fetching


## 3.keypoint

#### 1.Allows External Storage

1. 只有type为data时才有效
2. When you enable Allows External Storage, Core Data heuristically decides on a per-value basis if it should save the data directly in the database or store a URI that points to a separate file.


#### 2.Transformable
1. 需要遵守`NSCoding`协议


#### 3.Entity+CoreDataClass &Entity+CoreDataProperties
1. xcode重新生成类文件时，不会替换CoreDataClass,所以可以把自己的方法写在此处.不用担心文件被重写。


#### 4.xcdatamodel & momd
1. xcdatamodel编译后会生成momd文件
2. Core Data uses the compiled contents of the momd folder to initialize an NSManagedObjectModel at runtime