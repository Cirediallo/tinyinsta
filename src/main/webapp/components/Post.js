/**
 * 
 */
function post(){
	console.log("Post created: from Post component !");
	    let file = document.getElementById('postimage').files[0];
    let description = document.getElementById('postDescription').value;

    let form = new FormData();
    form.append("imagefile", file);
    form.append("description", description)
    form.append('userId', currentUser.googleId)
    m.request({
              method: "POST",
              url: "/createpost",
              body: form
          })
          .then(e => {
                if(e.status == "success") {
                    let data = JSON.parse(e.data)
                    let post = SimpleArticle(data)
                    Timeline.posts.push(post);
                    document.getElementById('newPost').remove();
                }
          });
}

const Post = {
	view: () => {
		/* class:"card col-12 d-none", id:"newPost" */
       return m('article', {id:"newPost"} [
				
                m('div', {class: 'card-header'},[
                    m('h6', {}, 'Post New Publication')
                ]),
                m('div', {class: 'card-body'},[
                    m("div", {class:"carousel slide", id:"postoutput"})
                ]),
                m('div', {class: 'card-footer'}, [
					/* class:"w-100", */
                    m('textarea', { id:"postDescription", placeholder: "write a description"}),
                    m('button', {class:'btn btn-secondary mt-1 float-right', onclick: post}, 'Publish')
                ])
           ]);

   }
}