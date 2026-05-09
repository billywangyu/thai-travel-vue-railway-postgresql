以下是完整的部署问题解决文档，记录了从本地开发到 Railway + GitHub Pages 部署全过程的所有问题和最终解决方案。

---

# 🇹🇭 泰国旅行全攻略 · 部署问题解决文档

## 一、项目概述

**项目名称**：泰国旅行全攻略卡片网站  
**技术栈**：Vue 3 + Vite + Pinia + Tailwind CSS（前端） / Spring Boot 2.7 + PostgreSQL + JPA（后端）  
**部署平台**：前端 → GitHub Pages，后端 → Railway，数据库 → Railway PostgreSQL  
**仓库地址**：`git@github.com:billywangyu/thai-travel-vue-railway-postgresql.git`  
**生产地址**：`https://billywangyu.github.io/thai-travel-vue-railway-postgresql/`  

---

## 二、总体部署架构

```
浏览器
  │
  ├─ GitHub Pages (gh-pages) ──── 静态资源 (Vue 前端)
  │
  └─ Railway ──── Spring Boot 后端 (thai-travel-1.0.0.jar)
                      │
                      └─ Railway PostgreSQL (数据持久化)
```

前后端分离部署，前端通过硬编码的 API 地址直接请求 Railway 后端。数据库挂载在 Railway 项目内，与后端服务内部网络互通。

---

## 三、遇到的所有问题及解决方案

### 问题 1：Railway 上无法识别 Java/Maven 项目，构建失败
- **现象**：Railpack 报错 `could not determine how to build the app`，日志中不识别 Java 项目。
- **原因**：Railway 默认使用 Railpack 自动检测构建器，当项目根目录有 `frontend/` 和 `backend/` 两个子项目时，Railpack 容易混淆，且 Root Directory 设置不正确。
- **解决方案**：
  1. 在项目根目录创建 `railway.toml` 文件，明确指定构建上下文和命令：
     ```toml
     [build]
     builder = "RAILPACK"
     root = "backend"

     [build.args]
     command = "mvn clean package -DskipTests"

     [deploy]
     start = "java -Dserver.port=$PORT -jar target/thai-travel-1.0.0.jar"
     ```
  2. 在 Railway 服务 Settings 中设置 **Root Directory** 为 `backend`（注意使用正斜杠 `/`，不能是反斜杠）。

---

### 问题 2：数据库连接不断失败，各种 URL 格式错误
- **现象**：应用启动后报 `Driver claims to not accept jdbcUrl`、`Connection refused`、`DATABASE_URL` 变量未展开，或连接字符串为 `jdbc:postgresql://:/` 等。
- **原因**：Railway 自动注入的 `DATABASE_URL` 格式为 `postgresql://...`，缺少 `jdbc:` 前缀，且在某些情况下 Spring Boot 的 YAML 中 `${DATABASE_URL}` 无法被正确展开。尝试使用 `PGHOST` 等独立变量时，也可能因为环境变量不同步导致为空。
- **最终解决方案**：
  1. 在 Railway 的 PostgreSQL 数据库服务 **Connect** 页面获取 Public Network 的连接信息（Host、Port、Database、Username、Password）。
  2. 直接硬编码完整的 JDBC URL 到 `application-railway.yml`：
     ```yaml
     spring:
       datasource:
         url: jdbc:postgresql://turntable.proxy.rlwy.net:46202/railway
         username: postgres
         password: <真实密码>
         driver-class-name: org.postgresql.Driver
       jpa:
         hibernate:
           ddl-auto: update
         database-platform: org.hibernate.dialect.PostgreSQLDialect
     ```
  3. 确保 `pom.xml` 中只包含 PostgreSQL 驱动，移除了 SQLite 依赖。

---

### 问题 3：前端请求 API 404，请求发到了 `https://billywangyu.github.io/api/...`
- **现象**：网站页面空白，Network 中显示所有 `/api/...` 请求都发往了 GitHub Pages 域名，返回 404。
- **原因**：前端构建时，环境变量 `VITE_API_BASE_URL` 未正确注入（GitHub Secret 设置后可能未生效，或 .env.production 未被读取），导致 axios 的 baseURL 仍为 `/api` 相对路径。
- **最终解决方案**：
  1. 直接在前端 `stores/travel.ts` 和 `stores/english.ts` 中硬编码 Railway 后端地址：
     ```typescript
     const api = axios.create({
       baseURL: 'https://thai-travel-vue-railway-postgresql-production.up.railway.app/api',
       timeout: 6000
     })
     ```
  2. 同时保留 GitHub Secret (`VITE_API_BASE_URL`) 以备将来优化，但目前以硬编码为最终手段。

