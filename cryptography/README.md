# 功能
对于特定的密码算法，根据对应的积分区分器，对其进行密钥恢复攻击，代替人力推导。
# 用法
首先根据format.txt文件写好密码算法及积分区分器的文档。

然后在Main.java中修改以下３处。
- cipherName&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;//密码算法文件名
- integeralDistinguisherName&emsp;//积分区分器文件名
- path&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;//存放以上文件的位置

最后运行Main.java 即可。
# 运行环境
jdk 1.8及以上，Eclipse等软件。
