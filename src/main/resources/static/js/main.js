var messageApi = Vue.resource('/message{/id}');

function getIndex(list, id){
    for(var i = 0; i < list.length; i++){
        if(list[i].id === id) return i;
    }
    return -1;
}


Vue.component('message-form', {
    template: '<div>' +
    '<input type="text" v-model="text" placeholder="Write message"/>' +
    '<input type="button" value="Save" @click="save"/>' +
    '</div>',
    props: ['messages', 'messageAttr'],
    data: function () {
        return {
            text: '',
            id: ''
        }
    },
    watch: {
        messageAttr: function (newVal, oldVal) {
            this.text = newVal.text;
            this.id = newVal.id;
        }
    },
    methods: {
        save() {
            var message = {text: this.text};
            if (this.id) {
                messageApi.update({id:this.id},message).then(
                    result => result.json().then(data =>{
                         var index = getIndex(this.messages, data.id);
                         this.messages.splice(index, 1, data);
                         this.id = '';
                         this.text = '';
                    })
                )
                // messageApi.update({id: this.id}, message).then(result =>{
                //     result.json().then(data => {
                //         var index = getIndex(this.messages, data.id);
                //         this.messages.splice(index, 1, data);
                //
                //     })
                // })
            }
            else {
                messageApi.save({},message).then(result => result.json().then(data =>{
                        this.messages.push(data);
                        this.text='';
                    })
                )
            //     messageApi.save({}, message).then(result = > {
            //         result.json().then(data = > {
            //         this.messages.push(data);
            //     this.text = '';
            // })
            // })
            }
        }
    }
});


Vue.component('message-row', {
    template: '<div>' +
        '<i>({{message.id}})</i>  {{message.text}}' +
        '<span style ="position:absolute; right:0">' +
            '<input type="button" value="Edit" @click="editMessage"/>' +
            '<input type="button" value="x" @click="deleteMessage"/>' +
        '</span>' +
    '</div>',
    props: ['messages','message','editMethod'],
    methods: {
        editMessage: function () {
            this.editMethod(this.message);
        },
        deleteMessage: function () {
            messageApi.remove({id: this.message.id}).then(result =>{
                if(result.ok){
                    this.messages.splice(this.messages.indexOf(this.message),1);
                }
            })
        }
    }
});


Vue.component('messages-list', {
    template: '<div style="position relative; with: 300px">' +
        '<message-form :messages="this.messages" :messageAttr="message"></message-form>' +
        '<message-row v-for="message in messages" :messages="messages"' +
            ':message="message" :key="message.id" :editMethod="editMethod"></message-row>' +
    '</div>',
    props: ['messages'],
    data: function () {
        return {
            message: null
        }
    },
    methods: {
        editMethod: function (message) {
            this.message=message;
        }
    }
});


var sarafan = new Vue({
    el: '#sarafan',
    template: '<messages-list :messages="messages"></messages-list>',
    data: {
        message: 'Hello user!',
        messages: [],
    },
    created: function () {
        messageApi.get().then(result => result.json().then(data =>{
            data.forEach(message => this.messages.push(message));
        }))
    //     messageApi.get().then(result = >
    //     result.json().then(data = >
    //     data.forEach(message = > this.messages.push(message)
    // )
    // )
    // )
    }
});