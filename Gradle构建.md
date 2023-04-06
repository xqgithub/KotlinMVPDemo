## Android Gradle学习(一)：Gradle基础入门

传送门：https://www.jianshu.com/p/e26236943dd6

### 一.build.gradle脚本

Gradle构建脚本默认的名字是build.gradle，当在shell中执行gradle命令时，Gradle会去当前目录下寻找名字是build.gradle的文件。

在Gradle中一个原子性的操作叫做task，简单理解为task是Gradle脚本中的最小可执行单元

```groovy
task helloWorld {
    doLast {
        println "Hello World!"
    }
}

task helloWorld2 << {
    println "Hello World2!"
}
```

这里列出几个常用的命令：

- 执行一个task：gradle taskName，如上例中的 gradle helloWorld
- 获取脚本中所有的task：gradle tasks --all
- 减少执行时的杂音，增加 -q 选项，例如：gradle -q tasks --all

### 二.Gradle 构建生命周期

#### 3个不同的生命周期阶段：初始化、配置、执行

- 初始化（Initialization）

  Gradle为每个项目创建一个Project实例，在多项目构建中，Gradle会找出哪些项目依赖需要参与到构建中。

- 配置（Configuration）

  执行所有项目的构建脚本，也就是执行每个项目的build.gradle文件。这里需要注意的是，task里的配置代码也会在这个阶段执行。

- 执行（Execution）

  Gradle按照依赖顺序依次执行task。

## Android Gradle学习(二)：如何创建Task

一个 Task 是 Gradle 里项目构建的原子执行单元，Gradle 通过将一个个Task串联起来完成具体的构建任务，每个 Task 都属于一个 Project

### 一.在Gradle里定义Task

在 build.gradle 里可以通过 task 关键字来创建Task：



```groovy
task myTask
task myTask { configure closure }
task myTask(type: SomeType)
task myTask(type: SomeType) { configure closure }
```

创建2个task

```groovy
task myTask1 {
    println "configure task1"
}

task myTask2 {
    println "configure task2"
}
```

执行其中一个task：gradle myTask1

```groovy
> Configure project :
configure task1
configure task2
```

上面定义了2个 task ：myTask1、myTask2，但是当我们执行 myTask1 时，发现2个 task 括号内部的代码都被执行了。括号内部的代码我们称之为配置代码，在 gradle 脚本的配置阶段都会执行，也就是说不管执行脚本里的哪个任务，所有 task 里的配置代码都会执行

### 二.Task Actions

一个 Task 是由一序列 Action (动作)组成的，当运行一个 Task 的时候，这个 Task 里的 Action 序列会按顺序依次执行。

前面例子括号里的代码只是配置代码，它们并不是 Action ，Task 里的 Action 只会在该 Task 真正运行时执行，Gralde 里通过 doFirst、doLast 来为 Task 增加 Action

- doFirst：task执行时最先执行的操作
- doLast：task执行时最后执行的操作

```groovy
task myTask1 {
    println "configure task1"
}

task myTask2 {
    println "configure task2"
}

myTask1.doFirst {
    println "task1 doFirst"
}

myTask1.doLast {
    println "task1 doLast"
}

myTask2.doLast {
    println "task2 doLast
}
```

运行 gradle myTask1

```groovy
> Configure project :
configure task1
configure task2

> Task :myTask1
task1 doFirst
task1 doLast
```

### 三.doLast等价操作

doLast有一种等价操作叫做leftShift，leftShift可以缩写为 << ，下面几种写法效果是一模一样

```groovy
myTask1.doLast {
    println "task1 doLast"
}

myTask1 << {
    println "task1 doLast<<"
}

myTask1.leftShift {
    println "task1 doLast leftShift"
}
```

<font color="#ff6600">需要注意的是 << 操作符，它只是一种 Gradle 里的语法糖而已，不要被这种写法迷惑了</font>

### 四.创建Task的几种常见写法

```groovy
task myTask1 {
    doLast {
        println "doLast in task1"
    }
}

task myTask2 << {
    println "doLast in task2"
}

//采用 Project.task(String name) 方法来创建
project.task("myTask3").doLast {
    println "doLast in task3"
}

//采用 TaskContainer.create(String name) 方法来创建
project.tasks.create("myTask4").doLast {
    println "doLast in task4"
}

project.tasks.create("myTask5") << {
    println "doLast in task5"
}
```

Gradle太灵活了，个人觉得记住最常用的即可，看到类似写法能看懂就行了

### 五.创建Task的参数介绍



| 参数名          | 含义                       | 默认值                             |
| --------------- | -------------------------- | :--------------------------------- |
| name            | task的名字                 | 必须指定，不能为空                 |
| type            | task的父类                 | 默认值为org.gradle.api.DefaultTask |
| overwrite       | 是否替换已经存在的同名task | false                              |
| group           | task所属的分组名           | null                               |
| description     | task的描述                 | null                               |
| dependsOn       | task依赖的task集合         | 无                                 |
| constructorArgs | 构造函数参数               | 无                                 |



