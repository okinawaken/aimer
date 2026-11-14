# 阿里鱼小程序 API 分析文档

---

## 一、结论速览

| 关注点 | 结论 |
|---|---|
| 平台性质 | 卖座（maitix）SaaS 票务系统 + 阿里鱼娱乐营销叠加层 |
| 请求入口 | `https://client.maitix.com/api` + `urlTemplate` |
| 鉴权方式 | **会话凭证束**（cookie + `Mzoperateid` + `X-MZ-SESSION` + `X-XSRF-TOKEN` + `site` + `__sr`），不是单一 token |
| 业务签名 | 无 `sign` 参数；但有 `hecate` / `epeius` / `coeus` 三个可离线计算的风控头 |
| 请求体加密 | 16 个接口的敏感字段使用 RSA-2048（PKCS#1 v1.5），已复现 |
| 无法离线生成的凭证 | 微信 `getPhoneNumber` 产出的 `encryptedData`/`iv`、baxia SDK 产出的 `bx-umidtoken`/`x-ticid`、登录后的 cookie |
| 接口规模 | 198 条调用记录 = **122 个业务接口** + 73 条页面跳转（去重 46 个页面）+ 2 个外部 URL + 1 个动态基址调用 |
| Java 侧 | 122 个业务接口全部建模，鉴权、加密、响应封装均已落地（见 §10） |

---

## 二、小程序基本信息

| 项目 | 值 |
|---|---|
| 名称 | 阿里鱼（阿里鱼娱乐营销平台） |
| AppID | `wx88adba08e6cf3e75` |
| 版本 | v37 |
| 商户 | 阿鲤鱼（杭州）文化传播有限公司，`ptnrId = 32138` |
| 商户前端域名 | `h5.alifishofficial.com` |
| 业务域 | 演出票务、积分商城、会员体系、抽签/抽奖、IP 衍生品 |
| 包结构 | 5 包 / 78 文件 / 11 个 JS（主包 + 4 个分包） |
| 静态资源 | alicdn / maizuo-saas OSS |

**识别依据**：源码中出现 `alifish`、「小鱼丸」（积分单位）、`阿里鱼`；接口命名空间以 `/aliyu`、`/aliyupoint`、`/aliyuMember` 为核心；页面标题包含「积分商城」「抽签详情」「小鱼丸明细」「会员装扮」「壁纸详情」等。

---

## 三、技术架构

```text
微信小程序 wx88adba08e6cf3e75
│
├─ 主包 app-service.js
│   ├─ 网络层  模块 93323  请求封装 / RSA 字段加密 / hecate·epeius·coeus
│   ├─ 鉴权层  模块 22085  启动引导 / CSRF / X-MZ-SESSION / __sr 拼接
│   ├─ 环境层  模块 27542  域名与 ptnrId 切换（4 套环境）
│   ├─ 白名单  模块 7188   支付宝渠道 baxia 头上报清单（26 条）
│   ├─ RSA 库  模块 10655  JSEncrypt 风格自包含实现
│   └─ 状态层  redux + redux-saga
│
├─ 分包 ×4
│   ├─ pages-member       会员 / 成长值 / 积分明细
│   ├─ pages-order        订单 / 活动 / 抽奖 / 收银台 / 发票
│   ├─ pages-others       地址 / 观众 / 优惠券 / 协议 / 装扮
│   └─ pages-points-mall  积分商城 / 奖品 / 壁纸
│
└─ 后端 卖座 maitix REST API（https://client.maitix.com/api）
```

**关键认知**：这是卖座为阿里鱼定制的前端，全部业务接口走 `client.maitix.com`；`/aliyu*` 系列是叠加在卖座基座上的营销扩展模块。

---

## 四、请求链路

### 4.1 域名与环境

模块 `27542` 定义四套环境，通过 `wx.getStorageSync("SERVICE_PATH")` 切换，默认正式环境。

| 环境 | 域名 | 随环境附带的 ptnrId |
|---|---|---|
| 正式（默认） | `https://client.maitix.com` | 无（租户身份由 `__sr` 反解析得出） |
| 集成 | `https://pre-rest.maitix.com` | 32161 |
| 测试 | `https://pre-rest-test.maitix.com` | 32160 |
| 开发 | `https://pre-rest-dev.maitix.com` | 32159 |

> 上表的 32159～32161 是**环境自带的调试商户号**；正式环境下真正的商户号 `32138` 由启动接口 `/partner/queryMDomain` 返回。两者不要混为一谈。

### 4.2 URL 构造

模块 `93323` 的最终 URL 由三段拼成：

```text
{环境域名}/api{urlTemplate}?__sr={encodeURIComponent("https://" + ptnrDomainMFrontend + "/")}
                            [&{GET 的 data}]
                            &langType={1|2|3}
```

拼接顺序固定：

1. `baseURL + "/api" + urlTemplate`
2. 追加 `__sr`（若 URL 已含 `?` 则用 `&`）
3. GET 的 `data` 序列化后追加（数组用 `repeat` 格式），并清空请求体
4. 最后追加 `langType`

