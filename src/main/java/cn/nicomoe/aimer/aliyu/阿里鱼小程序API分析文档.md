# 阿里鱼小程序 API 分析文档

> 本文以“协议 → 鉴权 → 风控加密 → 接口 → 路由 → 链路”为主线整理。接口路径均为相对路径，除非特别说明，实际请求需补 `https://client.maitix.com/api`、`__sr` 与 `langType`。

---

## 0. 速览

| 维度 | 结论 |
|---|---|
| 平台性质 | 卖座（maitix）为阿里鱼定制的前端，业务接口统一走 `client.maitix.com` |
| 请求基座 | `{环境域名}/api{urlTemplate}`，业务请求追加 `__sr`、GET 参数与 `langType` |
| 鉴权核心 | `cookie` + `X-MZ-SESSION` + `X-XSRF-TOKEN` + `Mzoperateid`，不存在单 token 简化路径 |
| 风控头 | `epeius` / `hecate` / `coeus` 为包内确定性算法，可离线复算 |
| 请求体加密 | 敏感字段 RSA-2048 / PKCS#1 v1.5，仅 POST，加密前明文参与 `hecate` 签名 |
| 不可离线复现 | 微信 `encryptedData` / `iv`、baxia `bx-umidtoken` / `x-ticid` / `x-mini-wua` / `x-umt` |
| 接口规模 | 129 个已确认的接口（含启动配置，不含 CSRF）；页面跳转和静态资源不计入 |
| 页面路由 | 去重后 46 个页面 |

---

## 1. 小程序概况

### 1.1 基本信息

| 项目 | 值 |
|---|---|
| 名称 | 阿里鱼娱乐营销平台 |
| AppID | `wx88adba08e6cf3e75` |
| 商户 | 阿鲤鱼（杭州）文化传播有限公司 |
| 商户前端域名 | `h5.alifishofficial.com` |

### 1.2 技术架构

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

### 1.3 平台定位与接口边界

这是**卖座（maitix）为阿里鱼定制的前端**，不是独立后端：

- 业务接口统一走 `client.maitix.com`，如 `/pro/projects`、`/order/queryOrderList`，属于卖座基座。
- `/aliyu`、`/aliyupoint`、`/aliyuMember`、`/aliyuPopUp` 系列是叠加在卖座基座上的营销扩展，覆盖积分、会员、裂变、壁纸等。
- “活动”存在两套接口，不要混用：
  - 阿里鱼侧裂变抽奖：`/aliyu/act/*`
  - 卖座侧报名/开售提醒：`/act/*`、`/event/*`

### 1.4 环境与租户

模块 `27542` 定义四套环境，通过 `wx.getStorageSync("SERVICE_PATH")` 切换，默认正式环境。

| 环境 | 域名 | 随环境附带 ptnrId |
|---|---|---|
| 正式（默认） | `https://client.maitix.com` | 无；租户身份由 `__sr` 反解析 |
| 集成 | `https://pre-rest.maitix.com` | `32161` |
| 测试 | `https://pre-rest-test.maitix.com` | `32160` |
| 开发 | `https://pre-rest-dev.maitix.com` | `32159` |

> `32159～32161` 是环境自带调试商户号；正式环境真正的商户号 `32138` 由启动接口 `/partner/queryMDomain` 返回，两者不要混用。

---

## 2. 请求与响应协议

### 2.1 URL 构造

模块 `93323` 的最终 URL 由三段拼成：

```text
{环境域名}/api{urlTemplate}?__sr={encodeURIComponent("https://" + ptnrDomainMFrontend + "/")}
                            [&{GET 的 data}]
                            &langType={1|2|3}
```

拼接顺序固定：

1. `baseURL + "/api" + urlTemplate`
2. 追加 `__sr`；若 URL 已含 `?` 则用 `&`
3. GET 的 `data` 序列化后追加，数组使用 `repeat` 格式，并清空请求体
4. 最后追加 `langType`

说明：

- `urlTemplate` 均以 `/` 开头，如 `/order/queryOrderDetail`。
- `__sr` 用于租户反解析，每个业务请求必带，值固定为 `https://h5.alifishofficial.com/` 的 URL 编码形式。
- `langType` 映射：简体 `1`、英文 `2`、繁体 `3`。
- 两个启动引导接口 `/partner/queryMDomain`、`/getCsrfToken` 同样走 `{环境域名}/api`，但**不追加 `__sr`**。

完整示例：

```text
GET https://client.maitix.com/api/order/queryOrderDetail?__sr=https%3A%2F%2Fh5.alifishofficial.com%2F&isNewUI=true&langType=1
```

### 2.2 请求头与凭证束

`__sr` 是查询参数，不是请求头；`X-MZ-SESSION` / `X-XSRF-TOKEN` 才是身份与协议头。模块 `93323` 将下列凭证逐项注入，不存在“一个 token 走天下”的简化路径。