看看 type 参数怎么个用法，在 Gradle 中通过 task 关键字创建的 task，默认的父类都是 org.gradle.api.DefaultTask，这里定义了一些 task 的默认行为

```kotlin
//自定义Task类，必须继承自DefaultTask
class SayHelloTask extends DefaultTask {
    
    String msg = "default name"
    int age = 18        

    //构造函数必须用@javax.inject.Inject注解标识
    @javax.inject.Inject
    SayHelloTask(int age) {
        this.age = age
    }

    //通过@TaskAction注解来标识该Task要执行的动作
    @TaskAction
    void sayHello() {
        println "Hello $msg ! age is ${age}"
    }

}

//通过constructorArgs参数来指定构造函数的参数值
task hello1(type: SayHelloTask, constructorArgs: [30])

//通过type参数指定task的父类，可以在配置代码里修改父类的属性
task hello2(type: SayHelloTask, constructorArgs: [18]) {
        //配置代码里修改 SayHelloTask 里的字段 msg 的值
    msg = "hjy"
}
```

## Android Gradle学习(三)：Task进阶学习

### 一. Task类图

- Gradle 所说的 Task 是 org.gradle.api.Task 接口，默认实现是 org.gradle.api.DefaultTask 类
- 只需关注 Task 接口即可，从这里基本可以了解到 Task 有哪些特性

### 二. Task接口解析

```kotlin
class SayHelloTask extends DefaultTask {
    
    String msg = "default name";
    int age = 20

    @TaskAction
    void sayHello() {
        println "Hello $msg ! Age is ${age}"
    }

}

task test1 << {
    println "task test1 exec..."
}
task test2 << {
    println "task test2 exec..."
}
task test3 << {
    println "task test3 exec..."
}
task hello(type: SayHelloTask, group: "MyGroup")

//对task进行配置，
hello.configure {
    println "hello task configure"
    msg = "hjy"
}

//获取task的名称
println "task name is ${hello.getName()}"
//获取task的组名
println "task group is ${hello.getGroup()}"

//设置task里的属性值，设置 age = 70
hello.setProperty("age", 70)
//获取task里的某个属性值
println "task msg is ${hello.property('msg')}"

//设置依赖的task，只有test1 task执行完后才会执行hello task
hello.dependsOn(test1)
//设置终结者任务，执行完hello task之后会执行test2 task，通常可以用该方法做一些清理操作
hello.finalizedBy(test2)

//如果同时执行hello、test3这2个task，会确保test3执行完之后才执行hello这个task，用这个来保证执行顺序
hello.setMustRunAfter([test3])

//设置满足某个条件后才执行该task
hello.setOnlyIf {
    //只有当 age = 70 时，才会执行task，否则不会执行
    return hello.property("age") == 70
}
```

执行任务gradle hello test3，结果如下

```kotlin
> Configure project :
hello task configure
task name is hello
task group is MyGroup
task msg is hjy

> Task :test3
task test3 exec...

> Task :test1
task test1 exec...

> Task :hello
Hello hjy ! Age is 70

> Task :test2
task test2 exec...
```

### 三. TaskContainer接口解析

TaskContianer 是用来管理所有的 Task 实例集合的，可以通过 Project.getTasks() 来获取

```groovy
org.gradle.api.tasks.TaskContainer接口：
//查找task
findByPath(path: String): Task
getByPath(path: String): Task
getByName(name: String): Task
withType(type: Class): TaskCollection
matching(condition: Closure): TaskCollection

//创建task
create(name: String): Task
create(name: String, configure: Closure): Task 
create(name: String, type: Class): Task
create(options: Map<String, ?>): Task
create(options: Map<String, ?>, configure: Closure): Task

//当task被加入到TaskContainer时的监听
whenTaskAdded(action: Closure)
```

创建 task 的方法

```groovy
//当有task创建时
getTasks().whenTaskAdded { Task task ->
    println "The task ${task.getName()} is added to the TaskContainer"
}

//采用create(name: String)创建
getTasks().create("task1")

//采用create(options: Map<String, ?>)创建
getTasks().create([name: "task2", group: "MyGroup", description: "这是task2描述", dependsOn: ["task1"]])

//采用create(options: Map<String, ?>, configure: Closure)创建
getTasks().create("task3", {
    group "MyGroup"
    setDependsOn(["task1", "task2"])
    setDescription "这是task3描述"
})
```

查找 task 的方法

```groovy
//通过名字查找指定的task
def task3 = getTasks().findByName("task3")
println "findByName() return task is " + task3

def taskList = getTasks().withType(DefaultTask)
def count = 0
//遍历所有的task，打印出其名字
taskList.all { Task t ->
    println "${count++} task name is ${t.name}"
}
```

### 四.Task增量构建

- Gradle 支持一种叫做 up-to-date 检查的功能，也就是常说的增量构建。Gradle 的 Task 会把每次运行的结果缓存下来，当下次运行时，会检查输出结果有没有变更，如果没有变更则跳过运行，这样可以提高 Gradle 的构建速度
- 一个 task 会有一些输入(inputs)和一些输出(outputs)，task 的输入会影响其输出结果

