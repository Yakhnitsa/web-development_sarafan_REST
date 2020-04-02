<template>
    <v-list-item>
        <v-list-item-icon>
            <v-icon></v-icon>
        </v-list-item-icon>
        <v-list-item-content>
            <v-list-item-title v-text="comment.text">{{comment.text}}</v-list-item-title>
        </v-list-item-content>
    </v-list-item>
</template>

<script>
    export default {
        name: "CommentItem",
        props:['comment']
    }
</script>

<style scoped>

</style>