| 名称/位置 | 取值/来源 | 作用 | 有效期/续期 | 备注 |
|---|---|---|---|---|
| `__sr`（查询参数） | `encodeURIComponent("https://h5.alifishofficial.com/")` | 租户标识，决定请求落到哪个商户 | 每个业务请求必带 | 启动接口不追加；由 `ptnrDomainMFrontend` 生成 |
| `cookie` | 小程序自维护 cookie 容器，含 `JSESSIONID` / `MZCONSUMERJSESSIONID` / `MZOPERATEID` | 持久身份 | cookie 失效即判未登录 | `getCookieToSend()` 返回拼好的 cookie 串，外观像单个 token，实际是浏览器式 cookie 容器 |
| `X-MZ-SESSION` | 登录时下发，须真机抓包 | maitix 会话标识 | 响应头 `x-mz-session-expire`；本地已有值时不覆盖 | 缺失即判未登录（`804 NO_LOGIN`）；未携带会话时服务端只下发匿名会话，无法自举登录态；配置值优先于响应头回写 |
| `Mzoperateid` | 服务端下发 | 操作者标识 | `43200s` | 每次响应刷新；实测业务接口不校验，缺失不影响返回 |
| `site` | `mp_weixin` | 渠道标识 | 固定 | 另有 `mp_douyin` / `mp_alipay` / `m` |
| `Content-Type` | `application/json` | POST 请求体类型 | 仅 POST 设置 | — |
| `X-XSRF-TOKEN` | `/getCsrfToken` 换取 | CSRF 令牌 | 由 `expireTime` 给出，约 60s | POST 必填；缺失时请求层先取再发，取不到则终止（`XSRF_RENEWAL_FAILED`） |
| `epeius` | 当前时间戳（毫秒） | 风控头 | 每请求 | 本地确定性计算，见 §4.3 |
| `hecate` | MD5 摘要 | 风控头 | 每请求 | 明文请求体 + `epeius` 的签名，见 §4.3 |
| `coeus` | `10010` / `10086` | 风控头 | 每请求 | 由 URL 是否命中固定路径清单决定，见 §4.3 |
| `x-mini-wua` / `x-umt` | baxia SDK | 设备与风控 | 每请求现算 | 仅支付宝渠道 + URL 命中模块 7188 白名单时附加，见 §4.4；Java 侧不注入 |
| `bx-umidtoken` / `x-ticid` | baxia 安全 SDK | 设备标识 | 每请求现算 | 含 URL 哈希与递增计数器，不可离线复现；Java 侧不注入 |

> 凭证必须成组使用：cookie 失效时，其余头齐全也会判未登录。

### 2.3 响应结构与错误码

```json
{
  "code": "200",
  "msg": "",
  "msgKey": "",
  "data": {}
}
```

- 成功判定：`String(code) === "200"`（字符串比较）。
- 前端特判错误码：
  - `804` / `23010016`：触发重新登录
  - `819` / `820` / `150601167` / `150601168`：专用文案
  - `602`：静默失败
  - `603`：提示“太火爆”
  - `305`：提示页面停留过久
  - `422` / `423`：特殊跳转
- 页面配置类接口（`.json`）不走该响应结构。

---

## 3. 鉴权与登录

### 3.1 启动引导与启动链路

```text
① GET /api/partner/queryMDomain?miniProgramAppId=wx88adba08e6cf3e75
   ← { ptnrDomainMFrontend: "h5.alifishofficial.com", ptnrId: "32138",
       ptnrName: "阿鲤鱼(杭州)文化传播有限公司" }
      前端保存 ptnrDomainMFrontend，用于生成 __sr

② GET /api/getCsrfToken
   ← { xsrfToken: "...", expireTime: 60 }
      前端按 expireTime 存入 storage，到期重取

③ 后续业务请求携带完整凭证束（见 §2.2）
```

`App.onLaunch` 启动链路：

```text
App.onLaunch
  → /partner/queryMDomain              # 租户信息 → 生成 __sr
  → /getCsrfToken                      # CSRF，按 expireTime 续期
  → /cms/getCdn
  → /cms/basicSetting/getBasicSetting
  → /cms/homepage/getHomepageSetting
  → /pageConfig/getPageConfig
  → /common/currentTimestamp
  → /aliyupoint/getMemberHomePageInfo  # 已登录时才请求
  → /aliyuPopUp/query
```

未登录时首页带 `msgKey=MZ_NO_LOGIN`，跳转 `/pages-others/newLogin/index?scene=native&redirectUrl=...`。

### 3.2 微信一键登录

v37 存在两条并行登录路径：

```text
① 动态选择（旧版一键登录）
   getLoginApi() = 微信小程序 ? "/login/authLogin" : "/login/authLoginForApplet"
   渠道标记来自模块 79514：at = 微信、Nc = 支付宝、Vi = 抖音

② 字面量直连（新版自动登录，v37 主用）
   POST /login/authLoginForApplet
   { langType: 1, authCode, authToken, ext: { iv, encryptedData },
     appType, skipAutoBind?, sqmCode?, inviteCode? }
```

