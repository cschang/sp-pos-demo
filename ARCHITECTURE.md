# SP-POS 系統架構文件

> **專案名稱**：sp-pos  
> **技術棧**：Spring Boot 2.7.x · Java 17 · SQLite · MyBatis-Plus · Spring Security · JWT

---

## 目錄

1. [系統概覽](#系統概覽)
2. [技術棧](#技術棧)
3. [專案結構](#專案結構)
4. [架構分層](#架構分層)
5. [資料模型 (ER 關係)](#資料模型)
6. [API 端點清單](#api-端點清單)
7. [安全機制](#安全機制)
8. [AOP 日誌機制](#aop-日誌機制)
9. [快取機制](#快取機制)
10. [設定檔說明](#設定檔說明)

---

## 系統概覽

SP-POS 是一套基於 Spring Boot 的 **餐飲銷售點 (Point of Sale)** 後端系統，提供 RESTful API 供前端應用程式（POS Frontend）呼叫。系統以 SQLite 作為輕量資料庫，透過 MyBatis-Plus 進行 ORM 映射，並整合 Spring Security + JWT 實現無狀態身份驗證。

```
┌─────────────────────────────────────────┐
│            Client (前端 / POS)           │
│           React / Vue SPA App           │
└──────────────┬──────────────────────────┘
               │ HTTP / REST (Port 8000)
               ▼
┌─────────────────────────────────────────┐
│           Spring Boot 應用程式           │
│                                         │
│  ┌──────────┐  ┌───────────────────┐   │
│  │ Security │  │  SpringDoc (OAS3) │   │
│  │  Filter  │  │  /swagger-ui.html │   │
│  └────┬─────┘  └───────────────────┘   │
│       │                                 │
│  ┌────▼──────────────────────────────┐  │
│  │       Controller Layer            │  │
│  │  Auth / User / Store / Category   │  │
│  │  CommonData / OperationLog        │  │
│  └────┬──────────────────────────────┘  │
│       │  AOP (@Log Aspect)              │
│  ┌────▼──────────────────────────────┐  │
│  │        Service Layer              │  │
│  │   UserService / StoreService      │  │
│  │   CategoryService / LogService    │  │
│  │   OperationLogService             │  │
│  └────┬──────────────────────────────┘  │
│       │                                 │
│  ┌────▼──────────────────────────────┐  │
│  │        Mapper Layer               │  │
│  │  MyBatis-Plus BaseMapper          │  │
│  │  + Custom XML Mapper              │  │
│  └────┬──────────────────────────────┘  │
│       │                                 │
│  ┌────▼──────────────────────────────┐  │
│  │   SQLite Database (db.sqlite3)    │  │
│  └───────────────────────────────────┘  │
└─────────────────────────────────────────┘
```

---

## 技術棧

| 類別 | 技術 / 套件 | 版本 |
|------|------------|------|
| 核心框架 | Spring Boot | 2.7.18 |
| 程式語言 | Java | 17 |
| ORM | MyBatis-Plus | 3.5.1 |
| 多資料源 | dynamic-datasource-spring-boot-starter | 3.5.0 |
| 資料庫 | SQLite (sqlite-jdbc) | 3.45.1.0 |
| 安全框架 | Spring Security | (Boot 管理) |
| JWT | jjwt | 0.9.1 |
| 快取 | Spring Cache + Caffeine | (Boot 管理) |
| AOP | Spring AOP | (Boot 管理) |
| API 文件 | SpringDoc OpenAPI UI | 1.6.7 |
| 模板引擎 | Thymeleaf | (Boot 管理) |
| 工具庫 | Lombok, Apache Tika, Commons IO, Guava | 各版本 |
| 建置工具 | Maven (Maven Wrapper) | — |

---

## 專案結構

```
sp-pos/
├── pom.xml                              # Maven 依賴設定
├── db-java.sqlite3                      # SQLite 資料庫（本地開發）
├── logs/                                # 應用程式日誌輸出目錄
└── src/
    ├── main/
    │   ├── java/com/philip/lab/pos/
    │   │   ├── SpPosApplication.java        # Spring Boot 啟動入口
    │   │   ├── annotation/
    │   │   │   └── Log.java                 # 自定義 @Log 日誌註解
    │   │   ├── aspect/
    │   │   │   └── LogAspect.java           # AOP 日誌切面
    │   │   ├── common/
    │   │   │   └── JwtUtils.java            # JWT 工具類
    │   │   ├── config/
    │   │   │   ├── MybatisPlusConfig.java   # MyBatis-Plus 設定
    │   │   │   ├── MybatisPlusHandler.java  # 自動填充處理器
    │   │   │   └── SecurityConfig.java      # Spring Security 設定
    │   │   ├── controller/               # API 控制器層
    │   │   │   ├── AuthController.java
    │   │   │   ├── CategoryController.java
    │   │   │   ├── CommonDataController.java
    │   │   │   ├── OperationLogController.java
    │   │   │   ├── StoreController.java
    │   │   │   └── UserController.java
    │   │   ├── dto/
    │   │   │   └── Result.java              # 統一回應格式
    │   │   ├── enums/
    │   │   │   ├── Gender.java
    │   │   │   ├── Role.java
    │   │   │   └── StoreType.java
    │   │   ├── mapper/                   # MyBatis-Plus Mapper 介面
    │   │   │   ├── CategoryMapper.java
    │   │   │   ├── CommonDataMapper.java
    │   │   │   ├── OperationLogMapper.java
    │   │   │   ├── OrderItemMapper.java
    │   │   │   ├── OrderMapper.java
    │   │   │   ├── OrderSessionMapper.java
    │   │   │   ├── StoreMapper.java
    │   │   │   ├── SysLogMapper.java
    │   │   │   └── UserMapper.java
    │   │   ├── model/                    # 資料實體 (領域模型)
    │   │   │   ├── Category.java
    │   │   │   ├── CommonData.java
    │   │   │   ├── OperationLog.java
    │   │   │   ├── Order.java
    │   │   │   ├── OrderItem.java
    │   │   │   ├── OrderSession.java
    │   │   │   ├── Store.java
    │   │   │   ├── SysLog.java
    │   │   │   └── User.java
    │   │   └── service/                  # 業務邏輯層
    │   │       ├── CategoryService.java / CategoryServiceImpl.java
    │   │       ├── CommonDataService.java / CommonDataServiceImpl.java
    │   │       ├── LogService.java
    │   │       ├── OperationLogService.java / OperationLogServiceImpl.java
    │   │       ├── StoreService.java / StoreServiceImpl.java
    │   │       └── UserService.java / UserServiceImpl.java
    │   └── resources/
    │       ├── application.yml              # 主設定檔
    │       ├── logback-spring.xml           # 日誌設定
    │       ├── static/                      # 靜態資源
    │       └── templates/                   # Thymeleaf 模板
    └── test/                            # 單元測試
```

---

## 架構分層

系統採用標準的 **四層架構**：

```
┌─────────────────────────────────────────────────────────────────┐
│  第一層：Controller Layer (表現層)                               │
│  負責接收 HTTP 請求、參數校驗、呼叫 Service、回傳統一 Result<T>  │
├─────────────────────────────────────────────────────────────────┤
│  第二層：Service Layer (業務邏輯層)                              │
│  封裝業務規則、事務控制、呼叫 Mapper 進行資料操作                │
├─────────────────────────────────────────────────────────────────┤
│  第三層：Mapper Layer (資料存取層)                               │
│  繼承 MyBatis-Plus BaseMapper，提供 CRUD 能力 + 自定義 XML SQL  │
├─────────────────────────────────────────────────────────────────┤
│  第四層：Model Layer (資料模型層)                                │
│  POJO 實體類，使用 @TableName、@TableField 等 MP 註解映射資料表  │
└─────────────────────────────────────────────────────────────────┘

橫切關注點 (Cross-Cutting Concerns)：
  ├── Spring Security Filter     → 認證 / 授權
  ├── AOP LogAspect (@Log)       → 自動日誌記錄
  ├── Caffeine Cache             → 快取加速
  └── MybatisPlusHandler         → 自動填充 createdAt / updatedAt
```

### Controller → Service → Mapper 流程

```
HTTP Request
    │
    ▼
[SecurityFilter] ── JWT 驗證 ──► 403 / 401
    │
    ▼
[Controller] ── 參數驗證 ──────► 400 Bad Request
    │
    ▼
[AOP @Log] ── 前置切入 ─────────► 記錄方法、參數、時間
    │
    ▼
[Service] ── 業務邏輯
    │
    ▼
[Mapper] ── SQL 執行 (MyBatis-Plus)
    │
    ▼
[SQLite DB] ── 持久化
    │
    ▼
[AOP @Log] ── 後置切入 ─────────► 記錄結果狀態、執行時間
    │
    ▼
Result<T> ── 統一回傳格式
    │
    ▼
HTTP Response (JSON)
```

---

## 資料模型

### 實體關係圖 (ERD)

```
┌──────────────┐        ┌─────────────────────┐
│    Store     │        │       User           │
│──────────────│        │─────────────────────│
│ id (PK)      │◄───────│ store_id (FK)        │
│ name         │        │ id (PK)              │
│ store_type   │        │ username             │
│ slug         │        │ password (BCrypt)    │
│ created_at   │        │ email                │
└──────┬───────┘        │ role                 │
       │                │ is_staff             │
       │                │ is_superuser         │
       │                │ total_spent          │
       │                └──────────────────────┘
       │
       │         ┌───────────────────────────────┐
       │         │         Order                 │
       │         │───────────────────────────────│
       └────────►│ store_id (FK)                 │
                 │ id (PK)                       │
                 │ order_sn                      │
                 │ session_token                 │
                 │ dining_type                   │
                 │ dining_number                 │
                 │ total_price                   │
                 │ status (pending/...)          │
                 │ created_by_id (FK → User)     │
                 │ created_at / updated_at       │
                 └──────────┬────────────────────┘
                            │
              ┌─────────────┴────────────┐
              │                          │
              ▼                          ▼
  ┌───────────────────┐      ┌───────────────────────┐
  │     OrderItem     │      │      OrderSession      │
  │───────────────────│      │───────────────────────│
  │ id (PK)           │      │ id (PK)               │
  │ order_id (FK)     │      │ session_token         │
  │ product_id        │      │ store_id (FK)         │
  │ product_name      │      │ created_at            │
  │ price             │      └───────────────────────┘
  │ quantity          │
  │ spicy             │
  │ is_side_dish      │
  │ attached_to_id    │ ◄─── (自我關聯：配菜掛在主菜上)
  └───────────────────┘

  ┌───────────────────────┐      ┌───────────────────────┐
  │      Category         │      │      CommonData        │
  │───────────────────────│      │───────────────────────│
  │ id (PK)               │      │ id (PK)               │
  │ name                  │      │ key                   │
  │ color_code            │      │ value                 │
  │ display_order         │      │ store_id (FK)         │
  │ is_side_dish          │      └───────────────────────┘
  │ store_id (FK)         │
  └───────────────────────┘

  ┌───────────────────────┐      ┌───────────────────────┐
  │    OperationLog       │      │        SysLog          │
  │───────────────────────│      │───────────────────────│
  │ id (PK)               │      │ id (PK)               │
  │ action                │      │ operator              │
  │ payload               │      │ method_name           │
  │ created_at            │      │ params                │
  └───────────────────────┘      │ action_desc           │
                                 │ result_status         │
                                 │ error_msg             │
                                 │ execution_time        │
                                 │ create_time           │
                                 └───────────────────────┘
```

### 資料表對應

| Java 實體 | 資料表名稱 | 說明 |
|-----------|-----------|------|
| `Store` | `store_store` | 門市資訊 |
| `User` | `users_user` | 使用者帳號 |
| `Order` | `orders_order` | 訂單主檔 |
| `OrderItem` | `orders_orderitem` | 訂單明細（含配菜自我關聯） |
| `OrderSession` | `orders_ordersession` | 點餐 Session 憑證 |
| `Category` | `products_category` | 商品分類 |
| `CommonData` | comman data 表 | 系統共用設定值 |
| `OperationLog` | oper log 表 | 操作日誌（業務層） |
| `SysLog` | sys log 表 | 系統日誌（AOP 自動寫入） |

---

## API 端點清單

> Base URL：`http://localhost:8000`  
> API 文件：`http://localhost:8000/swagger-ui.html`

### 公開端點（無需驗證）

| Method | 路徑 | 說明 |
|--------|------|------|
| `POST` | `/api/auth/login` | 使用者登入，回傳 JWT token |
| `POST` | `/api/users/register` | 使用者註冊 |

### 受保護端點（需攜帶 JWT）

#### 使用者管理 `/api/users`

| Method | 路徑 | 說明 |
|--------|------|------|
| `GET` | `/api/users` | 查詢所有使用者 |
| `GET` | `/api/users/{id}` | 查詢單一使用者詳情 |

#### 門市管理 `/api/stores`

| Method | 路徑 | 說明 |
|--------|------|------|
| `GET` | `/api/stores` | 查詢所有門市 |
| `POST` | `/api/stores` | 新增門市 |

#### 分類管理 `/api/categories`

| Method | 路徑 | 說明 |
|--------|------|------|
| `GET` | `/api/categories` | 查詢所有分類 |
| `GET` | `/api/categories/store/{storeId}` | 依門市查詢分類 |
| `POST` | `/api/categories` | 新增分類 |

#### 操作日誌 `/api/operation-logs` (推測路徑)

| Method | 路徑 | 說明 |
|--------|------|------|
| `GET` | `/api/logs` | 查詢操作日誌（僅店長可用） |

#### 共用資料 `/api/common-data` (推測路徑)

| Method | 路徑 | 說明 |
|--------|------|------|
| `GET` / `POST` | `/api/common-data` | 查詢 / 新增系統共用設定 |

---

## 安全機制

### 整體安全流程

```
HTTP Request
     │
     ▼
Spring Security Filter Chain
     │
     ├─ 路徑匹配 /api/auth/login, /api/users/register → permitAll()
     │
     └─ 其他路徑 → 需要認證 (authenticated)
              │
              ├─ 無/無效 Token → 403 / 401
              │
              └─ 有效 Token → 進入 Controller
```

### JWT 認證機制

| 項目 | 說明 |
|------|------|
| 演算法 | HS512 (HMAC-SHA512) |
| 有效期限 | 86400 秒（24 小時） |
| 傳遞方式 | HTTP Header（`Authorization: Bearer <token>`） |
| 工具類 | `JwtUtils.generateToken(username)` |

### 角色與權限 (Enums)

```java
// Role.java
enum Role { ADMIN, MANAGER, STAFF, GUEST }

// StoreType.java  
enum StoreType { HOTPOT, ... }

// Gender.java
enum Gender { M, F, O }
```

### 密碼加密

- 使用 **BCryptPasswordEncoder** 進行密碼雜湊
- 由 `SecurityConfig` 中宣告為 Spring Bean

---

## AOP 日誌機制

系統透過 **Spring AOP** + 自定義 `@Log` 註解，在不侵入業務邏輯的前提下自動記錄操作日誌。

### 運作流程

```
方法上標記 @Log
        │
        ▼
LogAspect.logToDb() 觸發 (@Around)
        │
        ├── 記錄：operator, methodName, params, createTime
        │
        ├── 依方法名稱前綴判斷 actionDesc：
        │      save*   → "新增或更新操作"
        │      delete* / remove* → "刪除操作"
        │      update* → "更新操作"
        │      其他    → "系統寫入操作"
        │
        ├── 執行原始方法
        │      成功 → resultStatus = "SUCCESS"
        │      失敗 → resultStatus = "FAIL", errorMsg 記錄例外訊息
        │
        └── 計算 executionTime，呼叫 LogService.saveLog() 寫入 DB
```

### 相關類別

| 類別 | 路徑 | 說明 |
|------|------|------|
| `@Log` | `annotation/Log.java` | 標記需要記錄日誌的方法 |
| `LogAspect` | `aspect/LogAspect.java` | AOP 切面實作 |
| `SysLog` | `model/SysLog.java` | 日誌資料實體 |
| `LogService` | `service/LogService.java` | 日誌儲存介面 |

---

## 快取機制

系統使用 **Spring Cache + Caffeine** 本地記憶體快取。

- 引入 `spring-boot-starter-cache` + `caffeine` 依賴
- 可在 Service 層使用 `@Cacheable`、`@CacheEvict` 等標準 Spring 快取註解
- Caffeine 提供高效能 LRU/LFU 快取置換策略

---

## 設定檔說明

### `application.yml` 重點設定

```yaml
server:
  port: 8000                  # 伺服器監聽埠

spring:
  application:
    name: sp-pos
  datasource:
    dynamic:
      primary: master
      datasource:
        master:
          url: jdbc:sqlite:db.sqlite3   # SQLite 資料庫路徑
          driver-class-name: org.sqlite.JDBC
  servlet:
    multipart:
      max-file-size: 10MB     # 最大上傳檔案大小
      max-request-size: 10MB
  thymeleaf:
    cache: false              # 開發環境關閉模板快取

mybatis:
  configuration:
    map-underscore-to-camel-case: true   # 自動駝峰命名映射
  mapper-locations: classpath:mapper/*.xml

logging:
  level:
    com.philip.lab.mapper: DEBUG        # Mapper 層顯示 SQL
```

### `logback-spring.xml`

提供應用程式日誌輸出策略，日誌寫入 `logs/` 目錄。

---

## 部署說明

### 本地開發啟動

```bash
# 使用 Maven Wrapper 啟動
./mvnw spring-boot:run

# 或打包後執行
./mvnw package -DskipTests
java -jar target/sp-pos-0.0.1-SNAPSHOT.jar
```

### 存取端點

| 服務 | URL |
|------|-----|
| API Server | http://localhost:8000 |
| Swagger UI | http://localhost:8000/swagger-ui.html |
| OpenAPI JSON | http://localhost:8000/v3/api-docs |

---

