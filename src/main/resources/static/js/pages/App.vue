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
                <messages-list></messages-list>
            </v-container>


        </v-content>
    </v-app>


</template>

<script>
    import MessagesList from 'componets/messages/MessageList.vue'
    import { addHandler} from "util/ws"
    import {mapState, mapMutations} from 'vuex'

    export default{
        components:{
            MessagesList
        },
        data: function() {
            return{

            }

        },
        // computed: {
        //     ...mapState(['profile'])
        // },
        computed: mapState(['profile']),
        methods: mapMutations(['addMessageMutation','updateMessageMutation','removeMessageMutation']),
        created(){
            addHandler(data =>{
                if(data.objectType == 'MESSAGE'){
                    switch(data.eventType){
                        case 'CREATE':
                            this.addMessageMutation(data.body)
                            break
                        case 'UPDATE':
                            this.updateMessageMutation(data.body)
                            break
                        case 'REMOVE':
                            this.removeMessageMutation(data.body)
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