#### 1.TaskInputs、TaskOutputs

一个增量构建必须至少指定一个输入、一个输出

```groovy
task test1 {
    //设置inputs
    inputs.property("name", "hjy")
    inputs.property("age", 20)
    //设置outputs
    outputs.file("$buildDir/test.txt")

    doLast {
        println "exec task task1"
    }
}

task test2 {
    doLast {
        println "exec task task2"
    }
}
```

连续2次运行task，执行命令gradle test1 test2

```groovy
//第一次的运行结果
> Task :test1
exec task task1

> Task :test2
exec task task2

BUILD SUCCESSFUL in 0s
2 actionable tasks: 2 executed

//第二次的运行结果
> Task :test2
exec task task2

BUILD SUCCESSFUL in 0s
2 actionable tasks: 1 executed, 1 up-to-date
```

第2次运行时，test1 task 并没有运行，而是被标记为 up-to-date，而 test2 task 则每次都会运行，这就是典型的增量构建

#### 2.taskInputs、taskOutputs注解

| 注解名             | 属性类型                          | 描述                     |
| ------------------ | --------------------------------- | ------------------------ |
| @Input             | 任意Serializable类型              | 一个简单的输入值         |
| @InputFile         | File                              | 一个输入文件，不是目录   |
| @InputDirectory    | File                              | 一个输入目录，不是文件   |
| @InputFiles        | Iterable<File>                    | File列表，包含文件和目录 |
| @OutputFile        | File                              | 一个输出文件，不是目录   |
| @OutputDirectory   | File                              | 一个输出目录，不是文件   |
| @OutputFiles       | Map<String, File>或Iterable<File> | 输出文件列表             |
| @OutputDirectories | Map<String, File>或Iterable<File> | 输出目录列表             |



```groovy
class SayHelloTask extends DefaultTask {
    
    //定义输入
    @Input
    String username;
    @Input
    int age

    //定义输出
    @OutputDirectory
    File destDir;

    @TaskAction
    void sayHello() {
        println "Hello $username ! age is $age"
    }

}

task test(type: SayHelloTask) {
    age = 18
    username = "hjy"
    destDir = file("$buildDir/test")
}
```

## Android Gradle学习(四)：Project详解

每一个 build.gradle 脚本文件被 Gradle 加载解析后，都会对应生成一个 Project 对象，在脚本中的配置方法其实都对应着 Project 中的API

### 一.Project类图

当构建进程启动后，Gradle基于build.gradle中的配置实例化org.gradle.api.Project类

### 二.getter/setter属性

```groovy
File bd = getBuildDir()
println "buildDir = ${bd.getAbsolutePath()}"

//获取Project的名字
String name = getName()
println "project name = $name"

//设置Project的描述信息
setDescription "这是一个测试案例"
String desc = getDescription()
println "project description = $desc"

//获取Project的路径
String path = getPath();
println "project path = $path"

class VersionInfo {
    String version
    boolean release

    VersionInfo(String v, boolean release) {
        version = v
        this.release = release
    }

    String toString() {
        return "V-${version}-${release ? 'release' : 'debug'}"
    }
}
//设置Project的版本号，参数可以是任何对象，gradle内部会使用 toString() 方法返回的值
setVersion(new VersionInfo("1.0.0", true))
println("project version = ${getVersion()}")

//设置Project的分组
setGroup "TestGroup"
println("project group = ${getGroup()}")
```

直接执行 gradle 命令，可以看到在配置阶段输出以下结果

```groovy
> Configure project :
rootDir = /Users/hjy/Desktop/gradle
buildDir = /Users/hjy/Desktop/testgradle/build
project name = gradle
project description = 这是一个测试案例
project path = :
project version = V-1.0.0-release
project group = TestGroup
```

### 三.文件操作

#### 1.通过mkdir创建目录

```groovy
File mkDir = mkdir("${buildDir}/test");
File mkDir2 = mkdir("${buildDir}/test2")
println "检测目录是否创建成功：${mkDir.exists()}, ${mkDir2.exists()}"
```

#### 2.通过file、files 定位文件

```groovy
//定位单个文件，参数可以是相对路径、绝对路径
File testDir = file("${buildDir}/test")
println "文件定位是否成功：${testDir.exists()}"

//文件集合，Gradle里用 FileCollection 来表示
FileCollection fileCollection = files("${buildDir}/test", "${buildDir}/test2")
println "-------对文件集合进行迭代--------"
fileCollection.each {File f ->
    println f.name
}
println "-------文件迭代结束-------"
//获取文件列表
Set<File> set = fileCollection.getFiles()
println "文件集合里共有${set.size()}个文件"
```

#### 3.通过fileTree创建文件树

Gradle里用 ConfigurableFileTree 来表示文件树，文件树会返回某个目录及其子目录下所有的文件，不包含目录

