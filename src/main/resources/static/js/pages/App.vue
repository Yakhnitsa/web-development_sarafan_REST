<template>
    <v-app>
        <v-app-bar app>
            <v-toolbar-title>Sarafan</v-toolbar-title>
            <v-btn text class="mx-2"
                   :disabled="$route.path === '/'"
                   @click="showMessages"
                   v-if="profile">Messages</v-btn>
            <v-spacer></v-spacer>
            <v-btn text
                    @click="showProfile"
                    :disabled="$route.path === '/user'"
                    v-if="profile">
                {{profile.name}}
            </v-btn>
            <v-btn icon
                   v-if="profile"
                   href="/logout" >
                <v-icon>mdi-logout-variant</v-icon>
            </v-btn>

        </v-app-bar>

        <v-content>
            <router-view></router-view>
        </v-content>
    </v-app>


</template>

<script>
    import { addHandler} from "util/ws"
    import {mapState, mapMutations} from 'vuex'

    export default{
        data: function() {
            return{

            }

        },

        computed: mapState(['profile']),

        methods:{
            ...mapMutations(['addMessageMutation',
                'updateMessageMutation',
                'removeMessageMutation',
                'addCommentMutation'
            ]),
            showMessages(){
                this.$router.push('/')
            },
            showProfile(){
                this.$router.push('/user')
            }
        },

        created(){
            console.log('created')
            addHandler(data =>{
                if(data.objectType == 'MESSAGE'){
                    console.log(data)
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
                } else if(data.objectType == 'COMMENT'){
                    switch(data.eventType){
                        case 'CREATE':
                            this.addCommentMutation(data.body)
                            break
                        default:
                            console.error('It seems event type is undefined ${data.eventType}')

                    }
                }
                else{
                    console.error('Oops, objectType is unknown ${data.objectType}')
                }
            })
        },
        beforeMount(){
            if(!this.profile){
                this.$router.replace('/auth')
            }
        }
    }
</script>

<style>

</style>