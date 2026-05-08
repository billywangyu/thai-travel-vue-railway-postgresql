<template>
  <div
    class="bg-card rounded-2xl shadow-md border overflow-hidden cursor-pointer min-h-[220px] flex flex-col justify-center items-center transition-transform duration-300"
    @click="$emit('flip')"
  >
    <!-- 正面：英文句子 -->
    <div v-if="!flipped" class="p-6 text-center">
      <p class="text-xl font-medium text-foreground">{{ word.en }}</p>
      <button @click.stop="playAudio" class="mt-4 text-sm text-primary hover:underline">
        🔊 朗读发音
      </button>
    </div>

    <!-- 背面：中文 + 操作按钮 -->
    <div v-else class="p-6 text-center bg-secondary/5">
      <p class="text-xl font-bold text-secondary">{{ word.cn }}</p>
      <div class="mt-6 flex gap-3 justify-center">
        <button
          @click.stop="$emit('master')"
          class="px-4 py-2 bg-primary text-white rounded-full text-sm font-semibold hover:bg-primary/90"
        >
          ✅ 已掌握
        </button>
        <button
          @click.stop="$emit('next')"
          class="px-4 py-2 border border-primary text-primary rounded-full text-sm font-semibold hover:bg-primary/10"
        >
          ➡️ 下一句
        </button>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
const props = defineProps<{
  word: { en: string; cn: string }
  flipped: boolean
}>()

defineEmits<{
  (e: 'flip'): void
  (e: 'master'): void
  (e: 'next'): void
}>()

const playAudio = () => {
  const utterance = new SpeechSynthesisUtterance(props.word.en)
  utterance.lang = 'en-US'
  utterance.rate = 0.85
  speechSynthesis.speak(utterance)
}
</script>