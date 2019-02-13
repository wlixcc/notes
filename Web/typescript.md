### 1. Variable

1. 基本数据类型
	1. `number`
	2. `boolean`
	3. `string`

2. `null`,`undefined`可以赋值给任何类型的变量
3. 数组定义
	1. `let list1: number[] = [1 ,2 ,3]`
	2. `let list2: Array<number> = [1, 2, 3]`
4. 元组
	1. `let tuple: [string, number] = ['Chris', 2]`

5. 枚举
	
		enum Color {Red, Green, Blue} //0,1,2
		let c: Color = Color.Green
		
6. any

		let myVariable: any = 10;
        myVariable = 'string'

7. unknown (不能调用方法，不能读取属性)
       
       
       	let myVariable: unknown = 10;
       (myVariable as string).toUpperCase()
		
### 2. Function

		//?表示num2参数可以忽略
     function add(num1: number, num2?:number):number {
     	return num1 + num2
     }
     
       function add(num1: number, num2:number = 30):number {
     	return num1 + num2
     }
### 3. Interface

    interface Persion {
    	firstName: string;
    	lastName?: string;
    }     
    
    function fullName(person:Persion) {
    	console.log(persion)
    }

### 4. Class


	//can use public | private
	class Employee {
		private employeeName: string
		
		constructor(name: string) {
			this.employeeName = name;
		}
	
	}