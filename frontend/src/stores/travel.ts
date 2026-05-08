import { defineStore } from 'pinia'
import api from '@/api'
export const useTravelStore = defineStore('travel', {
  state: () => ({
    cards: [] as any[], categories: [] as any[], vocabItems: [] as any[],
    loading: false, selectedCategory: null as string|null
  }),
  getters: {
    completedCount(state) { return state.cards.filter((c:any)=>c.completed).length },
    filteredCards(state) { return state.selectedCategory ? state.cards.filter((c:any)=>c.category===state.selectedCategory) : state.cards },
    filteredVocab(state) { return state.selectedCategory ? state.vocabItems.filter((v:any)=>v.category===state.selectedCategory) : state.vocabItems }
  },
  actions: {
    async fetchCards() { this.loading=true; try{const r=await api.get('/cards'); this.cards=r.data}finally{this.loading=false} },
    async toggleCardCompleted(id:number){ await api.put(`/cards/${id}/toggle`); const i=this.cards.findIndex((c:any)=>c.id===id); if(i!==-1)this.cards[i].completed=!this.cards[i].completed },
    async fetchCategories(){ const r=await api.get('/categories'); this.categories=r.data },
    async fetchVocab(){ this.loading=true; try{const r=await api.get('/vocab'); this.vocabItems=r.data}finally{this.loading=false} },
    async toggleVocabLearned(id:number){ await api.put(`/vocab/${id}/toggle`); const i=this.vocabItems.findIndex((v:any)=>v.id===id); if(i!==-1)this.vocabItems[i].learned=!this.vocabItems[i].learned }
  }
})
