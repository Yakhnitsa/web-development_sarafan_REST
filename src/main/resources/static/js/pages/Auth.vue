<template>
    <v-container>
        Необходимо авторизоваться через
        <a href="/login">Google</a>
    </v-container>
</template>

<script>
    import {mapState} from 'vuex'
    export default {
        name: "Auth",
        // computed: mapState(['profile'])
    }
</script>

<style scoped>

</style>