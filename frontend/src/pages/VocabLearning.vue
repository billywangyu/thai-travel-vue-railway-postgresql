<template>
  <div>
    <HeroSection />
    <div class="container mx-auto px-4 py-10">
      <div v-if="store.loading" class="text-center py-20">加载中...</div>
      <template v-else>
        <!-- 总体进度 -->
        <div class="mb-8">
          <h2 class="text-2xl font-display font-bold mb-2">词汇学习</h2>
          <ProgressBar :value="learnedCount" :max="store.vocabItems.length" :showLabel="true" />
          <p class="text-sm text-muted-foreground mt-1">
            已学习 {{ learnedCount }} / {{ store.vocabItems.length }} 个单词
          </p>
        </div>

        <router-link to="/" class="inline-flex items-center gap-2 text-primary hover:underline mb-6">
          ← 返回首页
        </router-link>

        <div class="flex flex-col lg:flex-row gap-8">
          <!-- 侧边栏：按类别筛选 -->
          <aside class="lg:w-56 shrink-0">
            <h2 class="font-semibold text-lg mb-3">按类别筛选</h2>
            <div class="flex lg:flex-col gap-2 flex-wrap">
              <button
                @click="selectedCategory = null"
                :class="['px-4 py-2 rounded-full font-medium transition-all text-left',
                  !selectedCategory ? 'bg-primary text-white shadow-lg' : 'bg-secondary/30 hover:bg-secondary/50']"
              >
                全部
              </button>
              <button
                v-for="cat in store.categories"
                :key="cat.id"
                @click="selectedCategory = cat.id"
                :class="['px-4 py-2 rounded-full font-medium transition-all text-left',
                  selectedCategory === cat.id ? 'text-white shadow-lg' : 'bg-secondary/30 hover:bg-secondary/50']"
                :style="{ backgroundColor: selectedCategory === cat.id ? cat.color : undefined }"
              >
                {{ cat.name }}
              </button>
            </div>
          </aside>

          <!-- 卡片网格 -->
          <div class="flex-1">
            <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
              <VocabCard
                v-for="word in filteredVocab"
                :key="word.id"
                :word="word"
                @toggle="store.toggleVocabLearned(word.id)"
              />
            </div>
            <div v-if="filteredVocab.length === 0" class="text-center py-20 text-muted-foreground">
              该类别暂无单词
            </div>
          </div>
        </div>
      </template>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'
import { useTravelStore } from '@/stores/travel'
import HeroSection from '@/components/HeroSection.vue'
import ProgressBar from '@/components/ProgressBar.vue'
import VocabCard from '@/components/VocabCard.vue'

const store = useTravelStore()
const selectedCategory = ref<string | null>(null)

const learnedCount = computed(() => store.vocabItems.filter(v => v.learned).length)

const filteredVocab = computed(() => {
  if (!selectedCategory.value) return store.vocabItems
  return store.vocabItems.filter(v => v.category === selectedCategory.value)
})

onMounted(async () => {
  await Promise.all([store.fetchVocab(), store.fetchCategories()])
})
</script>