- `urlTemplate` 均以 `/` 开头（如 `/order/queryOrderDetail`）
- `__sr` 用于租户反解析，**每个业务请求必带**，值固定为 `https://h5.alifishofficial.com/` 的 URL 编码形式
- `langType` 由当前语言映射：简体 `1`、英文 `2`、繁体 `3`

完整示例：

```text
GET https://client.maitix.com/api/order/queryOrderDetail?__sr=https%3A%2F%2Fh5.alifishofficial.com%2F&isNewUI=true&langType=1
```

### 4.3 请求头

| 头 | 取值 | 说明 |
|---|---|---|
| `cookie` | 小程序自维护的 cookie 容器 | 持久身份，含 JSESSIONID / MZCONSUMERJSESSIONID / MZOPERATEID |
| `site` | `mp_weixin` | 渠道标识（另有 `mp_douyin` / `mp_alipay` / `m`） |
| `Mzoperateid` | 服务端下发，存 43200s | 操作者标识，响应头回写后随请求带上 |
| `X-MZ-SESSION` | 服务端下发 | maitix 会话标识，响应头 `x-mz-session` 回写，有效期取响应头 `x-mz-session-expire` |
| `X-XSRF-TOKEN` | `/getCsrfToken` 换取，60s | POST 必填；缺失时请求层先取再发，取不到则终止 |
| `epeius` | 当前时间戳（毫秒） | 见 §6.3 |
| `hecate` | MD5 摘要 | 明文请求体 + `epeius` 的签名，见 §6.3 |
| `coeus` | `10010` / `10086` | 由 URL 是否命中固定路径清单决定 |
| `Content-Type` | `application/json` | **仅 POST** 设置 |
| `x-mini-wua` / `x-umt` | baxia SDK | 仅支付宝渠道 + URL 命中模块 7188 白名单时附加 |
| `bx-umidtoken` / `x-ticid` | baxia 安全 SDK | 设备绑定，随 SDK 注入，不可离线生成 |

> 旧版文档中的 `Authorization`、`token`、`ptnrId` 请求头**不在真实请求中出现**，已删除。`ptnrId` 只在开发/测试环境的服务端联动逻辑里使用。

### 4.4 响应结构

```json
{ "code": "200", "msg": "", "msgKey": "", "data": { } }
```

- 成功判定：`String(code) === "200"`（字符串比较）
- 部分错误码由前端特判：`804` / `23010016` 触发重新登录，`819` / `820` / `150601167` / `150601168` 走专用文案，`602` 静默失败，`603` 提示「太火爆」，`305` 提示页面停留过久，`422` / `423` 特殊跳转
- 页面配置类接口（`.json`）不走该结构

---

## 五、鉴权模型

### 5.1 为什么不是单一 token

模块 `93323` 的请求构造逻辑把这批头**逐项注入**，不存在「一个 token 走天下」的简化路径：

```text
租户   __sr 查询参数      → 决定本次请求落到哪个商户
身份   cookie            → 持久登录态（JSESSIONID 等）
会话   X-MZ-SESSION      → 响应头回写的滚动会话
操作者 Mzoperateid        → 操作者上下文，43200s 级
CSRF   X-XSRF-TOKEN      → POST 必填，60s 级
渠道   site               → 固定 mp_weixin
风控   epeius / hecate / coeus（本地计算）+ baxia 头（设备绑定）
```

> 之所以容易误判：`getCookieToSend()` 返回的是拼好的 cookie 串，肉眼看着像「一个 token」，实际是浏览器式 cookie 容器。只要 cookie 失效，其余头齐全也会被判定未登录。

### 5.2 启动链路

```text
① GET /api/partner/queryMDomain?miniProgramAppId=wx88adba08e6cf3e75
   ← { ptnrDomainMFrontend: "h5.alifishofficial.com", ptnrId: "32138", ptnrName: "阿鲤鱼(杭州)文化传播有限公司" }
      前端把 ptnrDomainMFrontend 存入 store，用于生成 __sr

② GET /api/getCsrfToken
   ← { xsrfToken: "...", expireTime: 60 }
      前端按 expireTime 存入 storage，到期重取

③ 之后每个业务请求都带全凭证束（见 §4.3）
```

> 这两个引导接口同样走 `{环境域名}/api` 前缀，且不追加 `__sr`。

### 5.3 短时效凭证的续期

| 凭证 | 来源 | 有效期 | 前端策略 |
|---|---|---|---|
| `X-XSRF-TOKEN` | `/getCsrfToken` 的 `expireTime` | 由响应给出 | 缺失或过期时先取再发；取不到则本次请求以 `XSRF_RENEWAL_FAILED` 终止 |
| `X-MZ-SESSION` | 响应头 `x-mz-session` | 响应头 `x-mz-session-expire` | 本地已有值时不覆盖 |
| `Mzoperateid` | 响应头 `Mzoperateid` / `MZOPERATEID` | 43200s（源码写死） | 每次响应刷新 |

### 5.4 模块 7188 白名单的真实作用

白名单共 26 条、去重后 25 条（`/api/user/registerSetPass` 重复一次），完整清单：

