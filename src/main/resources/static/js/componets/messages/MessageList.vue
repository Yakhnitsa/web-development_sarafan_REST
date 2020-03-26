<template>
    <v-layout alight-space-around justify-start column>
        <message-form :messageAttr="message"></message-form>
        <message-row v-for="message in sortedMessages"
                     :message="message"
                     :key="message.id"
                     :editMessage="editMessage">
        </message-row>
    </v-layout>
</template>

<script>
    import MessageForm from 'componets/messages/MessageForm.vue'
    import MessageRow from 'componets/messages/MessageRow.vue'
    import { mapGetters } from 'vuex'

    export default{
        components:{
            MessageRow,
            MessageForm
        },
        props: [],
        data: function () {
            return {
                message: null
            }
        },
        computed: mapGetters(['sortedMessages']),
        // Альтернативная запись гееттеров
        // computed:{
        //     sortedMessages: function (){
        //         // console.log(this.$store.getters.sortedMessages)
        //         return this.$store.getters.sortedMessages
        //     }
        // },
        methods: {
            editMessage: function (message) {
                this.message=message;
            },
        },
        created: function(){
            // console.log(this.$store.getters.sortedMessages)
        }
    }
</script>

<style>

</style>