- 路径 ② 的 URL 写死，易被解包提取；路径 ① 的 URL 是变量。
- Java 侧保留 v1/v2 的 `/login/authLoginForApplet`，另由 `AliyuAccountClient` 提供旧版 `/login/authLogin` 及账号密码接口。新客户端不读取已绑定账号，调用方传入本次流程的会话头、Cookie 和 CSRF 等必要请求头，并从 `ForestResponse` 读取登录响应及响应头。微信授权数据仍需通过正常的客户端授权流程取得。账号接口不会自动重试、记录请求体或保存 Cookie；尚未联调线上接口。

登录载荷变体：

| 形态 | 场景 | 载荷 |
|---|---|---|
| ② 主用 | 新版自动登录 | `{ langType, authCode, authToken, ext: { iv, encryptedData }, appType, … }` |
| A | 带手机号与验证码 | `{ appType, authCode, authToken, ext: { iv, encryptedData, phone, messageCode } }` |
| A' | 支付宝分支（`g.Nc`） | `{ appType, authCode, authToken, ext: { encryptedData } }` |
| B | 复用缓存的手机号凭证 | `{ langType: 1, jsCode, encryptedIv, encryptedData, userTel }` |

各形态都可能附带 `sqmCode`（来自阿里安全环境）与 `inviteCode`（邀请码）。

关键澄清：

- `authCode` / `jsCode` 是 `wx.login()` 返回的 code，**本身不加密**。
- `encryptedData` / `iv` 来自微信 `wx.getPhoneNumber` 授权回调，小程序只透传并 `setStorageSync` 暂存，**不是本地 RC4/RSA 产出**。
- `encryptedData` 嵌在 `ext` 内，不是顶层字段。
- 这些值必须在真机微信环境获取，**离线无法生成**。

登录成功落点：

```text
wx.login()          → code（authCode / jsCode）
wx.getPhoneNumber() → { iv, encryptedData }   # 微信产出，本地仅透传
  → POST /login/authLoginForApplet
  ← 服务端下发 cookie（JSESSIONID / MZCONSUMERJSESSIONID / MZOPERATEID）
  → 后续请求携带完整凭证束
```

---

## 4. 风控与加密

### 4.1 总览

| 维度 | 结论 | 可离线复现 |
|---|---|---|
| `sign` 请求参数 | 不存在 | — |
| `hecate` / `epeius` / `coeus` | 风控头，算法在包内 | ✅ |
| 请求体敏感字段 | RSA-2048 / PKCS#1 v1.5 | ✅ |
| 登录 `encryptedData` / `iv` | 微信平台产出 | ❌ 需真机 |
| `bx-umidtoken` / `x-ticid` | baxia SDK，每请求现算 | ❌ 无设备上下文不可复现 |
| `x-mini-wua` / `x-umt` | baxia SDK，仅支付宝 | ❌ 设备绑定 |

> 一句话区分：**风控头是确定性算法，能复算；设备头是现算信号，不能复现。**

### 4.2 请求体字段 RSA 加密

实现位置：模块 `93323`。

- `w(v)`：`setPublicKey(x)` 后 `encrypt(string)`，输出 Base64；RSA-2048 下密文固定 344 字符。
- RSA 实现：模块 `10655`，JSEncrypt 风格自包含库，默认 padding 为 PKCS#1 v1.5。
- 公钥 `x`：X.509 SubjectPublicKeyInfo，DER 长度 294 字节，对应 RSA-2048。
- `U(url, data)`：**仅 POST** 执行，先深拷贝再递归遍历（`D` 函数），命中字段的字符串/非零数字才加密。
- 空字符串与数字 `0` 保持原样；加密异常时返回原文（小程序侧降级）。

字段加密清单（小程序侧共 16 条，Java 覆盖其中有请求调用的 13 条）：

| 接口 | 加密字段 | Java 侧 |
|---|---|---|
| `/user/userLogin` | `loginPass`, `phone`, `email` | 已实现 |
| `/user/validateUserV2` | `userEmail`, `loginPass`, `userTel`, `contactInfo` | 已实现 |
| `/user/registerSetPass` | `loginPass`, `userTel`, `userEmail`, `userCard`, `emergencyContact`, `emergencyContactTelphone` | 已实现 |
| `/user/findPass` | `mobile`, `email` | 已实现 |
| `/user/findPassCheck` | `newPass` | 已实现 |
| `/user/resetMobileOrEmail` | `email`, `phone` | 未实现 |
| `/user/resetPwdValidate` | `mobile`, `email` | 未实现 |
| `/user/resetPwdSet` | `newPass` | 未实现 |
| `/user/modifyUser` | `userCard`, `emergencyContact`, `emergencyContactTelphone` | ✅ |
| `/user/addDis` | `email`, `telPhone` | ✅ |
| `/user/modifyDis` | `email`, `telPhone` | ✅ |
| `/user/addBearer` | `identification` | ✅ |
| `/msg/sendMobileMsg` | `telephone` | ✅ |
| `/msg/sendEmailMsg` | `email` | ✅ |
| `/trade/submitGroupedCart` | `receiveCard`, `receiveName`, `paymentPassword` | ✅ |
| `/invoice/simple/applyInvoice` | `buyerTel`, `email` | ✅ |

