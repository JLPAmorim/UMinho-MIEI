<template>
    <div class="home" v-show="!isPreparing">
      <h1>Preparação de Questões</h1>
      <button @click="started"> Começar </button>
    </div>
    <div class="titulos" v-show="isPreparing">
      <h2>Produção de Questões</h2>
      <h3>Preparação de Questões</h3>
    </div>
    <div class="form" v-show="isPreparing" >
        <button @click="startedCaracterizacao" >Caracterização</button>
        <button @click="startedTextobase">Texto Base</button>
        <button @click="startedRespostas">Respostas </button>
        <button @click="startedSuporte">Suporte</button>
        <button @click="startedEdicao">Edição</button>
    <formCaracterizacao ref="ct" @newdataCaracterizacao="handleDataCaracterizacao($event)"  v-show="isCaracterizacao" />
    <formTextoBase ref="tb" @newdataTextoBase="handleDataTextoBase($event)" v-show="isTextoBase"/>
    <formRespostas ref="rp" @newdataRespostas="handleDataRespostas($event)" v-show="isRespostas"/>
    <formSuporte ref="sp" @newdataSuporte="handleDataSuporte($event)" v-show="isSuporte" />

      <i @click="mostraQuestao" class="far fa-save fa-2x"></i>


    
    <i @click="clean" class="fas fa-broom fa-2x"></i>

    <i class="fas fa-file-import fa-2x"></i>
    

    <i @click="showModal = true" class="fas fa-question-circle fa-2x"></i>
   
    <Modal v-if="showModal" @close="showModal = false"/>

    <i @click="backed" class="fas fa-door-open fa-2x"></i>
  
      
    </div> 
  
</template>

<script>
import formCaracterizacao from './components/formCaracterizacao'
import formTextoBase from './components/formTextoBase'
import formRespostas from './components/formRespostas'
import formSuporte from './components/formSuporte.vue'
import Modal from './components/Modal'

export default {
  name: 'App',
  components: {formCaracterizacao,formTextoBase,formRespostas,formSuporte,Modal},
  data(){
    return{
      isPreparing: false,
      isCaracterizacao: false,
      isTextoBase: false,
      isRespostas: false,
      isSuporte: false, 
      isEdicao: false,
      showModal: false,
      questao:{
        id: '',
        study_cycle: '',
        scholarity: '',
        domain: '',
        subdomain: '',
        subsubdomain: '',
        difficulty_level: '',
        author: '',
        display_mode: '',
        answering_time: '',
        type: '',
        precedence: [],
        repetitions: '',
        header:'',
        body: [],
        explanation: "",
        images: "",
        videos: "",
        source: "",
        notes: "",
        status:"E",
        inserted_by: "User_default",
        inserted_at:new Date().toLocaleString(),
        validated_by:"",
        validated_at:""
      }
    }
  },
  methods:{
    started(){
      this.isPreparing=true
      this.isCaracterizacao=true
      this.isTextoBase= false
      this.isRespostas= false
      this.isSuporte= false
      this.isEdicao= false
    },
    
    startedCaracterizacao(){
      this.isCaracterizacao= true
      this.isTextoBase= false
      this.isRespostas= false
      this.isSuporte= false
      this.isEdicao= false
    },
    startedTextobase(){
      this.isCaracterizacao= false
      this.isTextoBase= true
      this.isRespostas= false
      this.isSuporte= false
      this.isEdicao= false
    },
    startedRespostas(){
      this.isCaracterizacao= false
      this.isTextoBase= false
      this.isRespostas= true
      this.isSuporte= false
      this.isEdicao= false
    },
    startedSuporte(){
      this.isCaracterizacao= false
      this.isTextoBase= false
      this.isRespostas= false
      this.isSuporte= true
      this.isEdicao= false
    },
    startedEdicao(){
      this.isCaracterizacao= false
      this.isTextoBase= false
      this.isRespostas= false
      this.isSuporte= false
      this.isEdicao= true
    },
    backed(){
      alert("Tens a certeza que queres sair?")
      this.isPreparing= false
      this.isCaracterizacao= false
      this.isTextoBase= false
      this.isRespostas= false
      this.isSuporte= false
      this.isEdicao= false
    },
    handleDataTextoBase(e) {
      this.questao.header = e;
    },
    handleDataSuporte(e) {
      [this.questao.images,this.questao.videos] = e;
    },
    handleDataCaracterizacao(e) {
      [this.questao.id,this.questao.study_cycle,this.questao.scholarity,this.questao.domain,this.questao.subdomain,
      this.questao.subsubdomain,this.questao.difficulty_level,this.questao.author,this.questao.display_mode,
      this.questao.answering_time,this.questao.type,this.questao.precedence,this.questao.repetitions] = e;
    },
    handleDataRespostas(e) {
      this.questao.body = e;
    },
    mostraQuestao(){
      console.log(this.questao)
    },
    clean(){
      this.$refs.tb.cleanTextoBase();
      this.$refs.ct.cleanCaracterizacao();
      this.$refs.sp.cleanSuporte();
      //this.$refs.rp.cleanRespostas();
    }
  },
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}





</style>
