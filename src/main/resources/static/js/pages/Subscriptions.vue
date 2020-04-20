<template>
    <v-container>
        <v-layout justify-space-around>
            <v-list flat>
                <v-list-item v-for="item in subscriptions">
                    <user-link
                            :user="item.subscriber"
                            size="24"
                    ></user-link>
                    <v-btn
                        @click="changeSubscriptionStatus(item.subscriber.id)"
                    >{{item.active ? 'Dismiss' : 'Approve'}}</v-btn>
                    <v-list-item-content>
                    </v-list-item-content>
                </v-list-item>
            </v-list>
        </v-layout>
    </v-container>
</template>

<script>
    import profileApi from 'api/profile'
    import UserLink from "components/UserLink.vue";
    export default {
        name: "Subscriptions",
        components: {UserLink},
        data(){
            return {
                subscriptions: []

            }
        },
        methods:{
            async changeSubscriptionStatus(subscriberId){
                const response = await profileApi.changeSubscriptionStatus(subscriberId)

                const subscriptionIndex = this.subscriptions.findIndex(item =>
                    item.subscriber.id === subscriberId
                )

                const subscription = this.subscriptions[subscriptionIndex]
                subscription['active'] = !subscription.active

                this.subscriptions.splice(subscriptionIndex,1,subscription)

                // this.subscriptions = [
                //     ...this.subscriptions.slice(0,subscriptionIndex),
                //     {
                //         ...subscription, active: !subscription.active
                //     },
                //     ...this.subscriptions.slice(subscriptionIndex + 1)
                // ]

            },

        },
        async beforeMount(){
            const response = await profileApi.subscriberList(this.$store.state.profile.id)
            /* TODO  - попробовать сделать линк на страницу любого пользователя

            const response = await profileApi.subscriberList(this.$route.params.id)*/
            this.subscriptions = await response.json()

        }
    }
</script>

<style scoped>

</style>