口径说明：

- `/user/reset*` 三条仅出现于小程序加密表，未找到请求调用点，不计入接口清单。
- `/user/findPass` 和 `/user/findPassCheck` 使用表单请求体，其余已封装的账号接口使用 JSON。
- 交叉验证：RSA-2048 + PKCS#1 v1.5 的密文为 256 字节，Base64 后正好 344 字符，与包内库输出长度吻合；同一明文经包内库与 JDK `RSA/ECB/PKCS1Padding` 加密结果一致。

### 4.3 风控头：hecate / epeius / coeus

三者全部在模块 `93323` 内本地计算，不需要任何 SDK：

```text
epeius = (now - parseInt(timeCompare)) || now        // 毫秒时间戳
hecate = MD5( JSON.stringify( canon(data) + epeius ) )
coeus  = URL 命中固定路径清单 ? "10010" : "10086"
```

#### epeius

`timeCompare` 在整包中只被读取、从未被写入，`parseInt(undefined)` 得 `NaN`，`now - NaN` 为假值，于是回落到 `now`。因此该头**恒等于当前毫秒时间戳**，不是时钟差。

#### hecate

源码为 `h({data, t})`，计算分三步：

1. 先执行 `data = data || {}`；字符串用 `JSON.parse` 解析，失败回退 `{}`，其他输入做 JSON 深拷贝。递归函数 `n` 对对象键使用 `localeCompare` 排序；叶子按 JS 双精度数值及字符串语义转换；`null` → `"null"`。
   - 数组并非统一保序：直接传给 `n` 的数组会对索引字符串排序（12 个元素顺序为 `0,1,10,11,2,…,9`）；作为属性值处理的数组使用 `map(n)`，本层保序，其内部数组再次进入 `n` 时会排序。
2. 附加 `String(epeius)`：结果是对象则加键 `epeius`，是数组则 `push`，是标量则直接字符串拼接。
3. 对 `JSON.stringify(结果)` 取 MD5 小写十六进制。整数索引键序列化时按数值升序；U+2028/U+2029 不转义，孤立代理码元转义。内联 MD5 编码函数按 UTF-16 码元生成字节，emoji 的代理对占六字节，与标准 UTF-8 不同。

#### coeus

源码数组共 14 个条目，其中 `/pro/prices` 重复一次，实际生效 13 条：

```text
/user/loginUser            /pro/projects            /pro/eventDiscount
/pro/prices                /order/detail            /user/userCenter
/user/getDis               /region/getRegionsByParentId
/user/addBearer            /user/modifyDis          /user/modifyUser
/user/userInfoGroupDesensitization                  /user/modifyUserBrief
```

#### Java 侧复算

`AliyuRiskCalculator` / `AliyuRiskServiceImpl` 不引入额外依赖：

- 用 JDK `BigDecimal` 实现 ECMAScript `Number::toString` 的最短往返表示。
- 用本地实现的英文 `localeCompare` 复刻 JS 默认排序：空白/符号 → 数字 → 字母，同字母小写先于大写。
- JSON 规范化与 MD5 字节编码独立实现。
- 排序不受服务器默认 Locale 影响；小程序默认 locale 变化或键引入非 ASCII 字符（如中文键）时需重新核验。

关键点：**签名对象是加密前的明文**。`U()` 的产物只赋给 `t.data`（网络层发送用），`hecate` 仍引用原始变量。GET 会将 `data` 拼进 URL，但仍用原始 `data` 签名；只有未传 `data` 时才等价于对 `{"epeius":"<时间戳>"}` 取 MD5。

### 4.4 baxia 头与模块 7188 白名单

baxia 安全头无法离线生成，Java 侧一律不注入。模块 7188 的白名单**只决定何时附加 `x-mini-wua` / `x-umt`**，与 toast 提示、登录态校验无关。

白名单共 26 条、去重后 25 条（`/api/user/registerSetPass` 重复一次）：

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

作用：当请求 URL 命中该清单、且当前是支付宝渠道时，请求层才调用 baxia SDK 取 `wua` / `umid` 并附加 `x-mini-wua` / `x-umt` 头。

四个 baxia 头的性质：

| 头 | 生成方式 | Java 侧 |
|---|---|---|
| `bx-umidtoken` / `x-ticid` | SDK 按“机型哈希 + 请求 URL 哈希 + 秒级时间戳 + 递增计数器（+ 电量/姿态）”每请求现算 | 不保存、不注入 |
| `x-mini-wua` / `x-umt` | 仅支付宝渠道 + 命中白名单时由 SDK 产出 | 不保存、不注入 |

取舍：冻结抓包值会与凭证束时间线矛盾，**缺失比伪造更安全**；用户配置只保留 `cookie` + `sessValue`。

