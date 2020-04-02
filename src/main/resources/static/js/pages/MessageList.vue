<template>
    <v-container>
        <message-form :messageAttr="message"></message-form>
        <v-container>

        </v-container>
        <v-layout alight-space-around justify-start column>
            <message-row v-for="message in sortedMessages"
                         :message="message"
                         :key="message.id"
                         :editMessage="editMessage">
            </message-row>

        </v-layout>
    </v-container>

</template>

<script>
    import MessageForm from '../components/messages/MessageForm.vue'
    import MessageRow from '../components/messages/MessageRow.vue'
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
        //Доступ к центральному хранилищу через mapGetters
        computed: mapGetters(['sortedMessages']),
        // Альтернативная запись гееттеров
        // computed:{
        //     sortedMessages: function (){
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