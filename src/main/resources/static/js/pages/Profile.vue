<template>
    <v-container>
        <v-flex :xs6="!$vuetify.breakpoint.xsOnly">
            <div class="title mb-3">User profile</div>
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
                    <v-flex>{{profile.email}}</v-flex>
                    <v-flex>{{profile.lastVisit}}</v-flex>
                </v-layout>
            </v-col>
        </v-row>
    </v-container>
</template>

<script>
    import {mapState} from 'vuex'
    export default {
        name: "Profile",
        computed: mapState(['profile'])
    }
</script>

<style scoped>
    img{
        max-width: 100%;
        height: auto;
    }
</style>