> 白名单中 `/api/pro/events` 以及 §5.9 所列 6 个选座/场馆/价格查询接口没有出现在业务调用点；其中后 6 个未计入 129。

### 4.5 其他加密信号

分析工具检出 `hmac-sha256`、`rc4`，均来自 mpassport 登录 SDK 内置的 CryptoJS 风格库，属 SDK 自用，未作用于业务请求体。`md5` 则是模块 `93323` 内联实现，服务于 §4.3 的 `hecate`。

---

## 5. 接口清单（129 个业务接口）

> 标 `（RSA）` 的接口请求体走字段级加密（见 §4.2）。本节路径均需按 §2.1 补全。

### 5.0 分组总览

| 小节 | 业务域 | 数量 | 说明 |
|---|---|---:|---|
| 5.1 | 登录与账号 | 11 | 微信授权及账号密码接口 |
| 5.2 | 用户资料、联系人与持票人 | 9 | 资料维护类写接口 |
| 5.3 | 会员与积分 | 17 | `/aliyupoint/*` |
| 5.4 | 积分商城 | 17 | `/aliyuMember/*` |
| 5.5 | 阿里鱼裂变活动 | 5 | `/aliyu/act/*` |
| 5.6 | 报名、抽奖与开售提醒 | 6 | 卖座侧 `/act/*`、`/event/*` |
| 5.7 | 演出项目、商品与地区 | 20 | `/pro/*`、`/item/*`、`/region/*` |
| 5.8 | 订单与售后 | 10 | 查询、取消、退款、退票 |
| 5.9 | 下单、选座与支付 | 9 | `/trade/*`、`/shoppingCart/*`、`/pms/chooseSeat/*`、`/maipay2/*` |
| 5.10 | 发票与验证码 | 8 | 开票与发票历史 + 短信/邮件验证码 |
| 5.11 | 内容与配置 | 10 | 启动链路依赖 |
| 5.12 | 协议与凭证 | 5 | `/agreement/*`、`/credential/*` |
| 5.13 | 弹窗营销 | 2 | `/aliyuPopUp/*` |
| — | **合计** | **129** | 另有 5.14 的 2 个外部 URL 不计入 |

### 5.1 登录与账号（11）

| 方法 | 路径 | 说明 |
|---|---|---|
| POST | `/login/authLoginForApplet` | 小程序授权登录 |
| POST | `/login/authLogin` | 微信旧版授权登录 |
| GET | `/user/loginUser` | 当前登录用户 |
| GET | `/user/userInfo` | 用户信息 |
| GET | `/user/userContact` | 用户联系方式 |
| POST | `/user/loginOff` | 退出登录 |
| POST | `/user/userLogin` | 账号密码登录（RSA） |
| POST | `/user/validateUserV2` | 用户校验 V2（RSA） |
| POST | `/user/registerSetPass` | 注册并设置密码（RSA） |
| POST | `/user/findPass` | 找回密码（RSA，表单） |
| POST | `/user/findPassCheck` | 找回密码校验（RSA，表单） |

### 5.2 用户资料、联系人与持票人（9）

| 方法 | 路径 | 说明 |
|---|---|---|
| GET | `/user/getBearers` | 持票人列表 |
| GET | `/user/getDis` | 联系人/收件人列表 |
| GET | `/user/getDisInfo` | 联系人详情 |
| POST | `/user/modifyUser` | 修改用户信息（RSA） |
| POST | `/user/addBearer` | 新增持票人（RSA） |
| POST | `/user/removeBearer` | 删除持票人 |
| POST | `/user/addDis` | 新增联系人（RSA） |
| POST | `/user/modifyDis` | 修改联系人（RSA） |
| POST | `/user/removeDis` | 删除联系人 |

### 5.3 会员与积分（17）

| 方法 | 路径 | 说明 |
|---|---|---|
| GET | `/aliyupoint/userInfo` | 阿里鱼会员信息 |
| GET | `/aliyupoint/getMemberInfo` | 会员详情 |
| GET | `/aliyupoint/getMemberHomePageInfo` | 会员首页 |
| GET | `/aliyupoint/checkMember` | 会员资格校验 |
| GET | `/aliyupoint/getPointsDetail` | 小鱼丸明细 |
| GET | `/aliyupoint/getPointsRule` | 积分规则 |
| GET | `/aliyupoint/getGrowthValueDetail` | 成长值明细 |
| GET | `/aliyupoint/getGrowthValueTierInfo` | 成长值等级 |
| GET | `/aliyupoint/getDynamicQrCode` | 动态会员码 |
| POST | `/aliyupoint/modifyUser` | 修改会员资料 |
| POST | `/aliyupoint/deregisterUser` | 注销会员 |
| POST | `/aliyupoint/batchSubscribe` | 批量订阅消息 |
| POST | `/aliyupoint/batchQuerySubscribed` | 批量查询订阅 |
| POST | `/aliyupoint/task/list` | 任务列表 |
| POST | `/aliyupoint/task/checkStatus` | 任务状态 |
| POST | `/aliyupoint/task/claim` | 领取任务积分 |
| POST | `/aliyupoint/task/report` | 任务行为上报 |

