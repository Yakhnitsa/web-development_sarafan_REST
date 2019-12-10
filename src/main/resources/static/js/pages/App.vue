<template>
    <div>
        <div v-if="!profile">Необходимо авторизоваться через <a href="/login">Google</a></div>
        <div v-else>
            <div>{{profile.name}} &nbsp;<a href="/logout">Выйти</a></div>
            <messages-list  :messages="messages" ></messages-list>
        </div>
    </div>


</template>

<script>
    import MessagesList from 'componets/messages/MessageList.vue'
    export default{
        components:{
            MessagesList
        },
        data: function() {
            return{
                message: 'Hello user!',
                messages: frontendData.messages,
                profile: frontendData.profile
            }

        },
    }
</script>

<style>

</style>