<template>
    <div>
        <input type="text" v-model="text" placeholder="Write message"/>
        <input type="button" value="Save" @click="save"/>
    </div>
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