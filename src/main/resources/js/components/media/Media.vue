<template>
    <v-card>
        <v-flex v-if="type === 'href'" xs-12 sm6 offcet-sm3>
            <v-img
                v-if="message.linkCover"
                :src="message.linkCover"
                aspect-ratio="2.75"
            >
            </v-img>
            <v-cart-title>
                <div>
                    <h3>
                        <a :href="message.link">{{message.linkTitle || message.link}}</a>
                    </h3>
                    <div v-if="message.linkDescription">{{message.linkDescription}}</div>
                </div>

            </v-cart-title>

        </v-flex>
        <v-flex v-if="type === 'image'" xs-12 sm6 offcet-sm3>
            <a :href="message.link">
                <v-img
                        v-if="message.linkCover"
                        :src="message.linkCover"
                        aspect-ratio="2.75"></v-img>
                {{message.link}}
            </a>
        </v-flex>
        <v-flex v-if="type === 'youtube'" xs-12 sm6 offcet-sm3>
            <youtube :src="message.link"></youtube>
        </v-flex>
    </v-card>
</template>

<script>
    import Youtube from './Youtube.vue'
    export default {
        name: "Media",
        components: {Youtube},
        props:['message'],
        comments:{Youtube},
        data(){
            return{
                type: 'href'
            }
        },
        beforeMount() {
            if (this.message.link.indexOf('youtu') > -1) {
                this.type = 'youtube'
            } else if (this.message.link.match(/\.(jpeg|jpg|gif|png)$/) !== null) {
                this.type = 'image'
            } else {
                this.type = 'href'
            }
        }
    }
</script>

<style scoped>

</style>