### 5.4 积分商城（17）

| 方法 | 路径 | 说明 |
|---|---|---|
| GET | `/aliyuMember/pointsMall/currentStore` | 当前门店 |
| GET | `/aliyuMember/pointsMall/prize/list` | 奖品列表 |
| GET | `/aliyuMember/pointsMall/prize/detail` | 奖品详情 |
| GET | `/aliyuMember/pointsMall/prize/check` | 兑换校验 |
| GET | `/aliyuMember/pointsMall/prize/store` | 奖品可用门店 |
| POST | `/aliyuMember/pointsMall/prize/exchange` | 奖品兑换 |
| GET | `/aliyuMember/pointsMall/wallpaper/list` | 壁纸列表 |
| GET | `/aliyuMember/pointsMall/wallpaper/seriesDetail` | 壁纸系列详情 |
| GET | `/aliyuMember/pointsMall/wallpaper/detail` | 壁纸详情 |
| GET | `/aliyuMember/pointsMall/wallpaper/check` | 壁纸兑换校验 |
| GET | `/aliyuMember/pointsMall/wallpaper/download` | 壁纸下载 |
| GET | `/aliyuMember/pointsMall/wallpaper/dressHome` | 装扮首页 |
| POST | `/aliyuMember/pointsMall/wallpaper/exchange` | 壁纸兑换 |
| GET | `/aliyuMember/memberCoupon/detail` | 会员券详情 |
| POST | `/aliyuMember/memberCoupon/list` | 会员券列表 |
| GET | `/aliyuMember/order/pointsExchange/detail` | 兑换订单详情 |
| POST | `/aliyuMember/order/pointsExchange/list` | 兑换订单列表 |

### 5.5 阿里鱼裂变活动（5）

| 方法 | 路径 | 说明 |
|---|---|---|
| GET | `/aliyu/act/fissionDrawActDetail` | 裂变抽奖详情 |
| GET | `/aliyu/act/getInviteCode` | 邀请码 |
| GET | `/aliyu/act/getShareImg` | 分享图 |
| POST | `/aliyu/act/registerActParticipate` | 报名参与 |
| POST | `/aliyu/act/registerActAssist` | 助力 |

### 5.6 报名、抽奖与开售提醒（6）

| 方法 | 路径 | 说明 |
|---|---|---|
| GET | `/act/registerActDetail` | 报名活动详情 |
| POST | `/act/registerActList` | 报名活动列表 |
| POST | `/act/registerUser` | 活动报名 |
| POST | `/act/drawList` | 抽奖记录 |
| POST | `/event/register/eventRegister` | 活动注册 |
| POST | `/event/register/updateSaleRemind` | 更新开售提醒 |

### 5.7 演出项目、商品与地区（20）

| 方法 | 路径 | 说明 |
|---|---|---|
| GET | `/pro/projects` | 项目列表 |
| GET | `/pro/customizableProjects` | 分类项目列表，支持分页与筛选 |
| GET | `/pro/language/projects` | 按 `projectName` 查询项目 |
| GET | `/pro/project` | 项目详情 |
| GET | `/pro/event` | 场次详情 |
| GET | `/pro/eventsByDate` | 按日期查场次 |
| GET | `/pro/getShowCalendar` | 演出日历 |
| GET | `/pro/getColumnInfo` | 栏目信息 |
| GET | `/pro/hotProjectVos` | 热门项目 |
| GET | `/pro/privilegeCheck` | 权益校验 |
| POST | `/pro/hasSaleRemind` | 设置开售提醒 |
| GET | `/proType/getProjectTypeList` | 项目分类 |
| GET | `/item/itemDetail` | 商品详情 |
| GET | `/item/itemBuy` | 商品购买 |
| GET | `/region/getProjectCityList` | 项目城市 |
| GET | `/region/getAreaCityList` | 地区城市 |
| GET | `/region/getRegionByCountryId` | 按国家查地区 |
| GET | `/region/getRegionByProvinceId` | 按省查地区 |
| GET | `/region/loadReceiveBasicInfo` | 收件基础信息 |
| GET | `/countryArea/queryForUserRegister` | 国家地区（注册用） |

### 5.8 订单与售后（10）

| 方法 | 路径 | 说明 |
|---|---|---|
| GET | `/order/queryOrderList` | 订单列表 |
| GET | `/order/queryOrderDetail` | 订单详情 |
| GET | `/order/queryOrderDetailNew` | 订单详情（新 UI） |
| GET | `/order/calculateRefundAmount` | 计算退款金额 |
| GET | `/order/ticket/discount/receive` | 领取票务折扣 |
| POST | `/order/cancelOrder` | 取消订单 |
| POST | `/order/delOrder` | 删除订单 |
| POST | `/order/applyRefund` | 申请退款 |
| POST | `/order/orderRefundConfirm` | 退款确认 |
| POST | `/order/restTickets` | 退票 |

