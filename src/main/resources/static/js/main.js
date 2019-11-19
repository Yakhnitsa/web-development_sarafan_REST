var messageApi = Vue.resource('/message{/id}');


Vue.component('message-row',{
   template:'<div><i>({{message.id}})</i>  {{message.text}}</div>',
   props:['message']
});


Vue.component('messages-list', {
    template:'<div >' +
                '<message-row v-for="message in messages" :message="message" :key="message.id"></message-row>' +
            '</div>',
    props:['messages'],
});


var sarafan = new Vue({
    el: '#sarafan',
    template:'<messages-list :messages="messages"></messages-list>',
    data:{
        message: 'Hello user!',
        messages:[],
    },
    created: function(){
        messageApi.get().then(result =>
            result.json().then( data =>
                data.forEach(message => this.messages.push(message))
            )
        )
    }
})