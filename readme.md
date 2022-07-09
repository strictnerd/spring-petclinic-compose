# Spring PetClinic Sample Application 
> 帮助开发者集成Java第三方组件

## 集成 prometheus io.micrometer
引入
```
    <dependency>
      <groupId>io.micrometer</groupId>
      <artifactId>micrometer-registry-prometheus</artifactId>
    </dependency>
```
暴露prometheus端口
```
# Actuator
management.endpoints.web.exposure.include=*
spring.application.name=spring-petclinic-compose
management.server.port=8091
management.metrics.tags.application=${spring.application.name}
```
访问 http://127.0.0.1:8091/actuator/prometheus 获取prometheus指标
```aidl
# TYPE executor_queue_remaining_tasks gauge
executor_queue_remaining_tasks{application="frontend",name="applicationTaskExecutor",} 2.147483647E9
# HELP jvm_memory_usage_after_gc_percent The percentage of long-lived heap pool used after the last GC event, in the range [0..1]
# TYPE jvm_memory_usage_after_gc_percent gauge
jvm_memory_usage_after_gc_percent{application="frontend",area="heap",pool="long-lived",} 0.024291138706014258
# HELP tomcat_sessions_expired_sessions_total  
# TYPE tomcat_sessions_expired_sessions_total counter
tomcat_sessions_expired_sessions_total{application="frontend",} 0.0
# HELP jvm_gc_overhead_percent An approximation of the percent of CPU time used by GC activities over the last lookback period or since monitoring began, whichever is shorter, in the range [0..1]
# TYPE jvm_gc_overhead_percent gauge
jvm_gc_overhead_percent{application="frontend",} 0.008939159543795356
# HELP hikaricp_connections_acquire_seconds Connection acquire time
# TYPE hikaricp_connections_acquire_seconds summary
hikaricp_connections_acquire_seconds_count{application="frontend",pool="HikariPool-1",} 19.0
hikaricp_connections_acquire_seconds_sum{application="frontend",pool="HikariPool-1",} 6.466E-4
# HELP hikaricp_connections_acquire_seconds_max Connection acquire time
# TYPE hikaricp_connections_acquire_seconds_max gauge
hikaricp_connections_acquire_seconds_max{application="frontend",pool="HikariPool-1",} 1.667E-4
# HELP http_server_requests_seconds  
# TYPE http_server_requests_seconds summary
http_server_requests_seconds_count{application="frontend",exception="None",method="GET",outcome="SUCCESS",status="200",uri="/owners/{ownerId}/edit",} 2.0
http_server_requests_seconds_sum{application="frontend",exception="None",method="GET",outcome="SUCCESS",status="200",uri="/owners/{ownerId}/edit",} 0.704812
http_server_requests_seconds_count{application="frontend",exception="None",method="GET",outcome="SUCCESS",status="200",uri="/owners/{ownerId}",} 2.0
http_server_requests_seconds_sum{application="frontend",exception="None",method="GET",outcome="SUCCESS",status="200",uri="/owners/{ownerId}",} 0.0711992
http_server_requests_seconds_count{application="frontend",exception="None",method="POST",outcome="REDIRECTION",status="302",uri="/owners/{ownerId}/edit",} 1.0
http_server_requests_seconds_sum{application="frontend",exception="None",method="POST",outcome="REDIRECTION",status="302",uri="/owners/{ownerId}/edit",} 0.101176
```