```text
/api/trade/addCartGood                /api/trade/confirmGroupedCart
/api/trade/submitGroupedCart          /api/shoppingCart/addCartGood
/api/shoppingCart/checkGoodSale       /api/pms/chooseSeat/queryAreaInfo
/api/pms/chooseSeat/queryDynamicInfo  /api/pms/chooseSeat/querySeatStatus
/api/venue/getOssBashPath             /api/queryStandPriceColor
/api/querySalableSeat                 /api/msg/sendMobileMsg
/api/user/userLogin                   /api/msg/sendEmailMsg
/api/user/registerSetPass             /api/user/validateUserV2
/api/login/authLoginForApplet         /api/pro/event
/api/pro/events                       /api/order/applyRefund
/api/act/registerUser                 /api/act/registerActDetail
/api/act/registerActParticipate       /api/act/registerActAssist
/api/aliyuMember/pointsMall/prize/exchange
```

它的作用**只有一个**：当请求 URL 命中该清单、且当前是支付宝渠道时，请求层才调用 baxia SDK 取 `wua` / `umid` 并附加 `x-mini-wua` / `x-umt` 头。它**与 toast 提示、登录态校验无关**（旧版文档此处描述有误，已更正）。

> 清单里的 `/api/pro/events`、`/api/venue/getOssBashPath`、`/api/queryStandPriceColor`、`/api/querySalableSeat` 及三个选座查询接口没有出现在任何调用点，仅作为白名单条目存在。

---

## 六、签名与加密

### 6.1 总览

| 维度 | 结论 | 可离线复现 |
|---|---|---|
| `sign` 请求参数 | 不存在 | — |
| `hecate` / `epeius` / `coeus` | 风控头，算法在包内 | ✅ |
| 请求体敏感字段 | RSA-2048 / PKCS#1 v1.5 | ✅ |
| 登录 `encryptedData` / `iv` | 微信平台产出 | ❌ 需真机 |
| `bx-umidtoken` / `x-ticid` | baxia SDK / hook 注入 | ❌ 设备绑定 |
| `x-mini-wua` / `x-umt` | baxia SDK（仅支付宝） | ❌ 设备绑定 |

### 6.2 字段级 RSA 加密

**实现位置**：模块 `93323`。

- `w(v)`：`setPublicKey(x)` 后 `encrypt(string)`，输出 **Base64**；RSA-2048 下密文固定 344 字符
- RSA 实现：模块 `10655`，JSEncrypt 风格的自包含库，默认 padding 即 **PKCS#1 v1.5**
- 公钥 `x`：X.509 SubjectPublicKeyInfo，DER 长度 294 字节，对应 RSA-2048（`aliyu_pubkey.pem` 中长度一致）
- `U(url, data)`：**仅 POST** 时执行，先深拷贝再递归遍历（`D` 函数），命中字段的字符串/非零数字才加密
- 空字符串与数字 `0` 保持原样；加密异常时返回原文（小程序侧降级）

**R 表（16 个接口）**：

| 接口 | 加密字段 |
|---|---|
| `/user/userLogin` | loginPass, phone, email |
| `/user/validateUserV2` | userEmail, loginPass, userTel, contactInfo |
| `/user/registerSetPass` | loginPass, userTel, userEmail, userCard, emergencyContact, emergencyContactTelphone |
| `/user/modifyUser` | userCard, emergencyContact, emergencyContactTelphone |
| `/user/findPass` | mobile, email |
| `/user/findPassCheck` | newPass |
| `/user/resetMobileOrEmail` | email, phone |
| `/user/resetPwdValidate` | mobile, email |
| `/user/resetPwdSet` | newPass |
| `/user/addDis` | email, telPhone |
| `/user/modifyDis` | email, telPhone |
| `/user/addBearer` | identification |
| `/msg/sendMobileMsg` | telephone |
| `/msg/sendEmailMsg` | email |
| `/trade/submitGroupedCart` | receiveCard, receiveName, paymentPassword |
| `/invoice/simple/applyInvoice` | buyerTel, email |

**交叉验证**：RSA-2048 + PKCS#1 v1.5 的密文为 256 字节，Base64 后正好 344 字符，与包内库输出长度吻合；同一明文经包内库与 JDK `RSA/ECB/PKCS1Padding` 加密的结果一致（历史沙箱验证）。

### 6.3 风控头 hecate / epeius / coeus

三者全部在模块 `93323` 内本地计算，**不需要任何 SDK**：

```text
epeius = (now - parseInt(timeCompare)) || now        // 毫秒时间戳
hecate = MD5( JSON.stringify( canon(data) + epeius ) )
coeus  = URL 命中固定路径清单 ? "10010" : "10086"
```

**epeius**：`timeCompare` 在整包中只被读取、从未被写入，`parseInt(undefined)` 得 `NaN`，`now - NaN` 为假值，于是回落到 `now`。因此该头**恒等于当前毫秒时间戳**，不是时钟差。

**hecate** 的计算分三步，源码为 `h({data, t})`：

1. `canon(data)`：深拷贝后递归，对象键按 `localeCompare` 排序，数组保持顺序，所有叶子值转成字符串，`null` → `"null"`；`data` 缺失时按 `{}` 处理。
2. 附加 `epeius`：结果是对象则加键 `epeius`，是数组则 `push`，是标量则直接字符串拼接。
3. 对 `JSON.stringify(结果)` 取 **MD5 小写十六进制**。

