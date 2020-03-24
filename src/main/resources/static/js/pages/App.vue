<template>
    <v-app>
        <v-app-bar app>
            <v-toolbar-title>Sarafan</v-toolbar-title>
            <v-spacer></v-spacer>
            <div v-if="profile">
                {{profile.name}}
                <v-btn icon href="/logout" >
                    <v-icon>mdi-logout-variant</v-icon>
                </v-btn>
            </div>

        </v-app-bar>

        <v-content>
            <v-container v-if="!profile">
                Необходимо авторизоваться через
                <a href="/login">Google</a>
            </v-container>
            <v-container v-if="profile">
                <messages-list  :messages="messages" ></messages-list>
            </v-container>


        </v-content>
    </v-app>


</template>

<script>
    import MessagesList from 'componets/messages/MessageList.vue'
    import { addHandler} from "util/ws"
    import { getIndex} from "util/collections"

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
        created(){
            addHandler(data =>{
                let index = getIndex(this.messages,data.id)
                if(index > -1){
                    this.messages.splice(index,1, data)
                }else{
                    this.messages.push(data)
                }
            })
        }
    }
</script>

<style>

</style>