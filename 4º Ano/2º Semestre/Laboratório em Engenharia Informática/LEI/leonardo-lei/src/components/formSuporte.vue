<template>
  <form>
      <fieldset class="image">
          <p>Imagens</p>
          <input 
          type="file"
          @change="onImageSelected">
          <button @click="onUploadImage"> Carregar imagem </button>
          <div id="preview">
            <img v-if="url" :src="url" />
         </div>
      </fieldset>
        
    <fieldset class="video">
        <p>Videos</p>
        <input 
        type="file"
        @change="onVideoSelected">
        <button @click="onUploadVideo"> Carregar video </button>
        

    </fieldset>
    



    

    
  </form>
</template>

<script>

import axios from 'axios'
export default {
    name:'formSuporte',
    data(){
        return{
            formData:{
             images: '',
            videos: ''
            },
            url: null,
        }
    },
    methods:{
    onImageSelected(event){
            this.formData.images=event.target.files[0]
            this.url = URL.createObjectURL(this.formData.images);
        },
    onVideoSelected(event){
            if(event.target.files[0]){
                this.formData.videos=event.target.files[0]
            } else {
                this.formData.videos=event.target.files[1]
            }
        },
    onUploadImage(){
        //const fd = new FormData();
        //fd.append('image',this.images,this.images.name)
        //axios.post('',fd)
        
    },
    onUploadVideo(){
        //const fd = new FormData();
        //fd.append('video',this.videos,this.videos.name)
        //axios.post('',fd)
        
    },
    cleanSuporte(){
        this.formData.images='',
        this.formData.videos='',
        this.url=''
    }
    },
    watch: {
        formData: {
            handler: function() {
              this.$emit('newdataSuporte', [this.images,this.videos]);
          },
            deep: true
        }
      } 
}
</script>

<style>



</style>