关键点：参与签名的 `data` 是**加密前的明文请求体**。`U()` 的产物只赋给 `t.data`（网络层发送用），`hecate` 仍引用原始变量。GET 请求没有 `data`，等价于对 `{"epeius":"<时间戳>"}` 取 MD5。

**coeus**：源码数组共 14 个条目，其中 `/pro/prices` 重复一次，实际生效 13 条。

`coeus` 的 13 条路径：

```text
/user/loginUser            /pro/projects            /pro/eventDiscount
/pro/prices                /order/detail            /user/userCenter
/user/getDis               /region/getRegionsByParentId
/user/addBearer            /user/modifyDis          /user/modifyUser
/user/userInfoGroupDesensitization                  /user/modifyUserBrief
```

> 旧版文档把 `hecate` / `epeius` / `coeus` 一并归为「设备绑定、无法离线生成」，**这是错误的**：它们是确定性算法，只要与请求体一致即可复算。真正无法离线生成的是 `bx-umidtoken`、`x-ticid`、`x-mini-wua`、`x-umt`。

### 6.4 微信一键登录（不可离线复现）

v37 中有**两条并行的登录路径**，落点不同：

```text
① 动态选择（旧版一键登录）
   getLoginApi() = 微信小程序 ? "/login/authLogin" : "/login/authLoginForApplet"
   渠道标记来自模块 79514：at = 微信、Nc = 支付宝、Vi = 抖音

② 字面量直连（新版自动登录，v37 主用）
   POST /login/authLoginForApplet            # 无渠道分支
   { langType: 1, authCode, authToken, ext: { iv, encryptedData },
     appType, skipAutoBind?, sqmCode?, inviteCode? }
```

第二条路径的 URL 是写死的，因此能被解包工具直接提取；第一条的 URL 是变量，提取不到。**Java 侧实现的是 `/login/authLoginForApplet`**（路径 ②）。

旧版流程还存在另外几种载荷变体：

```text
形态 A（带手机号与验证码）
  { appType, authCode, authToken, ext: { iv, encryptedData, phone, messageCode } }

形态 A'（支付宝分支 g.Nc）
  { appType, authCode, authToken, ext: { encryptedData } }

形态 B（复用缓存的手机号凭证）
  { langType: 1, jsCode, encryptedIv, encryptedData, userTel }
```

各形态都可能附带 `sqmCode`（来自阿里安全环境）与 `inviteCode`（邀请码）。

**关键澄清**：

- `authCode` / `jsCode` 是 `wx.login()` 返回的 code，**本身不加密**
- `encryptedData` / `iv` 来自**微信 `wx.getPhoneNumber` 授权回调**，小程序只是透传 + `setStorageSync` 暂存，**不是本地 RC4/RSA 产出**
- `encryptedData` 嵌在 `ext` 内，不是顶层字段
- 这些值必须在真机微信环境获取（抓包或注入），离线无法生成

### 6.5 其他加密信号

分析工具检出 `hmac-sha256`、`rc4`，均来自 mpassport 登录 SDK 内置的 CryptoJS 风格库（`StreamCipher`、`encrypt(`/`decrypt(` 调用链），属 SDK 自用，未作用于业务请求体。`md5` 则是模块 `93323` 内联实现，服务于 §6.3 的 `hecate`。

### 6.6 Java 侧与小程序的差异

| 差异点 | 小程序 | Java 实现 | 原因 |
|---|---|---|---|
| 加密失败 | 返回明文 | 抛 `EruptWebApiRuntimeException` | 避免手机号/密码以明文发往上游 |
| 加密范围 | 仅 POST 请求体 | 一致 | GET 的 data 拼 query，不参与加密 |
| 空值/0 | 不加密 | 一致 | 沿用真值判断 |
| `langType` 查询参数 | 总是追加 | 未追加 | 试跑中未见影响，暂按可选处理 |
| `Mzoperateid` | 响应头回写并续 43200s | 从用户配置读取 | 凭证束由人工绑定，不自动回写 |

---

## 七、接口清单（122 个业务接口）

> 路径均为相对路径，实际请求需补 `https://client.maitix.com/api` 与 `__sr`、`langType`。
> 7.1～7.12 合计 122 个业务接口；7.13 的 2 个外部 URL 不属于业务接口。

### 7.1 登录与用户（19）

| 方法 | 路径 | 说明 |
|---|---|---|
| POST | /login/authLoginForApplet | 小程序授权登录（凭证由微信产出）；另有渠道分支走 `/login/authLogin`，URL 为变量，未计入本表 |
| GET | /user/loginUser | 当前登录用户 |
| GET | /user/userInfo | 用户信息 |
| GET | /user/userContact | 用户联系方式 |
| GET | /user/getBearers | 持票人列表 |
| GET | /user/getDis | 联系人/收件人列表 |
| GET | /user/getDisInfo | 联系人详情 |
| POST | /user/userLogin | 账号密码登录（RSA） |
| POST | /user/validateUserV2 | 用户校验 V2（RSA） |
| POST | /user/registerSetPass | 注册并设置密码（RSA） |
| POST | /user/findPass | 找回密码（RSA） |
| POST | /user/findPassCheck | 找回密码校验（RSA） |
| POST | /user/modifyUser | 修改用户信息（RSA） |
| POST | /user/modifyDis | 修改联系人（RSA） |
| POST | /user/addBearer | 新增持票人（RSA） |
| POST | /user/removeBearer | 删除持票人 |
| POST | /user/addDis | 新增联系人（RSA） |
| POST | /user/removeDis | 删除联系人 |
| POST | /user/loginOff | 退出登录 |

