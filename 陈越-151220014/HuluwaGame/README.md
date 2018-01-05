# 葫芦娃大作业简述
## 代码运行过程
* field初始化：根据field中的string，初始化葫芦娃阵营和蛇精阵营的队形，所用数据类型为ArrayList。
* 按下空格键：为不同生命体开辟一个线程，并start.
* 按下L键：开辟一个新的线程，用于读取回放文件并再现战斗过程。
* 通过mvn clean test package可以生成一个直接通过java -jar *.jar可运行的jar包。
## 各类的分工
* Player(run函数):<br>
 1. 数据成员：boolean die； int blood.<br>
 2. 运行条件：线程未中断，对象未死亡，战斗未结束(field.completed!=true)。 <br>
 3. 执行功能：判断敌方阵营是否完全死亡，若未完全死亡，则执行移动，若移动的目标上已有生命体，则放弃此次移动，移动完成后，判断战斗情况，是否被杀死，是否杀死别人，并且更新血量，将更新后的状态写入文件，并调用field类的updateField（）函数，判断游戏是否结束.<br>
 4. 重要函数：<br>
  updatePlayer(),判断战斗情况,若两者在可袭击范围，调用fight函数。<br>
  fight(Player enemy)，两者相杀，血量相同则一起死亡，否则血量少着死亡，多者血量减去一个随机数（小于血量）。<br>
  move()，移动位置，通过ishit判断目标位置是否有其他生命体。<br>
  getdirection(),通过判断距离自己最近的敌人与自己的相对位置，来生成一个相对合理的移动。<br>
  writedown()，写入文件（生物体种类，（葫芦娃小喽啰序号），x坐标，y坐标,die）。<br>
* Field：<br>
 1. 数据成员：ArrayList huluwaTeam,snakeTeam;boolean completed.<br>
 2. 执行功能：响应键盘及鼠标事件，（按下空格）在每一个生物体run一次则判断一次战斗是否结束，通过buildWorld函数绘制。<br>
 3. 重要函数：
    updateField（），判断是否结束，若有某个阵营全部死亡，则completed=true,战斗结束，关闭文件。<br>
    buildWorld(),若战斗未结束，则绘制两队（先绘制死者尸体，在绘制生者）；若战斗结束显示哪对胜利。
 * PlayBack(回放):<br>
  1.  执行功能：一行一行读取记录文件，通过读取的字符串，控制生命体的移动和生存情况。<br>
  2.  重要函数：PlayerAction(),通过生物体种类（葫芦娃和小喽啰的序号）找到对应的生物，将其moveto(x,y)，setDie(die),更新生物体状态。文件读取结束，则field.completed=true.<br>
  3.  开辟一个新的线程的原因：
    (1)避免如果只有主线程，进入循环之后，无法再响应关闭窗口事件。<br>
    (2)如果只有一个线程，repaint（）绘制无法及时刷新，若直接调用paint(g)函数需要通过双缓冲来避免闪烁，另开一个线程则可以解决这个问题。<br>
## Test
* 测试move函数是否会超出边界或者与其他生命体占用一个位置：控制field中的一个Player上下左右动十次，若超出边界或占用一个位置则fail，测试通过。<br>
* 测试fight函数结果是否正确：使用多对已知血量的Player互相fight，若结果不正确则fail，测试通过。<br>
## pom.xml
* 添加一个depenency,junit<br>
* 添加<mainClass>Main</mainClass>,明确主类位置，否则java -jar运行时会报错没有主属性。<br>
## 面向对象机制
* 继承：Huluwa,HuluGrandfather,Snake,Scropion,Littlell继承Player;Player继承Thing2D类和Runable接口。<br>
* 多态：不同阵营使用ArrayList<Player>存储，通过重载Player类的函数，不同生物体实现一些不同的动作，比如初始化，加载图片，写入记录文件等等。<br>
## 所用机制
* 异常处理：各种文件读写的异常，线程异常。<br>
* 集合类型：使用ArrayList装载Player。<br>
* 泛型：ArrayList<Player>，而Player是虚基类，其实际创建对象时为new Huluwa,new Snake等等。<br>
* 注解：使用@override重载函数，使用@Test,@BeforeClass,@AfterClass,定义测试类。<br>
* 输入输出：使用FileWriter,FileReader和BufferedReader,用于记录文件的读写。<br>
 ## 多线程间的协同
  在Player的Run函数中，使用synchronized(Player.class)对移动和更新战局加锁。<br>
