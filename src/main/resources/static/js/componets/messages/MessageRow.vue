<template>
    <v-layout row class="my-2"
           align-self>
        <v-badge color="blue" left inline :content="message.id">
            <v-icon>mdi-message-reply-text</v-icon>
        </v-badge>
        <i>{{message.text}}</i>
        <v-spacer/>
        <v-btn @click="edit" small text>
            <v-icon dark>mdi-pencil</v-icon>
        </v-btn>

        <v-btn @click="del" small text>
            <v-icon>mdi-delete-forever</v-icon>
        </v-btn>
    </v-layout>
</template>

<script>
    export default {
        props: ['messages','message','editMessage','deleteMessage'],
        methods: {
            edit: function () {
                this.editMessage(this.message);
            },
            del: function () {
                this.deleteMessage(this.message);
            }
        }
    }
</script>

<style scoped>
    .v-badge__badge {
        font-size: 10px;
    }
</style>