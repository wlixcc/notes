
# MAC 下开发
1. [官方文档](https://blog.jetbrains.com/clion/2019/02/clion-2019-1-eap-clion-for-embedded-development-part-iii/)
2. [参考文档](https://zhuanlan.zhihu.com/p/63672432)
3. [配置](https://www.jetbrains.com/help/clion/2019.2/embedded-development.html?utm_campaign=CL&utm_medium=link&utm_source=product&utm_content=2019.2)

### 1.流程
1. 安装编译器 

		brew tap ArmMbed/homebrew-formulae
		brew install arm-none-eabi-gcc
2. 安装openocd `brew install openocd`
3. 安装stm32cubemx 官方下载后 `sudo java -jar SetupSTM32CubeMX-4.22.0.exe`
4. 安装st-link `brew install stlink`  

安装完成后到 `Settings/Preferences | Build, Execution, Deployment | Embedded Development` 测试openocd 和STM32CUbeMX 路径配置是否正确 。 brew install openocd的路径可能需要重新配置(eg:`/usr/local/Cellar/open-ocd/0.10.0/bin/openocd`)

### 2.解决clion无法烧写的情况 [参考资料](http://news.eeworld.com.cn/mcu/ic464732.html)
	 1. stm32cubemx -> Pinout & configuration -> System Core -> degbug -> serial wire
	 2. 在windows下烧写一次程序
	 3. clion可以正常编译烧写

### 3.HAL库 LED例程  [参考](http://news.eeworld.com.cn/mcu/ic468386.html)

1. 首先必备的SYS和RCC(reset clock control)选择外部晶振、配置LED引脚
2. 在clock configuration中配置HCLK 72MHz
3. 配置GPIO

### 4.STM32CUBEMX使用
1. 使用clion创建工程,选择stm32cubemx。
2. 选择芯片型号, 例: f103ze
3. Pinout->SYS->Debug->Serial wire
4. 配置时钟 Pinout->RCC->HSE 选择Crystal/Ceram Resonator
5. Clock Configuration选择HES并配置HCLK 为72MHZ
6. 根据原理图配置GPIO
7. Project Manager -> toolchain/IDE -> 选择SW4STM32


### 5.中断
1. [文档](https://simonmartin.ch/resources/stm32/dl/)
2. [优先级设置](https://stackoverflow.com/questions/53899882/hal-delay-stuck-in-a-infinite-loop)


### 6.串口通信
1. clion 安装插件 serial prot monitor
2. [视频](https://www.youtube.com/watch?v=xE6GVt7XuJI)
3. 中断

### 7.DMA
1. 

# WINDOWS

### 1.流程
1. 使用keil5 mdk5
2. 新建工程,选择对应的芯片。取消`Manage Run-Time Environment `
3. 添加启动文件

### 2.ST-LINK配置
1. Debug里选择st-link
2. Utilities->settings->勾选reset and run 