### 7.2 阿里鱼会员与积分（17）

| 方法 | 路径 | 说明 |
|---|---|---|
| GET | /aliyupoint/userInfo | 阿里鱼会员信息 |
| GET | /aliyupoint/getMemberInfo | 会员详情 |
| GET | /aliyupoint/getMemberHomePageInfo | 会员首页 |
| GET | /aliyupoint/checkMember | 会员资格校验 |
| GET | /aliyupoint/getPointsDetail | 小鱼丸明细 |
| GET | /aliyupoint/getPointsRule | 积分规则 |
| GET | /aliyupoint/getGrowthValueDetail | 成长值明细 |
| GET | /aliyupoint/getGrowthValueTierInfo | 成长值等级 |
| GET | /aliyupoint/getDynamicQrCode | 动态会员码 |
| POST | /aliyupoint/modifyUser | 修改会员资料 |
| POST | /aliyupoint/deregisterUser | 注销会员 |
| POST | /aliyupoint/batchSubscribe | 批量订阅消息 |
| POST | /aliyupoint/batchQuerySubscribed | 批量查询订阅 |
| POST | /aliyupoint/task/list | 任务列表 |
| POST | /aliyupoint/task/checkStatus | 任务状态 |
| POST | /aliyupoint/task/claim | 领取任务积分 |
| POST | /aliyupoint/task/report | 任务行为上报 |

### 7.3 积分商城（17）

| 方法 | 路径 | 说明 |
|---|---|---|
| GET | /aliyuMember/pointsMall/currentStore | 当前门店 |
| GET | /aliyuMember/pointsMall/prize/list | 奖品列表 |
| GET | /aliyuMember/pointsMall/prize/detail | 奖品详情 |
| GET | /aliyuMember/pointsMall/prize/check | 兑换校验 |
| GET | /aliyuMember/pointsMall/prize/store | 奖品可用门店 |
| POST | /aliyuMember/pointsMall/prize/exchange | 奖品兑换 |
| GET | /aliyuMember/pointsMall/wallpaper/list | 壁纸列表 |
| GET | /aliyuMember/pointsMall/wallpaper/detail | 壁纸详情 |
| GET | /aliyuMember/pointsMall/wallpaper/seriesDetail | 壁纸系列详情 |
| GET | /aliyuMember/pointsMall/wallpaper/check | 壁纸兑换校验 |
| GET | /aliyuMember/pointsMall/wallpaper/download | 壁纸下载 |
| GET | /aliyuMember/pointsMall/wallpaper/dressHome | 装扮首页 |
| POST | /aliyuMember/pointsMall/wallpaper/exchange | 壁纸兑换 |
| GET | /aliyuMember/memberCoupon/detail | 会员券详情 |
| POST | /aliyuMember/memberCoupon/list | 会员券列表 |
| GET | /aliyuMember/order/pointsExchange/detail | 兑换订单详情 |
| POST | /aliyuMember/order/pointsExchange/list | 兑换订单列表 |

### 7.4 阿里鱼活动（5）

| 方法 | 路径 | 说明 |
|---|---|---|
| GET | /aliyu/act/fissionDrawActDetail | 裂变抽奖详情 |
| GET | /aliyu/act/getInviteCode | 邀请码 |
| GET | /aliyu/act/getShareImg | 分享图 |
| POST | /aliyu/act/registerActParticipate | 报名参与 |
| POST | /aliyu/act/registerActAssist | 助力 |

### 7.5 活动报名与抽奖（6）

| 方法 | 路径 | 说明 |
|---|---|---|
| GET | /act/registerActDetail | 报名活动详情 |
| POST | /act/registerActList | 报名活动列表 |
| POST | /act/registerUser | 活动报名 |
| POST | /act/drawList | 抽奖记录 |
| POST | /event/register/eventRegister | 活动注册 |
| POST | /event/register/updateSaleRemind | 更新开售提醒 |

### 7.6 演出项目与地区（17）

| 方法 | 路径 | 说明 |
|---|---|---|
| GET | /pro/projects | 项目列表 |
| GET | /pro/project | 项目详情 |
| GET | /pro/event | 场次详情 |
| GET | /pro/eventsByDate | 按日期查场次 |
| GET | /pro/getShowCalendar | 演出日历 |
| GET | /pro/getColumnInfo | 栏目信息 |
| GET | /pro/hotProjectVos | 热门项目 |
| GET | /pro/privilegeCheck | 权益校验 |
| POST | /pro/hasSaleRemind | 设置开售提醒 |
| GET | /proType/getProjectTypeList | 项目分类 |
| GET | /region/getProjectCityList | 项目城市 |
| GET | /region/getAreaCityList | 地区城市 |
| GET | /region/getRegionByCountryId | 按国家查地区 |
| GET | /region/getRegionByProvinceId | 按省查地区 |
| GET | /region/loadReceiveBasicInfo | 收件基础信息 |
| GET | /item/itemDetail | 商品详情 |
| GET | /item/itemBuy | 商品购买 |

