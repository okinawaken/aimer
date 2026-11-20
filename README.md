# Aimer

> 基于 Spring Boot 3 + Erupt 的自动化运营平台：把两个小程序（自习室、阿里鱼）的日常操作搬到 Web 后台，支持定时自动预约、自动签到 / 签退、演出浏览购票与消息推送。

Aimer 不是通用脚手架，而是一个「个人自动化 + 运营后台」的落地项目。它用 [Erupt](https://www.erupt.xyz/) 提供登录、菜单、权限与会话能力，用 [Forest](https://forest.dtflys.com/) 声明式地对接第三方小程序接口，用 Redisson 处理分布式锁与运行态凭证，再把页面以自定义模板（HTML/CSS/JS）挂进 Erupt 后台。

---

## 目录

- [功能概览](#功能概览)
- [技术栈](#技术栈)
- [项目结构](#项目结构)
- [快速开始](#快速开始)
- [使用说明](#使用说明)
- [接口约定](#接口约定)
- [定时任务与执行流程](#定时任务与执行流程)
- [关键设计](#关键设计)
- [开发约定](#开发约定)
- [安全与免责声明](#安全与免责声明)

---

## 功能概览

### 自习室（study）

对接「时不我待自习馆」小程序接口，把抢座、签到、签退自动化。

| 能力 | 说明 |
| --- | --- |
| 运营看板 | 昵称 / 手机号 / 累计学习时长 / 累计签到次数，预约列表（可取消）与签到记录 |
| 自动预约 | 按门店、座位或区域、时段、星期创建任务；放号后自动抢座，支持手动立即执行、暂停 / 恢复 |
| 自动签到 | 每 10 分钟扫描开启用户，满足套餐 / 待签到预约 / 门店条件后自动签到 |
| 自动签退 | 每 10 分钟扫描开启用户，对仍在座的有效会话执行签退 |
| 用户设置 | 绑定小程序 Token、设置每日放号时刻、自动签到 / 签退开关、Bark 推送地址 |
| 签到签退页 | 手动签到、换座、签退，查看在座状态、当前签到、累计数据、座位与灯光信息 |
| 业务代理 | 配置、储物柜、暂离、套餐、报修、预约、签到、用户、门店等接口的 Web 化封装 |
| 消息推送 | 预约 / 签到 / 签退结果通过 Bark 推送到手机 |

### 阿里鱼（aliyu）

对接阿里鱼小程序（`client.maitix.com`）接口，覆盖登录、演出、购票、订单、会员、积分商城等域。

| 能力 | 说明 |
| --- | --- |
| 账号绑定 | 只需提供 `X-MZ-SESSION`，服务端校验后按系统用户隔离保存 |
| 演出浏览 | 演出列表、城市 / 类型筛选、演出详情、演出日历、按日期查场次 |
| 购票下单 | 加购物车、确认订单、提交订单、支付渲染与支付 |
| 订单与售后 | 订单列表 / 详情、取消、退款、发票申请与记录 |
| 会员与积分 | 会员信息、积分明细、成长值、会员任务、优惠券、积分商城兑换 |
| 活动与协议 | 活动报名 / 抽奖、协议记录与详情 |
| 用户资料 | 个人信息、观演人（Bearer）、收货地址、联系方式 |
| 页面 | 用户设置、个人资料、演出日历、演出列表、演出详情、购票 |

### 平台能力

- **Erupt 后台**：登录、菜单、按钮级权限、Redis 会话（`erupt.redis-session: true`）。
- **自定义页面**：两个模块各自注册 Erupt 菜单，页面用原生 HTML/CSS/JS 编写，数据通过 `/erupt-api/**` 拉取。
- **统一异常处理**：业务异常、参数校验异常、未知异常统一返回 `R<T>`。
- **异步事件 + 定时调度**：扫描与执行解耦，执行结果落库并推送。
- **分布式锁**：同一任务 / 用户同一时刻只允许一个执行实例。

---

## 技术栈

| 分类 | 选型 |
| --- | --- |
| 语言 / 运行时 | Java 17 |
| 基础框架 | Spring Boot 3.5.16 |
| 低代码后台 | Erupt 2.4.0（`erupt-spring-boot-starter`、`erupt-data-redis`） |
| HTTP 客户端 | Forest 1.8.0（`forest-spring-boot3-starter`） |
| 缓存 / 分布式 | Redis + Redisson 3.52.0 |
| 数据库 | MySQL（`mysql-connector-j`）+ Spring Data JPA / Hibernate |
| 工具库 | Hutool 5.8.48 |
| 对象映射 | MapStruct-Plus 1.5.3 |
| 序列化 | Gson（MVC 消息转换器）、Jackson（Forest 转换器） |
| 其他 | Lombok、Caffeine、TransmittableThreadLocal |

---

## 项目结构

```text
src/main/java/cn/nicomoe/aimer
├── AimerApplication.java          # 启动类（@EruptScan / @EntityScan）
├── aliyu/                         # 阿里鱼小程序域
│   ├── client/http/               # Forest 客户端（BaseClient、v1 各业务接口、拦截器、OkHttp Provider）
│   ├── constant/                  # 网关、请求头、缓存 Key、模板路由常量
│   ├── controller/                # client（业务代理）/ user（用户设置）
│   ├── model/                     # dto（上游报文）/ vo（前端响应）/ bo（配置对象）
│   ├── service/                   # client 业务封装、credential 凭证、crypto RSA、risk 风控头、user 设置
│   └── tpl/                       # Erupt 菜单模块与模板处理器
├── study/                         # 自习室域
│   ├── client/http/               # Forest 客户端（v1 / v2 / v3）
│   ├── controller/                # client / autoreserve / user
│   ├── filter/                    # 自动预约 / 签到 / 签退过滤器链
│   ├── listener/                  # 执行事件监听（异步）
│   ├── model/                     # entity / dto / vo / bo / context / event / query
│   ├── repository/                # JPA Repository
│   ├── scheduler/                 # 三个定时调度器
│   ├── service/                   # client / autoreserve / autosign / autosignout / notify / user
│   └── tpl/                       # Erupt 菜单模块与模板处理器
├── notify/                        # Bark 推送（client / dto / service）
├── config/                        # async、forest、gson、hutool 配置
├── enums/                         # BaseEnum 通用枚举接口
├── exception/                     # 全局异常处理
└── util/                          # CheckUtils、CookieUtils、GsonUtils、MapstructUtils、RedisCacheUtils、RedisLockUtils

src/main/resources
├── application.yml                # 数据源、Redis、Erupt 配置
├── public/                        # 静态资源（study / aliyu 的 css、js、字体、图片）
└── tpl/                           # 自定义页面模板（Native 引擎，替换 ${base}）
```

分层约定：`controller` → `service` → `client/http`（Forest 接口），实体与报文通过 `model` 下的 `entity / dto / vo / bo / query` 区分，DTO ↔ VO 使用 MapStruct-Plus 转换。

---

## 快速开始

### 环境要求

- JDK 17+
- Maven 3.8+
- MySQL 8.x
- Redis 6.x+

### 配置

`src/main/resources/application.yml` 已提供本地开发默认值，**生产环境请通过环境变量覆盖**：

| 环境变量 | 默认值 | 说明 |
| --- | --- | --- |
| `MYSQL_HOST` | `192.168.124.200` | MySQL 地址 |
| `MYSQL_PORT` | `30000` | MySQL 端口 |
| `MYSQL_ROOT_USER` | `root` | MySQL 用户名 |
| `MYSQL_ROOT_PASSWORD` | `wang13587` | MySQL 密码（仓库内为开发默认值，务必覆盖） |
| `REDIS_HOST` | `192.168.124.200` | Redis 地址 |
| `REDIS_PORT` | `30001` | Redis 端口 |

其他关键配置：

- 服务端口：`8080`
- 数据库：`aimer`，`spring.jpa.generate-ddl: true`，首次启动自动建表
- Erupt：`default-locales: zh-CN`、`redis-session: true`

### 构建与启动

```bash
# 开发模式
mvn spring-boot:run

# 打包运行
mvn clean package -DskipTests
java -jar target/aimer-0.0.1-SNAPSHOT.jar
```

启动后访问 <http://localhost:8080>。

Erupt 首次启动会自动初始化管理员账号，默认账号 / 密码均为 `erupt`，可通过以下配置覆盖：

```yaml
erupt:
  upms:
    default-account: your-account
    default-password: your-password
```

> 生产环境请务必修改默认管理员密码，并用环境变量注入数据库、Redis 凭据。

---

## 使用说明

### 自习室

1. 进入 **自习室 → 用户设置**，粘贴小程序 Token（保存时服务端会调用上游校验有效性），设置放号时刻（默认 `20:00:00`）、自动签到 / 自动签退开关、Bark 推送地址。
2. 进入 **自动预约**，创建任务：选择门店，指定座位或偏好区域（`0` 表示不限），设置预约时段与执行星期。
3. 到达放号时刻后，调度器每分钟扫描一次，命中任务即异步抢座；结果写入执行记录并通过 Bark 推送。
4. 在 **运营看板** 查看预约、签到记录，可取消待签到状态的预约；在 **签到签退** 页手动签到、换座、签退。

### 阿里鱼

1. 进入 **阿里鱼 → 用户设置**，粘贴小程序 `X-MZ-SESSION` 完成绑定（可先点校验）。
2. 进入 **演出列表 / 演出日历** 浏览演出，点击进入 **演出详情**，再进入 **购票** 完成下单支付。
3. 在 **个人资料** 查看账号信息，后续可扩展到观演人、地址、订单等页面。

---

## 接口约定

- **基础路径**：`/erupt-api`
- **鉴权**：接口标注 `@EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)`，前端请求头携带 Erupt 登录 `token`
- **响应体**：统一为 Erupt 的 `R<T>`（`success` / `message` / `data`）
- **自习室接口**：`/erupt-api/study/**`
  - 用户设置：`GET|PUT /study/user-setting/me`
  - 自动预约：`/study/auto-reserve/task`（增删改查、`/{id}/pause`、`/{id}/resume`、`/{id}/execute`、`/seat/options`）
  - 执行记录：`/study/auto-reserve/task/record`
  - 业务代理：`/study/config`、`/study/guard`、`/study/leave`、`/study/misc`、`/study/package`、`/study/repair`、`/study/reserve`、`/study/sign`、`/study/user`、`/study/venue`
- **阿里鱼接口**：`/erupt-api/aliyu/**`
  - 用户设置：`GET|PUT|DELETE /aliyu/user-setting/me`、`POST /aliyu/user-setting/check`
  - 业务域：`activity`、`activity-register`、`agreement`、`checkout`、`content`、`login`、`member-point`、`order`、`points-mall`、`pop-up`、`project`、`trade`、`user`

---

## 定时任务与执行流程

| 调度器 | Cron | 作用 |
| --- | --- | --- |
| `StudyRoomAutoReserveScheduler` | `0 * * * * ?` | 每分钟扫描启用任务，当前时间落在放号时刻的 1 分钟窗口内则发布执行事件；是否当天已执行由过滤器判断 |
| `StudyRoomAutoSignScheduler` | `0 0/10 * * * ?` | 每 10 分钟扫描开启自动签到的用户并发布事件 |
| `StudyRoomAutoSignOutScheduler` | `0 0/10 * * * ?` | 每 10 分钟扫描开启自动签退的用户并发布事件 |

自动预约执行链路：

```text
Scheduler 扫描
  └─ 发布 StudyRoomAutoReserveExecuteEvent
       └─ @Async 监听（StudyRoomAutoReserveExecuteListener）
            └─ Executor
                 ├─ Redisson 分布式锁（study:auto_reserve:execute:{taskId}）
                 ├─ 过滤器链（启用 → 目标日期 → 星期 → 门店 → 座位 → 资格）
                 ├─ 调用上游预约接口
                 ├─ 成功：写执行记录、推进放号基线日期、Bark 推送
                 └─ 失败 / 跳过：写执行记录、Bark 推送
```

签到 / 签退同理，分别使用 `study:auto_sign:execute:{userId}`、`study:auto_sign_out:execute:{userId}` 锁。

---

## 关键设计

### 过滤器链

自动预约、自动签到、自动签退各有一条 `FilterChain`，由 Spring 注入同类型 `Filter` 列表并按顺序执行，遇到失败立即返回 `FilterResultDTO`。顺序由过滤器实现的 `Ordered#getOrder()` 决定，取值来自各自的 `*FilterOrderEnum`（数值越小越先执行）。新增规则只需实现对应接口并注册为 Bean，无需修改执行器。

### 凭证与运行态缓存

- 自习室：小程序 Token 存在 `study_room_user_setting.config_json`，属于敏感字段，`@ToString.Exclude` 禁止进入日志。
- 阿里鱼：用户只提供 `X-MZ-SESSION`，其余运行态凭证全部落在 Redis，按系统用户 ID 隔离：
  - `aliyu:credential:session:{userId}`
  - `aliyu:credential:cookie:{userId}`
  - `aliyu:credential:operate_id:{userId}`
  - `aliyu:credential:csrf:{userId}`

### 阿里鱼请求装饰

`AliyuRequestInterceptor` 按固定顺序处理每个请求：

1. **风控头**：`epeius`（毫秒时间戳）、`hecate`（对规范化 JSON + 时间戳做 MD5）、`coeus`（按路径匹配风险等级）。
2. **租户参数**：`site` 请求头，`langType` 与 `__sr` 查询参数。
3. **凭证头**：`Cookie`、`X-MZ-SESSION`、`Mzoperateid`、`X-XSRF-TOKEN`。
4. **字段加密**：按接口路径对指定字段做 RSA（PKCS#1 v1.5）加密。

响应侧由两个拦截器分工：`AliyuResponseInterceptor` 回写 `X-MZ-SESSION` / `Mzoperateid` 并合并 `Set-Cookie`；`AliyuValidateResponseInterceptor` 校验 HTTP 状态、响应类型与业务码（非 `200` 直接抛出业务异常，`603` / `804` 常量用于识别需登录场景）。

> `hecate` 的计算刻意复刻了小程序 JS 语义：`localeCompare` 排序、`JSON.stringify` 序列化、UTF-16 码元编码、数字 `toString` 规则，均在 `AliyuRiskServiceImpl` 中本地实现，未直接复用框架序列化器。

### 页面与菜单

两个模块分别实现 `EruptModule` 注册菜单，页面路由常量同时是 `@TplAction` 值、菜单 `value` 与权限键。模板使用 Native 引擎，只输出静态 HTML 并替换 `${base}`，数据由前端 `fetch` 拉取。

---

## 开发约定

- **方法顺序**：`public` → `protected` → `private`。
- **判空 / 相等**：使用 `Objects.isNull` / `Objects.nonNull` / `Objects.equals`。
- **工具类**：优先 Hutool（`StrUtil`、`CollUtil`、`ObjectUtil`、`DateUtil` 等）。
- **私有方法调用**：本类私有方法加 `this.` 前缀，静态方法与成员变量不加。
- **规范**：遵循《阿里巴巴 Java 开发手册》与《阿里巴巴 MySQL 数据库开发规范》。
- **实体**：继承 Erupt `MetaModel`，使用 `@Comment`、`@Table`、索引与字段长度显式声明；JSON 配置字段配 `BO` 说明结构。
- **转换**：实体 / DTO 用 `@AutoMapper`，Controller 返回前统一转 `VO`。
- **敏感字段**：Token、Session、Cookie、Bark 地址等必须 `@ToString.Exclude`，禁止输出到日志。

---

## 安全与免责声明

- 本项目通过逆向 / 复刻第三方小程序接口实现自动化，**仅限用于本人账号或已获授权的账号**。
- 使用前请确认符合对应平台的服务条款；自动化操作可能导致账号被风控或限制，风险由使用者自行承担。
- 所有 Token、Session、Cookie 均为敏感凭据，请勿提交到仓库或分享；仓库中的数据库密码等为本地开发默认值，生产环境务必通过环境变量覆盖。
- 生产部署建议：修改 Erupt 默认管理员密码、启用 HTTPS、限制后台访问来源、为 Redis 设置密码与网络隔离。

---

## License

本项目未指定开源许可证。如需对外分发，请先补充 License 文件。
