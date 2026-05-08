import { defineStore } from 'pinia'
import api from '@/api'

export const useEnglishStore = defineStore('english', {
  state: () => ({
    categories: [] as any[],
    currentCategory: null as any | null,
    loading: false
  }),
  getters: {
    totalSentences(): number {
      return this.categories.reduce((s: number, c: any) => s + (c.total || 0), 0)
    },
    totalMastered(): number {
      return this.categories.reduce((s: number, c: any) => s + (c.masteredCount || 0), 0)
    },
    currentWords() {
      return this.currentCategory?.sentences || []
    }
  },
  actions: {
    async fetchCategories() {
      this.loading = true
      try {
        const res = await api.get('/english/categories')
        this.categories = res.data
      } finally {
        this.loading = false
      }
    },
    selectCategory(cat: any) {
      this.currentCategory = cat
    },
    clearCategory() {
      this.currentCategory = null
    },
    async markWordMastered() {
      if (!this.currentCategory) return
      if (this.currentCategory.masteredCount >= this.currentCategory.total) return
      const newCount = this.currentCategory.masteredCount + 1
      await api.put(`/english/categories/${this.currentCategory.id}/progress`, { masteredCount: newCount })
      this.currentCategory.masteredCount = newCount
      const idx = this.categories.findIndex((c: any) => c.id === this.currentCategory!.id)
      if (idx !== -1) this.categories[idx] = { ...this.categories[idx], masteredCount: newCount }
    }
  }
})