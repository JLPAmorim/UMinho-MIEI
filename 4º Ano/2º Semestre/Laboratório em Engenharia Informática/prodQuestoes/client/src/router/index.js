import Vue from 'vue'
import VueRouter from 'vue-router'
import Dominios from '../views/PrepDominios.vue'
import ProdQuestao from '../views/ProdQuestao.vue'
import ProdDominio from '../views/ProdDominio.vue'
import Questoes from '../views/PrepQuestoes.vue'


Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Questoes
  },
  {
    path: '/prepDominios',
    name: 'PrepDominios',
    component: Dominios
  },
  {
    path: '/prodQuestao',
    name: 'ProdQuestao',
    component: ProdQuestao
  },
  {
    path: '/prodDominio',
    name: 'ProdDominio',
    component: ProdDominio
  },
  {
    path: '/prepQuestoes',
    name: 'PrepQuestoes',
    component: Questoes
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
