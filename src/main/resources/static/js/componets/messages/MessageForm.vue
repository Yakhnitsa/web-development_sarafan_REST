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
    import { sendMessage } from 'util/ws'
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
                sendMessage({id: this.id, text: this.text})
                this.id = '';
                this.text = '';
            }
        }
    }


</script>

<style scoped>

</style>