### 5.9 下单、选座与支付（9）

| 方法 | 路径 | 说明 |
|---|---|---|
| POST | `/trade/renderConfig` | 下单页渲染配置 |
| POST | `/trade/addCartGood` | 加入购物车 |
| POST | `/trade/confirmGroupedCart` | 确认分组购物车 |
| POST | `/trade/submitGroupedCart` | 提交下单（RSA） |
| POST | `/shoppingCart/addCartGood` | 购物车加商品 |
| POST | `/shoppingCart/checkGoodSale` | 校验商品在售 |
| GET | `/pms/chooseSeat/seatCachePreheat` | 选座缓存预热 |
| GET | `/maipay2/renderPay` | 支付页渲染 |
| POST | `/maipay2/doPay` | 发起支付 |

### 5.10 发票与验证码（8）

| 方法 | 路径 | 说明 |
|---|---|---|
| GET | `/invoice/simple/getInvoicingOrderList` | 可开票订单列表 |
| GET | `/invoice/simple/getInvoicingOrderDetail` | 可开票订单详情 |
| GET | `/invoice/simple/getInvoiceInfoList` | 发票历史分页 |
| GET | `/invoice/simple/getInvoiceDetails` | 按 `tradeInvoiceId` 查询历史发票详情 |
| GET | `/invoice/simple/invoiceGray` | 开票灰度 |
| POST | `/invoice/simple/applyInvoice` | 申请开票（RSA） |
| POST | `/msg/sendMobileMsg` | 短信验证码（RSA） |
| POST | `/msg/sendEmailMsg` | 邮件验证码（RSA） |

### 5.11 内容与配置（10）

| 方法 | 路径 | 说明 |
|---|---|---|
| GET | `/partner/queryMDomain` | 按小程序 AppID 获取租户域名，不发送 `__sr` |
| GET | `/cms/querySiteConfig` | 读取当前租户的站点配置 |
| GET | `/cms/getCdn` | CDN 配置 |
| GET | `/cms/basicSetting/getBasicSetting` | 基础设置 |
| GET | `/cms/homepage/getHomepageSetting` | 首页配置 |
| GET | `/cms/homepage/getInformationDetail` | 资讯详情 |
| GET | `/cms/homepage/getSiteDetail` | 站点详情 |
| GET | `/pageConfig/getPageConfig` | 页面配置 |
| GET | `/pageConfig/getBackUrl` | 回跳地址 |
| GET | `/common/currentTimestamp` | 服务端时间戳 |

### 5.12 协议与凭证（5）

| 方法 | 路径 | 说明 |
|---|---|---|
| GET | `/agreement/detail` | 协议详情 |
| GET | `/agreement/deregistration` | 注销协议 |
| GET | `/agreement/record/list` | 协议记录列表 |
| GET | `/agreement/record/{recordId}` | 协议记录详情 |
| GET | `/credential/getList` | 凭证列表 |

### 5.13 弹窗营销（2）

| 方法 | 路径 | 说明 |
|---|---|---|
| POST | `/aliyuPopUp/query` | 查询弹窗配置 |
| POST | `/aliyuPopUp/record` | 弹窗曝光记录 |

### 5.14 非业务 URL（2，不计入 129）

| 方法 | URL | 说明 |
|---|---|---|
| GET | `https://fourier.taobao.com/rp?ext=52` | 淘宝埋点 |
| GET | `https://damaipopcorn.alibaba-inc.com/mock/getMockCases?id={mockId}` | 大麦 mock 平台（开发用） |

---

## 6. 页面路由（46 个）

共 73 条跳转调用，去重后 46 个页面。

| 分包 | 页面数 |
|---|---:|
| 主包 | 5 |
| pages-member | 5 |
| pages-order | 13 |
| pages-others | 18 |
| pages-points-mall | 5 |
| **合计** | **46** |

### 6.1 主包（5）

| 路由 | 关键参数 |
|---|---|
| `/pages/home/index` | `msgKey` |
| `/pages/member/index` | — |
| `/pages/order/index` | `orderToken` |
| `/pages/search/index` | `backUrl`, `type` |
| `/pages/debug/index` | — |

### 6.2 pages-member（5）

| 路由 | 关键参数 |
|---|---|
| `/pages-member/growthValue/index` | — |
| `/pages-member/growthValueRules/index` | `url` |
| `/pages-member/memberLevel/index` | — |
| `/pages-member/memberCode/index` | `cardType` |
| `/pages-member/pointsDetail/index` | `cardType` |

### 6.3 pages-order（13）

