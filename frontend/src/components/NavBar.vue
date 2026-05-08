<template>
  <nav class="fixed top-0 left-0 right-0 z-50 bg-transparent">
    <div class="container mx-auto px-4 flex items-center justify-between h-16">
      <router-link to="/" class="flex items-center gap-3">
        <span class="text-2xl">🇹🇭</span>
        <span class="text-xl font-bold text-white">泰国旅行攻略</span>
      </router-link>
      <div class="hidden md:flex gap-6">
        <router-link to="/" class="text-white/80 hover:text-primary font-medium">探索攻略</router-link>
        <router-link to="/english-cards" class="text-white/80 hover:text-primary font-medium">单词学习</router-link>
        <router-link to="/conversation" class="text-white/80 hover:text-accent font-medium">
  词汇学习
</router-link>
      </div>
    </div>
  </nav>
</template>