### 7.7 订单（10）

| 方法 | 路径 | 说明 |
|---|---|---|
| GET | /order/queryOrderList | 订单列表 |
| GET | /order/queryOrderDetail | 订单详情 |
| GET | /order/queryOrderDetailNew | 订单详情（新 UI） |
| GET | /order/calculateRefundAmount | 计算退款金额 |
| GET | /order/ticket/discount/receive | 领取票务折扣 |
| POST | /order/cancelOrder | 取消订单 |
| POST | /order/delOrder | 删除订单 |
| POST | /order/applyRefund | 申请退款 |
| POST | /order/orderRefundConfirm | 退款确认 |
| POST | /order/restTickets | 退票 |

### 7.8 交易与购物车（6）

| 方法 | 路径 | 说明 |
|---|---|---|
| POST | /trade/renderConfig | 下单页渲染配置 |
| POST | /trade/addCartGood | 加入购物车 |
| POST | /trade/confirmGroupedCart | 确认分组购物车 |
| POST | /trade/submitGroupedCart | 提交下单（RSA） |
| POST | /shoppingCart/addCartGood | 购物车加商品 |
| POST | /shoppingCart/checkGoodSale | 校验商品在售 |

### 7.9 选座 / 支付 / 发票 / 消息（9）

| 方法 | 路径 | 说明 |
|---|---|---|
| GET | /pms/chooseSeat/seatCachePreheat | 选座缓存预热 |
| GET | /maipay2/renderPay | 支付页渲染 |
| POST | /maipay2/doPay | 发起支付 |
| GET | /invoice/simple/getInvoicingOrderList | 可开票订单列表 |
| GET | /invoice/simple/getInvoicingOrderDetail | 可开票订单详情 |
| GET | /invoice/simple/invoiceGray | 开票灰度 |
| POST | /invoice/simple/applyInvoice | 申请开票（RSA） |
| POST | /msg/sendMobileMsg | 短信验证码（RSA） |
| POST | /msg/sendEmailMsg | 邮件验证码（RSA） |

> 另有 `/pms/chooseSeat/queryAreaInfo`、`queryDynamicInfo`、`querySeatStatus`、`/venue/getOssBashPath`、`/queryStandPriceColor`、`/querySalableSeat` 六个接口**只出现在白名单里，没有调用点**，未计入 122。

### 7.10 内容与配置（8）

| 方法 | 路径 | 说明 |
|---|---|---|
| GET | /cms/getCdn | CDN 配置 |
| GET | /cms/basicSetting/getBasicSetting | 基础设置 |
| GET | /cms/homepage/getHomepageSetting | 首页配置 |
| GET | /cms/homepage/getInformationDetail | 资讯详情 |
| GET | /cms/homepage/getSiteDetail | 站点详情 |
| GET | /pageConfig/getPageConfig | 页面配置 |
| GET | /pageConfig/getBackUrl | 回跳地址 |
| GET | /common/currentTimestamp | 服务端时间戳 |

### 7.11 协议 / 凭证 / 地区（6）

| 方法 | 路径 | 说明 |
|---|---|---|
| GET | /agreement/detail | 协议详情 |
| GET | /agreement/deregistration | 注销协议 |
| GET | /agreement/record/list | 协议记录列表 |
| GET | /agreement/record/{recordId} | 协议记录详情 |
| GET | /credential/getList | 凭证列表 |
| GET | /countryArea/queryForUserRegister | 国家地区（注册用） |

### 7.12 弹窗营销（2）

| 方法 | 路径 | 说明 |
|---|---|---|
| POST | /aliyuPopUp/query | 查询弹窗配置 |
| POST | /aliyuPopUp/record | 弹窗曝光记录 |

### 7.13 非业务 URL（2）

| 方法 | URL | 说明 |
|---|---|---|
| GET | `https://fourier.taobao.com/rp?ext=52` | 淘宝埋点 |
| GET | `https://damaipopcorn.alibaba-inc.com/mock/getMockCases?id={mockId}` | 大麦 mock 平台（开发用） |

---

## 八、页面路由（46 个）

共 73 条跳转调用，去重后 46 个页面。

