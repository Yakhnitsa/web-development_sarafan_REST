import Vue from 'vue';
import Vuex from 'vuex';
Vue.use(Vuex);
import messagesApi from '../api/messages'
import commentsApi from '../api/comments'

// Централизованное хранилище Vuex для данных приложения
export default new Vuex.Store({
    // Состояние объекта, массивы и прочее
    state: {
        profile,
        messages: messages,
        ...frontendData
    },
    // Изменяемые свойства объекта, computed properties, ets...
    getters:{
        sortedMessages: state => (state.messages || []).sort((a,b) => -(a.id - b.id))
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
        },
        removeMessageMutation(state,message){
            const deleteIndex = state.messages.findIndex(item => item.id === message.id)
            if(deleteIndex >-1){
                state.messages = [
                    ...state.messages.slice(0,deleteIndex),
                    ...state.messages.slice(updateIndex + 1)
                ]
            }
        },
        addCommentMutation(state,comment){
            const updateIndex = state.messages.findIndex(item => item.id === comment.message.id)
            const message = state.messages[updateIndex]
            if(!message.comments.find(it => it.id === comment.id)){
                message.comments.push(comment);
                const updatedMessages = state.messages;
                updatedMessages.splice(updateIndex,1,message)
                state.messages = [...updatedMessages]
            }

        },
        addMessagePageMutation(state,messages){
            const targetMessages = state.messages
                .concat(messages)
                .reduce((res,val) =>{
                    res[val.id] = val
                    return res
                },{})
            state.messages = Object.values(targetMessages);
        },
        updateMessagePageMutation(state,messages){
            state.messages = [...messages]
        },
        updateTotalPagesMutation(state, totalPages){
            state.totalPages = totalPages
        },
        updateCurrentPageMutation(state,currentPage){
            state.currentPage = currentPage
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
        async addCommentAction({commit,state},comment){
            const response = await commentsApi.add(comment)
            const data = await response.json();
            commit('addCommentMutation',data)
        },
        async loadPageAction({commit,state}){
            const response = await messagesApi.page(state.currentPage +1)
            const data = await response.json()
            commit('addMessagePageMutation',data.messages)
            commit('updateTotalPagesMutation',data.totalPages)
            commit('updateCurrentPageMutation',Math.min(data.currentPage,data.totalPages))
        },
        async updatePageAction({commit,state}){
            const response = await messagesApi.page(0)
            const data = await response.json()
            commit('updateMessagePageMutation',data.messages)
            commit('updateTotalPagesMutation',data.totalPages)
            commit('updateCurrentPageMutation',0)
        }
    }
})