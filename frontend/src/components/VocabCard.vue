<template>
  <div
    class="group bg-card rounded-2xl shadow-md hover:shadow-xl transition-all duration-300 overflow-hidden border border-border hover:border-primary cursor-pointer"
    @click="flipped = !flipped"
  >
    <!-- 正面 -->
    <div v-if="!flipped" class="p-6 flex flex-col items-center justify-center min-h-[180px]">
      <h3 class="text-lg font-display font-bold mb-1">{{ word.word }}</h3>
      <p class="text-sm text-muted-foreground mb-1">{{ word.pronunciation }}</p>
      <span class="text-xs px-2 py-1 bg-primary/10 text-primary rounded-full mb-3">{{ word.partOfSpeech }}</span>
      <button
        @click.stop="playAudio"
        class="w-full py-2 bg-secondary/10 hover:bg-secondary/20 rounded-lg text-sm font-medium transition-colors flex items-center justify-center gap-2"
      >
        🔊 听发音
      </button>
      <p class="text-xs text-center mt-3 text-muted-foreground">点击翻转查看详情</p>
    </div>

    <!-- 背面 -->
    <div v-else class="p-6">
      <div class="mb-4">
        <span class="text-xs text-muted-foreground uppercase tracking-wide">中文</span>
        <p class="text-lg font-semibold text-foreground">{{ word.chineseTranslation }}</p>
      </div>
      <div class="mb-4">
        <span class="text-xs text-muted-foreground uppercase tracking-wide">定义</span>
        <p class="text-foreground leading-relaxed text-sm">{{ word.definition }}</p>
      </div>
      <div class="mb-4">
        <span class="text-xs text-muted-foreground uppercase tracking-wide">例句</span>
        <p class="text-foreground italic leading-relaxed text-sm">{{ word.example }}</p>
      </div>
      <div class="mb-4">
        <span class="text-xs text-muted-foreground uppercase tracking-wide">难度</span>
        <div class="flex gap-1 mt-1">
          <span
            v-for="i in 3"
            :key="i"
            :class="['w-6 h-2 rounded-full', i <= difficultyLevel ? 'bg-primary' : 'bg-muted']"
          ></span>
        </div>
      </div>
      <button
        @click.stop="$emit('toggle')"
        :class="['w-full py-2 rounded-xl font-semibold text-sm transition-colors', word.learned ? 'bg-green-500 text-white' : 'bg-primary text-white hover:bg-primary/90']"
      >
        {{ word.learned ? '✓ 已学习' : '标记为已学习' }}
      </button>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed } from 'vue'

const props = defineProps<{ word: any }>()
defineEmits(['toggle'])

const flipped = ref(false)

const difficultyLevel = computed(() => {
  switch (props.word.difficulty) {
    case 'beginner': return 1
    case 'intermediate': return 2
    case 'advanced': return 3
    default: return 1
  }
})

const playAudio = () => {
  const utterance = new SpeechSynthesisUtterance(props.word.word)
  utterance.lang = 'en-US'
  utterance.rate = 0.85
  window.speechSynthesis.speak(utterance)
}
</script>