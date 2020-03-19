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
                // var message = {text: this.text};
                // if (this.id) {
                //     this.$resource('/message{/id}').update({id:this.id},message).then(
                //         result => result.json().then(data =>{
                //             var index = getIndex(this.messages, data.id);
                //             this.messages.splice(index, 1, data);
                //             this.id = '';
                //             this.text = '';
                //         })
                //     )
                // }
                // else {
                //     this.$resource('/message{/id}').save({},message).then(result => result.json().then(data =>{
                //             this.messages.push(data);
                //             this.text='';
                //         })
                //     )
                // }
            }
        }
    }


</script>

<style scoped>

</style>