| 路由 | 关键参数 |
|---|---|
| `/pages-order/projectDetail/index` | `projectId` |
| `/pages-order/projectSKU/index` | `projectId` |
| `/pages-order/activityDetail/index` | `actId` |
| `/pages-order/lotteryDetail/index` | `actId` |
| `/pages-order/confirmOrder/index` | `eventIds` / `orderId` |
| `/pages-order/orderInfo/index` | `orderToken` |
| `/pages-order/cashier/index` | `orderId` |
| `/pages-order/pay/index` | `payConfig` |
| `/pages-order/itemDetail/index` | `itemId`, `bizType` |
| `/pages-order/invoice/index` | `tab` |
| `/pages-order/invoice/invoiceInfo/index` | `orderId` |
| `/pages-order/invoice/historyInfo/index` | `tradeInvoiceId` |
| `/pages-order/shopOrderInfo/index` | `orderToken` |

### 6.4 pages-others（18）

| 路由 | 关键参数 |
|---|---|
| `/pages-others/activityList/index` | — |
| `/pages-others/addAddress/index` | `disId` |
| `/pages-others/addressManagement/index` | — |
| `/pages-others/addAudience/index` | — |
| `/pages-others/audienceManagement/index` | — |
| `/pages-others/myCoupon/index` | — |
| `/pages-others/historyCoupon/index` | — |
| `/pages-others/myAccount/index` | — |
| `/pages-others/dressUp/index` | — |
| `/pages-others/diversion/index` | — |
| `/pages-others/applicableStores/index` | 门店查询参数 |
| `/pages-others/paySuccess/index` | `orderId` |
| `/pages-others/newLogin/index` | `scene`, `redirectUrl` |
| `/pages-others/protocolIndex/index` | — |
| `/pages-others/protocolAssment/index` | `type` |
| `/pages-others/protocolAssmentDetail/index` | `type`, `recordId`, `version` |
| `/pages-others/refundRuleDetails/index` | `projectId` |
| `/pages-others/deregisterUser/index` | — |

### 6.5 pages-points-mall（5）

| 路由 | 关键参数 |
|---|---|
| `/pages-points-mall/detail/index` | `pointsPrizeId`, `cardId` |
| `/pages-points-mall/orderConfirm/index` | `pointsPrizeId`, `cardId`, `prizeType` |
| `/pages-points-mall/orderDetail/index` | `orderId` |
| `/pages-points-mall/orderResult/index` | `state`, `orderId`, `pointsPrizeId`, `prizeType` |
| `/pages-points-mall/wallpaperDetail/index` | `seriesId`, `materialId`, `cardId` |

---

## 7. 核心业务链路

> 以下按分包 saga / action 调用点归纳。接口归属可靠，先后顺序仅供参考，真实执行受页面跳转与 redux 状态影响。启动链路见 §3.1，登录链路见 §3.2。

### 7.1 演出购票

```text
浏览   /pro/projects → /pro/project → /pro/event → /pro/eventsByDate
选座   /pms/chooseSeat/seatCachePreheat
确认   /trade/renderConfig → /shoppingCart/checkGoodSale
       /user/getBearers → /user/getDis → /region/loadReceiveBasicInfo
       /order/ticket/discount/receive
提交   /trade/submitGroupedCart  ← orderId / orderToken
支付   /maipay2/renderPay → /maipay2/doPay → /pages-others/paySuccess
```

### 7.2 订单与售后

```text
列表 /order/queryOrderList          详情 /order/queryOrderDetailNew
取消 /order/cancelOrder             删除 /order/delOrder
退款 /order/calculateRefundAmount → /order/applyRefund → /order/orderRefundConfirm
退票 /order/restTickets             开票 /invoice/simple/invoiceGray → applyInvoice
```

### 7.3 积分商城兑换

```text
奖品   /aliyuMember/pointsMall/prize/list → detail → check → store
兑换   POST /aliyuMember/pointsMall/prize/exchange   # 扣小鱼丸
订单   /aliyuMember/order/pointsExchange/list → detail
```

### 7.4 壁纸兑换

```text
list → seriesDetail → detail → check → POST exchange → download → dressHome
```

### 7.5 裂变与抽奖

```text
入口   /aliyu/act/fissionDrawActDetail → /act/registerActDetail
参与   POST /aliyu/act/registerActParticipate
分享   /aliyu/act/getInviteCode → /aliyu/act/getShareImg
助力   POST /aliyu/act/registerActAssist
结果   POST /act/drawList
```

### 7.6 会员与任务

```text
会员   /aliyupoint/getMemberInfo → getDynamicQrCode（到店出示）
成长   /aliyupoint/getGrowthValueTierInfo → getGrowthValueDetail
积分   /aliyupoint/getPointsDetail → getPointsRule
任务   task/list → task/report → task/checkStatus → task/claim
订阅   batchSubscribe / batchQuerySubscribed
```

---

## 附录 A　解包产物

路径：`/Users/bixiu/MyProjects/wx-mp-mcp/static/out/wx88adba08e6cf3e75/`

```text
├── report.json          # 结构化报告（198 条调用记录 + crypto 信号）
├── REPORT.md            # 工具自动生成报告
├── repro.node.mjs       # Node.js 复现代码
├── repro.python.py      # Python 复现代码
└── app/                 # 解包源码（主包 + 4 分包）
```