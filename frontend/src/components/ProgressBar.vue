<template>
  <div>
    <div class="flex justify-between mb-1" v-if="showLabel"><span class="text-sm font-medium">{{ percent }}%</span></div>
    <div class="w-full bg-muted rounded-full h-3 overflow-hidden">
      <div class="h-full bg-gradient-to-r from-primary to-accent rounded-full transition-all duration-700 ease-out" :style="{width:percent+'%'}"></div>
    </div>
  </div>
</template>
<script setup lang="ts">import { computed } from 'vue'
const props = withDefaults(defineProps<{value:number;max:number;showLabel?:boolean}>(),{showLabel:false})
const percent = computed(()=> props.max ? Math.round((props.value/props.max)*100) : 0)</script>
