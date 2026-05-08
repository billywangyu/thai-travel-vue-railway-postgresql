import { createRouter, createWebHashHistory } from 'vue-router'
import Home from '@/pages/Home.vue'
import VocabLearning from '@/pages/VocabLearning.vue'   // 改这里
import EnglishCards from '@/pages/EnglishCards.vue'

const routes = [
  { path: '/', component: Home },
  { path: '/conversation', component: VocabLearning },   // 改这里
  { path: '/english-cards', component: EnglishCards }
]

export default createRouter({ history: createWebHashHistory(), routes })