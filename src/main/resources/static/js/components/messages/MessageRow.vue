<template>

    <v-card class="d-inline-block ml-2">
        <v-container>
            <v-row justify="space-between">
                <v-col cols="auto">
                    <v-row
                            class="flex-column ma-1"
                            justify="center"
                    >
                        <v-col class="px-0">
                            <media v-if="message.link" :message="message"></media>
                        </v-col>
                        <v-col>
                            <user-link
                                    size="48"
                                    :user="message.author"></user-link>

                            <div>
                                {{ message.text }}
                            </div>

                        </v-col>


                    </v-row>
                </v-col>

                <v-col cols="auto" class="text-left pl-1">
                    <v-row
                            class="flex-column ma-0 fill-height"
                            justify="center"
                    >
                        <v-col class="px-0">
                            <v-badge left inline :content="message.id">
                                <!--<v-icon>mdi-message-outline</v-icon>-->
                            </v-badge>
                        </v-col>
                        <v-col class="px-0">
                            <v-btn value="Edit" @click="edit" :disabled="!isAuthor" small text rounded>
                                <v-icon>mdi-email-edit-outline</v-icon>
                            </v-btn>
                        </v-col>

                        <v-col class="px-0">
                            <v-btn :disabled="!isAuthor" @click="del" small text rounded>
                                <v-icon>mdi-delete-forever</v-icon>
                            </v-btn>
                        </v-col>
                    </v-row>
                </v-col>
            </v-row>
        </v-container>
        <comment-list
            :message-id="message.id"
            :comments="message.comments"
        ></comment-list>
    </v-card>

</template>

<script>
    import {mapActions} from 'vuex'
    import Media from "../media/Media.vue";
    import CommentList from "../comment/CommentList.vue";
    import UserLink from "components/UserLink.vue";
    export default {
        components: {UserLink, CommentList, Media},
        props: ['message','editMessage'],
        computed:{
            isAuthor(){
                return this.$store.state.profile.id === this.message.author.id
            }
        },
        methods: {
            ...mapActions(['removeMessageAction']),
            edit: function () {
                this.editMessage(this.message);
            },
            del: function () {
                this.removeMessageAction(this.message);
            }

        }
    }
</script>

<style scoped>
    .v-badge__badge {
        font-size: 10px;
    }
</style>