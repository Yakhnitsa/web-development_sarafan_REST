<template>
    <v-layout row class="mx-1">
        <v-text-field
                label="new message"
                v-model="text"
                placeholder="Write message"/>
        <v-btn @click="save">
            Save
        </v-btn>
    </v-layout>
</template>

<script>
    import messagesApi from 'api/messages'
    export default {
        props: ['messages', 'messageAttr'],
        data: function () {
            return {
                text: '',
                id: ''
            }
        },
        watch: {
            messageAttr: function (newVal, oldVal) {
                this.text = newVal.text;
                this.id = newVal.id;
            }
        },
        methods: {
            save() {
                const message = {
                    id: this.id,
                    text: this.text}
                if(this.id){
                    messagesApi.update(message).then(result => {
                        result.json().then(data =>{
                            const index = this.messages.findIndex(item => item.id === data.id);
                            this.messages.splice(index,1,data)
                        })
                    })

                }
                else{
                    messagesApi.add(message).then(result =>{
                        result.json().then(data =>{
                            const index = this.messages.findIndex(item => item.id === data.id);
                            if(index > -1){
                                this.messages.splice(index,1,data)
                            }
                            this.messages.push(data)
                        })
                    })
                }
                this.id = '';
                this.text = '';
            }
        }
    }


</script>

<style scoped>

</style>