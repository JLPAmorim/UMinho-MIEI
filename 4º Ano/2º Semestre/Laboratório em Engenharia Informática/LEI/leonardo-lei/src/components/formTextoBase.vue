<template>
    <form>
        <div class="form-control">
            <p>Insira a Pergunta: </p>
            <textarea v-model="formData.header" cols="50" rows="5"></textarea> 
        </div>
    </form>
</template>

<script>
export default {
    data(){
        return{
            formData:{
                header:''
            }
        }
    },
    watch: {
        formData: {
            handler: function() {
              this.$emit('newdataTextoBase', this.formData.header);
          },
            deep: true
        }
      },
    
    methods:{
        cleanTextoBase(){
            this.formData.header=""
        }
    }
}
</script>

<style>

</style>