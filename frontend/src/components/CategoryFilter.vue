<template>
  <div class="flex flex-wrap gap-3 mb-8">
    <button @click="$emit('update:modelValue',null)" :class="['px-6 py-2 rounded-full font-medium', !modelValue ? 'bg-primary text-white shadow-lg' : 'bg-card border hover:border-primary']">全部</button>
    <button v-for="cat in categories" :key="cat.id" @click="$emit('update:modelValue',cat.id)" :class="['px-6 py-2 rounded-full font-medium flex items-center gap-2', modelValue===cat.id ? 'text-white shadow-lg' : 'bg-card border hover:border-primary']" :style="{backgroundColor:modelValue===cat.id?cat.color:undefined}">{{ cat.name }}</button>
  </div>
</template>
<script setup lang="ts">defineProps<{modelValue:string|null;categories:any[]}>(); defineEmits(['update:modelValue'])</script>