---

### 问题 4：Railway 环境变量 `SPRING_PROFILES_ACTIVE` 被错误设置
- **现象**：启动日志显示 `The following 1 profile is active: "PGHOST"`，导致 `application-railway.yml` 未被加载。
- **原因**：手动添加环境变量时误将 `SPRING_PROFILES_ACTIVE` 的值写成了 `PGHOST`。
- **解决方案**：在 Railway 后端服务 Settings → Environment Variables 中将 `SPRING_PROFILES_ACTIVE` 的值改为 `railway`，并重新部署。

---

### 问题 5：Railway 健康检查失败（Healthcheck Error）
- **现象**：部署日志出现 `Healthcheck failure`，路径为 `\api\cards`（反斜杠）。
- **原因**：健康检查路径被设置为 Windows 风格的路径。
- **解决方案**：在 Railway 服务 Settings → Healthcheck Path 中改为 `/api/cards`。

---

### 问题 6：Vue Router 子路由 404（GitHub Pages 不支持 History 模式）
- **现象**：直接访问 `/vocabulary` 等路径返回 404。
- **原因**：GitHub Pages 为静态托管，不支持 `createWebHistory` 所需的服务器端 URL 重写。
- **解决方案**：路由模式改为 `createWebHashHistory()`，URL 变为 `/#/english-cards` 等，前端可正常路由。
- **备注**：此问题在项目开发阶段已解决，部署时只需确保 `vite.config.ts` 中 `base` 路径设置为正确的仓库名。

---

### 问题 7：HTTPS 页面加载 HTTP 字体资源被阻止
- **现象**：控制台报 `Tracking Prevention blocked access to storage` 和 `content.js` 相关错误。
- **原因**：浏览器扩展（如阿里云 Tongyi）注入的第三方脚本触发了安全策略，并非项目代码问题。
- **解决方案**：可安全忽略，不影响项目功能。如需彻底清除，可更换浏览器或无痕模式测试。

---

## 四、标准部署步骤（总结）

1. **本地准备**
   - 确认 `application-railway.yml` 中硬编码了 Railway PostgreSQL 的真实连接信息。
   - 确认 `frontend/src/stores/travel.ts` 和 `english.ts` 中硬编码了 Railway 后端域名 + `/api`。
   - 确认 `vite.config.ts` 中 `base` 为 `/thai-travel-vue-railway-postgresql/`。
   - 确认 `pom.xml` 中移除了 `sqlite-jdbc` 和 `sqlite-dialect`，只保留 `postgresql` 驱动。

2. **推送到 GitHub**
   ```bash
   git add -A
   git commit -m "准备部署"
   git push origin main
   ```

3. **Railway 创建项目**
   - New Project → Deploy from GitHub repo → 选择仓库。
   - 创建完成后，在项目内添加 PostgreSQL 数据库服务。
   - 配置后端服务 Settings：
     - Root Directory: `backend`
     - Build Command: `mvn clean package -DskipTests`
     - Start Command: `java -Dserver.port=$PORT -jar target/thai-travel-1.0.0.jar`
     - 添加环境变量 `SPRING_PROFILES_ACTIVE` = `railway`
   - 等待构建和启动，直到日志显示 `Started TravelApplication` 和数据库初始化完成。
   - 生成公开域名。

4. **GitHub Actions 部署前端**
   - 仓库 Settings → Secrets → Actions，添加 `VITE_API_BASE_URL`（值为 Railway 域名）。
   - 也可以直接在 Store 中硬编码（若 Secret 不生效）。
   - 推送后自动触发 Actions，部署到 `gh-pages` 分支。
   - Settings → Pages 选择 `gh-pages` 分支为源。

5. **验证**
   - 访问 `https://billywangyu.github.io/thai-travel-vue-railway-postgresql/`
   - 打开 Network 面板，确认 API 请求地址为 Railway 域名且返回 200。

---

## 五、后续维护建议

- 修改卡片/词汇数据：编辑 JSON 文件 → 推送后 Railway 自动重新部署，或使用前端“刷新数据”按钮。
- 如果需要修改数据库密码，在 Railway 中 Rotate Password，并更新 `application-railway.yml` 中的密码（或使用环境变量 `${DB_PASSWORD}` 引用）。
- 定期备份数据库：可使用 Railway CLI 或 pg_dump 导出。
- 监控 Railway 免费额度，避免超额。

---

**文档版本**：v1.0  
**最后更新**：2026-05-09  
**适合项目阶段**：已完成首次成功部署，可供后续维护和迭代参考。