```groovy
//先在build目录下创建3个txt文件
file("${buildDir}/t1.txt").createNewFile()
file("${buildDir}/test/t2.txt").createNewFile()
file("${buildDir}/t1.java").createNewFile()

//1.通过一个基准目录创建文件树，参数可以是相对目录，也可以是绝对目录，与file()方法一样
println "通过基准目录来创建文件树"
ConfigurableFileTree fileTree1 = fileTree("build")
//添加包含规则
fileTree1.include "*.txt", "*/*.txt"
//添加排除规则
fileTree1.exclude "*.java"
fileTree1.each { f ->
    println f    
}

//2.通过闭包来创建文件树
println "通过闭包来创建文件树"
ConfigurableFileTree fileTree2 = fileTree("build") {
    include "*/*.txt", "*.java"
    exclude "*.txt"
}
fileTree2.each { f ->
    println f    
}

//3.通过map配置来创建文件树，可配置的选项有：dir: ''、include: '[]、exclude: []、includes: []、excludes: []
println "通过Map来创建文件树"
def fileTree3 = fileTree(dir: "build", includes: ["*/*.txt", "*.java"])
fileTree3 = fileTree(dir: "build", exclude: "*.java")
fileTree3.each { f ->
    println f    
}
```

#### 4.复制文件

