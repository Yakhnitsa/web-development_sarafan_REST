<template>
    <router-link
        :to="`/user/${user.id}`"
        class="px-2"
    >
        <v-avatar v-if="user && user.userpic">
            <img
                    :src="user.userpic"
                    :size="`${size || 36}px`"
                    :alt="user.name"
            >
        </v-avatar>
        <v-avatar
                v-else
                :size="`${size || 36}px`"
                color="indigo">
            <v-icon dark>mdi-account-circle</v-icon>
        </v-avatar>

        <span class="pl-2">{{userName}} </span>


    </router-link>
</template>

<script>
    export default {
        name: "UserLink",
        props:['size','user'],
        computed:{
            userName(){
                return this.user ? this.user.name : 'unknown'
            }
        },
    }
</script>

<style scoped>

</style>