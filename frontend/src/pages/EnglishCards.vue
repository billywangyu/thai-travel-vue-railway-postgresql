<template>
  <div>
    <HeroSection />
    <div class="container mx-auto px-4 py-12">
      <!-- 加载中 -->
      <div v-if="englishStore.loading" class="text-center py-20">加载中...</div>

      <!-- 分类网格 -->
      <template v-else-if="!englishStore.currentCategory">
        <div class="mb-12">
          <h2 class="text-3xl font-display font-bold mb-6">英语旅行单词卡</h2>
          <ProgressBar :value="englishStore.totalMastered" :max="englishStore.totalSentences" :showLabel="true" />
          <p class="text-sm text-muted-foreground mt-2 text-center">
            已掌握 {{ englishStore.totalMastered }} / {{ englishStore.totalSentences }} 句
          </p>
        </div>
        <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          <EnglishCategoryCard
            v-for="cat in englishStore.categories"
            :key="cat.id"
            :category="cat"
            @click="selectCategory(cat)"
          />
        </div>
        <div v-if="englishStore.categories.length === 0 && !englishStore.loading" class="text-center py-10 text-muted-foreground">
          暂无分类数据，请确认后端是否正常启动。
        </div>
      </template>

      <!-- 单词卡片浮层 -->
      <div
        v-if="englishStore.currentCategory"
        class="fixed inset-0 bg-black/50 z-40 flex items-center justify-center p-4"
        @click.self="englishStore.clearCategory()"
      >
        <div class="bg-card rounded-2xl shadow-xl border w-full max-w-2xl max-h-[90vh] overflow-y-auto p-6">
          <button @click="englishStore.clearCategory()" class="text-primary hover:underline mb-4 inline-block">
            ← 返回分类
          </button>
          <h2 class="text-2xl font-display font-bold mb-1">{{ englishStore.currentCategory.name }}</h2>
          <p class="text-muted-foreground mb-4">{{ englishStore.currentCategory.description }}</p>

          <!-- 展开介绍 -->
          <div class="mb-4">
            <button @click="showContent = !showContent" class="text-sm text-primary hover:underline">
              {{ showContent ? '收起介绍' : '展开旅行介绍' }}
            </button>
            <div v-if="showContent" class="mt-2 p-4 bg-secondary/5 rounded-xl border text-sm" v-html="englishStore.currentCategory.content"></div>
          </div>

          <!-- 当前分类进度 -->
          <div class="mb-4">
            <ProgressBar :value="englishStore.currentCategory.masteredCount || 0" :max="englishStore.currentCategory.total" :showLabel="true" />
            <p class="text-sm text-muted-foreground mt-1 text-center">
              已掌握 {{ englishStore.currentCategory.masteredCount || 0 }} / {{ englishStore.currentCategory.total }} 句
            </p>
          </div>

          <!-- 单词卡片 -->
          <EnglishWordCard
            v-if="currentWords.length > 0"
            :word="currentWords[currentIndex]"
            :flipped="flipped"
            @flip="flipped = !flipped"
            @master="handleMaster"
            @next="nextWord"
          />
          <div v-else class="text-center py-10 text-muted-foreground">
            该分类暂无单词
          </div>

          <div class="text-sm text-muted-foreground mt-2 text-center" v-if="currentWords.length > 0">
            {{ currentIndex + 1 }} / {{ currentWords.length }}
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted, watch } from 'vue'
import { useEnglishStore } from '@/stores/english'
import HeroSection from '@/components/HeroSection.vue'
import ProgressBar from '@/components/ProgressBar.vue'
import EnglishCategoryCard from '@/components/EnglishCategoryCard.vue'
import EnglishWordCard from '@/components/EnglishWordCard.vue'

const englishStore = useEnglishStore()
const currentIndex = ref(0)
const flipped = ref(false)
const showContent = ref(false)

const currentWords = computed(() => englishStore.currentWords)

const selectCategory = (cat: any) => {
  englishStore.selectCategory(cat)
  currentIndex.value = 0
  flipped.value = false
  showContent.value = false
}

// 监听分类切换
watch(() => englishStore.currentCategory, () => {
  currentIndex.value = 0
  flipped.value = false
  showContent.value = false
})

const nextWord = () => {
  if (currentWords.value.length === 0) return
  currentIndex.value = (currentIndex.value + 1) % currentWords.value.length
  flipped.value = false
}

const handleMaster = () => {
  englishStore.markWordMastered()
  nextWord()
}

onMounted(() => {
  englishStore.fetchCategories()
})
</script>