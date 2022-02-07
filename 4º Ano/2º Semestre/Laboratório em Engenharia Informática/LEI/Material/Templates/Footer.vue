<template>
  <v-footer color="white">
    
    <v-row 
    justify="center" 
    > 

    <!-- Invocação de componentes importados-->

    <v-col 
    cols="12" 
    > 
      <div class="text-center"> 
          <small class="grey--text change-font text-none">©Nome do Projeto, Ano</small> 
      </div>
    </v-col>
    </v-row>
  
  
  </v-footer>
</template>

<script>
//importação de componentes

export default {
  components: {
      //componentes a serem invocados no footer
  }
}
</script>

<style scoped>/* lang="stylus" se não resultar */
/* css do footer */
.change-font {
    font-family: "Arial";
}
</style>