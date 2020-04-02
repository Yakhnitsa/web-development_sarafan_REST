<template>
    <v-list flat>
        <v-subheader>Comments</v-subheader>
        <template v-for="(item,index) in comments">
            <v-divider v-if="index > 0" :key="index"></v-divider>
            <comment-item
                    :comment="item"
                    :key="'item ' + index"
            ></comment-item>
        </template>
        <comment-form :message-id="messageId"></comment-form>
    </v-list>
</template>

<script>
    import CommentItem from './CommentItem.vue'
    import CommentForm from "./CommentForm.vue";
    export default {
        name: "CommentList",
        props:['comments','message-id'],
        components:{CommentForm, CommentItem}

    }
</script>

<style scoped>

</style>