复制文件需要使用复制任务（Copy）来进行，它需要指定要复制的源文件和一个目标目录，复制的规则都是定义在 [CopySpec](https://links.jianshu.com/go?to=https%3A%2F%2Fdocs.gradle.org%2Fcurrent%2Fjavadoc%2Forg%2Fgradle%2Fapi%2Ffile%2FCopySpec.html) 接口里的

```groovy
task testCopyFile(type: Copy) {
    //复制build目录下的所有文件
    from "build"
    //复制单独的某个文件
    from "test.java"
    //复制某个文件树下的所有文件
    from fileTree("build")

    include "*.txt"
    include "*.java"
    exclude "t1.txt"
    //指定目标目录
    into "outputs"

    //对复制的文件重命名：通过闭包来映射
    rename { fileName ->
        //增加 rename_ 前缀
        return fileName.endsWith(".java") ? "rename_" + fileName : fileName
    }

    //通过正则来映射文件名：abctest.java 会映射成 abchjy.java
    rename '(.*)test(.*)', '$1hjy$2'
}
```

#### 5.删除文件

```groovy
//删除 build 目录下所有文件
delete("${buildDir}")
```

### 四.多项目构建

Gradle 就是通过 settings.gradle 来进行多项目构建的

#### 1.通过settings.gradle 引入子项目

```groovy
include ":app", ":library"
```

- Gradle 在运行时会读取并解析 settings.gradle 文件，生成一个 [Settings](https://links.jianshu.com/go?to=https%3A%2F%2Fdocs.gradle.org%2Fcurrent%2Fjavadoc%2Forg%2Fgradle%2Fapi%2Finitialization%2FSettings.html)对象，然后从中读取并解析子项目的 build.gradle 文件，然后为每个 build.gradle 文件生成一个 Project 对象，进而组装一个多项目构建出来
- Settings 里最核心的API就是 <font color="#ff6600">include</font> 方法，通过该方法引入需要构建的子项目。

#### 2.项目配置

在根项目里可以对子项目进行配置：

```groovy
//通过path定位并获取该 Project 对象
project(path: String): Project
//通过path定位一个Project，并进行配置
project(path: String, config: Closure): Project

//针对所有项目进行配置
allprojects(config: Closure)
//针对所有子项目进行配置
subprojects(config: Closure)
```

```groovy
println "-----root file config-----"

//配置 app 项目
project(":app") {
    ext {
        appParam = "test app"
    }
}

//配置所有的项目
allprojects {
    ext {
        allParam = "test all project"
    }   
}

//配置子项目
subprojects {
    ext {
        subParam = "test sub project"
    }
}

println "allParam = ${allParam}"
```

### 五.构建脚本配置

#### 1.buildscript

配置该 Project 的构建脚本的 classpath，在 Andorid Studio 中的 root project 中可以看到

```groovy
buildscript {
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath 'com.android.tools.build:gradle:3.0.1'
    }
}
```

#### 2.apply

```groovy
apply(options: Map<String, ?>)
```

通过该方法使用插件或者是其他脚本，options里主要选项有:

- from： 使用其他脚本，值可以为 Project.uri(Object) 支持的路径
- plugin：使用其他插件，值可以为插件id或者是插件的具体实现类

```groovy
//使用插件，com.android.application 就是插件id
apply plugin: 'com.android.application'
//使用插件，MyPluginImpl 就是一个Plugin接口的实现类
apply plugin: MyPluginImpl

//引用其他gradle脚本，push.gradle就是另外一个gradle脚本文件
apply from: './push.gradle'
```

### 六.属性

#### 1.Gradle属性

在与 build.gradle 文件同级目录下，定义一个名为 gradle.properties 文件，里面定义的键值对，可以在 Project 中直接访问。

```groovy
//gradle.properties里定义属性值
company="hangzhouheima"
username="hjy"
```

build.gradle 文件里可以这样直接访问

```groovy
println "company = ${company}"
println "username = ${username}"
```

#### 2.扩展属性

通过 ext 命名空间来定义属性，我们称之为扩展属性

```groovy
ext {
  username = "hjy"
  age = 30
}

println username
println ext.age
println project.username
println project.ext.age
```

## Android Gradle学习(五)：Extension详解

### 一.什么是Extension

```groovy
android {
    compileSdkVersion 26
    defaultConfig {
        applicationId "com.hm.iou.thinapk.demo"
        minSdkVersion 19
        targetSdkVersion 26
        versionCode 1
        versionName "1.0"
        testInstrumentationRunner "android.support.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        release {
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
        }
    }
}
```

这个 android 打包配置，就是 Gradle 的 Extension，翻译成中文意思就叫扩展。它的作用就是通过实现自定义的 Extension，可以在 Gradle 脚本中增加类似 android 这样命名空间的配置，Gradle 可以识别这种配置，并读取里面的配置内容

### 二.怎么定义Extension

#### 1.ExtensionContainer

通过 ExtensionContainer 来创建 Extension，这个类与 TaskContainer 命名有点类似，TaskContainer 是用来创建并管理 Task 的，而 ExtensionContainer 则是用来创建并管理 Extension 的

```groovy
ExtensionContainer getExtensions()
```

#### 2.简单的Extension

```groovy
//先定义一个普通的java类，包含2个属性
class Foo {
    int age
    String username

    String toString() {
        return "name = ${username}, age = ${age}"
    }
}
//创建一个名为 foo 的Extension
getExtensions().create("foo", Foo)

//配置Extension
foo {
    age = 30
    username = "hjy"
}

task testExt << {
    //能直接通过 project 获取到自定义的 Extension
    println project.foo
}
```

#### 3.ExtensionContainer主要API功能及用法

##### (1).创建Extension

```groovy
<T> T create(String name, Class<T> type, Object... constructionArguments)
<T> T create(Class<T> publicType, String name, Class<? extends T> instanceType, Object... constructionArguments)
```

- publicType：创建的 Extension 实例暴露出来的类类型；

- name：要创建的Extension的名字，可以是任意符合命名规则的字符串，不能与已有的重复，否则会抛异常；

- instanceType：该Extension的类类型；

- constructionArguments：类的构造函数参数值

  

  ```groovy
  //父类
  class Animal {
      
      String username
      int legs
  
      Animal(String name) {
          username = name
      }
      
      void setLegs(int c) {
          legs = c
      }
  
      String toString() {
          return "This animal is $username, it has ${legs} legs."
      }
  }
  
  //子类
  class Pig extends Animal {
      
      int age
      String owner
  
      Pig(int age, String owner) {
          super("Pig")
          this.age = age
          this.owner = owner
      }
  
      String toString() {
          return super.toString() + " Its age is $age, its owner is $owner."
      }
  
  }
  
  //创建的Extension是 Animal 类型
  Animal aAnimal = getExtensions().create(Animal, "animal", Pig, 3, "hjy")
  //创建的Extension是 Pig 类型
  Pig aPig = getExtensions().create("pig", Pig, 5, "kobe")
  
  animal {
      legs = 4    //配置属性
  }
  
  pig {
      setLegs 2   //这个是方法调用，也就是 setLegs(2)
  }
  
  task testExt << {
      println aAnimal
      println aPig
      //验证 aPig 对象是 ExtensionAware 类型的
      println "aPig is a instance of ExtensionAware : ${aPig instanceof ExtensionAware}"
  }
  ```

  运行 testExt 这个任务

  ```groovy
  This animal is Pig, it has 4 legs. Its age is 3, its owner is hjy.
  This animal is Pig, it has 2 legs. Its age is 5, its owner is kobe.
  aPig is a instance of ExtensionAware : true
  ```

  

##### (2).增加Extension

前面的 create() 方法会创建并返回一个 Extension 对象，与之相似的还有一个 add() 方法，唯一的差别是它并不会返回一个 Extension 对象

```groovy
void add(Class<T> publicType, String name, T extension)
void add(String name, T extension)
```

```groovy
getExtensions().add(Pig, "mypig", new Pig(5, "kobe"))
mypig {
    username = "MyPig"
    legs = 4
    age = 1
}
task testExt << {
    def aPig = project.getExtensions().getByName("mypig")
    println aPig
}
```

##### (3).查找Extension

```groovy
Object findByName(String name)
<T> T findByType(Class<T> type)
Object getByName(String name)       //找不到会抛异常
<T> T getByType(Class<T> type)  //找不到会抛异常
```

#### 4.嵌套Extension

形式上就是外面的 Extension 里面定义了另一个 Extension，这种叫做 nested Extension，也就是嵌套的 Extension

```groovy
outer {
    
    outerName "outer"
    msg "this is a outer message."

    inner {
        innerName "inner"
        msg "This is a inner message."
    }
    
}
```

```groovy
class OuterExt {
    
    String outerName
    String msg
    InnerExt innerExt = new InnerExt()

    void outerName(String name) {
        outerName = name
    }

    void msg(String msg) {
        this.msg = msg
    }
    
    //创建内部Extension，名称为方法名 inner
    void inner(Action<InnerExt> action) {
        action.execute(inner)
    }

    //创建内部Extension，名称为方法名 inner
    void inner(Closure c) {
        org.gradle.util.ConfigureUtil.configure(c, innerExt) 
    }

    String toString() {
        return "OuterExt[ name = ${outerName}, msg = ${msg}] " + innerExt
    }

}


class InnerExt {
    
    String innerName
    String msg

    void innerName(String name) {
        innerName = name
    }

    void msg(String msg) {
        this.msg = msg
    }

    String toString() {
        return "InnerExt[ name = ${innerName}, msg = ${msg}]"
    }

}

def outExt = getExtensions().create("outer", OuterExt)

outer {
    
    outerName "outer"
    msg "this is a outer message."

    inner {
        innerName "inner"
        msg "This is a inner message."
    }

}

task testExt << {
    println outExt
}
```

这里的关键点在于下面这2个方法的定义，只需要定义任意一个即可

```groovy
void inner(Action<InnerExt> action)
void inner(Closure c)
```

### 三.Android的Extension

1.app 的 build.gradle 里我们通常会采用插件 **apply plugin: 'com.android.application'** ，而在 library module 中则采用插件 **apply plugin: 'com.android.library'**

2.当我们不知道 android 里有哪些配置时，除了查看 API 文档以外，还可以直接翻看 BaseExtension 源码

## Android Gradle学习(六)：NamedDomainObjectContainer详解

### 一.NamedDomainObjectContainer的使用场景

讲解 Gradle Extension 的时候，说到名为 android 的 Extension 是由 BaseExtension 这个类来实现的，里面对 buildTypes 是这样定义的

```java
private final NamedDomainObjectContainer<BuildType> buildTypes;
```

buildTypes 就是 NamedDomainObjectContainer 类型的，先来看看 buildTypes 在 Android 中是怎么使用的，下面这段代码应该都很熟悉了，它定义了 debug、relase 两种打包模式

```groovy
android {

    buildTypes {
        release {
            // 是否开启混淆
            minifyEnabled true
            // 开启ZipAlign优化
            zipAlignEnabled true
            //去掉不用资源
            shrinkResources true
            // 混淆文件位置
            proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
            // 使用release签名
            signingConfig signingConfigs.hmiou
        }

        debug {
            signingConfig signingConfigs.hmiou
        }
    }
}
```

可以根据不同的场景定义不同的配置，每个不同的命名空间都会生成一个 BuildType 配置

### 二.NamedDomainObjectContainer是什么

- 它能够通过DSL(在Gradle脚本中)创建指定 type 的对象实例；
- 指定的 type 必须有一个 public 构造函数，且必须带有一个 String name 的参数，type 类型的领域对象必须有名为“name”的属性；
- 它是一个实现了 SortedSet 接口的容器，所以所有领域对象的 name 属性值都必须是唯一的，在容器内部会用 name 属性来排序；

### 三.怎么创建NamedDomainObjectContainer

Project.container(...) API 来创建

```groovy
<T> NamedDomainObjectContainer<T> container(Class<T> type)
<T> NamedDomainObjectContainer<T> container(Class<T> type, NamedDomainObjectFactory<T> factory)
<T> NamedDomainObjectContainer<T> container(java.lang.Class<T> type, Closure factoryClosure
```

```groovy
//这是领域对象类型定义
class TestDomainObj {
    
    //必须定义一个 name 属性，并且这个属性值初始化以后不要修改
    String name

    String msg

    //构造函数必须有一个 name 参数
    public TestDomainObj(String name) {
        this.name = name
    }

    void msg(String msg) {
        this.msg = msg
    }

    String toString() {
        return "name = ${name}, msg = ${msg}"
    }
}

//创建一个扩展
class TestExtension {

    //定义一个 NamedDomainObjectContainer 属性
    NamedDomainObjectContainer<TestDomainObj> testDomains

    public TestExtension(Project project) {
        //通过 project.container(...) 方法创建 NamedDomainObjectContainer 
        NamedDomainObjectContainer<TestDomainObj> domainObjs = project.container(TestDomainObj)
        testDomains = domainObjs
    }

    //让其支持 Gradle DSL 语法
    void testDomain(Action<NamedDomainObjectContainer<TestDomainObj>> action) {
        action.execute(testDomains)
    }

    void test() {
        //遍历命名领域对象容器，打印出所有的领域对象值
        testDomains.all { data ->
            println data        
        }
    }
}

//创建一个名为 test 的 Extension
def testExt = getExtensions().create("test", TestExtension, project)

test {
    testDomain {
        domain2 {
            msg "This is domain2"
        }
        domain1 {
            msg "This is domain1"
        }
        domain3 {
            msg "This is domain3"
        }
    }   
}

task myTask << {
    testExt.test()
}
```

### 四. 查找和遍历

```groovy
//遍历
void all(Closure action)
//查找
<T> T getByName(String name)
//查找
<T> T findByName(String name)
```

```groovy
//通过名字查找
TestDomainObj testData = testDomains.getByName("domain2")
println "getByName: ${testData}"

//遍历命名领域对象容器，打印出所有的领域对象值
testDomains.all { data ->
    println data        
}   
```

## Android Gradle学习(七)：Gradle构建生命周期

### 一.Gradle 进行构建，都要经过3个生命周期阶段

- 初始化阶段
- 配置阶段
- 执行阶段

### 二.Project

Project 提供的生命周期回调方法有

```groovy
//在 Project 进行配置前调用
void beforeEvaluate(Closure closure)
//在 Project 配置结束后调用
void afterEvaluate(Closure closure)
```

<font color="#ff6600">beforeEvaluate 必须在父模块的 build.gradle 对子模块进行配置才能生效，因为在当前模块的 build.gradle 中配置，它自己本身都没配置好，所以不会监听到</font>

### 三.Gradle

Gradle 提供的生命周期回调方法很多，部分与 Project 里的功能雷同

```groovy
//在project进行配置前调用，child project必须在root project中设置才会生效，root project必须在settings.gradle中设置才会生效
void beforeProject(Closure closure)

//在project配置后调用
afterProject(Closure closure)

//构建开始前调用
void buildStarted(Closure closure)

//构建结束后调用
void buildFinished(Closure closure)

//所有project配置完成后调用
void projectsEvaluated(Closure closure)

//当settings.gradle中引入的所有project都被创建好后调用，只在该文件设置才会生效
void projectsLoaded(Closure closure)

//settings.gradle配置完后调用，只对settings.gradle设置生效
void settingsEvaluated(Closure closure)
```

修改 setting.gradle 的代码如下:

```groovy
gradle.settingsEvaluated {
    println "settings：执行settingsEvaluated..."
}

gradle.projectsLoaded {
    println "settings：执行projectsLoaded..."
}

gradle.projectsEvaluated {
    println "settings: 执行projectsEvaluated..."
}

gradle.beforeProject { proj ->
    println "settings：执行${proj.name} beforeProject"
}

gradle.afterProject { proj ->
    println "settings：执行${proj.name} afterProject"
}

gradle.buildStarted {
    println "构建开始..."
}

gradle.buildFinished {
    println "构建结束..."
}

include ":app"
```

执行结果如下：

```groovy
settings：执行settingsEvaluated...
settings：执行projectsLoaded...
settings：执行test beforeProject
根项目配置开始---
根项目里任务配置---
根项目配置结束---
settings：执行test afterProject
settings：执行app beforeProject
子项目beforeEvaluate回调...
APP子项目配置开始---
APP子项目里任务配置---
APP子项目配置结束--- 
settings：执行app afterProject
APP子项目afterEvaluate回调...
settings: 执行projectsEvaluated...
构建结束...
```

Gradle 还有一个通用的设置生命周期监听器的方法：**addListener**

### 四.TaskExecutionGraph（Task执行图）

Gradle 可以对 task 的执行生命周期进行监听。

```groovy
//任务执行前掉用
void afterTask(Closure closure)
//任务执行后调用
void beforeTask(Closure closure)
//任务准备好后调用
void whenReady(Closure closure)
```

gradle.getTaskGraph() 方法来获取 task 执行图

```groovy
TaskExecutionGraph taskGraph = gradle.getTaskGraph()
taskGraph.whenReady {
    println "task whenReady"
}

taskGraph.beforeTask { Task task ->
    println "任务名称：${task.name} beforeTask"
}

taskGraph.afterTask { Task task ->
    println "任务名称：${task.name} afterTask"
}
```

### 五.小结

生命周期回调的执行顺序

```groovy
gradle.settingsEvaluated->
gradle.projectsLoaded->
gradle.beforeProject->
project.beforeEvaluate->
gradle.afterProject->
project.afterEvaluate->
gradle.projectsEvaluated->
gradle.taskGraph.graphPopulated->
gradle.taskGraph.whenReady->
gradle.buildFinished
```

## Android Gradle学习(八)：统计Task执行时长

```java
class BuildTimeCostPlugin implements Plugin<Project>{

    //用来记录 task 的执行时长等信息
    Map<String, TaskExecTimeInfo> timeCostMap = new HashMap<>()
    //用来按顺序记录执行的 task 名称
    List<String> taskPathList = new ArrayList<>()

    @Override
    void apply(Project project) {
        //监听每个task的执行
        project.getGradle().addListener(new TaskExecutionListener() {
            @Override
            void beforeExecute(Task task) {
                //task开始执行之前搜集task的信息
                TaskExecTimeInfo timeInfo = new TaskExecTimeInfo()
                //记录开始时间
                timeInfo.start = System.currentTimeMillis()
                timeInfo.path = task.getPath()
                timeCostMap.put(task.getPath(), timeInfo)
                taskPathList.add(task.getPath())
            }

            @Override
            void afterExecute(Task task, TaskState taskState) {
                //task执行完之后，记录结束时的时间
                TaskExecTimeInfo timeInfo = timeCostMap.get(task.getPath())
                timeInfo.end = System.currentTimeMillis()
                //计算该 task 的执行时长
                timeInfo.total = timeInfo.end - timeInfo.start
            }
        })

        //编译结束之后：
        project.getGradle().addBuildListener(new BuildListener() {
            @Override
            void buildStarted(Gradle gradle) {

            }

            @Override
            void settingsEvaluated(Settings settings) {

            }

            @Override
            void projectsLoaded(Gradle gradle) {

            }

            @Override
            void projectsEvaluated(Gradle gradle) {

            }

            @Override
            void buildFinished(BuildResult buildResult) {
                println "---------------------------------------"
                println "---------------------------------------"
                println "build finished, now println all task execution time:"
                //按 task 执行顺序打印出执行时长信息
                for (String path : taskPathList) {
                    long t = timeCostMap.get(path).total
                    if (t >= timeCostExt.threshold) {
                        println("${path}  [${t}ms]")
                    }
                }                println "---------------------------------------"
                println "---------------------------------------"
            }
        })

    }

    //关于 task 的执行信息
    class TaskExecTimeInfo {

        long total      //task执行总时长

        String path
        long start      //task 执行开始时间
        long end        //task 结束时间

    }

}
```

执行 “assembleDebug” 这个构建任务，打一个 debug 的测试包出来，构建完成之后，可以在 Gradle Console 里看到本次构建里所有 task 的执行时长信息

## Android Gradle学习(九)：有用的小技巧

### 一.resolutionStrategy 统一全局第三方库版本

#### 1.resolutionStrategy.force

可以通过 `resolutionStrategy.force` 来强制编译时统一库的版本号

```groovy
android {
    configurations.all {
        resolutionStrategy {
            force 'com.android.support:appcompat-v7:28.0.0'
            force 'com.android.support:support-v4:28.0.0'
        }
    }
}
```

#### 2.resolutionStrategy.dependencySubstitution(依赖修改)

```groovy
android {
    configurations.all {
        resolutionStrategy {
            //远程依赖替换成本地依赖
            substitute module('org.gradle:util:3.0') with project(':util')
            //也可以将远程依赖换成另外的远程依赖，假设我们修改过的代码发布到自己的 maven 中央仓库后叫：com.xxx.xxx:util:3.0
            //substitute module('org.gradle:util:3.0') with module('com.xxx.xxx:util:3.0') 
        }
    }
}
```

### 二.gradle.settingsEvaluated 在编译之前做一些初始化工作

`settings.gradle` 文件的最开头加上该方法的监听，注意该方法必须加在 settings.gradle 文件中，在 build.gradle 里没用

```groovy
gradle.settingsEvaluated {
    println "-------------settingsEvaluated start------------"
    //假设我们要修改的是 react-native-fs 这个库当中的 build.gradle 文件
    //先删除该文件
    delete("${rootDir}/../node_modules/react-native-fs/android/build.gradle")
    //我们将修改好的文件放到 ./replace-files/react-native-fs 这个目录当中
    //前面已经删除了目标文件，现在只需将我们需要的文件复制过去即可
    copy {
        from("./replace-files/react-native-fs/build.gradle")
        into("${rootDir}/../node_modules/react-native-fs/android")
    }
    println "-------------settingsEvaluated end------------"
}
```

- 执行打包命令 `./gradlew assembleRelease` 等的时候，一开始就会执行以上代码，自动帮我们做好这些事情
- 除此之外，还可以做很多事情，比如打包前先删除某个缓存目录的文件，每次打包时自动递增修改 `versionCode`、`versionName` ，检查 local.properties 配置文件是否存在已经配置是否正确等等

### 三.修改包的输出路径

```groovy
android {
    applicationVariants.all { variant ->
        variant.outputs.each { output ->
            def date = releaseTime()
            //这里只改变 release 包的输出路径
            if (variant.buildType.name.contains('release')) {
                //我们定义包的输出路径为 android_outputs/Android_APK
                //这里可以根据情况定义任意路径
                variant.packageApplicationProvider.get().outputDirectory = new File(project.rootDir.absolutePath + File.separator + "android_outputs"+ File.separator + "Android_APK")
                //包名范例：Android-pro-release-v2.6.6-c266-d202108181428.apk
                def fileName = "Android-${variant.productFlavors[0].name}-${buildType.name}-v" + versionName + "-c" + versionCode + "-d" + date + ".apk"
                output.outputFileName = fileName
            }

            //拓展一下，也可以根据 productFlavors 来做配置
            /*
            if (variant.getName() == "qaRelease") {
                //这里还可以动态修改 productFlavors 里的东西，例如：
                variant.buildConfigField "String", "APP_CHANNEL", '"xiaomi"'
            } else if (variant.getName() == "proRelease") {                

            }
            */
        }
    }
}

def releaseTime() {
    return new Date().format("yyyyMMddHHmm")
}
```

### 四.找到版本冲突的库

```groovy
configurations.all {
    resolutionStrategy {
        failOnVersionConflict()
    }
}
```
