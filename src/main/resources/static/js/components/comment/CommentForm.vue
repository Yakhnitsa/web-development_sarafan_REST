<template>
    <v-layout row class="mx-1">
        <v-text-field
                label="add comment"
                v-model="text"
                @keyup.enter="save"
                placeholder="write your opinion"/>
        <v-btn @click="save">
            <v-icon>mdi-comment-arrow-right-outline</v-icon>
        </v-btn>
    </v-layout>
</template>

<script>
    import {mapActions} from 'vuex'
    export default {
        name: "CommentForm",
        data(){
            return {
                text:''
            }
        },
        props:['message-id'],
        methods:

            {
                ...mapActions(['addCommentAction']),
            async save(){
                await this.addCommentAction({
                    text: this.text,
                    message: {
                        id: this.messageId
                    }
                })
                this.text = ''

            }
        }

        ,
    }
</script>

<style scoped>

</style>