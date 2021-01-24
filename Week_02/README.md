学习笔记



对于不同 GC 和堆内存的总结，提交到 GitHub。




本人环境 jdk11 mac os 11.x系统


以下测试基本每个参数测试次数在15~20次左右，基本近似取平均值，可能存在误差

不同机器测试可能存在误差，但大体不差；

分别测试了从128 256 512 1g 2g 到 4g 不通垃圾回收器下创建对象数量以及full gc时间大小对比




## 垃圾回收器

### 串行垃圾回收


java -XX:+UseSerialGC  -Xms128m -Xmx128m -XX:+PrintGCDetails GCLogAnalysis


>oom  young gc 时间大概在 2-3ms左右  ， full gc时间大概 2ms ~ 10ms 左右 不等


java -XX:+UseSerialGC  -Xms256m -Xmx256m -XX:+PrintGCDetails GCLogAnalysis
 
 
>oom  young gc 时间大概在 10ms左右  ， full gc时间大概 2ms ~ 15ms 左右 不等

 
java -XX:+UseSerialGC  -Xms512m -Xmx512m -XX:+PrintGCDetails GCLogAnalysis

>大概创建对象数量在1W4左右  young gc 时间大概在 30ms左右  ， full gc时间大概 30ms 


 
java -XX:+UseSerialGC  -Xms1g -Xmx1g -XX:+PrintGCDetails GCLogAnalysis

>大概创建对象数量在1W4 到 近1w6之间  young gc 时间大概在 40 ~ 70 ms 不等  ， full gc时间...


java -XX:+UseSerialGC  -Xms2g -Xmx2g -XX:+PrintGCDetails GCLogAnalysis


>大概创建对象数量在 1w3左右   young gc 时间大概在 90-120ms 不等  ， full gc时间....



java -XX:+UseSerialGC  -Xms4g -Xmx4g -XX:+PrintGCDetails GCLogAnalysis


>大概创建对象数量在 1w左右   young gc 时间大概在 140-170ms 不等  ， full gc时间....




`总结`
 
 可以看到在堆内存越来越大时候，创建对象数量先变大大概在1g的时候数量是最多的，young gc次数也慢慢变多
                              gc时间越来越长，几乎是指数上升
                              
 

### 并行GC


java -XX:+UseParallelGC  -Xms128m -Xmx128m -XX:+PrintGCDetails GCLogAnalysis


>oom  young gc 时间大概在 2-3ms左右  ， full gc时间大概 2ms ~ 7ms 左右 不等


java -XX:+UseParallelGC  -Xms256m -Xmx256m -XX:+PrintGCDetails GCLogAnalysis
 
 
>oom  young gc 时间大概在 1-10ms左右  ， full gc时间大概 7ms ~ 15ms 左右 不等

 
java -XX:+UseParallelGC  -Xms512m -Xmx512m -XX:+PrintGCDetails GCLogAnalysis

>大概创建对象数量在1W2 - 1w4左右  young gc 时间大概在 5ms左右  ， full gc时间大概 15-28ms 


 
java -XX:+UseParallelGC  -Xms1g -Xmx1g -XX:+PrintGCDetails GCLogAnalysis

>大概创建对象数量在1W7左右  young gc 时间大概在 5-9ms ，偶尔 10+ms 不等  ， full gc时间 30ms内


java -XX:+UseParallelGC  -Xms2g -Xmx2g -XX:+PrintGCDetails GCLogAnalysis


>大概创建对象数量在 1w6左右   young gc 时间大概在 10-50ms 不等  ， full gc时间....


java -XX:+UseParallelGC  -Xms4g -Xmx4g -XX:+PrintGCDetails GCLogAnalysis


>大概创建对象数量在 1w-1.2w左右   young gc 时间大概在 100m左右  ， full gc时间....




`总结`

创建对象数量随着内存的大小 逐渐变大进而变小
gc时间在堆内存大小1g开始时候 垃圾回收时间基本是串行回收期的一般时间




## CMS



java -XX:+UseConcMarkSweepGC  -Xms128m -Xmx128m -XX:+PrintGCDetails GCLogAnalysis


