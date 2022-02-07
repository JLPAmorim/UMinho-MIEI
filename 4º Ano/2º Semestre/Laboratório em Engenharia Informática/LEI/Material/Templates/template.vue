<template>
    <div>
        <appHeader></appHeader>
        <div>
            <navDraw></navDraw>
        </div>
        <v-data-table
            :headers="headers"
            :items="items a serem representados na tabela"
            :items-per-page="15"
            :sort-by="["valor do parâmetro pelo qual se quer ordenar inicialmente"]"
            :search="search"     
        ><template v-slot:top>
            <v-toolbar flat color="white">
                <v-toolbar-title>Título da janela</v-toolbar-title>
                <v-divider
                class="mx-4"
                inset
                vertical
                ></v-divider>
                <v-spacer></v-spacer>

                <!-- Barra de Pesquisa -->
                <v-text-field
                  v-model="search"
                  append-icon="mdi-magnify"
                  label="Pesquisa"
                  single-line
                  hide-details
                  class="mr-5"
                ></v-text-field>
            </v-toolbar>
        </template>
        <!-- Definição dos headers -->
        <template v-slot:header.valor="{ header }">
            <label> {{header.text}} </label>
        </template>
        ...
        <template v-slot:item.options="{ item }">
            <v-icon
                small
                class="mr-2"
                @click="Função a ser executada quando se carrega neste icon"
            >
                icon MDI
            </v-icon>
        </template>
        </v-data-table>
    </div>
</template>

<script>
//imports dos outros componentes e axios para os pedidos
import axios from 'axios'
import Header from '../components/header.vue'
import NavDraw from '../components/navDraw.vue'
export default {
    data(){
        return{
            headers:[
                {
                    text: "Texto correspondente ao parâmetro a ser representado na tabela",
                    value: "valor"
                },
                ...
                ,
                {
                    text:`Opções`,
                    value:'options',
                    sortable: false
                }
            ],
            search:'',
            ...
        }
    },
    components:{
        //Onde são invocados os outros componentes chamados neste, como o appHeader ou a navDraw
    }
}
</script>
<style scoped>
    /* css para tornar a aparência mais similar ao template */
    .v-data-table /deep/ th{
        background-color:#4b779e;
    }
    .v-data-table /deep/ tr{
        color: #73879C;
        font-size: 13px;
    }
    .v-data-table /deep/ tr:nth-child(even){
        background-color: rgb(245, 245, 245);
    }
    #docs *{
        box-sizing: border-box;
    }
    #docs{
        margin: 20px auto;
        max-width: 1100px;
        margin-bottom: 80px;
    }
    label {
        color: white;
        padding: 3px;
        font-family: Arial, sans-serif;
        font-weight: bold;
        font-size: 15px;
    }
</style>
