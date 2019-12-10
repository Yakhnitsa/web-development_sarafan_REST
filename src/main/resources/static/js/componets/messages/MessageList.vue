<template>
    <div style="position: relative; width: 300px">
        <message-form :messages="this.messages" :messageAttr="message"></message-form>
        <message-row v-for="message in messages"
                     :messages="messages"
                     :message="message"
                     :key="message.id"
                     :editMessage="editMessage"
                     :deleteMessage="deleteMessage">
        </message-row>
    </div>
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