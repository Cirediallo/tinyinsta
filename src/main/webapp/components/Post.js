/**
 * 
 */
var isEditPost = false;

function post(){
	console.log("Post created: from Post component !");
	    let files = document.getElementById('postimage').files;
	let file = 'nochange';
	if(files.length != 0){
		file = document.getElementById('postDescription').value;
	}
    let description = document.getElementById('postDescription').value;

    let form = new FormData();
    form.append("imagefile", file);
    form.append("description", description)

    if(isEditPost){
        form.append('postKey', currentPostKey)
         
            m.request({
                      method: "PUT",
                      url: "/createpost",
                      body: form
                  })
                  .then(e => {
                        if(e.status == "success") {
                            let post = PostDataSet.posts.find(e => {return e.key.name == currentPostKey})
                            let postIndex = PostDataSet.posts.indexOf(post);
                            let dom = document.getElementById('newPost');
                            dom.className = "card col-12 d-none"
                            isEditPost = false;
                            $('#messageDialog p').text("Post modified sucessfully !");
                            $('#messageDialog').modal('show');
                            m.route.set('/home')
                        }
                  });
        return;
    }


    form.append('userId', currentUser.googleId)
    m.request({
              method: "POST",
              url: "/createpost",
              body: form
          })
          .then(e => {
                if(e.status == "success") {
                    let post = PostDataSet.posts.find(e => {return e.key.name == currentPostKey})
                            let postIndex = PostDataSet.posts.indexOf(post);
                            PostDataSet.posts[postIndex].properties.description = description
                            let data = JSON.parse(e.data);
                            PostDataSet.posts[postIndex].properties.imageUrl = data.properties.imageUrl
                            PostDataSet.posts[postIndex].properties.imageName = data.properties.imageName

                            let dom = document.getElementById('newPost');
                            dom.className = "card col-12 d-none"
                            isEditPost = false;
                            $('#messageDialog p').text("Post modified successfully !");
                            $('#messageDialog').modal('show');
                            
                            m.redraw();
                }
          });
}

function onPostImageClick(event){
	if(isEditPost){
		let input = document.querySlector('post-icon input[type="file"]');
		input.click();
	}
}
const Post = {
	view: () => {
		/* class:"card col-12 d-none", id:"newPost" */
       return m('article', {class:"card col-12 d-none", id:"newPost"} [
				
                m('div', {class: 'card-header'},[
                    m('h6', {}, 'Post New Publication')
                ]),
                m('div', {class: 'card-body'},[
                    m("div", {class:"carousel slide", id:"postoutput", onclick: onPostImageClick})
                ]),
                m('div', {class: 'card-footer'}, [
					/* class:"w-100", */
                    m('textarea', {class:"w-100", id:"postDescription", placeholder: "write a description"}),
                    m('button', {class:'btn btn-secondary mt-1 float-right', onclick: post}, 'Publish')
                ])
           ]);

   }
}