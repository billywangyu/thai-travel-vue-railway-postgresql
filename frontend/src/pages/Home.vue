<template>
  <div>
    <HeroSection />
    <div class="container mx-auto px-4 py-12">
      <div v-if="store.loading" class="text-center py-20 text-white">⏳ 加载数据中...</div>
      <template v-else-if="store.cards && store.cards.length > 0">
        <div class="mb-12">
          <h2 class="text-3xl font-display font-bold mb-6 text-foreground">学习进度</h2>
          <ProgressBar :value="store.completedCount" :max="store.cards.length" :showLabel="true" />
          <div class="grid grid-cols-3 gap-4 mt-6 text-center">
            <div class="bg-card rounded-2xl p-6 shadow-sm border">
              <div class="text-3xl font-bold text-primary">{{ store.completedCount }}</div>
              <div class="text-sm text-muted-foreground mt-1">已完成</div>
            </div>
            <div class="bg-card rounded-2xl p-6 shadow-sm border">
              <div class="text-3xl font-bold text-accent">{{ store.cards.length - store.completedCount }}</div>
              <div class="text-sm text-muted-foreground mt-1">待完成</div>
            </div>
            <div class="bg-card rounded-2xl p-6 shadow-sm border">
              <div class="text-3xl font-bold text-secondary">{{ store.cards.length }}</div>
              <div class="text-sm text-muted-foreground mt-1">总计</div>
            </div>
          </div>
        </div>
        <CategoryFilter v-model="store.selectedCategory" :categories="store.categories" />
        <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8 mb-16">
          <TravelCard v-for="card in store.filteredCards" :key="card.id" :card="card" @toggle="store.toggleCardCompleted(card.id)" />
        </div>
        <div class="grid grid-cols-1 md:grid-cols-2 gap-8 mt-16">
          <router-link to="/english-cards" class="group bg-card rounded-3xl p-10 shadow-md hover:shadow-2xl transition-all border-2 border-border hover:border-primary">
            <div class="text-5xl mb-4">📖</div>
            <h3 class="text-2xl font-display font-bold mb-3 group-hover:text-primary">英语旅行单词卡</h3>
            <p class="text-muted-foreground">学习泰国旅行高频句子和词汇。</p>
            <span class="inline-block mt-6 text-primary font-semibold text-lg">开始学习 →</span>
          </router-link>
          <router-link to="/conversation" class="group bg-card rounded-3xl p-10 shadow-md hover:shadow-2xl transition-all border-2 border-border hover:border-accent">
            <div class="text-5xl mb-4">📚</div>
            <h3 class="text-2xl font-display font-bold mb-3 group-hover:text-accent">词汇学习</h3>
            <p class="text-muted-foreground">500+ 旅行高频词汇，翻转卡片学习。</p>
            <span class="inline-block mt-6 text-accent font-semibold text-lg">开始学习 →</span>
          </router-link>
        </div>

        <!-- 🔄 刷新数据按钮 -->
        <div class="text-center mt-10">
          <button
            @click="reloadData"
            class="px-6 py-3 bg-secondary/20 hover:bg-secondary/30 rounded-full text-sm font-medium transition-all"
          >
            🔄 从 JSON 文件刷新全部数据
          </button>
        </div>
      </template>
      <div v-else class="text-center py-20 text-muted-foreground">
        ❌ 未加载到卡片数据，请确认后端是否正常运行
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { onMounted } from 'vue'
import { useTravelStore } from '@/stores/travel'
import api from '@/api'    // 引入api实例，用于调用管理接口
import HeroSection from '@/components/HeroSection.vue'
import TravelCard from '@/components/TravelCard.vue'
import ProgressBar from '@/components/ProgressBar.vue'
import CategoryFilter from '@/components/CategoryFilter.vue'

const store = useTravelStore()

onMounted(async () => {
  try {
    await store.fetchCards()
    store.fetchCategories()
  } catch (e) { console.error('加载首页数据失败', e) }
})

const reloadData = async () => {
  if (!confirm('确定要从 JSON 文件重新加载所有数据吗？这会覆盖当前的学习进度。')) return
  try {
    await api.post('/admin/reload')
    alert('数据已刷新，即将重新加载页面')
    window.location.reload()
  } catch (e) {
    alert('刷新失败，请检查后端 AdminController 是否已部署')
    console.error(e)
  }
}
</script>