<template>
    <v-container>

        <v-flex :xs6="!$vuetify.breakpoint.xsOnly">
            <div class="title mb-3">
                <span>User profile</span>
                <v-btn
                        v-if="!isMyProfile"
                        @click="changeSubscription"
                >
                    {{isISubscribed ? 'Unsubscribe' : 'Subscribe'}}

                </v-btn>
            </div>

        </v-flex>
        <v-row no-gutters>
            <v-col>
                <v-img :src="profile.userpic"></v-img>
            </v-col>
            <v-col>
                <v-layout column>
                    <v-flex>{{profile.name}}</v-flex>
                    <v-flex>{{profile.locale}}</v-flex>
                    <v-flex>{{profile.gender}}</v-flex>
                    <!--<v-flex>{{profile.email}}</v-flex>-->
                    <v-flex>{{profile.lastVisit}}</v-flex>
                    <v-flex>{{profile.subscriptions && profile.subscriptions.length}} subscriptions</v-flex>
                    <v-flex>{{profile.subscribers && profile.subscribers.length}} subscribers</v-flex>
                </v-layout>

            </v-col>
        </v-row>
    </v-container>
</template>

<script>
    import profileApi from 'api/profile'
    export default {
        name: "Profile",
        data(){
            return {
                profile: {}
            }
        },
        computed: {
            isMyProfile(){
                return !this.$route.params.id ||
                    this.$route.params.id === this.$store.state.profile.id
            },
            isISubscribed(){
                // console.log(this.profile.id)
                // const notEmpty = this.profile.subscribers
                // const contains =  this.$store.state.profile.subscriptions.find(subscription =>{
                //         let param = subscription.channel.id === this.profile.id
                //         return param
                //     }
                // )

                return this.profile.subscribers &&
                    this.$store.state.profile.subscriptions.find(subscription =>{
                            return subscription.channel.id === this.profile.id
                        }
                    )

                // this.profile.subscribers &&
                //     this.profile.subscribers.find( subscription =>{
                //         return subscription.subscriber.id === this.$store.state.profile.id
                //     })
            }
        },
        watch:{
            '$route'(){
                this.updateProfile()
            }
        },
        methods:{
            async changeSubscription(){
                const data = await profileApi.changeSubscription(this.profile.id);
                this.profile = await data.json();
            },
            async updateProfile(){
                const id = this.$route.params.id || this.$store.state.profile.id
                const data = await profileApi.get(id)
                this.profile = await data.json()

                this.$forceUpdate()
            }
        },
        beforeMount(){
            this.updateProfile()
        }
    }
</script>

<style scoped>
    img{
        max-width: 100%;
        height: auto;
    }
</style>