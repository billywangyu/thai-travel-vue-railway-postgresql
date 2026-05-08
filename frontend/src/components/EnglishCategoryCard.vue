<template>
  <div class="bg-card rounded-2xl p-6 shadow-sm border hover:border-primary cursor-pointer transition-all hover:shadow-md" @click="$emit('click')">
    <h3 class="text-xl font-display font-bold mb-2">{{ category.name }}</h3>
    <p class="text-sm text-muted-foreground mb-4">{{ category.description }}</p>
    <div class="flex justify-between text-sm mb-2"><span>共 {{ category.total }} 句</span><span>已掌握 {{ category.masteredCount || 0 }}</span></div>
    <ProgressBar :value="category.masteredCount||0" :max="category.total" />
  </div>
</template>
<script setup lang="ts">import ProgressBar from '@/components/ProgressBar.vue'; defineProps<{category:any}>(); defineEmits(['click'])</script>
