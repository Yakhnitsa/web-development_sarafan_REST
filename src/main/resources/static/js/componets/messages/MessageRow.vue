<template>
    <div>
        <i>({{message.id}})</i>  {{message.text}}
        <span style ="position:absolute; right:0">
            <input type="button" value="Edit" @click="edit"/>
            <input type="button" value="x" @click="del"/>
        </span>
    </div>
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

</style>