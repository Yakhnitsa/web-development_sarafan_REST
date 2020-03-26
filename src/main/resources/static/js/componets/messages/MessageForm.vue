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
    import {mapActions} from 'vuex'
    export default {
        props: ['messageAttr'],
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
            ...mapActions(['addMessageAction','updateMessageAction']),
            save() {
                const message = {
                    id: this.id,
                    text: this.text}
                if(this.id){
                    this.updateMessageAction(message);

                }
                else{
                    this.addMessageAction(message);
                }
                this.id = '';
                this.text = '';
            }
        }
    }


</script>

<style scoped>

</style>