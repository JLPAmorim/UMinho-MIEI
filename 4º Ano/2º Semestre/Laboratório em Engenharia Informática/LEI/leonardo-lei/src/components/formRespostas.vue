<template>
  <form>
    <fieldset v-for="(resposta,index) in formData.body" :key="index">
         <div class="form-control">
        <label>Resposta: </label>
        <input type="text"  v-model="resposta.answer">
    </div>
    <div class="form-control">
      <label>Correto? </label>
      <input type="radio" value="0" v-model="resposta.correction" >  
      <label> 0</label>
      <input type="radio" value="1" v-model="resposta.correction" >  
      <label> 1</label>
    </div>

    <div class="form-control">
      <label>Mandatória? </label>
      <input type="radio" value="0" v-model="resposta.mandatory" >  
      <label> 0</label>
      <input type="radio" value="1" v-model="resposta.mandatory" >  
      <label> 1</label>
    </div>

    <div class="form-control">
      <label>Eliminativa? </label>
      <input type="radio" value="0" v-model="resposta.elimanative" >  
      <label> 0</label>
      <input type="radio" value="1" v-model="resposta.elimanative" >  
      <label> 1</label>
    </div>


    <div class="form-control">
    <label> Pontos: </label>
        <select v-model="resposta.points">
            <option value="1"> 1 </option>
            <option value="2"> 2 </option>
            <option value="3"> 3 </option>  
        </select>
    </div>
        <button @click="addResposta"> addResposta </button> {{formData.body}}
      </fieldset>
    
  </form>
</template>

<script>


export default {
    name: 'formRespostas',
    data(){
        return{
          formData:{
            body: [{answer: '',
            correction: '',
            mandatory: '',
            elimanative: '',
            points:''}]
          }
            
        }
    },
    methods:{
      addResposta(){
        this.formData.body.push({answer: '',
            correction: '',
            mandatory: '',
            elimanative: '',
            points:''})
            alert("inserida")
      }
    },
    watch: {
        formData: {
            handler: function() {
              this.$emit('newdataRespostas', this.formData.body);
          },
            deep: true
        }
      },
   

}
</script>

<style>

</style>