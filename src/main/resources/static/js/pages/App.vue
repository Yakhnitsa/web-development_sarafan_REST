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
                if(data.objectType == 'MESSAGE'){
                    const index = this.messages.findIndex(item => item.id === data.body.id)
                    switch(data.eventType){
                        case 'CREATE':
                        case 'UPDATE':
                            if(index > -1){
                                this.messages.splice(index,1,data.body)
                            }else{
                                this.messages.push(data.body)
                            }
                            break
                        case 'REMOVE':
                            this.messages.splice(index,1)
                            break
                        default:
                            console.error('It seems event type is undefined ${data.eventType}')

                    }
                } else{
                    console.error('Oops, objectType is unknown ${data.objectType}')
                }
            })
        }
    }
</script>

<style>

</style>