| 分包 | 路由 | 关键参数 |
|---|---|---|
| 主包 | /pages/home/index | msgKey |
| | /pages/member/index | — |
| | /pages/order/index | orderToken |
| | /pages/search/index | backUrl, type |
| | /pages/debug/index | — |
| pages-member | /pages-member/growthValue/index | — |
| | /pages-member/growthValueRules/index | url |
| | /pages-member/memberLevel/index | — |
| | /pages-member/memberCode/index | cardType |
| | /pages-member/pointsDetail/index | cardType |
| pages-order | /pages-order/projectDetail/index | projectId |
| | /pages-order/projectSKU/index | projectId |
| | /pages-order/activityDetail/index | actId |
| | /pages-order/lotteryDetail/index | actId |
| | /pages-order/confirmOrder/index | eventIds / orderId |
| | /pages-order/orderInfo/index | orderToken |
| | /pages-order/cashier/index | orderId |
| | /pages-order/pay/index | payConfig |
| | /pages-order/itemDetail/index | itemId, bizType |
| | /pages-order/invoice/index | tab |
| | /pages-order/invoice/invoiceInfo/index | orderId |
| | /pages-order/invoice/historyInfo/index | tradeInvoiceId |
| | /pages-order/shopOrderInfo/index | orderToken |
| pages-others | /pages-others/activityList/index | — |
| | /pages-others/addAddress/index | disId |
| | /pages-others/addressManagement/index | — |
| | /pages-others/addAudience/index | — |
| | /pages-others/audienceManagement/index | — |
| | /pages-others/myCoupon/index | — |
| | /pages-others/historyCoupon/index | — |
| | /pages-others/myAccount/index | — |
| | /pages-others/dressUp/index | — |
| | /pages-others/diversion/index | — |
| | /pages-others/applicableStores/index | 门店查询参数 |
| | /pages-others/paySuccess/index | orderId |
| | /pages-others/newLogin/index | scene, redirectUrl |
| | /pages-others/protocolIndex/index | — |
| | /pages-others/protocolAssment/index | type |
| | /pages-others/protocolAssmentDetail/index | type, recordId, version |
| | /pages-others/refundRuleDetails/index | projectId |
| | /pages-others/deregisterUser/index | — |
| pages-points-mall | /pages-points-mall/detail/index | pointsPrizeId, cardId |
| | /pages-points-mall/orderConfirm/index | pointsPrizeId, cardId, prizeType |
| | /pages-points-mall/orderDetail/index | orderId |
| | /pages-points-mall/orderResult/index | state, orderId, pointsPrizeId, prizeType |
| | /pages-points-mall/wallpaperDetail/index | seriesId, materialId, cardId |

---

## 九、核心业务链路

> 以下链路按各分包的 saga / action 调用点归纳，**接口归属可靠，先后顺序仅供参考**（真实执行受页面跳转与 redux 状态影响）。

### 9.1 启动

```text
App.onLaunch
  → /partner/queryMDomain             # 租户信息 → 生成 __sr
  → /getCsrfToken                   # CSRF（短时效，按 expireTime 续期）
  → /cms/getCdn
  → /cms/basicSetting/getBasicSetting
  → /cms/homepage/getHomepageSetting
  → /pageConfig/getPageConfig
  → /common/currentTimestamp
  → /aliyupoint/getMemberHomePageInfo   # 已登录时才请求
  → /aliyuPopUp/query
```

未登录时首页带 `msgKey=MZ_NO_LOGIN`，跳转 `/pages-others/newLogin/index?scene=native&redirectUrl=...`。

### 9.2 登录

```text
wx.login()          → code（authCode / jsCode）
wx.getPhoneNumber() → { iv, encryptedData }   # 微信产出，本地仅透传
  → POST /login/authLoginForApplet            # 渠道分支见 §6.4
  ← 服务端下发 cookie（JSESSIONID / MZCONSUMERJSESSIONID / MZOPERATEID）
  → 后续请求携带完整凭证束
```

### 9.3 演出购票

```text
浏览   /pro/projects → /pro/project → /pro/event → /pro/eventsByDate
选座   /pms/chooseSeat/seatCachePreheat
确认   /trade/renderConfig → /shoppingCart/checkGoodSale
       /user/getBearers → /user/getDis → /region/loadReceiveBasicInfo
       /order/ticket/discount/receive
提交   /trade/submitGroupedCart  ← orderId / orderToken
支付   /maipay2/renderPay → /maipay2/doPay → /pages-others/paySuccess
```

### 9.4 订单与退款

```text
列表 /order/queryOrderList          详情 /order/queryOrderDetailNew
取消 /order/cancelOrder             删除 /order/delOrder
退款 /order/calculateRefundAmount → /order/applyRefund → /order/orderRefundConfirm
退票 /order/restTickets             开票 /invoice/simple/invoiceGray → applyInvoice
```

### 9.5 积分商城兑换

```text
奖品   /aliyuMember/pointsMall/prize/list → detail → check → store
兑换   POST /aliyuMember/pointsMall/prize/exchange   # 扣小鱼丸
订单   /aliyuMember/order/pointsExchange/list → detail
```

### 9.6 壁纸兑换

```text
list → seriesDetail → detail → check → POST exchange → download → dressHome
```

### 9.7 裂变 / 抽奖活动

```text
入口   /aliyu/act/fissionDrawActDetail → /act/registerActDetail
参与   POST /aliyu/act/registerActParticipate
分享   /aliyu/act/getInviteCode → /aliyu/act/getShareImg
助力   POST /aliyu/act/registerActAssist
结果   POST /act/drawList
```

### 9.8 会员与任务

```text
会员   /aliyupoint/getMemberInfo → getDynamicQrCode（到店出示）
成长   /aliyupoint/getGrowthValueTierInfo → getGrowthValueDetail
积分   /aliyupoint/getPointsDetail → getPointsRule
任务   task/list → task/report → task/checkStatus → task/claim
订阅   batchSubscribe / batchQuerySubscribed
```

---

## 十、Java 侧实现对照