>oom  young gc 时间大概在 5-10ms左右  ， full gc时间大概 10ms ~ 20ms 左右 不等


java -XX:+UseConcMarkSweepGC  -Xms256m -Xmx256m -XX:+PrintGCDetails GCLogAnalysis
 
 
>oom  young gc 时间大概在 10-20+ms左右  ， full gc时间大概 20ms ~ 30ms 左右 不等

 
java -XX:+UseConcMarkSweepGC  -Xms512m -Xmx512m -XX:+PrintGCDetails GCLogAnalysis

>大概创建对象数量在 1w4左右  young gc 时间大概在 20-40ms左右  ， full gc时间大概 20-40ms 


 
java -XX:+UseConcMarkSweepGC  -Xms1g -Xmx1g -XX:+PrintGCDetails GCLogAnalysis

>大概创建对象数量在1W4左右  young gc 时间大概在 30-50ms  ， full gc时间 ...


java -XX:+UseConcMarkSweepGC  -Xms2g -Xmx2g -XX:+PrintGCDetails GCLogAnalysis


>大概创建对象数量在 1w2左右   young gc 时间大概在 60-100ms 不等  ， full gc时间....


java -XX:+UseConcMarkSweepGC  -Xms4g -Xmx4g -XX:+PrintGCDetails GCLogAnalysis


>大概创建对象数量在 1.2w左右   young gc 时间大概在 60-100m左右  ， full gc时间....



`总结`

可以看到 cms创建对象数量不是很多，吞吐量不如并发甚至串行收集器，垃圾回收时间与并行回收相差无异。







G1 垃圾回收期




java -XX:+UseG1GC  -Xms128m -Xmx128m -XX:+PrintGC GCLogAnalysis


>oom  young gc 时间大概在 0.1~0.2ms左右  ， full gc时间大概 1ms ~ 2ms 左右 不等

但gc次数达50~110次不等


java -XX:+UseG1GC  -Xms256m -Xmx256m -XX:+PrintGC GCLogAnalysis
 
 
>oom  young gc 时间大概在 0.5ms左右  ， full gc时间大概 1ms ~ 2ms 左右 不等

gc次数达 100~200次不等
 
java -XX:+UseG1GC  -Xms512m -Xmx512m -XX:+PrintGC GCLogAnalysis

>大概创建对象数量在 1w6左右  young gc 时间大概在 0.5-0.7ms左右， full gc时间大概 1 ~ 2ms 

gc次数达 170~200次不等

 
java -XX:+UseG1GC  -Xms1g -Xmx1g -XX:+PrintGC GCLogAnalysis

>大概创建对象数量在2.1W左右  young gc 时间大概在 0.5-3ms,个别 10+ms  full gc时间 ...


gc次数达 50次左右

java -XX:+UseG1GC  -Xms2g -Xmx2g -XX:+PrintGC GCLogAnalysis


>大概创建对象数量在 1.6-1.8w左右   young gc 时间大概在 10+ms ~ 40ms 不等  ， full gc时间....

gc次数达 10~40次左右

java -XX:+UseG1GC  -Xms4g -Xmx4g -XX:+PrintGC GCLogAnalysis

>大概创建对象数量在 1.6w左右   young gc 时间大概在 20+ms ~ 50ms 不等,个别90ms  ， full gc时间....

gc次数达 10~20次左右



## 总结






1 从 128m到256m 由于对大小小于创建对象占用的大小，导致oom，此种情况下；内存越小不管是yong gc 和 full gc次数会频繁触发，导致大量时间在做gc；
2 同等内存大小情况下 串行gc 时间 几乎是 并行gc的二倍；
3 内存越大的情况下 G1 回收的效率越高，gc时间比其他回收器时间短、内存越大g1效果越好；
4 可以看到内存越大的情况下g1的垃圾回收基本优于串行 并行 以及 cms 不管是从吞吐量和gc时间来说；
5 cms 于 吞吐量相对于其他回收器明显存在劣势，回收时间不太稳定；
 






