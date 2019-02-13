# 1.Project Directory
- src -> source code
- public -> static files , like images
- node_modules -> dependencies
- package.json -> reocrds our project dependencies and configures our project


# 2.JSX
1. sytle语法不同

		<button style={{backgroundColor:'blue', color:'white'}}>
	              {buttonText}
	    </button>
	    
2. class->className for->htmlFor
	
	
		<label className="label" htmlFor="name">
	              {labelText}
	          </label>
	          
3. jSX 可以引用js变量	 

# 3.Component

> component is a function(good for simple content) or class

1. Rules of class components
	
	1. must be a js class
	2. must extent React.Component
	3. must define a 'render' method that returns some amount of JSX
	
2. state
	1. state can only be updated using the function 'setState'
	2. `this.setState({term: '1'}`只会改变term的值,不会改变state中其他属性的值

#### life cycle

1. constructor (todo 初始化数据等)
2. render (返回jsx,避免做其他事情)

	>  contnet visible on screen
3. componentDidMount (加载数据等。。)

   > sit and wait for updates..
 
4. componentDidupdate (当state/prop变化时加载数据)

   > sit and wait until this component is not longer shown
   
5. componentWillUnmount (do something clean up)

# 4.Handing Event

#### Controled element, uncontrolled element
1. flow
	
	1. user types in input
	2. callback gets invoked
	3. we call setState with the new value
	4. Component rerenders
	5. input is told what its value is(coming from state)
	
#### This
1. this在函数内的指向问题，95%指向调用此函数的对象
	- 使用箭头函数会自动绑定this到class

# 5. Axios
1. 安装
	
	> npm install --save axios

# 6. Redux

> npm install --save redux react-redux

> Action Creator -> Action -> dispatch -> Middleware(redux-thunk)-> Reducers -> State

1. 在使用redux时需要注意避免改变现有的数据,比如返回array，需要新建一个。

2. 工程目录结构

		/src 
		  /actions(contains files related to action creators)
		  /components(Files related to components)
		  /reducers(File related to reducers)
		  index.js(sets up both the react and redux sides of the app)
		  
	
3. Provide & Connect & redux-thunk

	> npm install --save redux react-redux axios redux-thunk
			
		//index.js
		import React from 'react';
		import ReactDOM from 'react-dom'
		import {Provider} from 'react-redux'
		import {createStore, applyMiddleware} from 'redux'
		import thunk from 'redux-thunk'
		
		import App from './components/App'
		import reducers from './reducers'
		
		
		const store = createStore(reducers, applyMiddleware(thunk));
		
		ReactDOM.render(
		    <Provider store={store} >
		        <App/>
		    </Provider>
		    , document.querySelector("#root")
		);
		
	
4. General Data Loading with Redux
	
	1. Component gets rendered onto screen
	2. 'componentDidMount' lifecycle method get called
	3. we call action creator from 'componentDicMount'
	4. Action creator runs code to make API request
	5. Api response with data
	6. Action creator returns an 'action' with the fetched data on 'payload' property
	7. Some reducer sees the action, returns the data off the 'payload'
	8. Because we generated some new state object, redux/react-redux cause our React app to be rerenderd;
			
	   
5. Rules of Reducers
 	
 	1. must return any value beside 'undefined'
 	2. Produces'state', or data to be used inside of you app using only previous state and the action(state 需要有默认值)
 	3. Must not return reach 'out of itself' to decide what value to return(不要在reducer中调用api,Dom操作等)
 	4. Must not mutate its input 'state' argument(返回通一个对象，react不会刷新ui，参见reudx源码)
 		
 		- `state.pop()` -> `state.filter`
 		- `state.push()` -> `[...state, 'hi']`
 		- `state[0]` -> `state.map()`
 		
 		- `state.name='Sam'` -> `{...state, name:'Sam'}`
 		- `state.age =30` -> `{...state, age:30}`
 		- `delete state.name` -> `_.omit(state, 'age')`

6. lodash
	
	> npm install --save lodash
	
	1. _.memoize

# 7.React Router

1. family
	1. `react-router` -> Core navigation lib(we don't install this manually)
	2. `react-router-dom` -> navigation for dom-based app
	3. `react-router-native` -> navigation for react-native apps
	4. `react-router-redux` -> binding between Redux and Router(not necessary)

2. 不要使用`a`标签,使用`Link`-> `<Link to='/pageTwo'>navi to page two</Link>`
		
# 8.Redux Form

> npm install --save redux-form

1. Form is initially rendered OR user interacts with it
2. validate(formValues)
3. Did the user enter vaild input?
		
	> valid
	1. renturn an empty object
	
	> not valid
	1. return error message