请求基类 `AliyuBaseClient` 统一声明：`baseURL = https://client.maitix.com/api`、`contentType = application/json`、拦截器链 **加密 → 鉴权 → 响应**。

| 职责 | 文件 |
|---|---|
| 接口定义（15 个 Client：13 个业务域共 122 个方法 + 2 个引导接口） | `client/http/Aliyu*Client.java` |
| 字段级 RSA 加密 | `crypto/AliyuFieldEncryptor.java` |
| 加密拦截器（含 R 表 16 条） | `client/http/interceptor/AliyuEncryptInterceptor.java` |
| 凭证束注入 + `__sr` | `client/http/interceptor/AliyuAuthInterceptor.java` |
| CSRF / 会话缓存与续期 | `client/http/interceptor/AliyuCredentialCache.java` |
| 响应校验 + 会话回写 | `client/http/interceptor/AliyuResponseInterceptor.java` |
| CSRF 获取（不继承 BaseClient，避免递归） | `client/http/AliyuCsrfClient.java` |
| 绑定前凭证校验（不继承 BaseClient） | `client/http/AliyuCredentialClient.java` |
| 常量（租户、启动链路、头名、baxia） | `constant/AliyuApiConstants.java` |
| 凭证束模型 / 入参 / 出参 | `model/bo/AliyuUserConfigBO.java`、`model/dto/AliyuUserSettingReqDTO.java`、`model/vo/AliyuUserConfigVO.java` |
| 响应 DTO | `model/dto/resp/*.java`（51 个） |
| RSA 公钥 | `src/main/resources/aliyu/aliyu_pubkey.pem` |
| 绑定接口 | `controller/user/AliyuUserSettingController.java`（`GET/PUT /erupt-api/aliyu/user-setting/me`） |

**设计取舍**：

- 全量鉴权：不模拟小程序的白名单，缺 cookie / 缺 CSRF 直接抛错，避免匿名打到上游拿到误导性业务错误
- 凭证绑定前先调 `/user/loginUser` 校验，无效凭证不落库
- 查询参数 `userId` 可由 `@Var` 传入，支持无登录上下文的定时任务
- `AliyuUserConfigBO` / `AliyuUserSettingReqDTO` 中的凭证字段均标 `@ToString.Exclude`

---

## 十一、复现产物与验证

解包产物：`/Users/bixiu/MyProjects/wx-mp-mcp/static/out/wx88adba08e6cf3e75/`

```text
├── report.json          # 结构化报告（198 条调用记录 + crypto 信号）
├── REPORT.md            # 工具自动生成报告
├── repro.node.mjs       # Node.js 复现代码
├── repro.python.py      # Python 复现代码
└── app/                 # 解包源码（主包 + 4 分包）
```

**本仓库内可复核的结论**：

- 接口覆盖：`report.json` 提取的 122 个业务接口与 Java 侧 `@Get`/`@Post` 声明逐条比对，**完全一致**（唯一差异是路径变量名写作 `{record_id}` 与 `{recordId}`）
- 接口规模拆分：198 条记录 = 122 业务接口 + 73 页面跳转（去重 46）+ 2 外部 URL + 1 动态基址调用
- 公钥规模：`aliyu_pubkey.pem` 解出的 DER 为 294 字节，即 RSA-2048

**历史执行记录**（当时未入库，无法就地复核）：

- 交叉验证：包内 RSA 库与 JDK `RSA/ECB/PKCS1Padding` 对同一明文的加密输出一致（344 字符 Base64）
- 接口试跑：122 个接口全量请求，57 个返回真实业务数据，10 个被边缘 WAF 拦截（302 → err.taobao.com），其余为参数或数据层面的业务错误
- 端到端 6/6 通过：CSRF、会员信息（3 层嵌套）、登录用户、项目列表、壁纸系列、基础设置

---

## 十二、已知边界

| 事项 | 状态 |
|---|---|
| `bx-umidtoken` / `x-ticid` / `x-mini-wua` / `x-umt` | 设备绑定，无法离线生成；当前 Java 侧从用户配置读取（可留空） |
| 微信 `encryptedData` / `iv` | 需真机授权或抓包获取 |
| `hecate` / `epeius` / `coeus` | 算法已还原，可在 Java 侧自行计算；当前实现仍按配置注入 |
| 边缘 WAF | 部分路径在特定来源下返回 302 到 err.taobao.com，属链路限制 |
| `langType` | Java 侧未追加该查询参数（试跑中未见影响） |
| `mini-janus` | 解包产物中未出现该头，仅 Java 常量保留了可配置字段 |

---

## 十三、总结

| 维度 | 结论 |
|---|---|
| 平台 | 卖座 maitix SaaS + 阿里鱼营销层 |
| 规模 | 122 个业务接口 + 46 个页面 |
| 基址 | `https://client.maitix.com/api` |
| 鉴权 | 会话凭证束，无 `sign` 参数 |
| 风控 | `hecate` / `epeius` / `coeus` 可复算；baxia 头需真机 |
| 加密 | 16 个接口的敏感字段 RSA-2048（PKCS#1 v1.5），已由拦截器自动处理 |
| 难点 | 微信手机号凭证与设备指纹无法离线生成，其余链路均已打通 |
