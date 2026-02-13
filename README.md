# freeCall

基于 **FreeSWITCH + Spring Boot** 的呼叫中心后端基础工程，包含：

- FreeSWITCH 容器化部署（含 ESL 端口开放）
- Spring Boot 控制层，支持发起呼叫/挂机
- 默认拨号计划示例（回声测试与语音播报）

## 1. 启动 FreeSWITCH

```bash
docker compose up -d freeswitch
```

> 默认 ESL 参数：`127.0.0.1:8021 / ClueCon`

## 2. 启动后端

```bash
mvn spring-boot:run
```

可通过环境变量修改 FreeSWITCH 连接：

- `FS_HOST`
- `FS_PORT`
- `FS_PASSWORD`
- `FS_TIMEOUT_SECONDS`

## 3. API 示例

### 3.1 发起呼叫

```bash
curl -X POST http://localhost:8080/api/calls/originate \
  -H "Content-Type: application/json" \
  -d '{
    "caller": "1000",
    "callee": "9196",
    "endpoint": "loopback/9196/default"
  }'
```

### 3.2 挂机

```bash
curl -X POST "http://localhost:8080/api/calls/{uuid}/hangup?cause=NORMAL_CLEARING"
```

### 3.3 FreeSWITCH 健康检查

```bash
curl http://localhost:8080/api/freeswitch/health
```

## 4. 默认测试分机

- `9196`：echo 回声测试
- `9200`：播放欢迎语音后挂机

## 5. 后续建议

- 接入坐席状态机（就绪/示忙/离线）
- 增加排队策略（轮询/技能路由/优先级）
- 对接话单与录音归档
