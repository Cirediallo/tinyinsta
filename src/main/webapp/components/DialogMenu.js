/**
 * 
 */
function deletePost(event) {
     m.request({
              method: "GET",
              url: API_BASE_URL + "/postdelete/:key",
              params: {
                'key': currentPostKey
              }
        })
        .then(response =>  {
            
            let post = PostDataSet.posts.find(e => {return e.key.name == currentPostKey})
            let postIndex = PostDataSet.posts.indexOf(post);
            PostDataSet.posts.splice(postIndex, 1);
            $('#actionOnPost').modal('hide')
        })
        .catch(e=>{"Unable to delete post"})
}

function updatePost(event) {
     let post = PostDataSet.posts.find(e => {return e.key.name == currentPostKey})
     post = convertKindToPost(post);
     let postIndex = PostDataSet.posts.indexOf(post);
     let postElement = document.getElementById('newPost');
     postElement.querySelector(".card-header h6").innerHTML = "Moodify the publication";
     postElement.querySelector(".card-footer button").innerHTML = "Modifier";
     postElement.querySelector("#postoutput").innerHTML = `<img src=${post.imageUrl} class="d-block w-100" alt="New post">`;
     let description = postElement.querySelector("#postDescription");
     description.value = post.description;
     description.focus()
     postElement.className = 'card col-12';
     document.body.scrollTop = 50; //px
     isEditPost = true;
     $('#actionOnPost').modal('hide')
}

const DialogMenu = {
   view: () => {
       return m("div", {class:"modal", tabindex:"-1", id:"actionOnPost"},[
                   m('div', {class:"modal-dialog", role:"document"}, [
                        m("div", {class:"modal-content"}, [
                            m("div", {class:"modal-header"},[
                                 m("button", {type:"button", class:"close", "data-dismiss":"modal", "aria-label":"Close"},[
                                    m.trust('<span aria-hidden="true">&times;</span>')
                                 ])
                            ]),
                            m('div',{class:"modal-body"}, [
                                m("ul", {class:"list-group"},[
                                   m("li", {class:"list-group-item", onclick: updatePost}, "Modify"),
                                   m("li", {class:"list-group-item", onclick:deletePost}, "Delete")
                                ])
                            ])
                        ])
                   ])

       ])
   }
} 