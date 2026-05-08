<template>
  <div>
    <HeroSection />
    <div class="container mx-auto px-4 py-10 max-w-3xl">
      <router-link to="/" class="inline-flex items-center gap-2 text-primary hover:underline mb-6">← 返回首页</router-link>
      <div class="bg-card rounded-2xl shadow-md border border-border overflow-hidden">
        <div class="h-[500px] overflow-y-auto p-6 space-y-4" ref="chatContainer">
          <div v-if="messages.length === 0" class="text-center py-20">
            <div class="text-5xl mb-4">💬</div>
            <p class="text-lg text-muted-foreground mb-2">开始对话，学习泰国旅行相关的英语表达！</p>
            <div class="text-sm text-muted-foreground space-y-1">
              <p>提示：您可以询问</p>
              <p class="italic">"How do I enter Thailand?"</p>
              <p class="italic">"What are popular attractions in Bangkok?"</p>
              <p class="italic">"How to say hello in Thai?"</p>
            </div>
          </div>
          <div v-for="msg in messages" :key="msg.id" :class="['flex', msg.role === 'user' ? 'justify-end' : 'justify-start']">
            <div :class="['max-w-[80%] rounded-2xl px-4 py-3 shadow-sm', msg.role === 'user' ? 'bg-primary text-white' : 'bg-card border border-border text-foreground']">
              <div class="whitespace-pre-wrap" v-html="msg.content"></div>
              <div class="text-xs opacity-50 mt-1">{{ formatTime(msg.timestamp) }}</div>
            </div>
          </div>
          <div v-if="isLoading" class="flex justify-start">
            <div class="bg-secondary/10 rounded-2xl px-4 py-3">
              <div class="flex items-center gap-2">
                <div class="w-2 h-2 bg-primary rounded-full animate-bounce" style="animation-delay:0ms"></div>
                <div class="w-2 h-2 bg-primary rounded-full animate-bounce" style="animation-delay:150ms"></div>
                <div class="w-2 h-2 bg-primary rounded-full animate-bounce" style="animation-delay:300ms"></div>
              </div>
            </div>
          </div>
        </div>
        <form @submit.prevent="sendMessage" class="border-t border-border p-4 flex gap-3">
          <input v-model="input" type="text" placeholder="输入您的问题或话题..." :disabled="isLoading" class="flex-1 px-4 py-3 bg-secondary/20 border border-border rounded-full focus:outline-none focus:ring-2 focus:ring-primary text-foreground" />
          <button type="submit" :disabled="isLoading || !input.trim()" class="px-6 py-3 bg-primary text-white rounded-full hover:bg-primary/90 disabled:opacity-50 transition-colors font-semibold">发送</button>
        </form>
      </div>
    </div>
  </div>
</template>
<script setup lang="ts">
import { ref, nextTick } from 'vue'
import HeroSection from '@/components/HeroSection.vue'
interface Message { id:string; role:'user'|'assistant'; content:string; timestamp:Date }
const messages = ref<Message[]>([])
const input = ref('')
const isLoading = ref(false)
const chatContainer = ref<HTMLElement>()
const scrollToBottom = async () => { await nextTick(); if(chatContainer.value) chatContainer.value.scrollTop = chatContainer.value.scrollHeight }
const formatTime = (d:Date) => d.toLocaleTimeString([],{hour:'2-digit',minute:'2-digit'})
const sendMessage = async () => {
  if(!input.value.trim()||isLoading.value)return
  const userMsg:Message = { id:Math.random().toString(36).slice(2), role:'user', content:input.value.trim(), timestamp:new Date() }
  messages.value.push(userMsg); input.value=''; isLoading.value=true; await scrollToBottom()
  try {
    await new Promise(r=>setTimeout(r,1000))
    messages.value.push({ id:Math.random().toString(36).slice(2), role:'assistant', content:"Here's some helpful travel information about Thailand:\n\n• Thailand welcomes over 30 million tourists annually\n• The best time to visit is November to April\n• Don't forget to try Pad Thai and Mango Sticky Rice!\n\nIs there anything specific you'd like to know about traveling in Thailand?", timestamp:new Date() })
  } catch {
    messages.value.push({ id:Math.random().toString(36).slice(2), role:'assistant', content:'抱歉，我无法处理您的请求。请稍后重试。', timestamp:new Date() })
  } finally { isLoading.value=false; await scrollToBottom() }
}
</script>
