
    1.1 常见的日志门面 ：
    
        JCL、slf4j
        常见的日志实现：
        JUL、log4j、logback、log4j2
    
    1.2 日志框架出现的历史顺序：
        
        log4j -->JUL-->JCL--> slf4j --> logback --> log4j2


    2.1 绑定日志的实现（Binding）
    
        如前所述，SLF4J支持各种日志框架。SLF4J发行版附带了几个称为“SLF4J绑定”的jar文件，每个绑定对应
        一个受支持的框架。
        使用slf4j的日志绑定流程:
        1. 添加slf4j-api的依赖
        2. 使用slf4j的API在项目中进行统一的日志记录
        3. 绑定具体的日志实现框架
        1. 绑定已经实现了slf4j的日志框架,直接添加对应依赖
        2. 绑定没有实现slf4j的日志框架,先添加日志的适配器,再添加实现类的依赖
        4. slf4j有且仅有一个日志实现框架的绑定（如果出现多个默认使用第一个依赖日志实现）
    
    2.2 桥接旧的日志框架
    
        注意问题：
        1. jcl-over-slf4j.jar和 slf4j-jcl.jar不能同时部署。前一个jar文件将导致JCL将日志系统的选择委托给
        SLF4J，后一个jar文件将导致SLF4J将日志系统的选择委托给JCL，从而导致无限循环。
        2. log4j-over-slf4j.jar和slf4j-log4j12.jar不能同时出现
        3. jul-to-slf4j.jar和slf4j-jdk14.jar不能同时出现
        4. 所有的桥接都只对Logger日志记录器对象有效，如果程序中调用了内部的配置类或者是
        Appender,Filter等对象，将无法产生效果。

