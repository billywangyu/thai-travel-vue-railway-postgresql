<template>
  <div class="group bg-card rounded-2xl shadow-md hover:shadow-xl transition-all duration-300 overflow-hidden border border-border hover:border-[#FFD54F] cursor-pointer" @click="flipped = !flipped">
    <div v-if="!flipped" class="p-6">
      <img v-if="card.image" :src="card.image" class="w-full h-48 object-cover rounded-xl mb-4" />
      <div class="flex items-center gap-2 mb-2">
        <span class="text-2xl">{{ card.icon }}</span>
        <span class="text-xs px-2 py-1 bg-primary/10 text-primary rounded-full">{{ catName }}</span>
      </div>
      <h3 class="text-xl font-display font-bold mb-1">{{ card.title }}</h3>
      <p class="text-sm text-muted-foreground mb-3">{{ card.subtitle }}</p>
      <p class="text-foreground leading-relaxed">{{ card.frontContent }}</p>
      <div class="flex flex-wrap gap-1 mt-4">
        <span v-for="(kp,i) in (card.keyPoints||'').split(',')" :key="i" class="text-xs px-2 py-1 bg-muted rounded-full">{{ kp }}</span>
      </div>
      <p class="text-xs text-center mt-4 text-muted-foreground">点击翻转查看详情</p>
    </div>
    <div v-else class="p-6">
      <h3 class="text-xl font-display font-bold mb-4 text-secondary">{{ card.title }}</h3>
      <div class="prose prose-sm max-w-none mb-6 whitespace-pre-line text-foreground" v-html="card.backContent"></div>
      <button @click.stop="$emit('toggle')" :class="['w-full py-3 rounded-xl font-semibold transition-colors', card.completed ? 'bg-green-500 text-white' : 'bg-primary text-white hover:bg-primary/90']">{{ card.completed ? '✓ 已完成' : '标记为已完成' }}</button>
    </div>
  </div>
</template>
<script setup lang="ts">import { ref, computed } from 'vue'
const props = defineProps<{card:any}>()
defineEmits(['toggle'])
const flipped = ref(false)
const catName = computed(()=>{const m:Record<string,string>={entry:'入境',entertainment:'娱乐',accommodation:'住宿',attractions:'景点',return:'回程'}; return m[props.card.category]})</script>
