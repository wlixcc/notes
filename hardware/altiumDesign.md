### 1.整体流程
1. 创建pcb工程
2. 绘制原理图
3. 创建pcb文件,
4. design->update PCB Document 根据原理图生成pcb


### 2.修改封装
1. Tool->footprint Manager
2. 选中需要修改封装的器件->edit
3. 弹出框中选择any
4. 可以浏览,选择自己需要的封装类型
5. 修改完成后点击Accept changes

### 3.修改pcb版大小(AD19)
1. place->keepout-track。画出想要的图形,ctrl+a全选, design->board shape -> define from selected objects

### 4.PCB布线
1. Route ->interactive routing(ctrl+w) 
2. 一般使用10mil. 更粗的安全性更高
3. 布线不能走直角
4. 红色线表示在顶层，蓝色线表示在底层。 pcb板的正反两面

### 5.规则检测
1. pcb布线完成后进行规则检查。tool-> design rule check -> run design rule check
2. 根据问题修复

### 6.PCB Library
1. 在工程中添加pcb library
2. 依据图纸绘制
3. 调整焊盘的尺寸单位 -> ctrl+q
4. 孔尺寸决定好了以后，一般外形+1mm就可以
5. 然后使用栅格来调整尺寸,点击gg输入尺寸，拖动焊盘放置在正确位置。 
6. 上下微调，点击m移动 选择x/y便宜


### 7.导出光绘文件
1. file -> Fabrication outputs
2. 选择layers -> Plot Layers -> 点击 Used on   选出所有使用的层
3. include unconnected mid-layer pads 打勾 保险起见
4. 取消所有 Mechanical layers....中的勾
5. Drill Drawing ->先确保所有勾不选中

1. 第二次输入 layers -> Plot Layers -> all off 全部不选中
2. 选中mechanical 1
3. Drill Drawing 全部选择上

1. file -> Fabrication outputs - NC Drill Files
2. 选中supress leading zeroes
3. 确认-确认



### 小提示
1. pcb图中的细线是电路连接的辅助线,非实际连线
2. 布局首先考虑重要的接口、传感器的位置分布
3. 调整网格大小 按g
4. 尺寸测量 reports->mesure distance
5. 
