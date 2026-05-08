# 🇹🇭 泰国旅行全攻略 · 全栈卡片网站

基于 Vue 3 + Spring Boot + PostgreSQL 的泰国旅行攻略卡片网站，提供旅行攻略卡片、英语旅行单词卡、AI 模拟对话等功能。

## ✨ 功能
- 🗺️ 旅行攻略卡片（翻转查看详情、分类筛选、进度追踪）
- 📖 英语旅行单词卡（分类学习、发音朗读、掌握标记）
- 💬 AI 对话学习（模拟英语对话，可接入真实 AI API）
- 🎨 泰式热情暖色主题

## 🛠️ 技术栈
- 前端：Vue 3 + Vite + Pinia + Tailwind CSS
- 后端：Spring Boot 2.7 + Spring Data JPA + PostgreSQL
- 部署：GitHub Pages（前端）, Railway（后端 + PostgreSQL）

## 🚀 本地运行
1. 后端：`cd backend && mvn spring-boot:run`（确保本地 PostgreSQL 运行并配置好连接）
2. 前端：`cd frontend && npm install && npm run dev`

## 📦 部署
- 后端推送到 Railway，并绑定 PostgreSQL 数据库
- 前端推送到 GitHub main 分支，Actions 自动部署到 GitHub Pages

查看完整文档请访问 [在线网站](https://billywangyu.github.io/thai-travel-vue-railway-postgresql/).
