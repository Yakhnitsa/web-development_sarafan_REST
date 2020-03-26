import Vue from 'vue';
import Vuex from 'vuex';
Vue.use(Vuex);
import messagesApi from 'api/messages'

// Централизованное хранилище Vuex для данных приложения
export default new Vuex.Store({
    // Состояние объекта, массивы и прочее
    state: {
        count: 0,
        messages: frontendData.messages,
        profile: frontendData.profile
    },
    // Изменяемые свойства объекта, computed properties, ets...
    getters:{
        sortedMessages: state => state.messages.sort((a,b) => -(a.id - b.id))
    },
    // Методы для изменения объектов приолжения
    mutations: {
        addMessageMutation(state,message){
            state.messages = [...state.messages,message]
        },
        updateMessageMutation(state,message){
            const updateIndex = state.messages.findIndex(item => item.id === message.id)

            const updatedMessages = state.messages;
            updatedMessages.splice(updateIndex,1,message)
            state.messages = [...updatedMessages]

            // state.messages = [
            //     ...state.messages.slice(0,updateIndex),
            //     message,
            //     ...state.messages.slice(updateIndex + 1)
            // ]


        },
        removeMessageMutation(state,message){
            const deleteIndex = state.messages.findIndex(item => item.id === message.id)
            if(deleteIndex >-1){
                state.messages = [
                    ...state.messages.slice(0,deleteIndex),
                    ...state.messages.slice(updateIndex + 1)
                ]
            }
        }

    },

    // Асинхронные запросы на изменение данных хранилища
    actions:{
        async addMessageAction({commit,state},message){
            const result = await messagesApi.add(message)
            const data = await result.json()
            const index = state.messages.findIndex(item => item.id === data.id);
            if(index > -1){
                commit('updateMessageMutation',data)
            }else{
                commit('addMessageMutation',data)
            }
        },
        async updateMessageAction({commit},message){
            const result = await messagesApi.update(message)
            const data = await result.json()
            commit('updateMessageMutation',data)
        },
        async removeMessageAction({commit},message){
            const result = await messagesApi.remove(message.id)
            if(result.ok){
                commit('removeMessageMutation',message)
            }
        },
    }
})