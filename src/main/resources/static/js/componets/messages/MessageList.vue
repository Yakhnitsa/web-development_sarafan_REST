<template>
    <v-layout alight-space-around justify-start column>
        <message-form :messages="this.messages" :messageAttr="message"></message-form>
        <message-row v-for="message in sortedMessages"
                     :messages="messages"
                     :message="message"
                     :key="message.id"
                     :editMessage="editMessage"
                     :deleteMessage="deleteMessage">
        </message-row>
    </v-layout>
</template>

<script>
    import MessageForm from 'componets/messages/MessageForm.vue'
    import MessageRow from 'componets/messages/MessageRow.vue'
    export default{
        components:{
            MessageRow,
            MessageForm
        },
        props: ['messages'],
        data: function () {
            return {
                message: null
            }
        },
        computed:{
            sortedMessages(){
                return this.messages.sort((a,b) => -(a.id - b.id))
            }
        },
        methods: {
            editMessage: function (message) {
                this.message=message;
            },
            deleteMessage: function (message) {
                this.$resource('/message{/id}').remove({id: message.id})
                    .then(result =>{
                        if(result.ok){
                            this.messages.splice(this.messages.indexOf(message),1);
                        }
                    })
            }
        }
    }
</script>

<style>

</style>