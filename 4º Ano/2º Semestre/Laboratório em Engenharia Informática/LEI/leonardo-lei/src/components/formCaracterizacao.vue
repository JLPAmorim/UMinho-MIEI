<template>
  <form>
    <div class="form-control">
     <label>Identificador </label>
    <input type="text" required v-model="formData.id"> 
    </div>
    
    <div class="form-control">
    <label>Ciclo de Estudos </label>
    <input type="text" required v-model="formData.study_cycle">
    </div>

    <div class="form-control">
    <label>Escolaridade </label>
    <input type="text" required v-model="formData.scholarity">
    </div>

    <div class="form-control">
    <label>Domínio </label>
    <input type="text" required v-model="formData.domain">
    </div>

    <div class="form-control">
    <label>Subdomínio </label>
    <input type="text" required v-model="formData.subdomain">
    </div>

    <div class="form-control">
    <label>Subsubdomínio </label>
    <input type="text" required v-model="formData.subsubdomain">
    </div>

    <div class="form-control">  
    <label>Nível de dificuldade:  </label>
    <select v-model="formData.difficulty_level">
        <option value="1"> 1 </option>
        <option value="2"> 2 </option>
        <option value="3"> 3 </option>  
    </select>
    </div>

    <div class="form-control">
    <label>Autor </label>
    <input type="text" required v-model="formData.author">
    </div>

    <div>
      <input type="radio" value="G" v-model="formData.display_mode" >  
      <label> G</label>
      <input type="radio" value="I" v-model="formData.display_mode" >  
      <label> I</label>
    </div>

    <div class="form-control">    
    <label> Tempo de Resposta: </label>
    <select v-model="formData.answering_time">
        <option value="30"> 30 </option>
        <option value="45"> 45 </option>
        <option value="60"> 60 </option>  
    </select>
    </div>

    <div class="form-control">
    <label>Tipo de Questão:  </label>
    <select v-model="formData.type">
        <option value="1"> 1 </option>
        <option value="2"> 2 </option>
        <option value="3"> 3 </option>  
    </select>
    </div>

    <div class="form-control">
    <label>Precedências </label>
    <input type="text" required v-model="formData.precedence">
    </div>

    <div class="form-control">  
    <label>Repetições:  </label>
    <select v-model="formData.repetitions">
        <option value="1"> 1 </option>
        <option value="2"> 2 </option>
        <option value="3"> 3 </option>  
    </select>
    </div>

  </form>
</template>

<script>
export default {
  name: 'formCaracterizacao',
    data(){
        return{
          formData:{
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
            repetitions: ''
          }
            

        }
    },
    methods:{
      cleanCaracterizacao(){
        this.formData.id='',
        this.formData.study_cycle='',
        this.formData.scholarity='',
        this.formData.domain='',
        this.formData.subdomain='',
        this.formData.subsubdomain='',
        this.formData.difficulty_level='',
        this.formData.author='',
        this.formData.display_mode='',
        this.formData.answering_time='',
        this.formData.type='',
        this.formData.precedence=[],
        this.formData.repetitions=''
      }
    },
      watch: {
        formData: {
            handler: function() {
              this.$emit('newdataCaracterizacao', [this.formData.id,this.formData.study_cycle,this.formData.scholarity,this.formData.domain,
              this.formData.subdomain,this.formData.subsubdomain,this.formData.difficulty_level,this.formData.author,this.formData.display_mode,
              this.formData.answering_time,this.formData.type,this.formData.precedence,this.formData.repetitions]);
             
          },
            deep: true
        }
      }   
}
</script>

<style>

</style>