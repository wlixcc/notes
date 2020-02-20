# 1.知识点

1. react
2. typescript
3. ant design pro
4. umi
5. dva -基于redux和redux-saga
	1. 在redux-saga中,你可以直接dispath action。 和`redux-thunk`有一点区别

			import { createStore, applyMiddleware } from 'redux';
			import createSagaMiddleware from 'redux-saga'
			import {watchAgeUp} from "./sagas/saga";
			
			const sagaMiddleware = createSagaMiddleware();
			const store = createStore(reducer, applyMiddleware(sagaMiddleware));
			sagaMiddleware.run(watchAgeUp);	
	
6. 项目中import中使用`@`eg:`import PageLoading from '@/components/PageLoading';` 在`tsconfig.json`中定义了paths

		//https://stackoverflow.com/questions/43281741/how-to-use-paths-in-tsconfig-json
		"paths": {
	      "@/*": ["./src/*"]
	    }

7. [配置文件](https://umijs.org/zh/guide/config.html#%E9%85%8D%E7%BD%AE%E6%96%87%E4%BB%B6) `config/config.ts`。 
	1. 项目中使用的是umi的[配置式路由](https://umijs.org/zh/guide/router.html#%E9%85%8D%E7%BD%AE%E5%BC%8F%E8%B7%AF%E7%94%B1) 

8. [icon](https://ant.design/components/icon-cn/#%E8%87%AA%E5%AE%9A%E4%B9%25)

9. [dva-loading](https://www.jianshu.com/p/61fe7a57fad4)

10. router

	1.[基于 action 进行页面跳转](https://github.com/dvajs/dva-knowledgemap#%E5%9F%BA%E4%BA%8E-action-%E8%BF%9B%E8%A1%8C%E9%A1%B5%E9%9D%A2%E8%B7%B3%E8%BD%AC)
			
			import { routerRedux } from 'dva/router';

			// Inside Effects
			yield put(routerRedux.push('/logout'));
			
			// Outside Effects
			dispatch(routerRedux.push('/logout'));
			
			// With query
			routerRedux.push({
			  pathname: '/logout',
			  query: {
			    page: 2,
			  },
			});
	
	
	2. 页面跳转	

				import { router } from 'umi';
				router.push('/recipe/list');
		
11. [配置环境变量](https://github.com/umijs/umi/issues/1397)

	1. 在`config/config.ts`中定义环境变量
			
			const { IK_ENV } = process.env;
			
			  define: {
			    IK_ENV: IK_ENV || 'prod',
			  },
			  
	2. 使用，项目中直接读取。

			    console.log(IK_ENV)
 	
 	3. 可以在`package.json`设置启动时候的环境变量

12. chrome 可以安装 `redux dev tool`

# 2.起步

1. [ant design pro](https://pro.ant.design/docs/getting-started-cn)
2. 安装yarn
3. 创建空文件作为项目目录,cd到目录下运行`yarn create umi`
4. 完成后运行`yarn`安装依赖包 & `yarn start` 即可看到页面
5. 依据[文档](https://pro.ant.design/docs/environment-variables-cn)删除项目中的一些代码, 全局搜索`ANT_DESIGN_PRO_ONLY_DO_NOT_USE_IN_YOUR_PRODUCTION `并且删除
6. `src/components/GlobalHeader/RightContent.tsx`中删除一些不用的组件,如语言选择等
7. `src/assets`中替换logo
8. 在`BasicLayout.tsx`和`UserLayout.tsx`中移除footer

# 3.登录注册逻辑接入

1. 修改SecurityLayout中逻辑,未登陆状态下跳转到登陆页面
2. 

# 4.添加页面
1. 在`config/config.ts`中配置好路由
2. 在`src/pages/xx`中添加`index.tsx`
3. 在`data.d.ts`添加数据类型

		export interface RecipeStepDataType {
		  text: string;
		  time: number;
		  power: number;
		  temperature: number;
		}
		
		export interface RecipeListItemDataType {
		  id: number;
		  bytes: [number];
		  name: string;
		  steps: [RecipeStepDataType];
		  type: 'MANUAL' | 'RECORD';
		  tags: [string];
		  access: 'PRIVATE' | 'PUBLIC';
		}
	
4. 添加`service`	
		
		import request from '@/utils/request';

		interface ParamsType {
		  category: string
		}
		
		export async function queryList(params: ParamsType) {
		  return request('/recipes', {
		    params,
		  })
		}

		
5. 添加dva`model`


		import { Effect } from 'dva';
		import { Reducer } from 'redux';
		
		import { RecipeListItemDataType } from '@/pages/recipe/recipe-list/data';
		import { queryList } from '@/pages/recipe/recipe-list/service';
		
		export interface StateType {
		  list: RecipeListItemDataType[];
		}
		
		export interface ModelType {
		  namespace: string;
		  state: StateType;
		  effects: {
		    fetch: Effect;
		  };
		  reducers: {
		    queryList: Reducer<StateType>;
		  };
		}
		
		const Model: ModelType = {
		  namespace: 'recipeList',
		
		  state: {
		    list: [],
		  },
		
		  effects: {
		    *fetch({ payload }, { call, put }) {
		      const response = yield call(queryList, payload);
		      yield put({
		        type: 'queryList',
		        payload: Array.isArray(response) ? response : [],
		      });
		    },
		  },
		
		  reducers: {
		    queryList(state, action) {
		      return {
		        ...state,
		        list: action.payload,
		      };
		    },
		  },
		};
		
		export default Model;

6. 在`index.tsx`中定义props类型和state类型

		interface RecipeListProps extends FormComponentProps {
		  listBasicList: StateType;
		  dispatch: Dispatch<any>;
		  loading: boolean;
		}
		
		interface RecipeListState {
		  visible: boolean;
		  done: boolean;
		  current?: Partial<RecipeListItemDataType>;
		}
		
		
7. 在
		
## x.服务端cors配置
1. implements WebMvcConfigurer

		@Configuration
		@EnableWebSecurity
		@EnableGlobalMethodSecurity(prePostEnabled = true)
		public class WebSecurityConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {

	    /**
	     * 配置cors
	     */
	    @Override
	    public void addCorsMappings(CorsRegistry registry) {
	        registry.addMapping("/**")
	        .allowedMethods("*")
	        .allowedHeaders("*")
	        .allowedOrigins("*");
	    }


	    @Override
	    protected void configure(HttpSecurity http) throws Exception {
	        // Disable CSRF (cross site request forgery)
	        http.csrf().disable();
	
	        // No session will be created or used by spring security
	        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // Entry points ！！！CORS
        http.cors().and().authorizeRequests()
                .antMatchers("/users/signin").permitAll()
                .antMatchers("/users/signup").permitAll()
                .antMatchers("/users/root").permitAll()
                .antMatchers("/users/password").permitAll()
                .antMatchers("/sms/**").permitAll()
                .antMatchers("/actuator/**").permitAll()
                .antMatchers("/ele/callback").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
		    }
		
		}
