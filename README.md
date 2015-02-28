# Spage

### Tomcat配置：conf\catalina\localhost 创建xml文件（需要sudo）
**注意，如果path="/hello"，则文件并也要为hello.xml**

```
<?xml version="1.0"?>
<Context path="/hello" docBase="/Users/Thierry/Desktop/Spage/Spage/webRoot" debug="0" privileged="true">
</Context>
```

### 数据结构
- id (source+reference)
- source
- title
- enterprise
- field
- bac
- duration
  - 3
  - 6
- reference
- postDate